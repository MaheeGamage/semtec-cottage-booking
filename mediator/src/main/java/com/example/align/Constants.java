package com.example.align;

import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final List<String> baseOntologyRequestFields = Arrays.asList("requestPeopleCount", "requestDayCount",
			"requestMaxCityDistance", "requestBedroomCount", "requestBookerName", "requestMaxLakeDistance",
			"requestStartDate", "requestNearestCity", "requestMaxDayShifts");

	public static final List<String> baseOntologyResponseFields = Arrays.asList("responseBookerName",
			"responseBookingNumber", "responseCottageAddress", "responseCottageImageUrl", "responseNumberOfPlaces",
			"responseNumberOfBedrooms", "responseDistanceToLake", "responseNearestCity", "responseDistanceToCity",
			"responseBookingStartDate", "responseBookingEndDate");
}
