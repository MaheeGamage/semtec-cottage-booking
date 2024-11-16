package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class Bookings
 */
@WebServlet("/Booking")
public class Booking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Booking() {
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

		// Set the content type to JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ArrayList<BookingSuggestionResponse> bookingList = new ArrayList<>();
		// Create a sample object to return as JSON
		bookingList.add(new BookingSuggestionResponse("Alice Johnson", // name of the booker
				"BK-20241029-67890", // booking number
				"456 Mountain View Drive", // address of the cottage
				"http://example.com/mountain.jpg", // image of the cottage
				8, // actual number of places
				4, // actual number of bedrooms
				100, // distance to lake in meters
				"Evergreen City", // nearest city
				30, // distance to nearest city in km
				"2024-12-15", // booking start date
				"2024-12-25" // booking end date
		));
		bookingList.add(new BookingSuggestionResponse("Robert Smith", // name of the booker
				"BK-20241029-54321", // booking number
				"789 Sunset Blvd", // address of the cottage
				"http://example.com/sunset.jpg", // image of the cottage
				5, // actual number of places
				2, // actual number of bedrooms
				500, // distance to lake in meters
				"Riverside Town", // nearest city
				75, // distance to nearest city in km
				"2025-01-01", // booking start date
				"2025-01-07" // booking end date
		));

		// Convert the object to JSON using Gson
		String json = new Gson().toJson(bookingList);

		// Write JSON to the response output
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		ArrayList<BookingSuggestionResponse> results = new ArrayList<>();

		if (request.getParameter("reqType").toString().equals("doQuery")) {
			RequestParams params = new RequestParams();
			params.setName(request.getParameter("name"));
			params.setNoOfPeople(Integer.parseInt(request.getParameter("peopleCount")));
			params.setBedroomCount(Integer.parseInt(request.getParameter("bedroomCount")));
			params.setMaxLakeDistance(Integer.parseInt(request.getParameter("maxLakeDistance")));
			params.setCity(request.getParameter("city"));
			params.setMaxCityDistance(Integer.parseInt(request.getParameter("maxCityDistance")));
			params.setDayCount(Integer.parseInt(request.getParameter("dayCount")));
			params.setStartDate(request.getParameter("startDate"));
			params.setMaxDayShifts(Integer.parseInt(request.getParameter("maxDayShifts")));

			
			
		}
		
		// Convert the object to JSON using Gson
		String json = new Gson().toJson(results);

		// Write JSON to the response output
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
}