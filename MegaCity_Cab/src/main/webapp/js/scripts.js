document.addEventListener('DOMContentLoaded', function() {
    const riderRadio = document.getElementById('rider');
    const vehicleFields = document.getElementById('vehicleFields');

    function toggleVehicleFields() {
        const isRider = riderRadio.checked;
        vehicleFields.classList.toggle('hidden', !isRider);
        
        // Toggle required attribute for vehicle fields
        const vehicleInputs = vehicleFields.querySelectorAll('input, select');
        vehicleInputs.forEach(input => {
            input.required = isRider;
        });
    }

    // Add event listeners
    document.querySelectorAll('input[name="role"]').forEach(radio => {
        radio.addEventListener('change', toggleVehicleFields);
    });

    // Initial check
    toggleVehicleFields();
});

document.addEventListener('DOMContentLoaded', function() {
    // Edit Ride Modal
    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const rideId = this.dataset.rideId;
            fetch(`getRideDetails?rideId=${rideId}`)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('editRideId').value = data.rideId;
                    document.getElementById('editPickup').value = data.pickupLocation;
                    document.getElementById('editDestination').value = data.destination;
                    document.getElementById('editScheduledTime').value = data.scheduledTime;
                    document.getElementById('editModal').style.display = 'block';
                });
        });
    });

    // Delete Ride
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            if(confirm('Are you sure you want to delete this ride?')) {
                fetch(`deleteRide?rideId=${this.dataset.rideId}`, {
                    method: 'POST'
                }).then(() => window.location.reload());
            }
        });
    });

    // Modal Close
    document.querySelector('.close').addEventListener('click', () => {
        document.getElementById('editModal').style.display = 'none';
    });

    window.onclick = function(event) {
        const modal = document.getElementById('editModal');
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    }

    // Form Validation
    document.getElementById('editForm').addEventListener('submit', function(e) {
        const scheduledTime = new Date(document.getElementById('editScheduledTime').value);
        if (scheduledTime < new Date()) {
            e.preventDefault();
            alert('Cannot schedule rides in the past');
        }
    });
});