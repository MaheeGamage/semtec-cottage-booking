package com.example.align;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//Servlet imports
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Java IO and Networking
import java.io.StringReader;

//XML Parsing
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//JSON Handling
import com.google.gson.Gson;

//Utility Classes
import java.util.Random;

import org.apache.jena.rdf.model.*;

/**
 * Servlet implementation class OntologyAlignment
 */
@WebServlet("/OntologyAlignment")
public class OntologyAlignment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OntologyAlignment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		IOntologyAlignmentService ontologyAligner = new OntologyAligner1();

		/**
		 * 1. Recieve the request from FE 2. Send request to SSWAP get endpoint and get
		 * RDG 3. Parse the file and received file and extract request parameters 4. Do
		 * the alignment randomly and send that as JSON object with response back to FE
		 */

		// Step 1: Get RDG URL from request parameters
		String rdgUrl = request.getParameter("rdgUrl");
		if (rdgUrl == null || rdgUrl.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"error\": \"RDG URL is required\"}");
			return;
		}

		try {
			// Step 2: Fetch RDG file from provided URL
			String rdgContent = fetchRDGContent(rdgUrl);
			
		    // Parse the RDG RDF content as a Jena Model
		    StringReader rdgReader = new StringReader(rdgContent); // rdgContent is the String containing RDG XML
		    Model rdgModel = ModelFactory.createDefaultModel();
		    rdgModel.read(rdgReader, null, "RDF/XML");

			// Step 3: Parse RDG XML to extract request parameters
//			Map<String, String> parameters = parseRDG(rdgModel);

			// Step 4: Perform random alignment
//			Map<String, String> alignment = performRandomAlignment(parameters);
		    
		    OntologyAlignmentResult results = ontologyAligner.alignOntology(rdgModel);

			// Step 5: Convert alignment to JSON and send response
			String jsonResponse = new Gson().toJson(results);
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(jsonResponse);

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String fetchRDGContent(String rdgUrl) throws IOException {
		URL url = new URL(rdgUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		}
	}

	private Map<String, String> parseRDGOld(String rdgContent) throws Exception {
		Map<String, String> parameters = new HashMap<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(rdgContent)));

		NodeList paramNodes = document.getElementsByTagName("ns1:requestBookerName"); // Example
		for (int i = 0; i < paramNodes.getLength(); i++) {
			Element paramElement = (Element) paramNodes.item(i);
			String paramName = paramElement.getTagName();
			String paramType = paramElement.getAttribute("rdf:datatype");
			parameters.put(paramName, paramType);
		}
		return parameters;
	}

	private Map<String, String> parseRDG(Model rdgModel) throws Exception {
	    // Initialize a map to hold parsed data
	    Map<String, String> parsedData = new HashMap<>();

	    // Define the base URI for the RDF structure (sswap:Resource)
	    Resource sswapResource = rdgModel.createResource("http://localhost:8080/sswap-servlet/bookingService");

	    // Extract the <sswap:operatesOn> -> <sswap:Graph> -> <sswap:hasMapping> -> <sswap:Subject> path
	    StmtIterator statements = rdgModel.listStatements(
	        sswapResource, rdgModel.createProperty("http://sswapmeet.sswap.info/sswap/operatesOn"), (RDFNode) null);

	    while (statements.hasNext()) {
	        Statement operatesOnStatement = statements.next();
	        Resource operatesOnGraph = operatesOnStatement.getObject().asResource();
	        
	        // Extract all the <sswap:hasMapping> -> <sswap:Subject> within the <sswap:Graph>
	        StmtIterator mappingStatements = rdgModel.listStatements(
	            operatesOnGraph, rdgModel.createProperty("http://sswapmeet.sswap.info/sswap/hasMapping"), (RDFNode) null);
	        
	        while (mappingStatements.hasNext()) {
	            Statement mappingStatement = mappingStatements.next();
	            Resource subjectResource = mappingStatement.getObject().asResource();
	            
	            // Extract the individual fields (requestBookerName, requestPeopleCount, etc.)
	            StmtIterator fieldStatements = rdgModel.listStatements(
	                subjectResource, null, (RDFNode) null); // Get all predicates and their objects under Subject

	            while (fieldStatements.hasNext()) {
	                Statement fieldStatement = fieldStatements.next();
	                Property property = fieldStatement.getPredicate();
	                RDFNode value = fieldStatement.getObject();
	                
	                // Filter and extract the relevant fields (requestBookerName, requestPeopleCount)
	                if (value.isLiteral()) {
	                    String fieldName = property.getLocalName();  // Use property local name for field name
	                    String fieldValue = value.asLiteral().getString();
	                    parsedData.put(fieldName, fieldValue);
	                }
	            }
	        }
	    }

	    // Return the parsed data as a Map
	    return parsedData;
	}


	private Map<String, String> performRandomAlignment(Map<String, String> parameters) {
		Map<String, String> alignment = new HashMap<>();
		Random random = new Random();

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			alignment.put(entry.getKey(), "AlignedValue_" + random.nextInt(100)); // Random mapping
		}
		return alignment;
	}

}
