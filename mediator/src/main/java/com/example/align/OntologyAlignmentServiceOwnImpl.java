package com.example.align;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

import com.example.sswap.Extractor;

import java.util.*;

public class OntologyAlignmentServiceOwnImpl implements IOntologyAlignmentService {

	@Override
	public OntologyAlignmentResult alignOntology(Model guestRdgModel) {
		// Extract fields from the guest model
		Map<String, String> guestFieldsMap = Extractor.extractFieldsFromModel(guestRdgModel);
		List<String> guestFields = new ArrayList<>(guestFieldsMap.keySet());

		// Define base model fields
		List<String> baseFields = Constants.baseOntologyRequestFields;

		// Perform alignment
		Map<String, String> alignedFields = alignFields(guestFields, baseFields);

		// Populate OntologyAlignmentResult
		OntologyAlignmentResult result = new OntologyAlignmentResult();
		result.setRequestPeopleCount(alignedFields.get("requestPeopleCount"));
		result.setRequestDayCount(alignedFields.get("requestDayCount"));
		result.setRequestMaxCityDistance(alignedFields.get("requestMaxCityDistance"));
		result.setRequestBedroomCount(alignedFields.get("requestBedroomCount"));
		result.setRequestBookerName(alignedFields.get("requestBookerName"));
		result.setRequestMaxLakeDistance(alignedFields.get("requestMaxLakeDistance"));
		result.setRequestStartDate(alignedFields.get("requestStartDate"));
		result.setRequestNearestCity(alignedFields.get("requestNearestCity"));
		result.setRequestMaxDayShifts(alignedFields.get("requestMaxDayShifts"));

		return result;
	}

	private Map<String, String> alignFields(List<String> guestFields, List<String> baseFields) {
		Map<String, String> alignedFields = new HashMap<>();

		for (String baseField : baseFields) {
			String bestMatch = null;
			double bestScore = -1;

			for (String guestField : guestFields) {
				double score = calculateSimilarity(baseField, guestField);
				if (score > bestScore) {
					bestScore = score;
					bestMatch = guestField;
				}
			}

			// Map the best matching guest field to the base field
			if (bestMatch != null) {
				alignedFields.put(baseField, bestMatch);
			}
		}

		return alignedFields;
	}

	private double calculateSimilarity(String str1, String str2) {
		// Simple Levenshtein distance similarity implementation
		return 1.0 - (double) levenshteinDistance(str1, str2) / Math.max(str1.length(), str2.length());
	}

	private int levenshteinDistance(String a, String b) {
		int[][] dp = new int[a.length() + 1][b.length() + 1];

		for (int i = 0; i <= a.length(); i++) {
			for (int j = 0; j <= b.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = Math.min(dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
							Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
				}
			}
		}

		return dp[a.length()][b.length()];
	}
}
