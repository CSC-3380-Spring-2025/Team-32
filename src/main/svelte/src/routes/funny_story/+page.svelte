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
    let funny_story = "";
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

    async function submitFunnyStory() {

        const submission: Submission = {
            playerId: get(userUUID) ?? '',
            type: 'story',
            content: funny_story
        };

	const response = await fetch('http://localhost:8080/submit', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(submission),
	});

	goto("/vote_story");

	// const result = await response.json();
	// console.log('Submit response:', result);
    }
</script>

<main class="container">
    <div class = "instructions">
        Make a story with the word!
    </div>
    <h1>{word}</h1>
    
    <input 
        id="story"
        type="text" 
        bind:value={funny_story} 
        placeholder="Enter your funny story..."
    />

    <button on:click={submitFunnyStory}>Submit</button>
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
</style>
