package com.company.multigenesys_bookingsystemassignment_umeshmali.controller;

import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.ResourceEntity;
import com.company.multigenesys_bookingsystemassignment_umeshmali.exception.ResourceNotFoundException;
import com.company.multigenesys_bookingsystemassignment_umeshmali.service.ResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    private final ResourceService service;
    public ResourceController(ResourceService service) { this.service = service; }

    @GetMapping
    public Page<ResourceEntity> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public ResourceEntity get(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResourceEntity create(@RequestBody ResourceEntity resource) {
        return service.save(resource);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResourceEntity update(@PathVariable Long id, @RequestBody ResourceEntity update) {
        ResourceEntity r = service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        r.setName(update.getName());
        r.setType(update.getType());
        r.setDescription(update.getDescription());
        r.setCapacity(update.getCapacity());
        r.setActive(update.isActive());
        return service.save(r);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
