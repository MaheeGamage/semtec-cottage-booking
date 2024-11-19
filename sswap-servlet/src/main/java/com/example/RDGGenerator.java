package com.example;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.datatypes.TypeMapper;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.XSD;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RDGGenerator {

	// Namespaces for SSWAP and your custom service properties
	private static final String SSWAP_NAMESPACE = "http://sswapmeet.sswap.info/owl/sswap#";
	private static final String EX_NAMESPACE = "http://example.org/sswap#";

	public static Model generateRDG() {
		// Define namespaces
		String sswapNS = "http://sswapmeet.sswap.info/sswap/";
		String ontNS = "http://localhost:8080/SW_project/cottagebooking#";
		String ssNS = "http://localhost:8080/sswap-servlet/";

		// Create an empty model
		Model model = ModelFactory.createDefaultModel();

		// Define prefixes
		model.setNsPrefix("sswap", sswapNS);
		model.setNsPrefix("ont", ontNS);
		model.setNsPrefix("xsd", XSD.getURI());
		model.setNsPrefix("ss", ssNS);

		// Define resources and properties
		Resource bookingService = model.createResource(ssNS + "bookingService");
		Resource resourceProvider = model.createResource(ssNS + "res/resourceProvider");
		Resource graph = model.createResource().addProperty(RDF.type, model.createResource(sswapNS + "Graph"));

		Property sswapProvidedBy = model.createProperty(sswapNS + "providedBy");
		Property sswapName = model.createProperty(sswapNS + "name");
		Property sswapOneLineDescription = model.createProperty(sswapNS + "oneLineDescription");
		Property sswapOperatesOn = model.createProperty(sswapNS + "operatesOn");
		Property sswapHasMapping = model.createProperty(sswapNS + "hasMapping");
		Property sswapMapsTo = model.createProperty(sswapNS + "mapsTo");

		// Add BookingService resource
		bookingService.addProperty(RDF.type, model.createResource(sswapNS + "Resource"))
				.addProperty(RDF.type, model.createResource(ontNS + "BookingService"))
				.addProperty(sswapProvidedBy, resourceProvider).addProperty(sswapName, "Cottage booking sswap service")
				.addProperty(sswapOneLineDescription, "A service that search available cottages")
				.addProperty(sswapOperatesOn, graph);

		// Create hasMapping subject
		Resource subject = model.createResource().addProperty(RDF.type, model.createResource(sswapNS + "Subject"))
				.addProperty(RDF.type, model.createResource(ontNS + "BookingServiceRequest"))
				.addProperty(model.createProperty(ontNS + "bookerName"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
				.addProperty(model.createProperty(ontNS + "peopleCount"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "bedroomCount"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "maxLakeDistance"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "nearestCity"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
				.addProperty(model.createProperty(ontNS + "maxCityDistance"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "dayCount"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "startDate"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.date.getURI())))
				.addProperty(model.createProperty(ontNS + "maxDayShifts"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())));

		// Create mapsTo object
		Resource mapsToObject = model.createResource().addProperty(RDF.type, model.createResource(sswapNS + "Object"))
				.addProperty(RDF.type, model.createResource(ontNS + "BookingServiceResponse"))
				.addProperty(model.createProperty(ontNS + "bookerName"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
				.addProperty(model.createProperty(ontNS + "bookingNumber"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
				.addProperty(model.createProperty(ontNS + "cottageAddress"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
				.addProperty(model.createProperty(ontNS + "cottageImageUrl"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
				.addProperty(model.createProperty(ontNS + "numberOfPlaces"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "numberOfBedrooms"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "distanceToLake"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "nearestCity"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
				.addProperty(model.createProperty(ontNS + "distanceToCity"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
				.addProperty(model.createProperty(ontNS + "bookingStartDate"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.date.getURI())))
				.addProperty(model.createProperty(ontNS + "bookingEndDate"),
						model.createTypedLiteral("", TypeMapper.getInstance().getTypeByName(XSD.date.getURI())));

		// Link subject and mapsTo
		subject.addProperty(sswapMapsTo, mapsToObject);

		// Add hasMapping to graph
		graph.addProperty(sswapHasMapping, subject);

		return model;

	}

	public static Model generateSswapResources(RequestParams bookingRequest,
			ArrayList<BookingSuggestionResponse> bookingSuggestions) {
		// Define namespaces
		String sswapNS = "http://sswapmeet.sswap.info/sswap/";
		String ontNS = "http://localhost:8080/SW_project/cottagebooking#";
		String ssNS = "http://localhost:8080/sswap-servlet/";

		// Create an empty model
		Model model = ModelFactory.createDefaultModel();

		// Define prefixes
		model.setNsPrefix("sswap", sswapNS);
		model.setNsPrefix("ont", ontNS);
		model.setNsPrefix("xsd", XSD.getURI());
		model.setNsPrefix("ss", ssNS);

		// Define resources and properties
		Resource bookingService = model.createResource(ssNS + "bookingService");
		Resource resourceProvider = model.createResource(ssNS + "res/resourceProvider");
		Resource graph = model.createResource().addProperty(RDF.type, model.createResource(sswapNS + "Graph"));

		Property sswapProvidedBy = model.createProperty(sswapNS + "providedBy");
		Property sswapName = model.createProperty(sswapNS + "name");
		Property sswapOneLineDescription = model.createProperty(sswapNS + "oneLineDescription");
		Property sswapOperatesOn = model.createProperty(sswapNS + "operatesOn");
		Property sswapHasMapping = model.createProperty(sswapNS + "hasMapping");
		Property sswapMapsTo = model.createProperty(sswapNS + "mapsTo");

		// Add BookingService resource
		bookingService.addProperty(RDF.type, model.createResource(sswapNS + "Resource"))
				.addProperty(RDF.type, model.createResource(ontNS + "BookingService"))
				.addProperty(sswapProvidedBy, resourceProvider).addProperty(sswapName, "Cottage booking sswap service")
				.addProperty(sswapOneLineDescription, "A service that search available cottages")
				.addProperty(sswapOperatesOn, graph);

		String requestBookerName = ServiceUtil.checkAndReturnString(bookingRequest.getName());
		String requestPeopleCount = ServiceUtil.checkAndReturnString(String.valueOf(bookingRequest.getNoOfPeople()));
		String requestBedroomCount = ServiceUtil.checkAndReturnString(String.valueOf(bookingRequest.getBedroomCount()));
		String requestMaxLakeDistance = ServiceUtil
				.checkAndReturnString(String.valueOf(bookingRequest.getMaxLakeDistance()));
		String requestNearestCity = ServiceUtil.checkAndReturnString(bookingRequest.getCity());
		String requestMaxCityDistance = ServiceUtil
				.checkAndReturnString(String.valueOf(bookingRequest.getMaxCityDistance()));
		String requestDayCount = ServiceUtil.checkAndReturnString(String.valueOf(bookingRequest.getDayCount()));
		String requestStartDate = ServiceUtil.checkAndReturnString(bookingRequest.getStartDate());
		String requestMaxDayShifts = ServiceUtil.checkAndReturnString(String.valueOf(bookingRequest.getMaxDayShifts()));

		// Create hasMapping subject
		Resource subject = model.createResource().addProperty(RDF.type, model.createResource(sswapNS + "Subject"))
				.addProperty(RDF.type, model.createResource(ontNS + "BookingServiceRequest"))
				.addProperty(model.createProperty(ontNS + "requestBookerName"),
						model.createTypedLiteral(requestBookerName,
								TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))

				.addProperty(model.createProperty(ontNS + "requestPeopleCount"),
						model.createTypedLiteral(requestPeopleCount,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "requestBedroomCount"),
						model.createTypedLiteral(requestBedroomCount,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "requestMaxLakeDistance"),
						model.createTypedLiteral(requestMaxLakeDistance,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "requestNearestCity"),
						model.createTypedLiteral(requestNearestCity,
								TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))

				.addProperty(model.createProperty(ontNS + "requestMaxCityDistance"),
						model.createTypedLiteral(requestMaxCityDistance,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "requestDayCount"),
						model.createTypedLiteral(requestDayCount,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "requestStartDate"),
						model.createTypedLiteral(requestStartDate,
								TypeMapper.getInstance().getTypeByName(XSD.date.getURI())))

				.addProperty(model.createProperty(ontNS + "requestMaxDayShifts"), model.createTypedLiteral(requestMaxDayShifts,
						TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())));

		// Using an enhanced for loop
		for (BookingSuggestionResponse bookingSuggestion : bookingSuggestions) {
			String responseCottageImageUrl = ServiceUtil.checkAndReturnString(bookingSuggestion.getCottageImageUrl());

			Resource mapsToObject = model.createResource()
					.addProperty(RDF.type, model.createResource(sswapNS + "Object"))
					.addProperty(RDF.type, model.createResource(ontNS + "BookingServiceResponse"))
					.addProperty(model.createProperty(ontNS + "responseBookerName"),
							model.createTypedLiteral(bookingSuggestion.getBookerName(),
									TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
					.addProperty(model.createProperty(ontNS + "responseBookingNumber"),
							model.createTypedLiteral(bookingSuggestion.getBookingNumber(),
									TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
					.addProperty(model.createProperty(ontNS + "responseCottageAddress"),
							model.createTypedLiteral(bookingSuggestion.getCottageAddress(),
									TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
					.addProperty(model.createProperty(ontNS + "responseCottageImageUrl"),
							model.createTypedLiteral(responseCottageImageUrl,
									TypeMapper.getInstance().getTypeByName(XSD.anyURI.getURI())))
					.addProperty(model.createProperty(ontNS + "responseNumberOfPlaces"),
							model.createTypedLiteral(bookingSuggestion.getNumberOfPlaces(),
									TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
					.addProperty(model.createProperty(ontNS + "responseNumberOfBedrooms"),
							model.createTypedLiteral(bookingSuggestion.getNumberOfBedrooms(),
									TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
					.addProperty(model.createProperty(ontNS + "responseDistanceToLake"),
							model.createTypedLiteral(bookingSuggestion.getDistanceToLake(),
									TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
					.addProperty(model.createProperty(ontNS + "responseNearestCity"),
							model.createTypedLiteral(bookingSuggestion.getNearestCity(),
									TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))
					.addProperty(model.createProperty(ontNS + "responseDistanceToCity"),
							model.createTypedLiteral(bookingSuggestion.getDistanceToCity(),
									TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))
					.addProperty(model.createProperty(ontNS + "responseBookingStartDate"),
							model.createTypedLiteral(bookingSuggestion.getBookingStartDate(),
									TypeMapper.getInstance().getTypeByName(XSD.date.getURI())))
					.addProperty(model.createProperty(ontNS + "responseBookingEndDate"),
							model.createTypedLiteral(bookingSuggestion.getBookingEndDate(),
									TypeMapper.getInstance().getTypeByName(XSD.date.getURI())));

			subject.addProperty(sswapMapsTo, mapsToObject);

		}

		// Add hasMapping to graph
		graph.addProperty(sswapHasMapping, subject);

		return model;

	}
}