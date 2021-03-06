package logic;

import com.ferrari.finances.dk.rki.Rating;

public class Customer {
	private int phone;
	private String name;
	private String address;
	private int postalCode;
	private String city;
	private String cpr;
	private Rating rating;
	private boolean hasActiveOffer;

	public boolean hasActiveOffer() {
		return hasActiveOffer;
	}

	public void setHasActiveOffer(boolean hasActiveOffer) {
		this.hasActiveOffer = hasActiveOffer;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	public String getCPR() {
		return cpr;
	}

	public void setCPR(String cPR) {
		cpr = cPR;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
