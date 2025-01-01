const API_BASE_URL = "http://localhost:8090/api";
const API_HEADERS = {
    "Content-Type": "application/json",
    "x-api-key": "lUMaccz+6n5lluwz24aw4HTyXE9vIihx9ah9pKfR82A="
};

document.addEventListener('DOMContentLoaded', () => {
    getAllRooms(); // Affiche la liste des salles dès le chargement de la page
});

// Fonction pour créer une salle
async function createRoom(event) {
    event.preventDefault();

    const roomData = {
        name: document.getElementById("room-name").value,
        capacity: document.getElementById("room-capacity").value,
        location: document.getElementById("room-location").value,
    };

    try {
        const response = await fetch(`${API_BASE_URL}/rooms`, {
            method: "POST",
            headers: API_HEADERS,
            body: JSON.stringify(roomData),
        });

        if (!response.ok) {
            const errorText = await response.text();
            const errorMessage = JSON.parse(errorText).message || "Erreur inconnue";
            throw new Error(errorMessage);
        }

        const data = await response.json();
        console.log("Salle créée :", data);
        showNotification("Salle créée avec succès !");
        document.querySelector("form").reset();
        getAllRooms(); // Rafraîchit la liste des salles après création
    } catch (error) {
        console.error("Erreur lors de la création de la salle :", error.message);
        showNotification(`Erreur lors de la création de la salle : ${error.message}`, true);
    }
}

// Fonction pour mettre à jour une salle
async function updateRoom(event) {
    event.preventDefault();

    const roomId = document.getElementById("update-room-id").value;
    const roomData = {
        name: document.getElementById("update-room-name").value,
        capacity: document.getElementById("update-room-capacity").value,
        location: document.getElementById("update-room-location").value,
    };

    try {
        const response = await fetch(`${API_BASE_URL}/rooms/${roomId}`, {
            method: "PUT",
            headers: API_HEADERS,
            body: JSON.stringify(roomData),
        });

        if (!response.ok) {
            const errorText = await response.text();
            const errorMessage = JSON.parse(errorText).message || "Erreur inconnue";
            throw new Error(errorMessage);
        }

        const data = await response.json();
        console.log("Salle mise à jour :", data);
        showNotification("Salle mise à jour avec succès !");
        document.querySelector("form").reset();
        getAllRooms(); // Rafraîchit la liste des salles après mise à jour
    } catch (error) {
        console.error("Erreur lors de la mise à jour de la salle :", error.message);
        showNotification(`Erreur lors de la mise à jour de la salle : ${error.message}`, true);
    }
}

// Fonction pour supprimer une salle
async function deleteRoom(event) {
    event.preventDefault();

    const roomId = document.getElementById("delete-room-id").value;

    try {
        const response = await fetch(`${API_BASE_URL}/rooms/${roomId}`, {
            method: "DELETE",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            const errorText = await response.text();
            const errorMessage = JSON.parse(errorText).message || "Erreur inconnue";
            throw new Error(errorMessage);
        }

        showNotification("Salle supprimée avec succès !");
        document.querySelector("form").reset();
        getAllRooms(); // Rafraîchit la liste des salles après suppression
    } catch (error) {
        console.error("Erreur lors de la suppression de la salle :", error.message);
        showNotification(`Erreur lors de la suppression de la salle : ${error.message}`, true);
    }
}

// Fonction pour récupérer toutes les salles
async function getAllRooms() {
    try {
        const response = await fetch(`${API_BASE_URL}/rooms`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération de la liste des salles");
        }

        const data = await response.json();
        console.log("Liste des salles :", data);
        displayRooms(data); // Affiche les salles dans le tableau
    } catch (error) {
        console.error("Erreur lors de la récupération des salles :", error.message);
        showNotification(`Erreur lors de la récupération des salles : ${error.message}`, true);
    }
}

// Fonction pour afficher les salles dans le tableau
function displayRooms(rooms) {
    const tableBody = document.getElementById("roomTableBody");
    tableBody.innerHTML = ""; // Efface le contenu actuel du tableau

    rooms.forEach((room) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${room.id}</td>
            <td>${room.name}</td>
            <td>${room.capacity}</td>
            <td>${room.location}</td>
        `;
        tableBody.appendChild(row);
    });
}

// Fonction pour récupérer une salle par ID
async function getRoomById(event) {
    event.preventDefault();

    const roomId = document.getElementById("retrieve-room-id").value;

    try {
        const response = await fetch(`${API_BASE_URL}/rooms/${roomId}`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Salle non trouvée");
        }

        const data = await response.json();
        console.log("Salle récupérée :", data);
        displayRooms([data]); // Affiche uniquement la salle récupérée
    } catch (error) {
        console.error("Erreur lors de la récupération de la salle :", error.message);
        showNotification(`Erreur lors de la récupération de la salle : ${error.message}`, true);
    }
}

// Fonction pour afficher une notification
function showNotification(message, isError = false) {
    const notification = document.createElement("div");
    notification.className = `notification ${isError ? 'error' : 'success'}`;
    notification.textContent = message;
    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 5000);
}
