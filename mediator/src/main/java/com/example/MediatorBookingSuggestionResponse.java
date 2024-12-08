package com.example;

import java.util.Map;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.datatypes.xsd.XSDDatatype;

public class MediatorBookingSuggestionResponse {
	// Fields based on requirements
	private String bookerName;
	private String bookingNumber;
	private String cottageAddress;
	private String cottageImageUrl;
	private String numberOfPlaces;
	private String numberOfBedrooms;
	private String distanceToLake;
	private String nearestCity;
	private String distanceToCity;
	private String bookingStartDate;
	private String bookingEndDate;

	// Constructor
	public MediatorBookingSuggestionResponse(String bookerName, String bookingNumber, String cottageAddress,
			String cottageImageUrl, String numberOfPlaces, String numberOfBedrooms, String distanceToLake, String nearestCity,
			String distanceToCity, String bookingStartDate, String bookingEndDate) {
		this.bookerName = bookerName;
		this.bookingNumber = bookingNumber;
		this.cottageAddress = cottageAddress;
		this.cottageImageUrl = cottageImageUrl;
		this.numberOfPlaces = numberOfPlaces;
		this.numberOfBedrooms = numberOfBedrooms;
		this.distanceToLake = distanceToLake;
		this.nearestCity = nearestCity;
		this.distanceToCity = distanceToCity;
		this.bookingStartDate = bookingStartDate;
		this.bookingEndDate = bookingEndDate;
	}

	public MediatorBookingSuggestionResponse(Map<String, String> data) {
		// Converting RDF values to java primitives
        String maxPeopleNumericValueStr = data.getOrDefault("responseNumberOfPlaces", "0").split("\\^\\^")[0];
        String bedroomsNumericValueStr = data.getOrDefault("responseNumberOfBedrooms", "0").split("\\^\\^")[0];
        String distanceToLakeNumericValueStr = data.getOrDefault("responseDistanceToLake", "0").split("\\^\\^")[0];
        String distanceFromCityNumericValueStr = data.getOrDefault("responseDistanceToCity", "0").split("\\^\\^")[0];
		
		this.bookerName = data.getOrDefault("responseBookerName", null);
		this.bookingNumber = data.getOrDefault("responseBookingNumber", null);
		this.cottageAddress = data.getOrDefault("responseCottageAddress", null);
		this.cottageImageUrl = data.getOrDefault("responseCottageImageUrl", null);
		this.numberOfPlaces = maxPeopleNumericValueStr;
		this.numberOfBedrooms = bedroomsNumericValueStr;
		this.distanceToLake = distanceToLakeNumericValueStr;
		this.nearestCity = data.getOrDefault("responseNearestCity", null);
		this.distanceToCity = distanceFromCityNumericValueStr;
		this.bookingStartDate = data.getOrDefault("responseBookingStartDate", null);
		this.bookingEndDate = data.getOrDefault("responseBookingEndDate", null);
	}

	// Getters and Setters
	public String getBookerName() {
		return bookerName;
	}

	public void setBookerName(String bookerName) {
		this.bookerName = bookerName;
	}

	public String getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public String getCottageAddress() {
		return cottageAddress;
	}

	public void setCottageAddress(String cottageAddress) {
		this.cottageAddress = cottageAddress;
	}

	public String getCottageImageUrl() {
		return cottageImageUrl;
	}

	public void setCottageImageUrl(String cottageImageUrl) {
		this.cottageImageUrl = cottageImageUrl;
	}

	public String getNumberOfPlaces() {
		return numberOfPlaces;
	}

	public void setNumberOfPlaces(String numberOfPlaces) {
		this.numberOfPlaces = numberOfPlaces;
	}

	public String getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	public void setNumberOfBedrooms(String numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public String getDistanceToLake() {
		return distanceToLake;
	}

	public void setDistanceToLake(String distanceToLake) {
		this.distanceToLake = distanceToLake;
	}

	public String getNearestCity() {
		return nearestCity;
	}

	public void setNearestCity(String nearestCity) {
		this.nearestCity = nearestCity;
	}

	public String getDistanceToCity() {
		return distanceToCity;
	}

	public void setDistanceToCity(String distanceToCity) {
		this.distanceToCity = distanceToCity;
	}

	public String getBookingStartDate() {
		return bookingStartDate;
	}

	public void setBookingStartDate(String bookingStartDate) {
		this.bookingStartDate = bookingStartDate;
	}

	public String getBookingEndDate() {
		return bookingEndDate;
	}

	public void setBookingEndDate(String bookingEndDate) {
		this.bookingEndDate = bookingEndDate;
	}

}
