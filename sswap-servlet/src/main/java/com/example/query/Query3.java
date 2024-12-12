package com.example.query;

import com.example.RequestParams;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Query3 implements IQuery {

	@Override
	public String generateQuery(RequestParams params, LocalDate startDate, LocalDate endDate) {
		// Calculating end date
//		LocalDate startDate = LocalDate.parse(params.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
//        LocalDate endDate = startDate.plusDays(params.getDayCount());
		
		String queryString = "PREFIX : <http://localhost:8080/SW_project/cottagebooking#>\r\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "\r\n"
				+ "SELECT ?cottage ?address ?city ?distanceFromLake ?distanceFromCity ?maxPeople ?cottageImageUrl ?bedrooms\r\n"
				+ "WHERE {\r\n"
				+ "  # Define user requirements\r\n"
				+ "  BIND(\""+ startDate.toString() +"\"^^xsd:date AS ?userStart) .\r\n"
				+ "  BIND(\""+ endDate.toString() +"\"^^xsd:date AS ?userEnd) .\r\n"
				+ "\r\n"
				+ "  # Retrieve cottage details\r\n"
				+ "  ?cottage rdf:type :Cottage ;\r\n"
				+ "           :hasAddress ?address ;\r\n"
				+ "           :hasDistanceFromLake ?distanceFromLake ;\r\n"
				+ "           :hasMaxNumberOfPeople ?maxPeople ;\r\n"
				+ "           :hasNumberOfBedrooms ?bedrooms ;\r\n"
				+ "           :hasCottageImageUrl ?cottageImageUrl ;\r\n"
				+ "           :isLocatedAt ?location .\r\n"
				+ "\r\n"
				+ "  ?location :hasNearestCityName ?city ;\r\n"
				+ "            :hasDistanceFromCity ?distanceFromCity .\r\n"
				+ "\r\n"
				+ "  # Apply user filter requirements\r\n"
				+ "  FILTER (?maxPeople >= " + params.getNoOfPeople() + ")\r\n"
				+ "  FILTER (?bedrooms >= " + params.getBedroomCount() + ")\r\n"
				+ "  FILTER (?distanceFromLake <= " + params.getMaxLakeDistance() + ")\r\n"
				+ "  FILTER (?city =\""+ params.getCity() +"\")\r\n"
				+ "  FILTER (?distanceFromCity <= " + params.getMaxCityDistance() + ")\r\n"
				+ "\r\n"
				+ "  # Exclude cottages with overlapping bookings\r\n"
				+ "  FILTER NOT EXISTS {\r\n"
				+ "    ?booking rdf:type :Booking ;\r\n"
				+ "             :hasCottage ?cottage ;\r\n"
				+ "             :hasStartDate ?bookingStart ;\r\n"
				+ "             :hasEndDate ?bookingEnd .\r\n"
				+ "\r\n"
				+ "    # Overlapping condition\r\n"
				+ "    FILTER (?bookingStart <= ?userEnd && ?bookingEnd >= ?userStart)\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "ORDER BY ?cottage";
		
		return queryString;
	}

	@Override
	public String generateQuery(RequestParams params) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'generateQuery'");
	}

}
