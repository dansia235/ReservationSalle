const apiUrl = 'http://localhost:8090/api/users';

async function createUser() {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch(apiUrl + '/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, email, password })
    });

    const result = await response.json();
    showNotification('Utilisateur créé avec succès');
    getUsers();
}

async function getUsers() {
    const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const result = await response.json();
    displayUsers(result);
}

async function deleteUser(id) {
    const response = await fetch(apiUrl + '/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (response.status === 204) {
        showNotification('Utilisateur supprimé avec succès');
        getUsers();
    }
}

function displayUsers(users) {
    const userResults = document.getElementById('user-results');
    userResults.innerHTML = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom d'utilisateur</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                ${users.map(user => `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>
                            <button onclick="deleteUser(${user.id})">Supprimer</button>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

function showNotification(message) {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.classList.add('show');
    setTimeout(() => {
        notification.classList.remove('show');
    }, 3000);
}

async function registerUser(username, email, password) {
    const response = await fetch('/api/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, email, password })
    });
    if (!response.ok) {
        throw new Error('Failed to register user');
    }
    return await response.json();
}


async function updateUser(id, userData) {
    const response = await fetch(`/api/users/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    });
    if (!response.ok) {
        throw new Error('Failed to update user');
    }
    return await response.json();
}


async function deleteUser(id) {
    const response = await fetch(`/api/users/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Failed to delete user');
    }
}


async function updateRoom(id, roomData) {
    const response = await fetch(`/api/rooms/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(roomData)
    });
    if (!response.ok) {
        throw new Error('Failed to update room');
    }
    return await response.json();
}

async function deleteRoom(id) {
    const response = await fetch(`/api/rooms/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Failed to delete room');
    }
}

async function getAllRooms() {
    const response = await fetch('/api/rooms', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Failed to fetch rooms');
    }
    return await response.json();
}

async function createReservation(reservationData) {
    const response = await fetch('/api/reservations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reservationData)
    });
    if (!response.ok) {
        throw new Error('Failed to create reservation');
    }
    return await response.json();
}

async function updateReservation(id, reservationData) {
    const response = await fetch(`/api/reservations/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reservationData)
    });
    if (!response.ok) {
        throw new Error('Failed to update reservation');
    }
    return await response.json();
}


async function getAllReservations() {
    const response = await fetch('/api/reservations', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Failed to fetch reservations');
    }
    return await response.json();
}

async function getRoomById(id) {
    const response = await fetch(`/api/rooms/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Room not found');
    }
    return await response.json();
}

async function getReservationsByRoomId(id) {
    const response = await fetch(`/api/rooms/${id}/reservations`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Failed to fetch reservations for room');
    }
    return await response.json();
}
