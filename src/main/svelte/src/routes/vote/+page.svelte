<script lang="ts">
import {browser} from '$app/environment';

type Definition = {
  ownerId: string;
  text: string;
  id: string;
  votes: number;
};


let voting_options: Definition[] = [];
let socket: WebSocket | null = null;

async function submitVote(ownerId: string): Promise<void> {
  try {
      const response = await fetch(`http://localhost:8080/game/submitVote?playerId=${ownerId}`, {
	method: "POST"
    });

    if (!response.ok) {
      console.error("Vote submission failed:", response.statusText);
    } else {
	console.log(`Vote for definition submitted by ${ownerId} sent successfully.`);
    }
  } catch (error) {
    console.error("Error submitting vote:", error);
  }
}

async function fetchVotingOptions() {
  try {
      const response = await fetch('http://localhost:8080/game/getDefinitions', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    voting_options = await response.json();
    console.log('Fetched definitions:', voting_options);
  } catch (error) {
    console.error('Failed to fetch definitions:', error);
  }
}

if (browser) {
  fetchVotingOptions();
}
</script>

<h3>Vote for an Option:</h3>

{#if voting_options.length === 0}
    <p>Waiting for voting options...</p>
{:else}
    {#each voting_options as option, index}
        <button on:click={() => submitVote(option.ownerId)}>{option.text}</button>
    {/each}
{/if}

