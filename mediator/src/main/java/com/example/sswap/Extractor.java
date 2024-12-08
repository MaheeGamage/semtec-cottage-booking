package com.example.sswap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

public class Extractor {

	public static Map<String, String> extractFieldsFromModel(Model model) {
//		List<String> requestProperties = new ArrayList<String>();
		Map<String, String> requestProperties = new HashMap<String, String>();

		// Define namespace URIs
		String sswapNs = "http://sswapmeet.sswap.info/sswap/";

		// Step 1: Find all resources that have the 'sswap:operatesOn' predicate
		Property operatesOn = model.getProperty(sswapNs + "operatesOn");

		// Iterate over all resources that have the 'operatesOn' predicate
		StmtIterator stmtIterator = model.listStatements(null, operatesOn, (RDFNode) null);
		while (stmtIterator.hasNext()) {
			Statement stmt = stmtIterator.nextStatement();
			Resource resource = stmt.getSubject(); // This is the resource with 'operatesOn' predicate
			Resource graph = resource.getPropertyResourceValue(operatesOn);

			// Step 2: Check if the object of 'operatesOn' is a Graph
			if (graph != null && graph.hasProperty(RDF.type, model.getResource(sswapNs + "Graph"))) {

				// Step 3: Find the 'hasMapping' predicate within the Graph
				Property hasMapping = model.getProperty(sswapNs + "hasMapping");
				Resource subject = graph.getPropertyResourceValue(hasMapping);

				// Step 4: Extract all predicates connected to the subject of 'hasMapping'
				if (subject != null) {
					System.out.println("Extracted predicates with full URIs for resource: " + resource.getURI());
					StmtIterator subjectIterator = subject.listProperties();
					while (subjectIterator.hasNext()) {
						Statement subjectStmt = subjectIterator.nextStatement();
						Property predicate = subjectStmt.getPredicate();
						System.out.println(subjectStmt.getObject());
						requestProperties.put(predicate.getURI(), subjectStmt.getObject().toString());
					}
				} else {
					System.out.println("'hasMapping' did not map to any subject for resource: " + resource.getURI());
				}
			} else {
				System.out.println("'operatesOn' does not refer to a valid Graph for resource: " + resource.getURI());
			}
		}

		return requestProperties;
	}

	public static List<Map<String, String>> extractResultsFromModel(Model model, boolean isFullUri) {
		// Define namespace URIs
		String sswapNs = "http://sswapmeet.sswap.info/sswap/";

		// Step 1: Find all resources that have the 'sswap:operatesOn' predicate
		Property operatesOn = model.getProperty(sswapNs + "operatesOn");

		// List to hold results (maps of predicates for each result)
		List<Map<String, String>> results = new ArrayList<>();

		// Iterate over all resources that have the 'operatesOn' predicate
		StmtIterator stmtIterator = model.listStatements(null, operatesOn, (RDFNode) null);
		while (stmtIterator.hasNext()) {
			Statement stmt = stmtIterator.nextStatement();
			Resource resource = stmt.getSubject(); // This is the resource with 'operatesOn' predicate
			Resource graph = resource.getPropertyResourceValue(operatesOn);

			// Step 2: Check if the object of 'operatesOn' is a Graph
			if (graph != null && graph.hasProperty(RDF.type, model.getResource(sswapNs + "Graph"))) {

				// Step 3: Find the 'hasMapping' predicate within the Graph
				Property hasMapping = model.getProperty(sswapNs + "hasMapping");
				Resource subject = graph.getPropertyResourceValue(hasMapping);

				// Step 4: Extract all predicates connected to the subject of 'hasMapping'
				if (subject != null) {
					// Step 5: Find all 'sswap:mapsTo' predicates for this subject
					Property mapsTo = model.getProperty(sswapNs + "mapsTo");
					StmtIterator mapsToIterator = subject.listProperties(mapsTo);

					// Step 6: Process all 'mapsTo' results
					while (mapsToIterator.hasNext()) {
						Statement mapsToStmt = mapsToIterator.nextStatement();
						Resource resultObject = mapsToStmt.getObject().asResource(); // This is the object of 'mapsTo'

						// Step 7: Get all predicates for this resultObject
						Map<String, String> result = new HashMap<>();
						StmtIterator resultIterator = resultObject.listProperties();
						while (resultIterator.hasNext()) {
						    Statement resultStmt = resultIterator.nextStatement();
						    String predicateURI = resultStmt.getPredicate().getURI();

						    // Handle literal values with type checking
						    RDFNode objectNode = resultStmt.getObject();
						    String object;
						    if (objectNode.isLiteral()) {
						        Literal literal = objectNode.asLiteral();
						        // Check if the literal has a datatype
						        if (literal.getDatatype() != null) {
						            String datatypeURI = literal.getDatatypeURI();
						            // Handle specific datatypes like xsd:date
						            if ("http://www.w3.org/2001/XMLSchema#date".equals(datatypeURI)) {
						                try {
						                    object = literal.getString(); // Validate if it's a valid date
						                } catch (DatatypeFormatException e) {
						                    System.err.println("Invalid date literal: " + literal + " for predicate: " + predicateURI);
						                    object = "Invalid date"; // Handle invalid date gracefully
						                }
						            } else {
						                object = literal.getValue().toString();
						            }
						        } else {
						            object = literal.getValue().toString(); // No datatype, interpret as string
						        }
						    } else {
						        object = objectNode.toString();
						    }

						    result.put(predicateURI, object);
						}

						if(!isFullUri) {
							result = convertToLocalNameMap(result);
						}
						// Add the result to the list
						results.add(result);
					}
				} else {
					System.out.println("'hasMapping' did not map to any subject for resource: " + resource.getURI());
				}
			} else {
				System.out.println("'operatesOn' does not refer to a valid Graph for resource: " + resource.getURI());
			}
		}

		// Print all results
		System.out.println("Extracted results:");
		for (Map<String, String> result : results) {
			System.out.println(result);
		}
		
		return results;
	}
	
    // Function to convert full URI map to local name map
    public static Map<String, String> convertToLocalNameMap(Map<String, String> uriMap) {
        Map<String, String> localNameMap = new HashMap<>();

        for (Map.Entry<String, String> entry : uriMap.entrySet()) {
            String fullURI = entry.getKey();
            String value = entry.getValue();

            // Use Jena Resource to extract the local name from the URI
            Resource resource = ModelFactory.createDefaultModel().createResource(fullURI);
            String localName = resource.getLocalName();

            // Add local name as key with the original value
            localNameMap.put(localName, value);
        }

        return localNameMap;
    }
}
