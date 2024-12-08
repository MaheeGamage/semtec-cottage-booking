package com.example.align;

public class OntologyAlignmentResult {
	RequestOntologyAlignmentResult requestAlignment;
	ResponseOntologyAlignmentResult responseAlignment;
	
	 // Constructor
    public OntologyAlignmentResult(RequestOntologyAlignmentResult requestAlignment, ResponseOntologyAlignmentResult responseAlignment) {
        this.requestAlignment = requestAlignment;
        this.responseAlignment = responseAlignment;
    }
    
    public OntologyAlignmentResult() {}

    // Getter for requestAlignment
    public RequestOntologyAlignmentResult getRequestAlignment() {
        return requestAlignment;
    }

    // Setter for requestAlignment
    public void setRequestAlignment(RequestOntologyAlignmentResult requestAlignment) {
        this.requestAlignment = requestAlignment;
    }

    // Getter for responseAlignment
    public ResponseOntologyAlignmentResult getResponseAlignment() {
        return responseAlignment;
    }

    // Setter for responseAlignment
    public void setResponseAlignment(ResponseOntologyAlignmentResult responseAlignment) {
        this.responseAlignment = responseAlignment;
    }
}
