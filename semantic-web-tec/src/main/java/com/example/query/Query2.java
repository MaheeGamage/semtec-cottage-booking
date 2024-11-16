package com.example.query;

import com.example.RequestParams;

public class Query2 implements IQuery {

	@Override
	public String generateQuery(RequestParams params) {
//		String queryString = "PREFIX : <http://localhost:8080/SW_project/cottagebooking#>\n"
//				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
//				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
//				+ "\n"
//				+ "SELECT ?cottage ?address ?city ?distanceFromLake ?distanceFromCity ?maxPeople ?bedrooms ?bookingStart ?bookingEnd\n"
//				+ "WHERE {\n"
//				+ "  # Filter cottages based on Booker requirements\n"
//				+ "  ?cottage rdf:type :Cottage ;\n"
//				+ "           :hasAddress ?address ;\n"
//				+ "           :hasDistanceFromLake ?distanceFromLake ;\n"
//				+ "           :hasMaxNumberOfPeople ?maxPeople ;\n"
//				+ "           :hasNumberOfBedrooms ?bedrooms ;\n"
//				+ "           :isLocatedAt ?location .\n"
//				+ "}";
		
		System.out.println(params.getNoOfPeople());
		
		String queryString = "PREFIX : <http://localhost:8080/SW_project/cottagebooking#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "\n"
				+ "SELECT ?cottage ?address ?city ?distanceFromLake ?distanceFromCity ?maxPeople ?bedrooms ?bookingStart ?bookingEnd\n"
				+ "WHERE {\n"
				+ "  # Filter cottages based on Booker requirements\n"
				+ "  ?cottage rdf:type :Cottage ;\n"
				+ "           :hasAddress ?address ;\n"
				+ "           :hasDistanceFromLake ?distanceFromLake ;\n"
				+ "           :hasMaxNumberOfPeople ?maxPeople ;\n"
				+ "           :hasNumberOfBedrooms ?bedrooms ;\n"
				+ "           :isLocatedAt ?location .\n"
				+ "\n"
				+ "  ?location :hasNearestCityName ?city ;\n"
				+ "            :hasDistanceFromCity ?distanceFromCity .\n"
				+ "\n"
				+ "  # User requirements input\n"
				+ "  FILTER (?maxPeople >= "+ params.getNoOfPeople() +")\n"
				+ "  FILTER (?bedrooms >= 2)\n"
				+ "  FILTER (?distanceFromLake <= 200)\n"
				+ "  FILTER (?city = \"Jyvaskyla\")\n"
				+ "  FILTER (?distanceFromCity <= 15)\n"
				+ "}\n";
		
		return queryString;
	}

}
