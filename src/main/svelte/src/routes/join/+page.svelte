<script lang="ts">
    import { goto } from "$app/navigation";
    import { userUUID } from "$lib/stores/user";
    let username: string = "";
    let socket: WebSocket | null = null;
    let serverResponse: string = "";

    async function joinGame(): void {
        await setupWebSocket();
        const response: Response = await fetch(
            `http://localhost:8080/join?username=${encodeURIComponent(username)}`,
            {
                method: "POST",
            },
        );

        const data: Record<string, unknown> = await response.json();
        serverResponse = data.message
            ? String(data.message)
            : "Unexpected response from server";
        console.log("Server response:", data);
        userUUID.set(data.id);
    }

    async function setupWebSocket(): Promise<void> {
        return new Promise((resolve, reject) => {
            socket = new WebSocket("ws://localhost:8080/websocket");

            socket.onopen = () => {
                console.log("WebSocket connected");
                resolve();
            };

            socket.onmessage = (event) => {
                console.log("Message from server:", event.data);
                if (event.data == "lobby full") {
                    console.log("Lobby full, redirecting...");
                    goto("/shiritori");
                }
            };

            socket.onerror = (error) => {
                console.error("WebSocket error:", error);
            };

            socket.onclose = () => {
                console.log("WebSocket closed");
            };
        });
    }
</script>

<div class="wrapper">
    <div class="shadowBox"><span>supercalifragilisticexpialidocious</span></div>
    <div class="instructions">Instructions go here</div>
    <div class="input-form">
        <input type="text" bind:value={username} placeholder="Enter username" />
        <button on:click={joinGame}>Join Game</button>
    </div>
    {#if serverResponse}
        <p>{serverResponse}</p>
    {/if}
</div>

<style>
    :root {
        font-family: system-ui, Avenir, Helvetica, Arial, sans-serif;
        line-height: 1.5;
        font-weight: 400;

        color-scheme: light dark;
        color: rgba(255, 255, 255, 0.87);
        background-color: #242424;

        font-synthesis: none;
        text-rendering: optimizeLegibility;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }

    a {
        font-weight: 500;
        color: #646cff;
        text-decoration: inherit;
    }
    a:hover {
        color: #535bf2;
    }

    body {
        margin: 0;
        display: flex;
        place-items: center;
        min-width: 320px;
        min-height: 100vh;
    }

    h1 {
        font-size: 3.2em;
        line-height: 1.1;
    }

    .card {
        padding: 2em;
    }

    #app {
        max-width: 1280px;
        margin: 0 auto;
        padding: 2rem;
        text-align: center;
    }

    button {
        border-radius: 8px;
        border: 1px solid transparent;
        padding: 0.6em 1.2em;
        font-size: 1em;
        font-weight: 500;
        font-family: inherit;
        background-color: #1a1a1a;
        cursor: pointer;
        transition: border-color 0.25s;
    }
    button:hover {
        border-color: #646cff;
    }
    button:focus,
    button:focus-visible {
        outline: 4px auto -webkit-focus-ring-color;
    }

    @media (prefers-color-scheme: light) {
        :root {
            color: #213547;
            background-color: #ffffff;
        }
        a:hover {
            color: #747bff;
        }
        button {
            background-color: #f9f9f9;
        }
    }
    .instructions {
        background-color: #0a41d8;
        text-align: center;

        padding: 5px;
        border: 2px solid green;
        border-radius: 10px;
    }

    .wrapper {
        max-width: 75%;
        margin: auto;
        position: relative;
    }

    .input-form{
        text-align: center;
    }

    .shadowBox {
    background-color: rgb(0, 0, 0);
    /* Fallback color */
    background-color: rgba(0, 0, 0, 0.2);
    /* Black w/opacity/see-through */
    border: 3px solid;
    position: absolute;
        right:0;
        margin-right: 100%;
        padding: 10px;
        text-orientation: upright;
        writing-mode: vertical-lr;
        font-size: 18px;
}

.shadowBox > span {
    text-transform: uppercase;
    text-align: center;
    text-decoration: underline;
    font-size: 32px;
    font-family: monospace;
    background: linear-gradient(to right, #6666ff, #0099ff , #00ff00, #ff3399, #6666ff);
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
    animation: rainbow_animation 6s ease-in-out infinite;
    background-size: 400% 100%;
}

@keyframes rainbow_animation {
    0%,100% {
        background-position: 0 0;
    }

    50% {
        background-position: 100% 0;
    }
}
</style>
