package com.example.jader.repository;

public enum FilterField {
	DRUG_NAME("drugName"),
	PRODUCT_NAME("productName"),
	INDICATION("indication"),
	REACTION_TERM("reactionTerm");  // ReacEntry側

	private final String path;
	FilterField(String path) { this.path = path; }
	public String getPath() { return path; }
}
