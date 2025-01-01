const API_BASE_URL = "http://localhost:8090/api";
const API_HEADERS = {
    "Content-Type": "application/json",
    "x-api-key": "lUMaccz+6n5lluwz24aw4HTyXE9vIihx9ah9pKfR82A="
};

document.addEventListener('DOMContentLoaded', () => {
    listReservations(); // Affiche la liste des réservations dès le chargement de la page
    populateRoomOptions(); // Charge les options de salles dans le formulaire
    populateUserOptions(); // Charge les options des utilisateurs dans le formulaire
});

// Fonction pour créer une réservation
async function createReservation(event) {
    event.preventDefault();

    const reservationData = {
        roomId: document.getElementById("create-roomId").value,
        userId: document.getElementById("create-userId").value,
        reservationDate: document.getElementById("create-reservationDate").value,
        startTime: document.getElementById("create-startTime").value, // Ne plus ajouter les secondes
        endTime: document.getElementById("create-endTime").value, // Ne plus ajouter les secondes
        purpose: document.getElementById("create-purpose").value,
        code: generateReservationCode()
    };

    try {
        const response = await fetch(`${API_BASE_URL}/reservations`, {
            method: "POST",
            headers: API_HEADERS,
            body: JSON.stringify(reservationData),
        });

        if (!response.ok) {
            const errorText = await response.text();
            const errorMessage = JSON.parse(errorText).message || "Erreur inconnue";
            throw new Error(errorMessage);
        }

        const data = await response.json();
        console.log("Réservation créée :", data);
        showNotification("Réservation créée avec succès !");
        document.getElementById("createReservationForm").reset();
        listReservations(); // Rafraîchit la liste des réservations après création
    } catch (error) {
        console.error("Erreur lors de la création de la réservation :", error.message);
        showNotification(`Erreur lors de la création de la réservation : ${error.message}`, true);
    }
}

// Fonction pour générer un code de réservation unique en hexadécimal
function generateReservationCode() {
    const characters = "0123456789ABCDEF";
    let code = "";
    for (let i = 0; i < 8; i++) {
        code += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return code;
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

// Fonction pour récupérer toutes les réservations
async function listReservations() {
    try {
        const response = await fetch(`${API_BASE_URL}/reservations`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération de la liste des réservations");
        }

        const data = await response.json();
        console.log("Liste des réservations :", data);
        displayReservations(data); // Affiche les réservations dans le tableau
    } catch (error) {
        console.error("Erreur lors de la récupération des réservations :", error.message);
        showNotification(`Erreur lors de la récupération des réservations : ${error.message}`, true);
    }
}

// Fonction pour afficher les réservations dans le tableau
function displayReservations(reservations) {
    const tableBody = document.getElementById("reservationTableBody");
    tableBody.innerHTML = ""; // Efface le contenu actuel du tableau

    reservations.forEach((reservation) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${reservation.code}</td>
            <td>${reservation.username}</td>
            <td>${reservation.roomName}</td>
            <td>${reservation.reservationDate}</td>
            <td>${reservation.startTime}</td>
            <td>${reservation.endTime}</td>
            <td>${reservation.purpose}</td>
        `;
        tableBody.appendChild(row);
    });
}

// Fonction pour charger les options des salles
async function populateRoomOptions() {
    try {
        const response = await fetch(`${API_BASE_URL}/rooms`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des salles");
        }

        const rooms = await response.json();
        const roomSelect = document.getElementById("create-roomId");
        roomSelect.innerHTML = `<option value="" disabled selected>Sélectionnez une salle</option>`; // Réinitialise les options

        rooms.forEach((room) => {
            const option = document.createElement("option");
            option.value = room.id;
            option.textContent = room.name || `Salle ${room.id}`;
            roomSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Erreur lors de la récupération des salles :", error.message);
        showNotification(`Erreur lors de la récupération des salles : ${error.message}`, true);
    }
}

// Fonction pour charger les options des utilisateurs
async function populateUserOptions() {
    try {
        const response = await fetch(`${API_BASE_URL}/users`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des utilisateurs");
        }

        const users = await response.json();
        const userSelect = document.getElementById("create-userId");
        userSelect.innerHTML = `<option value="" disabled selected>Sélectionnez un utilisateur</option>`; // Réinitialise les options

        users.forEach((user) => {
            const option = document.createElement("option");
            option.value = user.id;
            option.textContent = user.username || `Utilisateur ${user.id}`;
            userSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Erreur lors de la récupération des utilisateurs :", error.message);
        showNotification(`Erreur lors de la récupération des utilisateurs : ${error.message}`, true);
    }
}

// Fonction pour récupérer une réservation par code
async function getReservationByCode() {
    const code = document.getElementById("retrieve-code").value;

    if (!code) {
        showNotification("Veuillez entrer un code valide.", true);
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/reservations/code/${code}`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Réservation non trouvée");
        }

        const data = await response.json();
        console.log("Réservation récupérée :", data);
        displayReservations([data]); // Affiche uniquement la réservation récupérée
    } catch (error) {
        console.error("Erreur lors de la récupération de la réservation :", error.message);
        showNotification(`Erreur lors de la récupération : ${error.message}`, true);
    }
}

// Fonction pour supprimer une réservation par code
async function deleteReservation(event) {
    event.preventDefault();

    const code = document.getElementById("delete-code").value;

    try {
        const response = await fetch(`${API_BASE_URL}/reservations/code/${code}`, {
            method: "DELETE",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            const errorText = await response.text();
            const errorMessage = JSON.parse(errorText).message || "Erreur inconnue";
            throw new Error(errorMessage);
        }

        showNotification("Réservation supprimée avec succès !");
        document.getElementById("deleteReservationForm").reset();
        listReservations(); // Rafraîchit la liste des réservations après suppression
        
    } catch (error) {
        console.error("Erreur lors de la suppression de la réservation :", error.message);
        showNotification(`Erreur lors de la suppression de la réservation : ${error.message}`, true);
    }
}
