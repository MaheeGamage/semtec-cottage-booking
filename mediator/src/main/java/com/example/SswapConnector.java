package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.jena.rdf.model.Model;

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
				System.out.println("Response Body: " + response.toString());

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
