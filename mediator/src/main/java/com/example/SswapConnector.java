package com.example;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

public class SswapConnector {

	public static void sampleRequest(RequestParams requestParams) {
		try {
			// Define the target URL
			URL url = new URL("http://localhost:8080/sswap-servlet/cottage");

			// Open connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set request properties
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/turtle");
			connection.setDoOutput(true);

			Model rdgModel = RDGGenerator.generateRequestSswapResources(requestParams);
			StringWriter modelOutput = new StringWriter();
			rdgModel.write(modelOutput, "TURTLE");
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
		            ByteArrayInputStream inputStream = new ByteArrayInputStream(response.toString().getBytes(StandardCharsets.UTF_8));

		            // Create an empty Jena Model
		            Model model = ModelFactory.createDefaultModel();

		            // Read the Turtle content into the Model
		            RDFDataMgr.read(model, inputStream, Lang.TURTLE);

		            System.out.println("RDF Model Out");
		            // Print the Model to verify
		            model.write(System.out, "TURTLE");

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
	}

}
