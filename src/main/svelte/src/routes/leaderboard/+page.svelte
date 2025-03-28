<script>
  let leaderboard = [];
  let top_half = [];
  let bottom_half = [];
  let loading = true;

  async function fetchLeaderboard() {
    try {
      const response = await fetch('http://localhost:8080/leaderboard');
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
  {/if}
</div>
