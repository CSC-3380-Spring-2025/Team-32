<script lang="ts">

import { onMount } from 'svelte';
import { userUUID } from '$lib/stores/user';
import { get } from 'svelte/store';
import { goto } from '$app/navigation';

let score: number = 0;
let timeLeft: number = 10;
let isPlaying: boolean = false;
let interval: number | undefined;
let gameFinished: boolean = false;

type MinigameResult = {
  playerId : string;
  score : number;
};

async function startGame() : Promise<void> {
  score = 0;
  timeLeft = 10;
  isPlaying = true;

  interval = setInterval(() => {
    timeLeft -= 1;
    if (timeLeft <= 0) {
      clearInterval(interval);
      endGame();
    }
  }, 1000);
}

async function endGame(): Promise<void> {
  isPlaying = false;
  gameFinished = true;
  await sendResponse();
}

function mash() : void {
  if (isPlaying) {
    score += 1;
  }
}

async function sendResponse() : Promise<void> {
  const finalScore = score >= 60 ? 1 : 0;

  const result: MinigameResult = {
    playerId: get(userUUID) ?? '',
    score: finalScore
  };

  await fetch('http://localhost:8080/minigame', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(result),
  });
}
</script>
  
<main class="game">
    <h1>Button Mash Minigame!</h1>

    <h2>Try to mash the button at least 60 times!</h2>
  
    {#if !isPlaying && !gameFinished}
    <button on:click={startGame} class="start-button">Start Game</button>
  {/if}

  {#if isPlaying}
    <p>Time left: {timeLeft}s</p>
    <button on:click={mash} class="mash-button">Mash me!</button>
  {/if}

  {#if gameFinished}
    <p>Game over!</p>
    <button class="start-button" on:click={() => goto('/leaderboard')}>Go to Leaderboard</button>
  {/if}
  
    <h2>Score: {score}</h2>
</main>
 
<style>
    .game {
      text-align: center;
      margin-top: 2rem;
      font-family: sans-serif;
    }
  
    .start-button, .mash-button {
      padding: 1rem 2rem;
      font-size: 1.5rem;
      margin-top: 1rem;
      cursor: pointer;
      border: none;
      border-radius: 10px;
      background-color: #4CAF50;
      color: white;
      transition: background-color 0.3s;
    }
  
    .start-button:hover, .mash-button:hover {
      background-color: #45a049;
    }
  
    .mash-button {
      animation: pulse 0.5s infinite alternate;
    }
  
    @keyframes pulse {
      from { transform: scale(1); }
      to { transform: scale(1.1); }
    }
</style>
