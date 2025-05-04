<script lang="ts">
    import { onMount } from "svelte";

    let canvas;
    let ctx;
    let drawing = false;
    let lineColor = "#ffffff";
    let lineWidth = 5;
    let bgColor = "#000000";
    let mazeColor = "#00CC00"

    function startDrawing(event) {
    drawing = true;
    draw(event);
  }

    function stopDrawing() {
    drawing = false;
    ctx.beginPath();
  }

  function draw(event) {
    if (!drawing) return;

    ctx.lineWidth = lineWidth;
    ctx.lineCap = lineCap;
    ctx.strokeStyle = color;

    ctx.lineTo(
      event.clientX - canvas.offsetLeft,
      event.clientY - canvas.offsetTop
    );
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(
      event.clientX - canvas.offsetLeft,
      event.clientY - canvas.offsetTop
    );
  }
  
  function setDefaultBackground() {
    ctx.fillStyle = "#000000"; // Default background color
    ctx.fillRect(0, 0, canvas.width, canvas.height);
  }

</script>

<div class="w-full flex justify-center">
  <canvas
    class="cursor-crosshair border-4 shadow-2xl rounded-2xl"
    bind:this={canvas}
    width="800"
    height="600"
    on:mousedown={startDrawing}
    on:mouseup={stopDrawing}
    on:mousemove={draw}
  ></canvas>

import { maze,display } from "./maze"
const X = 10
const Y = 10

let canvas = document.createElement('canvas')
document.body.appendChild(canvas)
let m = maze(X,Y)
display(canvas,m)
</div>