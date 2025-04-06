const express = require('express');
const http = require('http');
const { Server } = require("socket.io");
const { v4: uuidv4 } = require('uuid'); // For generating unique Game IDs

const app = express();
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: "*", // Allow all origins for development; restrict in production
        methods: ["GET", "POST"]
    }
});

const gameSessions = new Map(); // To store active game sessions

app.get('/', (req, res) => {
    res.send('Math Clash Backend is running!');
});

app.post('/api/createGame', (req, res) => {
    const gameId = uuidv4().substring(0, 8).toUpperCase(); // Generate a short unique ID
    gameSessions.set(gameId, {
        id: gameId,
        players: [],
        status: 'waiting'
    });
    res.json({ gameId });
});

io.on('connection', (socket) => {
    console.log('A user connected:', socket.id);

    socket.on('joinGame', (gameId) => {
        const session = gameSessions.get(gameId);
        if (session && session.status === 'waiting' && session.players.length < 2) {
            session.players.push(socket.id);
            socket.join(gameId); // Join the Socket.IO room for this game

            if (session.players.length === 2) {
                session.status = 'inProgress';
                io.to(gameId).emit('startGame', { player1Id: session.players[0], player2Id: session.players[1] });
            } else {
                socket.emit('waitingForOpponent');
            }
        } else {
            socket.emit('joinFailed', { message: 'Invalid Game ID or game is full.' });
        }
    });

    socket.on('sendAnswer', (data) => {
        const { gameId, questionId, answer } = data;
        io.to(gameId).emit('receiveAnswer', { playerId: socket.id, questionId, answer });
    });

    socket.on('disconnect', () => {
        console.log('User disconnected:', socket.id);
        // Implement logic to handle disconnections during a game if needed
        for (const [gameId, session] of gameSessions.entries()) {
            session.players = session.players.filter(id => id !== socket.id);
            if (session.status === 'inProgress' && session.players.length < 2) {
                io.to(gameId).emit('opponentDisconnected');
                gameSessions.delete(gameId); // For simplicity, end the game
            } else if (session.status === 'waiting' && session.players.length === 0) {
                gameSessions.delete(gameId); // Clean up empty waiting sessions
            }
        }
    });
});

const port = 3000; // You can choose a different port
server.listen(port, () => {
    console.log(`Server listening on port ${port}`);
});