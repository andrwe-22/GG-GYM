document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    fetch('/api/members/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email })
    })
    .then(response => response.json())
    .then(data => {
        if (data.token) {
            sessionStorage.setItem('token', data.token); // Сохранение токена
            window.location.href = '/profile.html'; // Перенаправление
        } else {
            console.error('Ошибка входа');
        }
    })
    .catch(error => console.error('Ошибка во время входа:', error));
});
