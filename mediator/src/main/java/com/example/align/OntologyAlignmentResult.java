package com.example.align;

public class OntologyAlignmentResult {
	private String requestPeopleCount;
    private String requestDayCount;
    private String requestMaxCityDistance;
    private String requestBedroomCount;
    private String requestBookerName;
    private String requestMaxLakeDistance;
    private String requestStartDate;
    private String requestNearestCity;
    private String requestMaxDayShifts;

    // Getters and Setters
    public String getRequestPeopleCount() {
        return requestPeopleCount;
    }

    public void setRequestPeopleCount(String requestPeopleCount) {
        this.requestPeopleCount = requestPeopleCount;
    }

    public String getRequestDayCount() {
        return requestDayCount;
    }

    public void setRequestDayCount(String requestDayCount) {
        this.requestDayCount = requestDayCount;
    }

    public String getRequestMaxCityDistance() {
        return requestMaxCityDistance;
    }

    public void setRequestMaxCityDistance(String requestMaxCityDistance) {
        this.requestMaxCityDistance = requestMaxCityDistance;
    }

    public String getRequestBedroomCount() {
        return requestBedroomCount;
    }

    public void setRequestBedroomCount(String requestBedroomCount) {
        this.requestBedroomCount = requestBedroomCount;
    }

    public String getRequestBookerName() {
        return requestBookerName;
    }

    public void setRequestBookerName(String requestBookerName) {
        this.requestBookerName = requestBookerName;
    }

    public String getRequestMaxLakeDistance() {
        return requestMaxLakeDistance;
    }

    public void setRequestMaxLakeDistance(String requestMaxLakeDistance) {
        this.requestMaxLakeDistance = requestMaxLakeDistance;
    }

    public String getRequestStartDate() {
        return requestStartDate;
    }

    public void setRequestStartDate(String requestStartDate) {
        this.requestStartDate = requestStartDate;
    }

    public String getRequestNearestCity() {
        return requestNearestCity;
    }

    public void setRequestNearestCity(String requestNearestCity) {
        this.requestNearestCity = requestNearestCity;
    }

    public String getRequestMaxDayShifts() {
        return requestMaxDayShifts;
    }

    public void setRequestMaxDayShifts(String requestMaxDayShifts) {
        this.requestMaxDayShifts = requestMaxDayShifts;
    }

    // Constructor
    public OntologyAlignmentResult(String requestPeopleCount, String requestDayCount, String requestMaxCityDistance,
                          String requestBedroomCount, String requestBookerName, String requestMaxLakeDistance,
                          String requestStartDate, String requestNearestCity, String requestMaxDayShifts) {
        this.requestPeopleCount = requestPeopleCount;
        this.requestDayCount = requestDayCount;
        this.requestMaxCityDistance = requestMaxCityDistance;
        this.requestBedroomCount = requestBedroomCount;
        this.requestBookerName = requestBookerName;
        this.requestMaxLakeDistance = requestMaxLakeDistance;
        this.requestStartDate = requestStartDate;
        this.requestNearestCity = requestNearestCity;
        this.requestMaxDayShifts = requestMaxDayShifts;
    }

    // Default Constructor
    public OntologyAlignmentResult() {
        // Default values can be set here if necessary
    }

    @Override
    public String toString() {
        return "RequestDetails{" +
                "requestPeopleCount='" + requestPeopleCount + '\'' +
                ", requestDayCount='" + requestDayCount + '\'' +
                ", requestMaxCityDistance='" + requestMaxCityDistance + '\'' +
                ", requestBedroomCount='" + requestBedroomCount + '\'' +
                ", requestBookerName='" + requestBookerName + '\'' +
                ", requestMaxLakeDistance='" + requestMaxLakeDistance + '\'' +
                ", requestStartDate='" + requestStartDate + '\'' +
                ", requestNearestCity='" + requestNearestCity + '\'' +
                ", requestMaxDayShifts='" + requestMaxDayShifts + '\'' +
                '}';
    }
}
