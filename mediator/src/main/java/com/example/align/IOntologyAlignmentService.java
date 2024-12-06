package com.example.align;

import org.apache.jena.rdf.model.Model;

public interface IOntologyAlignmentService {
	OntologyAlignmentResult alignOntology(Model guestRdgModel);
}
