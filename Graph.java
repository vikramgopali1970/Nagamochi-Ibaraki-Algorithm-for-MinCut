package NagamochiIbarakiAlgorithm;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Graph {

    public static int N = 25;                               // Total number of vertices
    public static int M;                                    // Total number of edges

    HashMap<Integer,Vertex> vertex;                         // Map names to vertices
    HashSet<Edge> edges;                                    // Collection of all edges
    HashMap<Vertex, HashSet<Edge>> adjList;                 // Adjacency List of Graph

    public Graph(){
        this.adjList = new HashMap<Vertex, HashSet<Edge>>();
        this.vertex = new HashMap<Integer, Vertex>();
        this.edges = new HashSet<Edge>();
    }

    /**
     * This method is used to generate the vertices randomly with random weights.
     * */
    public void generateVertices(){
        for(int i=0;i<N;i++){
            Vertex inpV = new Vertex(i);
            this.vertex.put(i, inpV);
            this.adjList.put(inpV, new HashSet<Edge>());
        }
    }

    /**
     * This method is used to accept user input vertices.
     * */
    public void getVertices(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the number of vertices");
        int n = sc.nextInt();
        N = n;
        for(int i=0;i<n;i++){
            int num = sc.nextInt();
            Vertex inpV = new Vertex(num);
            this.vertex.put(num, inpV);
            this.adjList.put(inpV, new HashSet<Edge>());
        }
        System.out.println(this.vertex);
    }

    /**
     * This method is used to accept user input edges and edge-weights.
     * */
    public void getEdges(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the number of vertices");
        int m = sc.nextInt();
        int pedge = 0;
        for(int i=0;i<m;i++){
            int f = sc.nextInt();
            int t = sc.nextInt();
            int c = sc.nextInt();
            Edge e = new Edge(this.vertex.get(f),this.vertex.get(t),c);
            this.edges.add(e);
            HashSet<Edge> tmpEdge = this.adjList.get(this.vertex.get(f));
            if(tmpEdge.contains(e)){
                pedge++;
            }
            tmpEdge.add(e);
            this.adjList.put(this.vertex.get(f),tmpEdge);
            tmpEdge = this.adjList.get(this.vertex.get(t));
            tmpEdge.add(e);
            this.adjList.put(this.vertex.get(t),tmpEdge);
        }
        M-=pedge;
    }

    /**
     * This method is used to generate the edges randomly with random weights.
     * @param m     indicated the edges to be generated
     * */
    public void generateEdges(int m){
        M = m;
        int pedge = 0;
        for(int i=0;i<m;i++){
            int f = (int)(Math.random() * N);
            int t = (int)(Math.random() * N);
            int c = (int)(Math.random() * 32);
            while(f == t){
                f = (int)(Math.random() * N);
            }
            Edge e = new Edge(this.vertex.get(f),this.vertex.get(t),c);
            this.edges.add(e);
            HashSet<Edge> tmpEdge = this.adjList.get(this.vertex.get(f));
            if(tmpEdge.contains(e)){
                pedge++;
            }
            tmpEdge.add(e);
            this.adjList.put(this.vertex.get(f),tmpEdge);
            tmpEdge = this.adjList.get(this.vertex.get(t));
            tmpEdge.add(e);
            this.adjList.put(this.vertex.get(t),tmpEdge);
        }
        M-=pedge;
    }


    /**
     * This method is used to Print the adjacency list of the graph.
     * */
    public void printGraph(){
        for(Vertex v : this.adjList.keySet()){
            System.out.println(v+" : "+this.adjList.get(v));
        }
    }
}



/*

6
1
2
3
4
5
6


7
1 2 6
2 3 8
2 4 3
3 5 1
4 5 20
4 6 5
5 6 2

* */
