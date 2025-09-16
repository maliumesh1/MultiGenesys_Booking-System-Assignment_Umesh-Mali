package com.company.multigenesys_bookingsystemassignment_umeshmali.service;


import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.Reservation;
import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.ReservationStatus;
import com.company.multigenesys_bookingsystemassignment_umeshmali.repository.ReservationRepository;
import com.company.multigenesys_bookingsystemassignment_umeshmali.filter.ReservationSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Page<Reservation> search(ReservationStatus status, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, Long userId, Pageable pageable) {
        Specification<Reservation> spec = Specification.where(ReservationSpecification.hasStatus(status))
                .and(ReservationSpecification.minPrice(minPrice))
                .and(ReservationSpecification.maxPrice(maxPrice))
                .and(ReservationSpecification.belongsToUser(userId));
        return reservationRepository.findAll(spec, pageable);
    }

    public Reservation save(Reservation r) { return reservationRepository.save(r); }
    public java.util.Optional<Reservation> findById(Long id) { return reservationRepository.findById(id); }
    public void deleteById(Long id) { reservationRepository.deleteById(id); }
}
