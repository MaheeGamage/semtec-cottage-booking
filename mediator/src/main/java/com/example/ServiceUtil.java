package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

		// Find the blank nodes with sswap:hasMapping predicate
		ResIterator resIterator = model.listSubjectsWithProperty(model.createProperty(NS_SSWAP + "hasMapping"));

		while (resIterator.hasNext()) {
			Resource subject = resIterator.next();

			// Get the blank node (subject) connected to sswap:hasMapping
			Statement hasMappingStmt = subject.getProperty(model.createProperty(NS_SSWAP + "hasMapping"));
			if (hasMappingStmt != null) {
				// Retrieve the blank node
				Resource mappingNode = hasMappingStmt.getObject().asResource();

				// Extract ont:bedroomCount
				Statement bedroomCountStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "bedroomCount"));
				if (bedroomCountStmt != null) {
//					System.out.println("ont:bedroomCount: " + bedroomCountStmt.getObject().toString());
					jsonObject.put("bedroomCount", bedroomCountStmt.getObject().toString());
				}

				// Extract ont:bookerName
				Statement bookerNameStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "bookerName"));
				if (bookerNameStmt != null) {
//					System.out.println("ont:bookerName: " + bookerNameStmt.getObject().toString());
					jsonObject.put("bookerName", bookerNameStmt.getObject().toString());
				}

				// Extract ont:dayCount
				Statement dayCountStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "dayCount"));
				if (dayCountStmt != null) {
//					System.out.println("ont:dayCount: " + dayCountStmt.getObject().toString());
					jsonObject.put("dayCount", dayCountStmt.getObject().toString());
				}

				// Extract ont:maxCityDistance
				Statement maxCityDistanceStmt = mappingNode
						.getProperty(model.createProperty(NS_ONT + "maxCityDistance"));
				if (maxCityDistanceStmt != null) {
//					System.out.println("ont:maxCityDistance: " + maxCityDistanceStmt.getObject().toString());
					jsonObject.put("maxCityDistance", maxCityDistanceStmt.getObject().toString());
				}

				// Extract ont:maxDayShifts
				Statement maxDayShiftsStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "maxDayShifts"));
				if (maxDayShiftsStmt != null) {
//					System.out.println("ont:maxDayShifts: " + maxDayShiftsStmt.getObject().toString());
					jsonObject.put("maxDayShifts", maxDayShiftsStmt.getObject().toString());
				}

				// Extract ont:maxLakeDistance
				Statement maxLakeDistanceStmt = mappingNode
						.getProperty(model.createProperty(NS_ONT + "maxLakeDistance"));
				if (maxLakeDistanceStmt != null) {
//					System.out.println("ont:maxLakeDistance: " + maxLakeDistanceStmt.getObject().toString());
					jsonObject.put("maxLakeDistance", maxLakeDistanceStmt.getObject().toString());
				}

				// Extract ont:nearestCity
				Statement nearestCityStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "nearestCity"));
				if (nearestCityStmt != null) {
//					System.out.println("ont:nearestCity: " + nearestCityStmt.getObject().toString());
					jsonObject.put("nearestCity", nearestCityStmt.getObject().toString());
				}

				// Extract ont:peopleCount
				Statement peopleCountStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "peopleCount"));
				if (peopleCountStmt != null) {
//					System.out.println("ont:peopleCount: " + peopleCountStmt.getObject().toString());
					jsonObject.put("peopleCount", peopleCountStmt.getObject().toString());
				}

				// Extract ont:startDate
				Statement startDateStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "startDate"));
				if (startDateStmt != null) {
//					System.out.println("ont:startDate: " + startDateStmt.getObject().toString());
					jsonObject.put("startDate", startDateStmt.getObject().toString());
				}
			}
		}

		return jsonObject;
	}

	public static ArrayList<Map<String, String>> extractBookingsFromRRG(Model model) {
		ArrayList<Map<String, String>> bookingData = new ArrayList<Map<String, String>>();

		// Define namespaces for ease of use
		String NS_SSWAP = "http://sswapmeet.sswap.info/sswap/";
		String NS_ONT = "http://localhost:8080/SW_project/cottagebooking#";

		Property mapsTo = model.createProperty(NS_SSWAP, "mapsTo");

		// Find the blank nodes with sswap:hasMapping predicate
		ResIterator resIterator = model.listSubjectsWithProperty(model.createProperty(NS_SSWAP + "hasMapping"));

		while (resIterator.hasNext()) {
			Resource subject = resIterator.next();

			// Get the blank node (subject) connected to sswap:hasMapping
			Statement hasMappingStmt = subject.getProperty(model.createProperty(NS_SSWAP + "hasMapping"));
			if (hasMappingStmt != null) {
				// Retrieve the blank node
				Resource mappingNode = hasMappingStmt.getObject().asResource();

				// Find all resources connected via sswap:mapsTo within the mapping node
				StmtIterator mapsToStatements = model.listStatements(mappingNode, mapsTo, (RDFNode) null);

				while (mapsToStatements.hasNext()) {
					Statement mapsToStatement = mapsToStatements.nextStatement();

					RDFNode object = mapsToStatement.getObject();
					if (object.isResource()) {
						Resource blankNode = object.asResource();
						System.out.println("Found blank node connected via sswap:mapsTo: " + blankNode);

//						// Print all properties of the blank node
//						StmtIterator properties = blankNode.listProperties();
//						while (properties.hasNext()) {
//							Statement property = properties.nextStatement();
//							System.out.println("  " + property.getPredicate() + " -> " + property.getObject());
//						}
						
	                    // Convert the blank node properties to a Map
						bookingData.add(extractPropertiesAsMap(blankNode));
	                    System.out.println("Extracted data: " + bookingData);
					}
				}

			}
		}

		return bookingData;
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
	
    public static Map<String, String> extractPropertiesAsMap(Resource resource) {
        Map<String, String> data = new HashMap<>();
        StmtIterator properties = resource.listProperties();
        while (properties.hasNext()) {
            Statement property = properties.nextStatement();
            String predicate = property.getPredicate().getURI();
            String object = property.getObject().isLiteral()
                    ? property.getObject().asLiteral().getString()
                    : property.getObject().toString();
            data.put(predicate, object);
        }
        return data;
    }
}
