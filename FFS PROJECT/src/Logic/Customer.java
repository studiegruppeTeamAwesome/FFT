package Logic;

import com.ferrari.finances.dk.rki.Rating;

public class Customer {
private int phone;
private String name;
private String adress;
private String CPR;
private Rating rating;


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
public String getAdress() {
	return adress;
}
public void setAdress(String adress) {
	this.adress = adress;
}
public String getCPR() {
	return CPR;
}
public void setCPR(String cPR) {
	CPR = cPR;
}


}
