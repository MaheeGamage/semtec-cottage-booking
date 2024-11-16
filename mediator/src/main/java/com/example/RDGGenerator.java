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

	public static Model generateRequestSswapResources(RequestParams bookingRequest) {
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
		String requestMaxCityDsitance = ServiceUtil
				.checkAndReturnString(String.valueOf(bookingRequest.getMaxCityDistance()));
		String requestDayCount = ServiceUtil.checkAndReturnString(String.valueOf(bookingRequest.getDayCount()));
		String requestStartDate = ServiceUtil.checkAndReturnString(bookingRequest.getStartDate());
		String requestMaxDayShifts = ServiceUtil.checkAndReturnString(String.valueOf(bookingRequest.getMaxDayShifts()));

		// Create hasMapping subject
		Resource subject = model.createResource().addProperty(RDF.type, model.createResource(sswapNS + "Subject"))
				.addProperty(RDF.type, model.createResource(ontNS + "BookingServiceRequest"))
				.addProperty(model.createProperty(ontNS + "bookerName"),
						model.createTypedLiteral(requestBookerName,
								TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))

				.addProperty(model.createProperty(ontNS + "peopleCount"),
						model.createTypedLiteral(requestPeopleCount,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "bedroomCount"),
						model.createTypedLiteral(requestBedroomCount,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "maxLakeDistance"),
						model.createTypedLiteral(requestMaxLakeDistance,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "nearestCity"),
						model.createTypedLiteral(requestNearestCity,
								TypeMapper.getInstance().getTypeByName(XSD.xstring.getURI())))

				.addProperty(model.createProperty(ontNS + "maxCityDistance"),
						model.createTypedLiteral(requestMaxCityDsitance,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "dayCount"),
						model.createTypedLiteral(requestDayCount,
								TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())))

				.addProperty(model.createProperty(ontNS + "startDate"),
						model.createTypedLiteral(requestStartDate,
								TypeMapper.getInstance().getTypeByName(XSD.date.getURI())))

				.addProperty(model.createProperty(ontNS + "maxDayShifts"), model.createTypedLiteral(requestMaxDayShifts,
						TypeMapper.getInstance().getTypeByName(XSD.integer.getURI())));

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
}