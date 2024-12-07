package com.example.sswap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

public class Extractor {

	public static List<String> extractFieldsFromModel(Model model) {
		List<String> requestProperties = new ArrayList<String>();

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
                        System.out.println(predicate.getURI());
                        requestProperties.add(predicate.getURI());
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
}
