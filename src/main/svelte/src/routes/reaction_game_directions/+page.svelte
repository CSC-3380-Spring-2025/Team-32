<script lang="ts">
  import { goto } from '$app/navigation';

  const startGame = () => {
    goto('/reaction_game');
  };
</script>

<main class="p-8 max-w-xl mx-auto text-center">
  <h1 class="text-3xl font-bold mb-4">How to Play the Reaction Minigame</h1>
  <ul class="text-left text-lg mb-6 space-y-2">
    <li>You must click the blue circles as they appear as fast as possible!</li>
    <li>Make sure not to click the red circles or else you'll lose!</li>
  </ul>

  <button 
    class="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700 transition"
    on:click={startGame}
  >
    Start Game
  </button>
</main>

<style>
  main {
    font-family: system-ui, sans-serif;
  }
</style>
  
