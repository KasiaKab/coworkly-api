package com.coworkly.repository;

import com.coworkly.domain.entity.Booking;
import com.coworkly.domain.entity.BookingStatus;
import com.coworkly.domain.entity.Resource;
import com.coworkly.domain.entity.TimeSlot;
import com.coworkly.domain.repository.BookingRepository;
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
        OffsetDateTime start = now.plusHours(1);
        OffsetDateTime end   = now.plusHours(2);

        TimeSlot slot = TimeSlot.builder()
                .resource(resource)
                .startAt(start)
                .endAt(end)
                .build();
        entityManager.persist(slot);

        Booking booking = Booking.builder()
                .userEmail("user1@coworkly.com")
                .resource(resource)
                .timeSlot(slot)
                .startAt(start)
                .endAt(end)
                .status(BookingStatus.BOOKED)
                .build();

        // when
        Booking savedBooking = bookingRepository.save(booking);

        // then
        assertThat(savedBooking.getId()).isNotNull();
        Booking foundBooking = entityManager.find(Booking.class, savedBooking.getId());
        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getUserEmail()).isEqualTo("user1@coworkly.com");
        assertThat(foundBooking.getStatus()).isEqualTo(BookingStatus.BOOKED);
        assertThat(foundBooking.getResource().getName()).isEqualTo("Focus Room");
        assertThat(foundBooking.getTimeSlot().getId()).isEqualTo(slot.getId());
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
        OffsetDateTime start = now.plusHours(3);
        OffsetDateTime end   = now.plusHours(4);

        TimeSlot slot = TimeSlot.builder()
                .resource(resource)
                .startAt(start)
                .endAt(end)
                .build();
        entityManager.persist(slot);

        Booking booking = Booking.builder()
                .userEmail("user2@coworkly.com")
                .resource(resource)
                .timeSlot(slot)
                .startAt(now.plusHours(3))
                .endAt(now.plusHours(4))
                .status(BookingStatus.BOOKED)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        // when
        var foundOption = bookingRepository.findById(savedBooking.getId());

        // then
        assertThat(foundOption).isPresent();
        var found = foundOption.get();
        assertThat(found.getUserEmail()).isEqualTo("user2@coworkly.com");
        assertThat(found.getResource().getName()).isEqualTo("Workshop Room");
        assertThat(found.getTimeSlot().getId()).isEqualTo(slot.getId());
        assertThat(found.getStatus()).isEqualTo(BookingStatus.BOOKED);
    }

}