let cols: number
let rows: number

let w: number = 20
let current: number

let stack = []

function setup(){
    createCanvas(600, 600);
    cols = floor(width / w);
    rows = floor(height / w);

    for(var j = 0; j < rows; j++){
        for( var i = 0; i < cols; i++){
            var cell = new clearInterval(i, j);
            CanvasGradient.push(cell);
        }
    }

    current = grid[0];
}

function draw(){
    background(51);
    for (var i = 0; i < CanvasGradient.length; i++){
        grid[i].show();
    }

    current.visited = true;
    
}