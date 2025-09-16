package com.company.multigenesys_bookingsystemassignment_umeshmali.seeder;

import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.Reservation;
import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.ResourceEntity;
import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.User;
import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.ReservationStatus;
import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.Role;
import com.company.multigenesys_bookingsystemassignment_umeshmali.repository.ResourceRepository;
import com.company.multigenesys_bookingsystemassignment_umeshmali.repository.ReservationRepository;
import com.company.multigenesys_bookingsystemassignment_umeshmali.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepo;
    private final ResourceRepository resourceRepo;
    private final ReservationRepository reservationRepo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepo,
                      ResourceRepository resourceRepo,
                      ReservationRepository reservationRepo,
                      PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.resourceRepo = resourceRepo;
        this.reservationRepo = reservationRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(Role.ROLE_ADMIN))
                    .enabled(true)
                    .build();

            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .roles(Set.of(Role.ROLE_USER))
                    .enabled(true)
                    .build();

            userRepo.save(admin);
            userRepo.save(user);
        }

        if (resourceRepo.count() == 0) {
            ResourceEntity r1 = ResourceEntity.builder()
                    .name("Conference Room A")
                    .type("ROOM")
                    .capacity(10)
                    .active(true)
                    .build();

            ResourceEntity r2 = ResourceEntity.builder()
                    .name("Projector X")
                    .type("EQUIPMENT")
                    .capacity(1)
                    .active(true)
                    .build();

            resourceRepo.save(r1);
            resourceRepo.save(r2);

            if (reservationRepo.count() == 0) {
                Reservation res = Reservation.builder()
                        .resource(resourceRepo.findAll().get(0))
                        .user(userRepo.findByUsername("user").get())
                        .status(ReservationStatus.PENDING)
                        .price(new BigDecimal("50.00"))
                        .startTime(Instant.now().plusSeconds(3600))
                        .endTime(Instant.now().plusSeconds(7200))
                        .build();
                reservationRepo.save(res);
            }
        }
    }
}
