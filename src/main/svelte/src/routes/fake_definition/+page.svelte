<script>
    import { onMount } from "svelte";
    
    let word = "Waiting for word..."; // Default text before receiving a word
    let definition = "";
    let ws;

    onMount(() => {
        ws = new WebSocket("ws://localhost:8080/websocket"); // Connect to backend WebSocket

        ws.onmessage = (event) => {
            word = event.data; // Update the word when received from backend
        };

        ws.onclose = () => {
            console.log("WebSocket disconnected");
        };
    });

    function submitDefinition() {
        if (definition.trim() && ws.readyState === WebSocket.OPEN) {
            ws.send(JSON.stringify({ word, definition })); // Send word & definition to backend
            definition = ""; // Clear input field
        }
    }
</script>

<main class="container">
    <h1>Word: {word}</h1>
    
    <label for="definition">Your Definition:</label>
    <input 
        id="definition"
        type="text" 
        bind:value={definition} 
        placeholder="Enter your definition..."
    />

    <button on:click={submitDefinition}>Submit</button>
</main>

<style>
    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
        font-family: Arial, sans-serif;
    }

    input {
        padding: 8px;
        font-size: 1rem;
        width: 300px;
    }

    button {
        padding: 10px 15px;
        font-size: 1rem;
        cursor: pointer;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
    }

    button:hover {
        background-color: #0056b3;
    }
</style>
