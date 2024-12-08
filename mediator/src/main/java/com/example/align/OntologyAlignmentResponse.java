package com.example.align;

import java.util.List;

public class OntologyAlignmentResponse {

	private OntologyAlignmentResult alignmentResults;
	private List<String> sadiRequestProperties;
	private List<String> sadiResponseProperties;
	private String rdfFile;

	// Constructor
	public OntologyAlignmentResponse(OntologyAlignmentResult alignmentResults, List<String> sadiRequestProperties,
			List<String> sadiResponseProperties, String rdfFile) {
		this.alignmentResults = alignmentResults;
		this.sadiRequestProperties = sadiRequestProperties;
		this.sadiResponseProperties = sadiResponseProperties;
		this.rdfFile = rdfFile;
	}

	public OntologyAlignmentResponse() {
	};

	// Getters and Setters
	public OntologyAlignmentResult getAlignmentResults() {
		return alignmentResults;
	}

	public void setAlignmentResults(OntologyAlignmentResult alignmentResults) {
		this.alignmentResults = alignmentResults;
	}

	public List<String> getSadiRequestProperties() {
		return sadiRequestProperties;
	}

	public void setSadiRequestProperties(List<String> sadiRequestProperties) {
		this.sadiRequestProperties = sadiRequestProperties;
	}
	
	public List<String> getSadiResponseProperties() {
		return sadiResponseProperties;
	}

	public void setSadiResponseProperties(List<String> sadiResponseProperties) {
		this.sadiResponseProperties = sadiResponseProperties;
	}

	public String getRdfFile() {
		return rdfFile;
	}

	public void setRdfFile(String rdfFile) {
		this.rdfFile = rdfFile;
	}
}
