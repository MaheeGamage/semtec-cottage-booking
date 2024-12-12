package com.example.align;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

import com.example.sswap.Extractor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OntologyAlignmentServiceOwnImpl implements IOntologyAlignmentService {

	@Override
	public OntologyAlignmentResult alignOntology(Model guestRdgModel) {
		// Extract request fields from the guest model
		Map<String, String> guestRequestFieldsMap = Extractor.extractFieldsFromModel(guestRdgModel);
		List<String> guestRequestFields = new ArrayList<>(guestRequestFieldsMap.keySet());

		// Define base model fields
		List<String> baseRequestFields = Constants.baseOntologyRequestFields;

		// Perform alignment
		Map<String, String> alignedRequestFields = alignFields(guestRequestFields, baseRequestFields);

		// Populate OntologyAlignmentResult
		RequestOntologyAlignmentResult requestResult = new RequestOntologyAlignmentResult();
		requestResult.setRequestPeopleCount(alignedRequestFields.get("requestPeopleCount"));
		requestResult.setRequestDayCount(alignedRequestFields.get("requestDayCount"));
		requestResult.setRequestMaxCityDistance(alignedRequestFields.get("requestMaxCityDistance"));
		requestResult.setRequestBedroomCount(alignedRequestFields.get("requestBedroomCount"));
		requestResult.setRequestBookerName(alignedRequestFields.get("requestBookerName"));
		requestResult.setRequestMaxLakeDistance(alignedRequestFields.get("requestMaxLakeDistance"));
		requestResult.setRequestStartDate(alignedRequestFields.get("requestStartDate"));
		requestResult.setRequestNearestCity(alignedRequestFields.get("requestNearestCity"));
		requestResult.setRequestMaxDayShifts(alignedRequestFields.get("requestMaxDayShifts"));

		// Extract response fields from the guest model
		Map<String, String> guestResponseFieldsMap = Extractor.extractResultsFromModel(guestRdgModel, true).get(0);
		List<String> guestResponseFields = new ArrayList<>(guestResponseFieldsMap.keySet());

		// Define base model fields
		List<String> baseResponseFields = Constants.baseOntologyResponseFields;

		// Perform alignment
		Map<String, String> alignedResponseFields = alignFields(guestResponseFields, baseResponseFields);

		// Populate OntologyAlignmentResult
		ResponseOntologyAlignmentResult responseResult = new ResponseOntologyAlignmentResult();
		responseResult.setResponseBookerName(alignedResponseFields.get("responseBookerName"));
		responseResult.setResponseBookingNumber(alignedResponseFields.get("responseBookingNumber"));
		responseResult.setResponseCottageAddress(alignedResponseFields.get("responseCottageAddress"));
		responseResult.setResponseCottageImageUrl(alignedResponseFields.get("responseCottageImageUrl"));
		responseResult.setResponseNumberOfPlaces(alignedResponseFields.get("responseNumberOfPlaces"));
		responseResult.setResponseNumberOfBedrooms(alignedResponseFields.get("responseNumberOfBedrooms"));
		responseResult.setResponseDistanceToLake(alignedResponseFields.get("responseDistanceToLake"));
		responseResult.setResponseNearestCity(alignedResponseFields.get("responseNearestCity"));
		responseResult.setResponseDistanceToCity(alignedResponseFields.get("responseDistanceToCity"));
		responseResult.setResponseBookingStartDate(alignedResponseFields.get("responseBookingStartDate"));
		responseResult.setResponseBookingEndDate(alignedResponseFields.get("responseBookingEndDate"));

		OntologyAlignmentResult alignResults = new OntologyAlignmentResult(requestResult, responseResult);
		return alignResults;
	}

	private Map<String, String> alignFields(List<String> guestFields, List<String> baseFields) {
		Map<String, String> alignedFields = new HashMap<>();
		StringBuilder alignmentLog = new StringBuilder();

		alignmentLog.append("Base Field,Guest Field,Similarity Score\n");

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
				alignmentLog.append(String.format("%s,%s,%.2f\n", baseField, bestMatch, bestScore));
			}
		}

		// Get the current date and time
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String timestamp = now.format(formatter);

		// Save alignment log to a file
		String specificPath = "D:\\Academic & Courses\\JYU MSc AI\\Semantic Tec for Dev\\temp\\alignment_log"
				+ timestamp + ".csv"; // Change this to your desired location
		saveAlignmentLogToFile(alignmentLog.toString(), specificPath);

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

	private void saveAlignmentLogToFile(String logContent, String filePath) {
		try {
			// Create the directories if they do not exist
			java.nio.file.Path path = java.nio.file.Paths.get(filePath);
			java.nio.file.Files.createDirectories(path.getParent());

			// Write the log content to the specified file
			java.nio.file.Files.write(path, logContent.getBytes());
			System.out.println("Alignment log saved to: " + filePath);
		} catch (Exception e) {
			System.err.println("Failed to save alignment log: " + e.getMessage());
		}
	}
}
