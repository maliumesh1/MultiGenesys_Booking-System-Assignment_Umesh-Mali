package com.company.multigenesys_bookingsystemassignment_umeshmali.filter;


import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.Reservation;
import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.ReservationStatus;

import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;

public class ReservationSpecification {
    public static Specification<Reservation> hasStatus(ReservationStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Reservation> minPrice(BigDecimal min) {
        return (root, query, cb) -> min == null ? null : cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Reservation> maxPrice(BigDecimal max) {
        return (root, query, cb) -> max == null ? null : cb.lessThanOrEqualTo(root.get("price"), max);
    }

    public static Specification<Reservation> belongsToUser(Long userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }
}
