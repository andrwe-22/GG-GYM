document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('join-form');
    const emailInput = form.elements['email'];
    const emailError = document.createElement('div');
    emailError.className = 'error-message';
    emailInput.parentNode.insertBefore(emailError, emailInput.nextSibling);

    // Email validation function
    function validateEmail() {
        const email = emailInput.value;
        const emailPattern = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}/i;
        if (email === '') {
            emailError.textContent = 'Email is required.';
            return false;
        } else if (!emailPattern.test(email)) {
            emailError.textContent = 'Please enter a valid email address.';
            return false;
        } else {
            emailError.textContent = '';
            return true;
        }
    }

    // Validate email in real-time
    emailInput.addEventListener('input', function() {
        validateEmail();
    });

    // Form submission event listener
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // Validate email before submission
        if (!validateEmail()) {
            alert('Please correct the errors in the form before submitting.');
            return false;
        }

        // Gather form data
        const formData = {
            name: form.elements['name'].value,
            email: form.elements['email'].value,
            membershipPackage: form.elements['membershipPackage'].value
        };

        // Send the form data using Fetch API
        fetch('/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    throw response;
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                alert('You have successfully joined!');
            })
            .catch((error) => {
                error.text().then(errorMessage => {
                    console.error('Error:', errorMessage);
                    emailError.textContent = errorMessage;
                    emailInput.focus();
                });
            });
    });
});
