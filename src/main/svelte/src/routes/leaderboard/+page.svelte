<script lang="ts"> 
  import { goto } from '$app/navigation';
  import { page } from '$app/stores';
  import { userUUID } from '$lib/stores/user';
  import { get } from 'svelte/store';

  type LeaderboardEntry = {
    username: string;
    score: number;
  };

  let leaderboard : LeaderboardEntry[] = [];
  let top_half : LeaderboardEntry[] = [];
  let bottom_half : LeaderboardEntry[] = [];
  let loading : boolean = true;
  let socket: WebSocket | null = null;
  let serverResponse: string = "";
  let rejoined : boolean = false;

    async function resetGame() : Promise<void> {
      await fetch('http://localhost:8080/game/reset');
      goto('/join');
    }
    async function waitForRestart(): void {
        await setupWebSocket();
        const response: Response = await fetch(
            `http://localhost:8080/rejoin?id=${get(userUUID)}`,
            {
                method: "POST",
            },
        );

        const data: Record<string, unknown> = await response.json();
        serverResponse = data.message
            ? String(data.message)
            : "Unexpected response from server";
        console.log("Server response:", data);
        rejoined = true;
    }

    async function setupWebSocket(): Promise<void> {
        return new Promise((resolve, reject) => {
            socket = new WebSocket("ws://localhost:8080/websocket");

            socket.onopen = () => {
                console.log("WebSocket connected");
                resolve();
            };

            socket.onmessage = (event) => {
                console.log("Message from server:", event.data);
                if (event.data == "Game is restarting") {
                    console.log("Lobby full, redirecting...");
                    goto("/shiritori");
                }
            };

            socket.onerror = (error) => {
                console.error("WebSocket error:", error);
            };

            socket.onclose = () => {
                console.log("WebSocket closed");
            };
        });
    }

  async function fetchLeaderboard() : Promise<void> {
    try {
      const response : Response = await fetch('http://localhost:8080/leaderboard');
      if (!response.ok) throw new Error('Failed to fetch leaderboard');
      leaderboard = await response.json();
    } catch (error) {
      console.error('Error loading leaderboard:', error);
    } finally {
      loading = false;
    }
      top_half = leaderboard.slice(0, leaderboard.length/2);
      bottom_half = leaderboard.slice(leaderboard.length/2, leaderboard.length);
  }

  fetchLeaderboard();


</script>

<style>
  .leaderboard {
    max-width: 600px;
    margin: 2rem auto;
    padding: 1rem;
    border-radius: 12px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background: url("/forest_background.webp");
  }
  .entry {
    display: flex;
    justify-content: space-between;
    padding: 0.75rem 1rem;
    border-bottom: 1px solid #ddd;
  }
  .entry:last-child {
    border-bottom: none;
  }
  .rank {
    font-weight: bold;
  }
  
  .container {
    display: flex;
  }

  .half {
    flex: 1;
    margin: 30px;
  }

  .half:first-child {
      background: url("/sky.webp") ;
  }
  .half:last-child {
    background: url("/lava.webp");
    color: white;
  }
  h1, h2 {
    text-align: center;
    color: white;
  }

  .continuebutton{
    font-size: 20px;
    background-color: blue;
    color: white;
    border-radius: 7px;
    margin: auto;
    display: block;

  }

</style>

<div class="leaderboard">
  <h1>Leaderboard</h1>

  {#if loading}
    <p>Loading...</p>
  {:else}
    <div class="container">
      <div class="half">
        <h2>Top Players</h2>
        {#each top_half as { username, score }, index}
          <div class="entry">
            <span class="rank">{index + 1}.</span>
            <span>{username}</span>
            <span>{score} pts</span>
          </div>
        {/each}
      </div>

      <div class="half">
        <h2>Bottom Players</h2>
        {#each bottom_half as { username, score }, index}
          <div class="entry">
            <span class="rank">{index + top_half.length + 1}.</span>
            <span>{username}</span>
            <span>{score} pts</span>
          </div>
        {/each}
      </div>
    </div>
    {#if $page.url.searchParams.get("nextpage")}
    <div>
      <button class = "continuebutton" on:click="{goto("/"+$page.url.searchParams.get("nextpage"))}">Continue</button>
    </div>
    {:else if !rejoined}
    <div>
      <button class = "continuebutton" on:click="{waitForRestart}">Play again?</button>
    </div>
    {:else}
    <div>
      Waiting for other players...
    </div>
    {/if}
  {/if}
</div>
