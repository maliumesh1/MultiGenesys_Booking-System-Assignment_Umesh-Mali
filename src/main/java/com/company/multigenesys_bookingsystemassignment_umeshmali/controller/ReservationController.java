package com.company.multigenesys_bookingsystemassignment_umeshmali.controller;


import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.Reservation;
import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.ReservationStatus;
import com.company.multigenesys_bookingsystemassignment_umeshmali.exception.ResourceNotFoundException;
import com.company.multigenesys_bookingsystemassignment_umeshmali.service.ReservationService;
import org.springframework.data.domain.*;
        import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) { this.service = service; }

    // LIST
    @GetMapping
    public Page<Reservation> list(
            @RequestParam(name="status", required = false) ReservationStatus status,
            @RequestParam(name="minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name="maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "20") int size,
            @RequestParam(name="sort", required = false) String sort,
            Principal principal // injected from SecurityContext (the JwtAuthenticationFilter put a Map principal)
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Long userId = null;
        boolean isAdmin = requestIsAdmin();
        if (!isAdmin) {
            Map<?,?> p = (Map<?,?>) ((org.springframework.security.core.Authentication) principal).getPrincipal();
            userId = ((Number) p.get("uid")).longValue();
        }
        return service.search(status, minPrice, maxPrice, userId, pageable);
    }

    // GET by id
    @GetMapping("/{id}")
    public Reservation get(@PathVariable Long id, Principal principal) {
        Reservation r = service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        if (!requestIsAdmin()) {
            Map<?,?> p = (Map<?,?>) ((org.springframework.security.core.Authentication) principal).getPrincipal();
            Long uid = ((Number) p.get("uid")).longValue();
            if (!r.getUser().getId().equals(uid)) throw new AccessDeniedException("Can't access this reservation");
        }
        return r;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Reservation create(@RequestBody Reservation reservation, Principal principal) {
        if (!requestIsAdmin()) {
            Map<?,?> p = (Map<?,?>) ((org.springframework.security.core.Authentication) principal).getPrincipal();
            Long uid = ((Number) p.get("uid")).longValue();
            reservation.setUser(new com.company.multigenesys_bookingsystemassignment_umeshmali.entity.User()); // minimal user ref; better to fetch full user
            reservation.getUser().setId(uid);
        }
        reservation.setStatus(ReservationStatus.PENDING);
        return service.save(reservation);
    }

    // UPDATE + DELETE: ADMIN: any; USER: only own
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation update, Principal principal) {
        Reservation r = service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        if (!requestIsAdmin()) {
            Map<?,?> p = (Map<?,?>) ((org.springframework.security.core.Authentication) principal).getPrincipal();
            Long uid = ((Number) p.get("uid")).longValue();
            if (!r.getUser().getId().equals(uid)) throw new AccessDeniedException("Not allowed");
        }
        r.setStatus(update.getStatus());
        r.setPrice(update.getPrice());
        r.setStartTime(update.getStartTime());
        r.setEndTime(update.getEndTime());
        return service.save(r);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void delete(@PathVariable Long id, Principal principal) {
        Reservation r = service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        if (!requestIsAdmin()) {
            Map<?,?> p = (Map<?,?>) ((org.springframework.security.core.Authentication) principal).getPrincipal();
            Long uid = ((Number) p.get("uid")).longValue();
            if (!r.getUser().getId().equals(uid)) throw new AccessDeniedException("Not allowed");
        }
        service.deleteById(id);
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by("createdAt").descending();
        String[] parts = sort.split(",");
        if (parts.length == 1) return Sort.by(parts[0]).ascending();
        return Sort.by(parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, parts[0]);
    }

    private boolean requestIsAdmin() {
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
