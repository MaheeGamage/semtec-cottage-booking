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
        StmtIterator statements = resource.listProperties();  // Get all statements (property-value pairs) for the resource
        while (statements.hasNext()) {
            Statement stmt = statements.nextStatement();  // Get the next statement
            Property property = stmt.getPredicate();  // Get the property (predicate)
            RDFNode object = stmt.getObject();  // Get the object (value) of the statement
            
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
                    System.out.println("ont:bedroomCount: " + bedroomCountStmt.getObject().toString());
                    jsonObject.put("bedroomCount", bedroomCountStmt.getObject().toString());
                }
                
                // Extract ont:bookerName
                Statement bookerNameStmt = mappingNode.getProperty(model.createProperty(NS_ONT + "bookerName"));
                if (bookerNameStmt != null) {
                    System.out.println("ont:bookerName: " + bookerNameStmt.getObject().toString());
                    jsonObject.put("bookerName", bookerNameStmt.getObject().toString());
                }
            }
        }
        
        return jsonObject;
    }
}
