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
    
    let word : string= "Waiting for word..."; 
    let definition : string = "";

    onMount(() => {
    (async () => {
      try {
        const response : Response = await fetch('http://localhost:8080/game/getWord');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        word = await response.text();
      } catch (error) {
        console.error('Failed to fetch word:', error);
      }
    })();
    });

    async function submitDefinition() : Promise<void> {

        const submission: Submission = {
            playerId: get(userUUID) ?? '',
            type: 'definition',
            content: definition
        };

        const response : Response = await fetch('http://localhost:8080/submit', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(submission),
	});

	goto("/vote");
    }
</script>

<div class="page">
<img src="book1.webp" alt="Book Left" class="side-image" />
<main class="container">
    <div class = "instructions">
        What do ya think it means?
    </div>
    <h1 class="word-box"> {word}</h1>
    
    <label for="definition"></label>
    <input 
        id="definition"
        type="text" 
        bind:value={definition} 
        placeholder="Enter your definition..."
    />

    <button on:click={submitDefinition}>Submit</button>
</main>
<img src="book2.webp" alt="Book Right" class="side-image" />
</div>

<style>
    .page {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    gap: 1rem;
    margin: 2rem;
    }

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

    .instructions {
        background-color: #0a41d8;
        text-align: center;

        padding: 5px;
        border: 2px solid green;
        border-radius: 10px;
    }

    button:hover {
        background-color: #0056b3;
    }

    .word-box {
        width: 90%;
        height: 90%;
        text-align: center;
        padding: 3rem;
        background-image: url('heading.webp');
        background-size: cover;
        background-position: center;
        color: black;
        font-size: 2rem;
        border-radius: 10px;
    }

    .side-image {
        width: auto;
        height: auto;
        object-fit: contain;
	margin: 3rem;
    }
</style>
