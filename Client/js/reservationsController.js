const API_BASE_URL = "http://localhost:8090/api";
const API_HEADERS = {
    "Content-Type": "application/json",
    "x-api-key": "lUMaccz+6n5lluwz24aw4HTyXE9vIihx9ah9pKfR82A="
};

document.addEventListener('DOMContentLoaded', () => {
    loadRooms(); // Load the list of rooms on page load
    loadUsers(); // Load the list of users on page load
    getAllReservations(); // Load the list of reservations on page load
});

// Function to load rooms
async function loadRooms() {
    try {
        const response = await fetch(`${API_BASE_URL}/rooms`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération de la liste des salles");
        }

        const rooms = await response.json();
        const roomSelect = document.getElementById("create-roomId");

        rooms.forEach(room => {
            const option = document.createElement("option");
            option.value = room.id;
            option.textContent = room.name;
            roomSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Erreur lors de la récupération des salles :", error.message);
        showNotification(`Erreur lors de la récupération des salles : ${error.message}`, true);
    }
}

// Function to load users
async function loadUsers() {
    try {
        const response = await fetch(`${API_BASE_URL}/users`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération de la liste des utilisateurs");
        }

        const users = await response.json();
        const userSelect = document.getElementById("create-userId");

        users.forEach(user => {
            const option = document.createElement("option");
            option.value = user.id;
            option.textContent = user.username;
            userSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Erreur lors de la récupération des utilisateurs :", error.message);
        showNotification(`Erreur lors de la récupération des utilisateurs : ${error.message}`, true);
    }
}

// Function to display reservations
async function getAllReservations() {
    try {
        const response = await fetch(`${API_BASE_URL}/reservations/details`, {
            method: "GET",
            headers: API_HEADERS,
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération de la liste des réservations");
        }

        const data = await response.json();
        console.log("Liste des réservations :", data); // Log de la liste des réservations
        displayReservations(data); // Display the reservations in the table
    } catch (error) {
        console.error("Erreur lors de la récupération des réservations :", error.message);
        showNotification(`Erreur lors de la récupération des réservations : ${error.message}`, true);
    }
}

// Function to display reservations in the table
function displayReservations(reservations) {
    const tableBody = document.getElementById("reservationTableBody");
    tableBody.innerHTML = ""; // Clear the current table content

    reservations.forEach((reservation) => {
        console.log("Affichage de la réservation :", reservation); // Log chaque réservation
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${reservation.id}</td>
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

// Function to create a reservation
async function createReservation(event) {
    event.preventDefault();

    const reservationData = {
        userId: document.getElementById("create-userId").value,
        roomId: document.getElementById("create-roomId").value,
        reservationDate: document.getElementById("create-reservationDate").value,
        startTime: document.getElementById("create-startTime").value,
        endTime: document.getElementById("create-endTime").value,
        purpose: document.getElementById("create-purpose").value,
    };

    console.log("Données de réservation créées :", reservationData); // Log des données de réservation

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
        getAllReservations(); // Refresh the list of reservations
    } catch (error) {
        console.error("Erreur lors de la création de la réservation :", error.message);
        showNotification(`Erreur lors de la création de la réservation : ${error.message}`, true);
    }
}

// Function to delete a reservation by ID
async function deleteReservation(event) {
    event.preventDefault();

    const reservationId = document.getElementById("delete-reservation-id").value;
    console.log("ID de la réservation à supprimer :", reservationId); // Log de l'ID de la réservation

    try {
        const response = await fetch(`${API_BASE_URL}/reservations/${reservationId}`, {
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
        getAllReservations(); // Refresh the list of reservations after deletion
    } catch (error) {
        console.error("Erreur lors de la suppression de la réservation :", error.message);
        showNotification(`Erreur lors de la suppression de la réservation : ${error.message}`, true);
    }
}

// Function to display a notification
function showNotification(message, isError = false) {
    const notification = document.createElement("div");
    notification.className = `notification ${isError ? 'error' : 'success'}`;
    notification.textContent = message;
    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 5000);
}
