package com.example.jader.repository;

public enum GroupField {
    DRUG_NAME("drugName"),
    PRODUCT_NAME("productName"),
    INDICATION("indication"),
    REACTION_TERM("reactionTerm");  // ReacEntry 側

    private final String path;
    GroupField(String path) { this.path = path; }
    public String getPath() { return path; }
}
