package com.example.query;

import com.example.RequestParams;

public class Query1 implements IQuery {

	@Override
	public String generateQuery(RequestParams params) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("PREFIX cb: <http://example.org/ex#>\n");
		queryBuilder.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
		queryBuilder.append("SELECT ?cottage\n");
		queryBuilder.append("WHERE {\n");
		queryBuilder.append("  ?cottage rdf:type cb:Cottage .\n");

		if (params.getNoOfPeople() > 0) {
			queryBuilder.append("  ?cottage cb:hasCapacity ?capacity .\n");
			queryBuilder.append("  FILTER(?capacity >= " + params.getNoOfPeople() + ") \n");
		}
		if (params.getBedroomCount() > 0) {
			queryBuilder.append("  ?cottage cb:hasBedroomCount ?bedrooms .\n");
			queryBuilder.append("  FILTER(?bedrooms >= " + params.getBedroomCount() + ") \n");
		}
		if (params.getMaxLakeDistance() > 0) {
			queryBuilder.append("  ?cottage cb:distanceToLake ?lakeDistance .\n");
			queryBuilder.append("  FILTER(?lakeDistance <= " + params.getMaxLakeDistance() + ") \n");
		}
		if (params.getCity() != null && !params.getCity().isEmpty()) {
			queryBuilder.append("  ?cottage cb:locatedInCity ?city .\n");
			queryBuilder.append("  FILTER(?city = \"" + params.getCity() + "\") \n");
		}
		if (params.getMaxCityDistance() > 0) {
			queryBuilder.append("  ?cottage cb:distanceToCity ?cityDistance .\n");
			queryBuilder.append("  FILTER(?cityDistance <= " + params.getMaxCityDistance() + ") \n");
		}
		if (params.getDayCount() > 0) {
			queryBuilder.append("  ?cottage cb:availableForDays ?days .\n");
			queryBuilder.append("  FILTER(?days >= " + params.getDayCount() + ") \n");
		}
		if (params.getStartDate() != null && !params.getStartDate().isEmpty()) {
			queryBuilder.append("  ?cottage cb:availableFrom ?startDate .\n");
			queryBuilder.append("  FILTER(?startDate = \"" + params.getStartDate() + "\") \n");
		}
		if (params.getMaxDayShifts() > 0) {
			queryBuilder.append("  ?cottage cb:maxDayShift ?dayShift .\n");
			queryBuilder.append("  FILTER(?dayShift <= " + params.getMaxDayShifts() + ") \n");
		}

		queryBuilder.append("}");

		String queryString = queryBuilder.toString();
		return queryString;
	}

}
