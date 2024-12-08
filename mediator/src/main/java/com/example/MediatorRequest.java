package com.example;

import com.example.align.OntologyAlignmentResult;

public class MediatorRequest {
	private OntologyAlignmentResult alignment;
	private String rig;
	
    // Getters and setters
    public OntologyAlignmentResult getAlignment() {
        return alignment;
    }

    public void setAlignment(OntologyAlignmentResult alignment) {
        this.alignment = alignment;
    }
    
    public String getRig() {
    	return rig;
    }
    
    public void setRig(String rig) {
        this.rig = rig;
    }
}
