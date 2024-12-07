package com.example;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import com.example.align.OntologyAlignmentResult;

public class SswapConnector {

//	private static String sswapURL = "http://localhost:8080/sswap-servlet/cottage";
	
	public static ArrayList<MediatorBookingSuggestionResponse> retrieveDataFromSswap(RequestParams requestParams,
			OntologyAlignmentResult alignment, String sswapUrl){
		
		Model rigModel = MediatorRDGGenerator.generateRequestSswapResources(requestParams, alignment);
		return retrieveDataFromSswapWithRig(rigModel, sswapUrl);
	}

	public static ArrayList<MediatorBookingSuggestionResponse> retrieveDataFromSswapWithRig(Model rigModel, String sswapUrl) {
		ArrayList<MediatorBookingSuggestionResponse> bookingList = new ArrayList<>();

		try {
			// Define the target URL
			URL url = new URL(sswapUrl);

			// Open connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set request properties
			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Content-Type", "text/turtle");
			connection.setRequestProperty("Content-Type", "application/rdf+xml");
			connection.setDoOutput(true);

//			Model rigModel = MediatorRDGGenerator.generateRequestSswapResources(requestParams, alignment);
			StringWriter modelOutput = new StringWriter();
//			rdgModel.write(modelOutput, "TURTLE");
			rigModel.write(modelOutput, "RDF/XML");
			String turtleData = modelOutput.toString();

			// Send the Turtle data
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = turtleData.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Get the response code
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
				System.out.println("Request sent successfully.");

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					response.append(line).append("\n");
				}
				reader.close();

				// Print response body
//				System.out.println("Response Body: " + response.toString());

				// Convert response back to rdf model
				try {
					// Convert Turtle string to InputStream
					ByteArrayInputStream inputStream = new ByteArrayInputStream(
							response.toString().getBytes(StandardCharsets.UTF_8));

					// Create an empty Jena Model
					Model model = ModelFactory.createDefaultModel();

					// Read the Turtle content into the Model
					RDFDataMgr.read(model, inputStream, Lang.RDFXML);

//		            System.out.println("RDF Model Out");
					// Print the Model to verify
//		            model.write(System.out, "TURTLE");

					ArrayList<Map<String, String>> bookingData = MediatorServiceUtil.extractBookingsFromRRG(model);

					for (Map<String, String> singleBookingDataMap : bookingData) {
						bookingList.add(new MediatorBookingSuggestionResponse(singleBookingDataMap));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Error sending request. Response Code: " + responseCode);
			}

			// Close the connection
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookingList;
	}

}
