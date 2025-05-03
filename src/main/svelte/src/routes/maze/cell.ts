class Cell{
    x:number
    y:number
    wallsTo!:Cell[]
    visited = false
    constructor(x:number, y:number){
        this.x = x
        this.y = y
    }
    getTouchingNodes(cells:Cell[],blockSize:number){
        return cells.filter(n=>
        (this != n) &&
        (Math.hypot(this.x-n.x, this.y-n.y) == blockSize)
        )
    }
    
}