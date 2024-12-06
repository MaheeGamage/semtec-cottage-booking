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

	const sswapUrl = document.getElementById('sswapServiceUrl').value.trim();

	if (
		sswapUrl !== '' &&
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
		q_str += '&requestBookerName=' + encodeURIComponent(name);
		q_str += '&requestPeopleCount=' + encodeURIComponent(peopleCount);
		q_str += '&requestBedroomCount=' + encodeURIComponent(bedroomCount);
		q_str += '&requestMaxLakeDistance=' + encodeURIComponent(maxLakeDistance);
		q_str += '&requestNearestCity=' + encodeURIComponent(city);
		q_str += '&requestMaxCityDistance=' + encodeURIComponent(maxCityDistance);
		q_str += '&requestDayCount=' + encodeURIComponent(dayCount);
		q_str += '&requestStartDate=' + encodeURIComponent(startDate);
		q_str += '&requestMaxDayShifts=' + encodeURIComponent(maxDayShifts);
		q_str += '&sswapUrl=' + encodeURIComponent(sswapUrl);

		// Define body content
		var bodyContent = {
			alignment: {
				requestPeopleCount: "requestDayCount",
				requestDayCount: "requestPeopleCount",
				requestMaxCityDistance: "requestMaxCityDistance",
				requestBedroomCount: "requestBedroomCount",
				requestBookerName: "requestBookerName",
				requestMaxLakeDistance: "requestMaxLakeDistance",
				requestStartDate: "requestStartDate",
				requestNearestCity: "requestNearestCity",
				requestMaxDayShifts: "requestMaxDayShifts"
			}
		}

		// Convert body content to JSON
		var bodyJson = JSON.stringify(bodyContent);

		doAjax('Booking?' + q_str, bodyJson, 'doQuery_back', 'post', 0);
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
				<img src="${booking.cottageImageUrl}" alt="Cottage Image">			
				<div class="details">
					<p><strong>Name of the Booker:</strong> ${booking.bookerName}</p>
	                <p><strong>Booking Number:</strong> ${booking.bookingNumber}</p>
	                <p><strong>Address:</strong> ${booking.cottageAddress}</p>	                
	                <p><strong>Number of Places:</strong> ${booking.numberOfPlaces}</p>
	                <p><strong>Number of Bedrooms:</strong> ${booking.numberOfBedrooms}</p>
	                <p><strong>Distance to Lake:</strong> ${booking.distanceToLake} meters</p>
	                <p><strong>Nearest City:</strong> ${booking.nearestCity}</p>
	                <p><strong>Distance to City:</strong> ${booking.distanceToCity} km</p>
	                <p><strong>Booking Start Date:</strong> ${booking.bookingStartDate}</p>
	                <p><strong>Booking End Date:</strong> ${booking.bookingEndDate}</p>
				</div>
            `;

			container.appendChild(bookingDiv);
		});
	} catch (error) {
		alert('Error: ' + error.message);
	}
}

function initiModal() {
	// Get the modal
	var modal = document.getElementById("configModal");

	// Get the button that opens the modal
	var btn = document.getElementById("configBtn");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks the button, open the modal 
	btn.onclick = function () {
		modal.style.display = "block";
	}

	// When the user clicks on <span> (x), close the modal
	span.onclick = function () {
		modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function (event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
}

function connectToService() {
	var url = document.getElementById('sswapServiceUrl').value;
	// Implement AJAX call to connect to the service
	// On success:
	document.getElementById('searchFormFieldSet').disabled = false;
	document.getElementById('modalMessage').innerText = 'Connected successfully!';
	document.getElementById('responseModal').style.display = 'block';
}

// Call the function to initialize the modal
initiModal();