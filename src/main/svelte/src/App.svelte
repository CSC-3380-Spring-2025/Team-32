<script lang="ts">
    let username = "";
    let socket: WebSocket | null = null;

    async function joinGame() {
        setupWebSocket();
        const response = await fetch(`http://localhost:8080/join?username=${encodeURIComponent(username)}`, {
            method: "POST",
        });

        const data = await response.json();
        console.log("Server response:", data);
    }

    function setupWebSocket() {
        socket = new WebSocket("ws://localhost:8080/websocket");

        socket.onopen = () => {
            console.log("WebSocket connected");
	    //            socket?.send(username);
        };

        socket.onmessage = (event) => {
            console.log("Message from server:", event.data);
        };

        socket.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        socket.onclose = () => {
            console.log("WebSocket closed");
        };
    }
</script>

<input type="text" bind:value={username} placeholder="Enter username" />
<button on:click={joinGame}>Join Game</button>
