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


document.addEventListener('DOMContentLoaded', () => {
           const modal = document.getElementById('riderModal');
           const span = document.querySelector('.close');
           
           document.querySelectorAll('.show-rider-btn').forEach(btn => {
               btn.addEventListener('click', () => {
                   document.getElementById('modalVehicleNumber').textContent = 
                       btn.dataset.vehicleNumber;
                   document.getElementById('modalVehicleType').textContent = 
                       btn.dataset.vehicleType;
                   document.getElementById('modalPhone').textContent = 
                       btn.dataset.phone;
                   modal.style.display = 'block';
               });
           });

           span.onclick = () => modal.style.display = 'none';
           window.onclick = (event) => {
               if (event.target === modal) modal.style.display = 'none';
           }
       });