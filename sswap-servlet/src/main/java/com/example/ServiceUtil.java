package com.example;

import org.apache.jena.rdf.model.*;
import org.json.JSONObject;

public class ServiceUtil {

	public static void printResource(Resource resource) {
		StmtIterator statements = resource.listProperties();
		while (statements.hasNext()) {
			Statement statement = statements.nextStatement();
			Property property = statement.getPredicate();
			String value = statement.getObject().toString();
			System.out.println(property.getLocalName() + ": " + value);
		}
	}

	// Method to print all values for a given resource
	public static void printResourceProperties(Resource resource) {
		StmtIterator statements = resource.listProperties(); // Get all statements (property-value pairs) for the
																// resource
		while (statements.hasNext()) {
			Statement stmt = statements.nextStatement(); // Get the next statement
			Property property = stmt.getPredicate(); // Get the property (predicate)
			RDFNode object = stmt.getObject(); // Get the object (value) of the statement

			// Print the property and its value
			System.out.println("Property: " + property.getLocalName() + " Value: " + object.toString());
		}
	}

	public static JSONObject extract1(Model model) {
		JSONObject jsonObject = new JSONObject();

		// Define namespaces for ease of use
		String NS_SSWAP = "http://sswapmeet.sswap.info/sswap/";
		String NS_ONT = "http://localhost:8080/SW_project/cottagebooking#";

		String[] sswapRequestParams = { "requestBookerName", "requestPeopleCount", "requestBedroomCount",
				"requestMaxLakeDistance", "requestNearestCity", "requestMaxCityDistance", "requestDayCount",
				"requestStartDate", "requestMaxDayShifts" };

		// Find the blank nodes with sswap:hasMapping predicate
		ResIterator resIterator = model.listSubjectsWithProperty(model.createProperty(NS_SSWAP + "hasMapping"));

		while (resIterator.hasNext()) {
			Resource subject = resIterator.next();

			// Get the blank node (subject) connected to sswap:hasMapping
			Statement hasMappingStmt = subject.getProperty(model.createProperty(NS_SSWAP + "hasMapping"));
			if (hasMappingStmt != null) {
				// Retrieve the blank node
				Resource mappingNode = hasMappingStmt.getObject().asResource();
				
				// Add requestParams from turtle file to s
				for (String requestParam : sswapRequestParams) {
					Statement ontStmt = mappingNode.getProperty(model.createProperty(NS_ONT + requestParam));
					if (ontStmt != null) {
						jsonObject.put(requestParam, ontStmt.getObject().toString());
					}
				}

			}
		}

		return jsonObject;
	}

	public static String checkAndReturnString(String str) {
		return (str == null) ? "" : str;
	}

	public static int retrieveXSDIntValue(String val) {
		return Integer.parseInt(val.split("\\^\\^")[0]);
	}

	public static String retrieveXSDStringValue(String val) {
		return val.split("\\^\\^")[0];
	}
}
