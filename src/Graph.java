/*
    To make adjacency matrix graph
 */
public class Graph {
    int[][] matrix; //represents adjacency matrix

    //take the size of the matrix
    public Graph(int size){
        this.matrix = new int[size][size];
    }
    //add an edge between 2 vertices a and b, a will point to be (set a-b to 1)
    public void addEdge(int a, int b){
        this.matrix[a][b] = 1;
    }

}

