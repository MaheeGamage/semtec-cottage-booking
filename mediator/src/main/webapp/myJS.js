function doQuery() {
	const name = document.getElementById('inpName').value.trim();
	const peopleCount = document.getElementById('inpPeopleCount').value.trim();
	const bedroomCount = document.getElementById('inpBedroomCount').value.trim();
	const maxLakeDistance = document.getElementById('inpMaxLakeDistance').value.trim();
	const city = document.getElementById('inpCity').value.trim();
	const maxCityDistance = document.getElementById('inpMaxCityDistance').value.trim();
	const dayCount = document.getElementById('inpDayCount').value.trim();
	const startDate = document.getElementById('inpStartDate').value.trim();
	const maxDayShifts = document.getElementById('inpMaxDayShifts').value.trim();

	if (
		name !== '' &&
		peopleCount !== '' &&
		bedroomCount !== '' &&
		maxLakeDistance !== '' &&
		city !== '' &&
		maxCityDistance !== '' &&
		dayCount !== '' &&
		startDate !== '' &&
		maxDayShifts !== ''
	) {
		var q_str = 'reqType=doQuery';
		q_str += '&name=' + encodeURIComponent(name);
		q_str += '&peopleCount=' + encodeURIComponent(peopleCount);
		q_str += '&bedroomCount=' + encodeURIComponent(bedroomCount);
		q_str += '&maxLakeDistance=' + encodeURIComponent(maxLakeDistance);
		q_str += '&city=' + encodeURIComponent(city);
		q_str += '&maxCityDistance=' + encodeURIComponent(maxCityDistance);
		q_str += '&dayCount=' + encodeURIComponent(dayCount);
		q_str += '&startDate=' + encodeURIComponent(startDate);
		q_str += '&maxDayShifts=' + encodeURIComponent(maxDayShifts);

		doAjax('Booking', q_str, 'doQuery_back', 'post', 0);
	} else {
		alert('Please, fill all the search fields...');
	}
}

function doQuery_back(result) {
	try {
		// Parse the result to ensure it's a JSON object
		const parsedResult = JSON.parse(result);

		// Check if the parsed result is an array
		if (!Array.isArray(parsedResult)) {
			throw new Error('Result is not an array');
		}

		// Check if each item in the array is an object
		parsedResult.forEach(item => {
			if (typeof item !== 'object' || item === null) {
				throw new Error('Array contains non-object elements');
			}
		});

		// If all validations pass, proceed to display the bookings
		const container = document.getElementById('booking-suggestion-container');
		container.innerHTML = ''; // Clear previous content

		parsedResult.forEach(booking => {
			const bookingDiv = document.createElement('div');
			bookingDiv.classList.add('booking');

			bookingDiv.innerHTML = `
				<p>Name of the Booker: ${booking.bookerName}</p>
                <p>Booking Number: ${booking.bookingNumber}</p>
                <p>Address: ${booking.cottageAddress}</p>
                <img src="${booking.cottageImageUrl}" alt="Cottage Image">
                <p>Number of Places: ${booking.numberOfPlaces}</p>
                <p>Number of Bedrooms: ${booking.numberOfBedrooms}</p>
                <p>Distance to Lake: ${booking.distanceToLake} meters</p>
                <p>Nearest City: ${booking.nearestCity}</p>
                <p>Distance to City: ${booking.distanceToCity} km</p>
                <p>Booking Start Date: ${booking.bookingStartDate}</p>
                <p>Booking End Date: ${booking.bookingEndDate}</p>
				<p>------------------------</p>
            `;

			container.appendChild(bookingDiv);
		});
	} catch (error) {
		alert('Error: ' + error.message);
	}
}





