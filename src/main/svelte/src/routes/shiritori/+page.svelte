<script lang="ts">
  type Submission = {
    playerId: string; 
    type: 'wordchain' | 'definition' | 'story'; 
    content: string;
  };

  import { onMount } from "svelte";
  import { goto } from '$app/navigation';
  import { userUUID } from '$lib/stores/user';
  import { get } from 'svelte/store';
  
  let word = "Waiting for word..."; 
  let wordchain = "";
  let ws;


  onMount(() => {
  (async () => {
    try {
      const response = await fetch('http://localhost:8080/game/getWord');
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      word = await response.text(); // This automatically triggers re-render
    } catch (error) {
      console.error('Failed to fetch word:', error);
    }
  })();
  });

  async function submitWordChain() {

      const submission: Submission = {
          playerId: get(userUUID) ?? '',
          type: 'wordchain',
          content: wordchain
      };

const response = await fetch('http://localhost:8080/submit', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(submission),
});

goto("/vote");

// const result = await response.json();
// console.log('Submit response:', result);
  }
</script>

<main class="container">
  <h1>Word: {word}</h1>
  
  <label for="wordchain">Your Word:</label>
  <input 
      id="wordchain"
      type="text" 
      bind:value={wordchain} 
      placeholder="Enter your word..."
  />

  <button on:click={submitWordChain}>Submit</button>
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
