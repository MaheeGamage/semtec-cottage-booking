
function updateRDF(rdfData, inputObject) {
    const rdf = $rdf; // Alias for rdflib.js
    const store = rdf.graph(); // Create a graph

    // Parse the RDF/XML string with the base URI 'http://example.com'
    rdf.parse(rdfData, store, 'http://example.com', 'application/rdf+xml');

    // Dynamically update properties using inputObject
    Object.keys(inputObject).forEach((propertyURI) => {
        const value = inputObject[propertyURI];
        const predicate = rdf.namedNode(propertyURI);

        // Find the subject with type BookingServiceRequest
        const bookingRequest = store.any(
            undefined,
            rdf.namedNode("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
            undefined // We dynamically match the object type if needed
        );

        if (bookingRequest) {
            // Find the existing triple
            const existingTriple = store.anyStatementMatching(bookingRequest, predicate);

            if (existingTriple) {
                const existingDatatype = existingTriple.object.datatype; // Retain existing datatype
                store.remove(existingTriple); // Remove old triple
                store.add(
                    bookingRequest,
                    predicate,
                    rdf.literal(value, existingDatatype) // Add new value with existing datatype
                );
            } else {
                // If no existing triple, add a new one without datatype assumption
                store.add(
                    bookingRequest,
                    predicate,
                    rdf.literal(value)
                );
            }
        }
    });

    // Serialize back to RDF/XML
    const updatedRDF = rdf.serialize(null, store, 'http://example.com', 'application/rdf+xml');
    return updatedRDF;
}

function testAlign() {
    // Original RDF/XML string
    let rdfData = `<rdf:RDF
    xmlns:ss="http://localhost:8080/sswap-servlet/"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:sswap="http://sswapmeet.sswap.info/sswap/"
    xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
    xmlns:ont="http://localhost:8080/SW_project/cottagebooking#">
    <sswap:Resource rdf:about="http://localhost:8080/sswap-servlet/bookingService">
    <sswap:providedBy rdf:resource="http://localhost:8080/sswap-servlet/res/resourceProvider"/>
    <sswap:name>Cottage booking sswap service</sswap:name>
    <sswap:oneLineDescription>A service that search available cottages</sswap:oneLineDescription>
    <sswap:operatesOn>
      <sswap:Graph>
        <sswap:hasMapping>
          <ont:BookingServiceRequest>
            <ont:requestPeopleCount rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
            >0</ont:requestPeopleCount>
            <ont:requestStartDate rdf:datatype="http://www.w3.org/2001/XMLSchema#date"
            ></ont:requestStartDate>
            <sswap:mapsTo>
              <sswap:Object>
                <ont:responseBookingStartDate rdf:datatype="http://www.w3.org/2001/XMLSchema#date"
                >0</ont:responseBookingStartDate>
                <ont:responseNearestCity></ont:responseNearestCity>
                <ont:responseNumberOfPlaces rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
                >0</ont:responseNumberOfPlaces>
                <rdf:type rdf:resource="http://localhost:8080/SW_project/cottagebooking#BookingServiceResponse"/>
                <ont:responseDistanceToLake rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
                >0</ont:responseDistanceToLake>
                <ont:responseBookingEndDate rdf:datatype="http://www.w3.org/2001/XMLSchema#date"
                >0</ont:responseBookingEndDate>
                <ont:responseCottageAddress></ont:responseCottageAddress>
                <ont:responseNumberOfBedrooms rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
                >0</ont:responseNumberOfBedrooms>
                <ont:responseDistanceToCity rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
                >0</ont:responseDistanceToCity>
                <ont:responseCottageImageUrl rdf:datatype="http://www.w3.org/2001/XMLSchema#anyURI"
                ></ont:responseCottageImageUrl>
                <ont:responseBookerName></ont:responseBookerName>
                <ont:responseBookingNumber></ont:responseBookingNumber>
              </sswap:Object>
            </sswap:mapsTo>
            <ont:requestMaxCityDistance rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
            >0</ont:requestMaxCityDistance>
            <ont:requestMaxLakeDistance rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
            >0</ont:requestMaxLakeDistance>
            <ont:requestNearestCity></ont:requestNearestCity>
            <rdf:type rdf:resource="http://sswapmeet.sswap.info/sswap/Subject"/>
            <ont:requestMaxDayShifts rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
            >0</ont:requestMaxDayShifts>
            <ont:requestBookerName></ont:requestBookerName>
            <ont:requestDayCount rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
            >0</ont:requestDayCount>
            <ont:requestBedroomCount rdf:datatype="http://www.w3.org/2001/XMLSchema#integer"
            >0</ont:requestBedroomCount>
          </ont:BookingServiceRequest>
        </sswap:hasMapping>
      </sswap:Graph>
    </sswap:operatesOn>
    <rdf:type rdf:resource="http://localhost:8080/SW_project/cottagebooking#BookingService"/>
  </sswap:Resource>
</rdf:RDF>
`;

    // Sample input object (full URIs as keys)
    const inputObject = {
        "http://example.com/requestPeopleCount": "4",
        "http://example.com/requestDayCount": "7",
        "http://example.com/requestMaxCityDistance": "100",
        "http://example.com/requestBedroomCount": "2",
        "http://example.com/requestBookerName": "John Doe",
        "http://example.com/requestMaxLakeDistance": "111",
        "http://example.com/requestStartDate": "2021-07-01",
        "http://example.com/requestNearestCity": "Helsinki",
        "http://example.com/requestMaxDayShifts": "1",
    };

    const updatedRDF = updateRDF(rdfData, inputObject);
    console.log(updatedRDF);
}