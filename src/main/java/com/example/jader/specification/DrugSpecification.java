package com.example.jader.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.jader.model.DrugEntry;

public class DrugSpecification {

	public static Specification<DrugEntry> byCaseId(String caseId) {
		return !StringUtils.hasText(caseId) ? null : (root, query, builder) ->
				builder.like(builder.lower(root.get("caseId")), "%" + caseId.trim().toLowerCase() + "%");
	}

	public static Specification<DrugEntry> byDrugName(String drugName) {
		return !StringUtils.hasText(drugName) ? null : (root, query, builder) ->
				builder.like(builder.lower(root.get("drugName")), "%" + drugName.trim().toLowerCase() + "%");
	}

	public static Specification<DrugEntry> byProductName(String productName) {
		return !StringUtils.hasText(productName) ? null : (root, query, builder) ->
				builder.like(builder.lower(root.get("productName")), "%" + productName.trim().toLowerCase() + "%");
	}

	public static Specification<DrugEntry> byAdministrationRoute(String administrationRoute) {
		return !StringUtils.hasText(administrationRoute) ? null : (root, query, builder) ->
				builder.like(builder.lower(root.get("administrationRoute")), "%" + administrationRoute.trim().toLowerCase() + "%");
	}

	public static Specification<DrugEntry> byIndication(String indication) {
		return !StringUtils.hasText(indication) ? null : (root, query, builder) ->
				builder.like(builder.lower(root.get("indication")), "%" + indication.trim().toLowerCase() + "%");
	}
}