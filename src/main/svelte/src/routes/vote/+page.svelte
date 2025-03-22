<script lang="ts">
    import {browser} from '$app/environment';
    let voting_options: string[] = [];
    let socket: WebSocket | null = null;

    async function submitVote(index: number): Promise<void> {
        try {
            const response = await fetch(
                `http://localhost:8080/submitVote?playerId=${playerUUID}&definitionId=${index}`,
                { method: "POST" }
            );

            if (!response.ok) {
                console.error("Vote submission failed:", response.statusText);
            } else {
                console.log(`Vote for option ${index} sent successfully.`);
            }
        } catch (error) {
            console.error("Error submitting vote:", error);
        }
    }

    function setupWebSocket(): void {
        socket = new WebSocket("ws://localhost:8080/websocket");

        socket.onopen = () => {
            console.log("WebSocket connected");
        };

	    /* The WebSocket message this page is listening for is of the form:
	    {
		"type": "voting_options",
		"data": [
		    "voting option 1",
		    "voting option 2",
		    "voting option 3"
		]
	    }
	    First we check if the message type is the expected one (voting_options), and then we store the voting options in an array to iterate through and render
	     */
        socket.onmessage = (event: MessageEvent): void => {
            try {
                const message = JSON.parse(event.data) as { type: string; data: string[] };
                
                if (message.type === "voting_options" && Array.isArray(message.data)) {
                    voting_options = message.data;
                } else {
		    console.error("Wrong message type");
		}
            } catch (error) {
                console.error("Error parsing WebSocket message:", error);
            }
        };

        socket.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        socket.onclose = () => {
            console.log("WebSocket closed");
        };
    }


    if (browser) {
        setupWebSocket();
    }

</script>

<h3>Vote for an Option:</h3>

{#if voting_options.length === 0}
    <p>Waiting for voting options...</p>
{:else}
    {#each voting_options as option, index}
        <button on:click={() => submitVote(index)}>{option}</button>
    {/each}
{/if}

