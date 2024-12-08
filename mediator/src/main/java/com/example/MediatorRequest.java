package com.example;

import com.example.align.RequestOntologyAlignmentResult;

public class MediatorRequest {
	private RequestOntologyAlignmentResult alignment;
	private String rig;
	
    // Getters and setters
    public RequestOntologyAlignmentResult getAlignment() {
        return alignment;
    }

    public void setAlignment(RequestOntologyAlignmentResult alignment) {
        this.alignment = alignment;
    }
    
    public String getRig() {
    	return rig;
    }
    
    public void setRig(String rig) {
        this.rig = rig;
    }
}
