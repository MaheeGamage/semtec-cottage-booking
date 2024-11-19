package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.rdf.model.*;

import org.json.JSONObject;

/**
 * Servlet implementation class SswapService
 */
@WebServlet("/cottage")
public class SswapService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Assuming ontModel is defined and populated earlier in your code
	private Model ontModel = ModelFactory.createDefaultModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SswapService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set content type to RDF
//		response.setContentType("application/rdf+xml");
		response.setContentType("text/turtle");

		RequestParams params = new RequestParams();
		ArrayList<BookingSuggestionResponse> responseList =  new ArrayList<BookingSuggestionResponse>();
		responseList.add(new BookingSuggestionResponse("",
		"", // booking number
		"", // address of the cottage
		"", // image of the cottage
		0, // actual number of places
		0, // actual number ofs bedrooms
		0, // distance to lake in meters
		"", // nearest city
		0, // distance to nearest city in km
		"0", // booking start date
		"0" // booking end date
));

		// Generate RDG
		Model rdgModel = RDGGenerator.generateSswapResources(new RequestParams(), responseList);
		try (PrintWriter out = response.getWriter()) {
//            rdgModel.write(out, "RDF/XML");
			rdgModel.write(out, "TURTLE");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set response type
		resp.setContentType("text/turtle");
		
		SWDB mediator = new SWDB();

		// Read RDF content from the POST body
		Model ontModel = ModelFactory.createDefaultModel();
		try (InputStream inputStream = req.getInputStream()) {
			ontModel.read(inputStream, null, "TURTLE");
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("{\"error\": \"Invalid RDF format\"}");
			return;
		}

		// Parse the RDF to extract `hasMapping` section values
		try {
			JSONObject jObj = ServiceUtil.extract1(ontModel);
//			resp.getWriter().write(jObj.toString());

			RequestParams params = new RequestParams();
			params.setName(jObj.getString("requestBookerName"));
			params.setNoOfPeople(ServiceUtil.retrieveXSDIntValue(jObj.getString("requestPeopleCount")));
			params.setBedroomCount(ServiceUtil.retrieveXSDIntValue(jObj.getString("requestBedroomCount"))); 
			params.setMaxLakeDistance(ServiceUtil.retrieveXSDIntValue(jObj.getString("requestMaxLakeDistance")));
			params.setCity(jObj.getString("requestNearestCity"));
			params.setMaxCityDistance(ServiceUtil.retrieveXSDIntValue(jObj.getString("requestMaxCityDistance")));
			params.setDayCount(ServiceUtil.retrieveXSDIntValue(jObj.getString("requestDayCount")));
			params.setStartDate(ServiceUtil.retrieveXSDStringValue(jObj.getString("requestStartDate")));
			params.setMaxDayShifts(ServiceUtil.retrieveXSDIntValue(jObj.getString("requestMaxDayShifts")));

//			resp.setContentType("text/turtle");
			
			String pathToDB = this.getServletContext().getRealPath("/res/ontology/cottage_booking_ontology.ttl");
			ArrayList<BookingSuggestionResponse> bookingList = mediator.searchForResult(pathToDB, params);

			// Generate RDG
			Model rdgModel = RDGGenerator.generateSswapResources(params, bookingList);
			try (PrintWriter out = resp.getWriter()) {
				rdgModel.write(out, "TURTLE");
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\": \"Server error occurred\"}");
		}
	}

	/**
	 * Extract the `hasMapping` section from the model.
	 */
	private Resource extractHasMapping(Model model) {
		Property sswapHasMapping = model.createProperty("http://sswapmeet.sswap.info/sswap/hasMapping");
		ResIterator iterator = model.listResourcesWithProperty(sswapHasMapping);
		if (iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}

	/**
	 * Extract data from the `hasMapping` resource and return as a JSON string.
	 */
	private String extractHasMappingDataToJson(Resource hasMappingResource) {
		JSONObject jsonObject = new JSONObject();

		// Extract properties from the hasMapping resource
		Property bookerNameProp = ontModel.createProperty("http://localhost:8080/SW_project/cottagebooking#bookerName");
		Statement bookerNameStmt = hasMappingResource.getProperty(bookerNameProp);
		if (bookerNameStmt != null) {
			jsonObject.put("bookerName", bookerNameStmt.getObject().toString());
		}

		Property bedroomCountProp = ontModel
				.createProperty("http://localhost:8080/SW_project/cottagebooking#bedroomCount");
		Statement bedroomCountStmt = hasMappingResource.getProperty(bedroomCountProp);
		if (bedroomCountStmt != null) {
			jsonObject.put("bedroomCount", bedroomCountStmt.getObject().toString());
		}

		// Repeat the process for other properties you need to extract
		// ...

		return jsonObject.toString();
	}
}
