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

let word: string = "Waiting for word...";
let wordchain: string = "";
let timeLimit: number = 10;
let timeRemaining: number = timeLimit;
let enteredWords: string[] = [];
let effect: string = "";

onMount(() => {
  (async () => {
    try {
      const response : Response = await fetch('http://localhost:8080/game/getWord');
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      word = await response.text();
      startTimer();
    } catch (error) {
      console.error('Failed to fetch word:', error);
    }
  })();
});

function startTimer(): void {
  enteredWords = [];
  timeRemaining = timeLimit;
  const timer = setInterval(() => {
    timeRemaining--;
    if (timeRemaining <= 0) {
      clearInterval(timer);
    }
  }, 1000);
}

function handleEnter(event: KeyboardEvent): void {
  if (event.key === "Enter") {
    submitWordChain();
  }
}

function nextScreen(): void {
  goto("/leaderboard?nextpage=fake_definition");
}

async function submitWordChain(): Promise<void> {
  effect = "";
  const submission: Submission = {
    playerId: get(userUUID) ?? '',
    type: 'wordchain',
    content: wordchain,
  };

  try {
    const response : Response = await fetch('http://localhost:8080/submit', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(submission),
    });

    const body = await response.text();
    if (body === "Accepted") {
      enteredWords = [...enteredWords, wordchain];
      effect = "hit";
    } else {
      effect = "miss";
    }

    wordchain = "";
    setTimeout(() => effect = "", 1000);
  } catch (error) {
    console.error("Error submitting wordchain:", error);
    effect = "miss";
  }
}
</script>

<main class="container">
  <div class = "instructions">
    Type words with last letter of the word!
  </div>
  <h1>{word}</h1>



  {#if timeRemaining == 0}
  <div class = "timer">
    Time's Up!
  </div>
  <button on:click={nextScreen}>Continue</button>
  {:else}
  <div class = "timer">
    {timeRemaining}
  </div>

  <div class = "effect {effect}">
    
  </div>

  <input 
      id="wordchain"
      type="text" 
      bind:value={wordchain}
      on:keydown={handleEnter} 
      placeholder="Enter your word..."
  />
  {/if}
  
  
<div class = "words">
  {#each enteredWords as enteredWord}
    <span class = "word">{enteredWord}</span>
  {/each}
</div>
  
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

  .effect{
    position:relative;

    &.hit::after{
        content: "Hit!";
        position:absolute;
        color: green;
        margin-top: -1ex;

      }
      &.miss::after{
        content: "Miss!";
        position:absolute;
        color: red;
        margin-top: -1ex;
      }
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

  .timer{
    font-size: 100px;
  }

  .instructions {
        background-color: #0a41d8;
        text-align: center;

        padding: 5px;
        border: 2px solid green;
        border-radius: 10px;
    }

  .words{
    .word{
      padding: 1ex;
    }
  }
</style>
