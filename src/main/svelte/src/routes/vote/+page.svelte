<script lang="ts">
import { goto } from '$app/navigation';

type Definition = {
  ownerId: string;
  text: string;
  id: string;
  votes: number;
};


let voting_options: Definition[] = [];
let socket: WebSocket | null = null;
const sleep = (ms: number) => new Promise((r) => setTimeout(r, ms));

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
  goto("/leaderboard");
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

(async () => {
  while (voting_options.length < 4) {
      fetchVotingOptions();
      await sleep(2500);
}
})();

</script>

<h3>Vote for an Option:</h3>

{#if voting_options.length < 4}
    <p>Waiting for voting options...</p>
{:else}
    {#each voting_options as option, index}
        <button on:click={() => submitVote(option.ownerId)}>{option.text}</button>
    {/each}
{/if}

