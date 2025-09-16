package com.company.multigenesys_bookingsystemassignment_umeshmali.repository;

import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.Reservation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
}