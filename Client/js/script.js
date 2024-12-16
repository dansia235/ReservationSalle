const API_BASE_URL = "http://localhost:8090/api";
const API_HEADERS = {
    "Content-Type": "application/json",
    "x-api-key": "lUMaccz+6n5lluwz24aw4HTyXE9vIihx9ah9pKfR82A="
};

document.addEventListener('DOMContentLoaded', () => {
    listUsers();  // Affiche la liste des utilisateurs dès l'ouverture de la page
});

async function registerUser(username, email, password) {
    try {
        const response = await fetch(`${API_BASE_URL}/users/register`, {
            method: "POST",
            headers: API_HEADERS,
            body: JSON.stringify({ username, email, password })
        });

        if (!response.ok) {
            const errorText = await response.text();
            try {
                const errorData = JSON.parse(errorText);
                throw new Error(`Erreur lors de l'enregistrement : ${errorData.message}`);
            } catch (e) {
                throw new Error('Le nom d\'utilisateur ou l\'adresse email existe déjà.');
            }
        }

        const data = await response.json();
        console.log("Utilisateur enregistré :", data);
        alert(`Utilisateur enregistré : ${data.username}`);
        listUsers();  // Rafraîchir la liste des utilisateurs après l'enregistrement
    } catch (error) {
        console.error("Erreur lors de l'enregistrement :", error);
        alert(`Erreur lors de l'enregistrement : ${error.message}`);
    }
}

async function getUserByUsername(username) {
    try {
        const response = await fetch(`${API_BASE_URL}/users/${username}`, {
            method: "GET",
            headers: API_HEADERS
        });
        if (!response.ok) {
            throw new Error('Erreur lors de la récupération');
        }
        const data = await response.json();
        console.log("Utilisateur récupéré :", data);
        displayUsers([data]);  // Affichez uniquement l'utilisateur récupéré dans le tableau
    } catch (error) {
        console.error("Erreur lors de la récupération :", error);
        alert(`Erreur lors de la récupération : ${error.message}`);
    }
}

async function listUsers() {
    try {
        const response = await fetch(`${API_BASE_URL}/users`, {
            method: "GET",
            headers: API_HEADERS
        });
        if (!response.ok) {
            throw new Error('Erreur lors de la récupération de la liste');
        }
        const data = await response.json();
        console.log("Liste des utilisateurs :", data);
        displayUsers(data);  // Affichez les utilisateurs dans le tableau
    } catch (error) {
        console.error("Erreur lors de la récupération de la liste :", error);
        alert(`Erreur lors de la récupération de la liste : ${error.message}`);
    }
}

function displayUsers(users) {
    const tableBody = document.getElementById("userTableBody");
    tableBody.innerHTML = "";  // Efface le contenu actuel du tableau

    users.forEach(user => {
        const row = document.createElement("tr");
        const usernameCell = document.createElement("td");
        const emailCell = document.createElement("td");

        usernameCell.textContent = user.username;
        emailCell.textContent = user.email;

        row.appendChild(usernameCell);
        row.appendChild(emailCell);
        tableBody.appendChild(row);
    });
}

async function updateUser(username, email, password) {
    try {
        const response = await fetch(`${API_BASE_URL}/users/${username}`, {
            method: "PUT",
            headers: API_HEADERS,
            body: JSON.stringify({ email, password })
        });
        if (!response.ok) {
            throw new Error('Erreur lors de la mise à jour');
        }
        const data = await response.json();
        console.log("Utilisateur mis à jour :", data);
        alert(`Utilisateur mis à jour : ${JSON.stringify(data)}`);
        listUsers();  // Rafraîchir la liste des utilisateurs après la mise à jour
    } catch (error) {
        console.error("Erreur lors de la mise à jour :", error);
        alert(`Erreur lors de la mise à jour : ${error.message}`);
    }
}

async function deleteUser(username) {
    try {
        const response = await fetch(`${API_BASE_URL}/users/${username}`, {
            method: "DELETE",
            headers: API_HEADERS
        });
        if (!response.ok) {
            throw new Error('Erreur lors de la suppression');
        }
        console.log("Utilisateur supprimé :", response.status);
        alert(`Utilisateur supprimé (Nom d'utilisateur: ${username})`);
        listUsers();  // Rafraîchir la liste des utilisateurs après la suppression
    } catch (error) {
        console.error("Erreur lors de la suppression :", error);
        alert(`Erreur lors de la suppression : ${error.message}`);
    }
}