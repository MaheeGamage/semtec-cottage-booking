package com.example;

import java.util.Map;
import java.util.StringJoiner;

public class SparqlQueryBuilder {

    public static String buildCottageSparqlQuery(Map<String, Map<String, String>> entityProperties) {
    	String baseUri = "http://example.org/ex#";
    	
        // Initialize the SPARQL query with the PREFIX and SELECT clauses
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("PREFIX cb: <").append(baseUri).append(">\n");
        queryBuilder.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
        queryBuilder.append("SELECT ?cottage ?maxPeople ?bedrooms ?distanceFromLake ?address ?nearestCity ?distanceFromCity\n");
        queryBuilder.append("WHERE {\n");

        // Add each entity and its properties to the WHERE clause
        for (Map.Entry<String, Map<String, String>> entityEntry : entityProperties.entrySet()) {
            String entity = entityEntry.getKey();
            Map<String, String> predicates = entityEntry.getValue();

            // If the entity has specific predicates, add them in a single block
            StringJoiner predicateJoiner = new StringJoiner(" ;\n", "?"+entity+" ", " .\n");
            for (Map.Entry<String, String> predicateEntry : predicates.entrySet()) {
                predicateJoiner.add("cb:" + predicateEntry.getKey() + " ?" + predicateEntry.getValue());
            }

            queryBuilder.append(predicateJoiner.toString());
        }

        queryBuilder.append("}");
        return queryBuilder.toString();
    }
}