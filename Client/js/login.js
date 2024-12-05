async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('error-message');

    const response = await fetch('http://localhost:8090/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        window.location.href = 'index.html';
    } else {
        const errorText = await response.text();
        errorMessage.textContent = errorText;
    }
}
