<script lang="ts">
  import { createEventDispatcher, onMount } from 'svelte';

  export let x : number;
  export let y : number;
  export let isBomb : boolean;
  let lifetime : number = 1000; // milliseconds

  const dispatch = createEventDispatcher<{
    pop: void;
    expire: void;
  }>();
  let timeout : number;

  onMount(() => {
    timeout = setTimeout(() => {
      dispatch('expire');
    }, lifetime);
  });

  function handleClick() {
    clearTimeout(timeout);
    dispatch('pop');
  }
</script>

<style>
  .clickable {
    position: absolute;
    width: 150px;
    height: 150px;
    border-radius: 50%;
    cursor: pointer;
    transition: transform 0.1s ease;
  }

  .clickable:active {
    transform: scale(0.9);
  }
</style>

<div
  class="clickable"
  style="top: {y}px; left: {x}px; background: {isBomb ? 'red' : 'lightblue'};"
  on:click={handleClick}
/>
