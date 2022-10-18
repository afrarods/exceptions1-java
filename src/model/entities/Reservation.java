package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import model.exceptions.DomainException;

public class Reservation {
	
	private Integer roomNumber;
	private Date checkin;
	private Date checkout;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Reservation() {
	}

	public Reservation(Integer roomNumber, Date checkin, Date checkout) {
		if (checkout.before(checkin)) {
			throw new DomainException("Check-out date must be after check-in date");
		}
		this.roomNumber = roomNumber;
		this.checkin = checkin;
		this.checkout = checkout;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getCheckin() {
		return checkin;
	}

	public Date getCheckout() {
		return checkout;
	}
	
	public Long duration() {
		Long diff = checkout.getTime() - checkin.getTime();  // diferença em milisegundos
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);    // converte os milisegundos para dias
	}
	
	public void updataDates(Date checkin, Date checkout) {
		Date now = new Date();
		if (checkin.before(now) || checkout.before(now)) {
			throw new DomainException("Reservation dates for update must be future dates");
		}
		if (checkout.before(checkin)) {
			throw new DomainException("Check-out date must be after check-in date");
		}
		this.checkin = checkin;
		this.checkout = checkout;
	}
	
	@Override
	public String toString() {
		return "Room "
			+ roomNumber
			+ ", check-in: "
			+ sdf.format(checkin)
			+ ", check-out: "
			+ sdf.format(checkout)
			+ ", "
			+ duration()
			+" nights.";
			
	}
}
