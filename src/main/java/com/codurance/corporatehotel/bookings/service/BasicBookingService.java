package com.codurance.corporatehotel.bookings.service;

import com.codurance.corporatehotel.bookings.exception.InsufficientPolicyException;
import com.codurance.corporatehotel.bookings.exception.NoRoomAvailableException;
import com.codurance.corporatehotel.bookings.model.Booking;
import com.codurance.corporatehotel.bookings.repository.BookingRepository;
import com.codurance.corporatehotel.common.model.RoomTypes;
import com.codurance.corporatehotel.hotels.exception.HotelNotExistsException;
import com.codurance.corporatehotel.hotels.service.HotelService;
import com.codurance.corporatehotel.policies.service.PolicyService;
import com.codurance.corporatehotel.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BasicBookingService implements BookingService {

  private IdGenerator idGenerator = new IdGenerator();

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private PolicyService policyService;

  @Autowired
  private HotelService hotelService;

  public Booking book(Integer employeeId, Integer hotelId, RoomTypes roomType,
      LocalDateTime checkIn, LocalDateTime checkOut) {
    this.validate(employeeId, hotelId, roomType, checkIn, checkOut);

    Booking booking = new Booking();

    booking.setUuid(this.idGenerator.generate());

    booking.setEmployeeId(employeeId);
    booking.setHotel(this.hotelService.findHotelById(hotelId));
    booking.setRoomType(roomType);
    booking.setCheckIn(checkIn);
    booking.setCheckOut(checkOut);

    this.bookingRepository.save(booking);

    return booking;
  }

  private void validate(Integer employeeId, Integer hotelId, RoomTypes roomType,
      LocalDateTime checkIn, LocalDateTime checkOut) {
    if (this.hotelService.findHotelById(hotelId) == null) {
      throw new HotelNotExistsException();
    }

    if (!this.policyService.isBookingAllowed(employeeId, roomType)) {
      throw new InsufficientPolicyException();
    }

    if (this.bookingRepository.findAvailableRooms(roomType, checkIn, checkOut) == null) {
      throw new NoRoomAvailableException();
    }
  }
}
