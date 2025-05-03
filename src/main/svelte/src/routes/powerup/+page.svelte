<script lang="ts">
  import { onMount } from 'svelte';
  import { userUUID } from '$lib/stores/user';
  import { get } from 'svelte/store';
  import { goto } from '$app/navigation';

  let powerup: string | null = null;
  let message = '';
  let loading = false;

  onMount(async () => {
    try {
	const response = await fetch('http://localhost:8080/game/getPowerup');
      if (!response.ok) throw new Error('Failed to get powerup');
      powerup = await response.text();
    } catch (err) {
      console.error(err);
      message = 'Error fetching powerup.';
    }
  });

  async function usePowerup() {
    if (!powerup) return;

    loading = true;
    message = '';
    try {
      const response = await fetch(`http://localhost:8080/game/usePowerup?playerId=${encodeURIComponent(get(userUUID))}&powerup=${encodeURIComponent(powerup)}`, {
        method: 'POST'
      });

      if (!response.ok) {
        throw new Error('Failed to use powerup');
      }

      const result = await response.text();
      message = `Powerup used: ${result}`;
      setTimeout(() => {
        goto('/funny_story');
      }, 3000);
    } catch (err) {
      console.error(err);
      message = 'Error using powerup.';
    } finally {
      loading = false;
    }
  }
</script>

{#if powerup}
  <h1>You've received: <strong>{powerup.replaceAll('_', ' ')}</strong>!</h1>
  <button
    on:click={usePowerup}
    disabled={loading}
    style="font-size: 2rem; padding: 1rem 2rem; margin-top: 1rem;"
  >
    {loading ? 'Using...' : 'Use Powerup'}
  </button>
{:else}
  <p>Loading your powerup...</p>
{/if}

{#if message}
  <p style="margin-top: 1rem;">{message}</p>
{/if}
