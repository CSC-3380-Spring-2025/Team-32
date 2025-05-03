<script lang="ts">

import { onMount } from 'svelte';
import Clickable from '$lib/Clickable.svelte';
import { userUUID } from '$lib/stores/user';
import { get } from 'svelte/store';
import { goto } from '$app/navigation';

type ClickableType = {
  id: number;
  x: number;
  y: number;
  isBomb: boolean;
};

type MinigameResult = {
  playerId : string;
  score : number;
};

let score : number = 0;
let nextId : number = 0; // ids to identify bubbles
let clickables : ClickableType[] = [];
let gameRunning : boolean = true;

let width : number = 0;
let height : number = 0;

onMount(() => {
  function updateSize() {
    width = window.innerWidth;
    height = window.innerHeight;
  }

  updateSize();
  window.addEventListener('resize', updateSize);

  return () => {
    window.removeEventListener('resize', updateSize);
  };
});

function spawnClickable() {
  console.log("Spawning a bubble");
  const x : number = Math.random() * (width - 50);
  const y : number = Math.random() * (height - 50);
  const isBomb : boolean = Math.random() <= 0.25;
      //  const lifetime = 1000 + Math.random() * 1000;

  clickables = [
    ...clickables,
    {
      id: nextId++,
      x,
      y,
      isBomb	
    }
  ];
}

function startSpawningClickables() {
  function spawnAndScheduleNext() {
    if (!gameRunning) {
	console.log("Game over");
	return;
    }

    spawnClickable();

    const delay = 800 + Math.random() * 1200;
    setTimeout(spawnAndScheduleNext, delay);
  }

  spawnAndScheduleNext();
}

async function handlePop(id : number) {
  const clicked : ClickableType = clickables.find(b => b.id === id);
  if (!clicked) return;

  if (clicked.isBomb) {
    // send failure message to server
    gameRunning = false;
    goto("/leaderboard");
    return;
  }

  clickables = clickables.filter(b => b.id !== id);
  score++;
  if (score > 10) {
    gameRunning = false;
    await sendResponse();
    goto("/leaderboard");
  }
}

function handleExpire(id) {
  clickables = clickables.filter(b => b.id !== id);
}

/* send response to server indicating win or loss of the game.
status = true indicates winning the game */
async function sendResponse() {
  const result : MinigameResult = {
	playerId: get(userUUID) ?? '',
	score: 1
  };
    
  await fetch('http://localhost:8080/minigame', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(result),
  });
}

startSpawningClickables();
</script>

<div>
{#each clickables as clickable (clickable.id)}
  <Clickable
    x={clickable.x}
    y={clickable.y}
    isBomb={clickable.isBomb}
    on:pop={() => handlePop(clickable.id)}
    on:expire={() => handleExpire(clickable.id)}
  />
{/each}
</div>
