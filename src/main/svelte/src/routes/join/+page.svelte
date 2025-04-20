<script lang="ts">
    import { goto } from '$app/navigation';
    import { userUUID } from '$lib/stores/user';
    let username: string = "";
    let socket: WebSocket | null = null;
    let serverResponse: string = "";

    async function joinGame(): void {
        setupWebSocket();
        const response: Response = await fetch(`http://localhost:8080/join?username=${encodeURIComponent(username)}`, {
            method: "POST",
        });

        const data: Record<string, unknown> = await response.json();
        serverResponse = data.message ? String(data.message) : "Unexpected response from server";
        console.log("Server response:", data);
        userUUID.set(data.id);

    }

    function setupWebSocket(): void {
        socket = new WebSocket("ws://localhost:8080/websocket");

        socket.onopen = () => {
            console.log("WebSocket connected");
	    //            socket?.send(username);
        };

        socket.onmessage = (event) => {
            console.log("Message from server:", event.data);
            if (event.data == "lobby full") {
                console.log("Lobby full, redirecting...");
		// goto("/shiritori");
		// just navigating to fake_definition screen before shiritori screen is finished
		goto("/fake_definition");
	    }
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
{#if serverResponse}
    <p>{serverResponse}</p>
{/if}


<style>
@import './app.css';
</style>
