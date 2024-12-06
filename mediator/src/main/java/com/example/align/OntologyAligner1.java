package com.example.align;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class OntologyAligner1 implements IOntologyAlignmentService {
	
	@Override
	public OntologyAlignmentResult alignOntology(Model rdgModel) {
		OntologyAlignmentResult result = new OntologyAlignmentResult();
		// Initialize a map to hold parsed data
		Map<String, String> parsedData = new HashMap<>();

		// Define the base URI for the RDF structure (sswap:Resource)
		Resource sswapResource = rdgModel.createResource("http://localhost:8080/sswap-servlet/bookingService");

		// Extract the <sswap:operatesOn> -> <sswap:Graph> -> <sswap:hasMapping> ->
		// <sswap:Subject> path
		StmtIterator statements = rdgModel.listStatements(sswapResource,
				rdgModel.createProperty("http://sswapmeet.sswap.info/sswap/operatesOn"), (RDFNode) null);

		while (statements.hasNext()) {
			Statement operatesOnStatement = statements.next();
			Resource operatesOnGraph = operatesOnStatement.getObject().asResource();

			// Extract all the <sswap:hasMapping> -> <sswap:Subject> within the
			// <sswap:Graph>
			StmtIterator mappingStatements = rdgModel.listStatements(operatesOnGraph,
					rdgModel.createProperty("http://sswapmeet.sswap.info/sswap/hasMapping"), (RDFNode) null);

			while (mappingStatements.hasNext()) {
				Statement mappingStatement = mappingStatements.next();
				Resource subjectResource = mappingStatement.getObject().asResource();

				// Extract the individual fields (requestBookerName, requestPeopleCount, etc.)
				StmtIterator fieldStatements = rdgModel.listStatements(subjectResource, null, (RDFNode) null); 

				while (fieldStatements.hasNext()) {
					Statement fieldStatement = fieldStatements.next();
					Property property = fieldStatement.getPredicate();
					RDFNode value = fieldStatement.getObject();

					// Filter and extract the relevant fields (requestBookerName,
					// requestPeopleCount)
					if (value.isLiteral()) {
						String fieldName = property.getLocalName(); // Use property local name for field name
						String fieldValue = value.asLiteral().getString();
						parsedData.put(fieldName, fieldValue);
					}
				}
			}
		}

        List<String> fieldValues = new ArrayList<>(parsedData.keySet());
        
        // Assign the shuffled values to the result properties
        result.setRequestPeopleCount(fieldValues.size() > 0 ? fieldValues.get(0) : null);
        result.setRequestDayCount(fieldValues.size() > 1 ? fieldValues.get(1) : null);
        result.setRequestMaxCityDistance(fieldValues.size() > 2 ? fieldValues.get(2) : null);
        result.setRequestBedroomCount(fieldValues.size() > 3 ? fieldValues.get(3) : null);
        result.setRequestBookerName(fieldValues.size() > 4 ? fieldValues.get(4) : null);
        result.setRequestMaxLakeDistance(fieldValues.size() > 5 ? fieldValues.get(5) : null);
        result.setRequestStartDate(fieldValues.size() > 6 ? fieldValues.get(6) : null);
        result.setRequestNearestCity(fieldValues.size() > 7 ? fieldValues.get(7) : null);
        result.setRequestMaxDayShifts(fieldValues.size() > 8 ? fieldValues.get(8) : null);
		

		// Return the parsed data as a Map
		return result;
	}
}
