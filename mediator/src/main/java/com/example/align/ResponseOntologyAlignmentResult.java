package com.example.align;

public class ResponseOntologyAlignmentResult {

	private String responseBookerName;
	private String responseBookingNumber;
	private String responseCottageAddress;
	private String responseCottageImageUrl;
	private String responseNumberOfPlaces;
	private String responseNumberOfBedrooms;
	private String responseDistanceToLake;
	private String responseNearestCity;
	private String responseDistanceToCity;
	private String responseBookingStartDate;
	private String responseBookingEndDate;

	// Constructor
	public ResponseOntologyAlignmentResult(String responseBookerName, String responseBookingNumber,
			String responseCottageAddress, String responseCottageImageUrl, String responseNumberOfPlaces,
			String responseNumberOfBedrooms, String responseDistanceToLake, String responseNearestCity,
			String responseDistanceToCity, String responseBookingStartDate, String responseBookingEndDate) {
		this.responseBookerName = responseBookerName;
		this.responseBookingNumber = responseBookingNumber;
		this.responseCottageAddress = responseCottageAddress;
		this.responseCottageImageUrl = responseCottageImageUrl;
		this.responseNumberOfPlaces = responseNumberOfPlaces;
		this.responseNumberOfBedrooms = responseNumberOfBedrooms;
		this.responseDistanceToLake = responseDistanceToLake;
		this.responseNearestCity = responseNearestCity;
		this.responseDistanceToCity = responseDistanceToCity;
		this.responseBookingStartDate = responseBookingStartDate;
		this.responseBookingEndDate = responseBookingEndDate;
	}

	public ResponseOntologyAlignmentResult() {
	}

	// Getters and Setters
	public String getResponseBookerName() {
		return responseBookerName;
	}

	public void setResponseBookerName(String responseBookerName) {
		this.responseBookerName = responseBookerName;
	}

	public String getResponseBookingNumber() {
		return responseBookingNumber;
	}

	public void setResponseBookingNumber(String responseBookingNumber) {
		this.responseBookingNumber = responseBookingNumber;
	}

	public String getResponseCottageAddress() {
		return responseCottageAddress;
	}

	public void setResponseCottageAddress(String responseCottageAddress) {
		this.responseCottageAddress = responseCottageAddress;
	}

	public String getResponseCottageImageUrl() {
		return responseCottageImageUrl;
	}

	public void setResponseCottageImageUrl(String responseCottageImageUrl) {
		this.responseCottageImageUrl = responseCottageImageUrl;
	}

	public String getResponseNumberOfPlaces() {
		return responseNumberOfPlaces;
	}

	public void setResponseNumberOfPlaces(String responseNumberOfPlaces) {
		this.responseNumberOfPlaces = responseNumberOfPlaces;
	}

	public String getResponseNumberOfBedrooms() {
		return responseNumberOfBedrooms;
	}

	public void setResponseNumberOfBedrooms(String responseNumberOfBedrooms) {
		this.responseNumberOfBedrooms = responseNumberOfBedrooms;
	}

	public String getResponseDistanceToLake() {
		return responseDistanceToLake;
	}

	public void setResponseDistanceToLake(String responseDistanceToLake) {
		this.responseDistanceToLake = responseDistanceToLake;
	}

	public String getResponseNearestCity() {
		return responseNearestCity;
	}

	public void setResponseNearestCity(String responseNearestCity) {
		this.responseNearestCity = responseNearestCity;
	}

	public String getResponseDistanceToCity() {
		return responseDistanceToCity;
	}

	public void setResponseDistanceToCity(String responseDistanceToCity) {
		this.responseDistanceToCity = responseDistanceToCity;
	}

	public String getResponseBookingStartDate() {
		return responseBookingStartDate;
	}

	public void setResponseBookingStartDate(String responseBookingStartDate) {
		this.responseBookingStartDate = responseBookingStartDate;
	}

	public String getResponseBookingEndDate() {
		return responseBookingEndDate;
	}

	public void setResponseBookingEndDate(String responseBookingEndDate) {
		this.responseBookingEndDate = responseBookingEndDate;
	}
}
