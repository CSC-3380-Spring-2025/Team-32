
<!-- Generate Maze-->
<script lang="ts">
  import {maze, display} from "./maze"
  const X = 10
  const Y = 10

  let canvas = document.createElement('canvas')
  document.body.appendChild(canvas)
  let m = maze(X,Y)
  display(canvas, m)
 </script>

 <!--Create collision zones for maze-->

 <!--Canvas-->

 <!--Results-->