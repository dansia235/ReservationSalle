const API_BASE_URL = "http://localhost:8090/api";
const API_HEADERS = {
    "Content-Type": "application/json",
    "x-api-key": "lUMaccz+6n5lluwz24aw4HTyXE9vIihx9ah9pKfR82A="
};

async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('error-message');

    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: "POST",
            headers: API_HEADERS,
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            const errorText = await response.text();
            if (errorText === "Nom d'utilisateur ou mot de passe incorrect.") {
                throw new Error('Nom d\'utilisateur ou mot de passe incorrect.');
            } else {
                throw new Error('Erreur lors de l\'authentification');
            }
        }

        const data = await response.json();
        console.log("Utilisateur authentifié :", data);
        window.location.href = 'index.html';  // Redirige vers la page d'accueil après la connexion réussie
    } catch (error) {
        console.error("Erreur lors de l'authentification :", error);
        errorMessage.textContent = "Nom d'utilisateur ou mot de passe incorrect.";
    }
}

