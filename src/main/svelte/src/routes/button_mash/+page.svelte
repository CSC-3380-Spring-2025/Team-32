<script>
    import { onMount } from 'svelte';
    let score : number = 0;
    let timeLeft : number = 10;
    let isPlaying : boolean = false;
    /**
   * @type {number | undefined}
   */
    let interval;
  
    function startGame() : void {
      score = 0;
      timeLeft = 10;
      isPlaying = true;
  
      interval = setInterval(() => {
        timeLeft -= 1;
        if (timeLeft <= 0) {
          clearInterval(interval);
          isPlaying = false;
        }
      }, 1000);
    }
  
    function mash() : void {
      if (isPlaying) {
        score += 1;
      }
    }
  
    onMount(() => {
      // not sure waht to add here
    });
</script>
  
<main class="game">
    <h1>Button Mash Minigame!</h1>
  
    {#if !isPlaying}
      <button on:click={startGame} class="start-button">Start Game</button>
    {/if}
  
    {#if isPlaying}
      <p>Time left: {timeLeft}s</p>
      <button on:click={mash} class="mash-button">Mash me!</button>
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
