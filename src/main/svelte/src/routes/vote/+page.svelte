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
  goto("/powerup");
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



<div class = "wrapper">
  <div class = "instructions">
  Which is going straight to Webster's Dictionary?</div>

{#if voting_options.length < 4}
    <p>Waiting for voting options...</p>
{:else}
    {#each voting_options as option, index}
        <button on:click={() => submitVote(option.ownerId)}>{option.text}</button>
    {/each}
{/if}
</div>

<style>
.wrapper{
  max-width: 75%;
  margin: auto;
  text-align: center;

  button{
    display: block;
    margin: auto;
    margin-top: 1em;
    font-size: 17px;
    padding: 1ex;
  }

  .instructions {
        background-color: #0a41d8;
        text-align: center;

        padding: 5px;
        border: 2px solid green;
        border-radius: 10px;
    }

  
}
</style>

