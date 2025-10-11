package com.coworkly.repository;

import com.coworkly.entity.Booking;
import com.coworkly.entity.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(
        properties = {
        "spring.flyway.enabled=false"
})
class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    void shouldSavedBooking() {
        // given
        Resource resource = new Resource();
        resource.setName("Focus Room");
        resource.setCapacity(2);
        resource.setFeatures("Monitor, HDMI");
        entityManager.persist(resource);

        OffsetDateTime now = OffsetDateTime.now();

        Booking booking = Booking.builder()
                .userEmail("user1@coworkly.com")
                .resource(resource)
                .startAt(now.plusHours(1))
                .endAt(now.plusHours(2))
                .status("BOOKED")
                .createdAt(now.toString())
                .build();

        // when
        Booking savedBooking = bookingRepository.save(booking);

        // then
        assertThat(savedBooking.getId()).isNotNull();
        Booking foundBooking = entityManager.find(Booking.class, savedBooking.getId());
        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getUserEmail()).isEqualTo("user1@coworkly.com");
        assertThat(foundBooking.getStatus()).isEqualTo("BOOKED");
        assertThat(foundBooking.getResource().getName()).isEqualTo("Focus Room");
    }

    @Test
    void shouldFindBookingById() {
        // given
        Resource resource = new Resource();
        resource.setName("Workshop Room");
        resource.setCapacity(10);
        resource.setFeatures("Projector");
        entityManager.persist(resource);

        OffsetDateTime now = OffsetDateTime.now();

        Booking booking = Booking.builder()
                .userEmail("user2@coworkly.com")
                .resource(resource)
                .startAt(now.plusHours(3))
                .endAt(now.plusHours(4))
                .status("BOOKED")
                .createdAt(now.toString())
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        // when
        var foundOption = bookingRepository.findById(savedBooking.getId());

        // then
        assertThat(foundOption).isPresent();
        var found = foundOption.get();
        assertThat(found.getUserEmail()).isEqualTo("user2@coworkly.com");
        assertThat(found.getResource().getName()).isEqualTo("Workshop Room");
    }

}