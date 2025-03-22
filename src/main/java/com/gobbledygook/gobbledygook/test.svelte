<script>
  import { onMount } from 'svelte';
  let word = '';
  let newWord = '';
  let ws;

  onMount(() => {
    ws = new WebSocket('ws://localhost:8080/websocket'); // Updated URL to match Spring Boot WebSocket endpoint

    ws.onmessage = (event) => {
        word = event.data;
    };
});

  function submitWord() {
    if (ws && ws.readyState === WebSocket.OPEN) {
      ws.send(newWord);
      newWord = '';
    }
  }
</script>

<main class="container">
  <h1>Current Word: {word}</h1>
  <input type="text" bind:value={newWord} placeholder="Enter your word..." />
  <button on:click={submitWord}>Submit</button>
</main>

<style>
  .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    font-family: Arial, sans-serif;
  }

  input {
    margin: 10px 0;
    padding: 10px;
    font-size: 1rem;
  }

  button {
    padding: 10px 20px;
    font-size: 1rem;
    cursor: pointer;
  }
</style>
