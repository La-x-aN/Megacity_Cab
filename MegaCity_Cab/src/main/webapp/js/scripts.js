document.addEventListener('DOMContentLoaded', function() {
            const roleRadios = document.querySelectorAll('input[name="role"]');
            const vehicleFields = document.getElementById('vehicleFields');

            // Toggle vehicle fields based on role selection
            roleRadios.forEach(radio => {
                radio.addEventListener('change', function() {
                    const showVehicleFields = this.value === 'Rider';
                    vehicleFields.classList.toggle('d-none', !showVehicleFields);
                    
                    // Toggle required attributes
                    const vehicleInputs = vehicleFields.querySelectorAll('input, select');
                    vehicleInputs.forEach(input => {
                        input.required = showVehicleFields;
                    });
                });
            });

            // Form validation
            const form = document.querySelector('.needs-validation');
            form.addEventListener('submit', function(event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });


		document.addEventListener('DOMContentLoaded', function() {
		         const riderModal = document.getElementById('riderModal');
		         riderModal.addEventListener('show.bs.modal', function(event) {
		             const button = event.relatedTarget;
		             document.getElementById('modalVehicleNumber').textContent = button.dataset.vehicleNumber;
		             document.getElementById('modalVehicleType').textContent = button.dataset.vehicleType;
		             document.getElementById('modalPhone').textContent = button.dataset.phone;
		         });
		     });