<script lang="ts">
import { goto } from '$app/navigation';

type Story = {
  playerId: string;
  text: string;
  votes: number;
};


let voting_options: Story[] = [];
const sleep = (ms: number) => new Promise((r) => setTimeout(r, ms));

async function submitVote(playerId: string): Promise<void> {
  try {
      const response : Response = await fetch(`http://localhost:8080/game/submitVote?playerId=${playerId}`, {
	method: "POST"
    });

    if (!response.ok) {
      console.error("Vote submission failed:", response.statusText);
    } else {
	console.log(`Vote for story submitted by ${playerId} sent successfully.`);
    }
  } catch (error) {
    console.error("Error submitting vote:", error);
  }
  goto("/reaction_game");
}

async function fetchVotingOptions() : Promise<void> {
  try {
      const response : Response = await fetch('http://localhost:8080/game/getStories', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    voting_options = await response.json();
    console.log('Fetched stories:', voting_options);
  } catch (error) {
    console.error('Failed to fetch stories:', error);
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
    The academy award for the best story goes to...
  </div>

  {#if voting_options.length < 4}
      <p>Waiting for voting options...</p>
  {:else}
      {#each voting_options as option, index}
          <button on:click={() => submitVote(option.playerId)}>{option.text}</button>
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

