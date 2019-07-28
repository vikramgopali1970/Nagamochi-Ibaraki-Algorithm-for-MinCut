package NagamochiIbarakiAlgorithm;


import java.util.ArrayList;
import java.util.HashSet;

public class NaagamochiIbarakiAlgorithm {

    Vertex last = null;
    Vertex lastMinusOne = null;

    /**
     * This method implements the Namagochi Ibaraki Algorithm for the input Graph to calculate the minCut if available
     * else if the graph is disconnected, it cannot calculate minCut.
     *
     * @param g     This is the Graph on which the nagamochi ibaraki algorithm runs to calculate min cut
    **/
    public void nagamochiIbaraki(Graph g){
        int minCut = Integer.MAX_VALUE;

        while(g.vertex.size() > 1){
            int phaseCut = this.MAOrdering(g);
            if(phaseCut == 0){
                return;
            }
            if(phaseCut < minCut){
                minCut = phaseCut;
            }
            g = this.mergeGraph(g);
        }
        System.out.println("The min cut is "+minCut);
    }

    /**
     * This method implements the MAOrdering Algorithm for the input Graph to calculate the MA Ordering of the
     * vertices and then return the phase cut value from the last 2 vertices of the order.
     *
     * @param g     This is the Graph with vertices whose MA Ordering and phasecut is to be calculated.
     * @return      Phase cut value of the graph.
     **/
    public int MAOrdering(Graph g){
        int maOrderAB = 0;
        Vertex[] MAOrder = new Vertex[g.vertex.size()];
        HashSet<Vertex> set = new HashSet<Vertex>();
        int startVertex = (int)(Math.random() * g.vertex.size());
        while(!g.vertex.keySet().contains(startVertex)){
            startVertex = (int)(Math.random() * g.N);
        }
        MAOrder[0] = g.vertex.get(startVertex);
        set.add(MAOrder[0]);
        for(int i=1;i<MAOrder.length;i++){
            Vertex nodeToBeIncluded = null;
            int sum = 0;
            int testedNode=0;
            while(!g.vertex.keySet().contains(testedNode) && testedNode< g.N){
                testedNode++;
            }
            Vertex testNode = new Vertex(testedNode);
            while(testedNode < g.N){
                int setSum = 0;
                testNode = new Vertex(testedNode);
                if(!set.contains(testNode)){
                    for(Vertex u : set){
                        HashSet<Edge> tmpEdgeSet1 = new HashSet<Edge>(g.adjList.get(u));
                        HashSet<Edge> tmpEdgeSet2 = new HashSet<Edge>(g.adjList.get(testNode));
                        tmpEdgeSet1.retainAll(tmpEdgeSet2);
                        ArrayList<Edge> elist = new ArrayList<Edge>(tmpEdgeSet1);
                        if(tmpEdgeSet1.size() > 0){
                            setSum += elist.get(0).capacity;
                        }
                    }
                    if(setSum > sum){
                        nodeToBeIncluded = testNode;
                        sum = setSum;
                    }
                }

                testedNode++;
                while(!g.vertex.keySet().contains(testedNode)  && testedNode< g.N){
                    testedNode++;
                }
            }
            if(nodeToBeIncluded == null){
                System.out.println("Graph is disconnected");
                return 0;
            }
            MAOrder[i] = nodeToBeIncluded;
            set.add(nodeToBeIncluded);
        }
        this.last = MAOrder[g.vertex.size()-1];
        this.lastMinusOne = MAOrder[g.vertex.size()-2];
        for(Edge e : g.adjList.get(this.last)){
            maOrderAB += e.capacity;
        }
        return maOrderAB;
    }

    /**
     * This method is used to remove a vertex from the input Graph.
     *
     * @param g     This is the input Graph.
     * @param v     The vertex to be removed.
     **/
    private void removeVertex(Graph g, Vertex v){
        HashSet<Edge> edges = new HashSet<Edge>(g.adjList.get(v));
        for(Edge e: edges){
            this.removeEdge(g,e);
        }
    }

    /**
     * This method is used to redirect all edges from the last vertex in the MA order
     * to the last but one vertex in the MA order.
     *
     * @param g         This is the input Graph.
     * @param eset      Set of edges to be redirected.
     **/
    private void redirectEdge(Graph g, ArrayList<Edge> eset){
        for(Edge e : eset){
            this.removeEdge(g,e);
            Vertex one = e.otherEnd(this.last);
            Vertex two = this.lastMinusOne;
            Edge newEdge = new Edge(one,two,e.capacity);
            this.addEdge(g, newEdge);
        }
    }

    /**
     * This method is used to remove a edge from the input Graph.
     *
     * @param g     This is the input Graph.
     * @param e     The edge to be removed.
     **/
    public void removeEdge(Graph g, Edge e){
        Vertex from = e.from;
        HashSet<Edge> eset = g.adjList.get(from);
        eset.remove(e);
        g.adjList.put(from, eset);
        Vertex to = e.to;
        eset = g.adjList.get(to);
        eset.remove(e);
        g.adjList.put(to, eset);

    }

    /**
     * This method is used to add a edge to the input Graph.
     *
     * @param g     This is the input Graph.
     * @param e     The edge to be added.
     **/
    public void addEdge(Graph g, Edge e){
        Vertex from = e.from;
        Vertex to = e.to;
        HashSet<Edge> eset = g.adjList.get(from);
        eset.add(e);
        g.adjList.put(from, eset);
        eset = g.adjList.get(to);
        eset.add(e);
        g.adjList.put(to, eset);
    }

    /**
     * This method is used to merge the 2 vertices in the MA ordering and return a new Graph.
     *
     * @param g     This is the input Graph.
     * @return      New Graph with 2 vertices from the MA order of vertices merged.
     **/
    public Graph mergeGraph(Graph g){
        int newWeight = 0;
        for(int num : g.vertex.keySet()){
            Vertex tmp = g.vertex.get(num);
            if(!tmp.equals(this.last) && !tmp.equals(this.lastMinusOne)){
                HashSet<Edge> lastES = new HashSet<Edge>(g.adjList.get(this.last));
                HashSet<Edge> lastMinusOneES = new HashSet<Edge>(g.adjList.get(this.lastMinusOne));
                HashSet<Edge> curES = new HashSet<Edge>(g.adjList.get(tmp));
                lastES.retainAll(curES);
                ArrayList<Edge> a1 = new ArrayList<Edge>(lastES);
                lastMinusOneES.retainAll(curES);
                ArrayList<Edge> a2 = new ArrayList<Edge>(lastMinusOneES);
                lastES = new HashSet<Edge>(g.adjList.get(this.last));
                lastMinusOneES = new HashSet<Edge>(g.adjList.get(this.lastMinusOne));
                lastMinusOneES.retainAll(lastES);
                ArrayList<Edge> a3 = new ArrayList<Edge>(lastMinusOneES);
                if(a1.size() == 1 && a2.size() == 1){
                    Edge celast = a1.get(0);
                    Edge celastMinusOne = a2.get(0);
                    this.removeEdge(g, celast);
                    this.removeEdge(g, celastMinusOne);
                    newWeight += a1.get(0).capacity + a2.get(0).capacity;
                    Edge newEdge = new Edge(tmp, lastMinusOne, newWeight);
                    this.addEdge(g,newEdge);
                }
                if(a1.size() == 1 && a2.size() == 0){
                    //these edges shd be redirected to lastMinusOne
                    this.redirectEdge(g,a1);
                }
                if(a3.size()==1){
                    HashSet<Edge> edg = g.adjList.get(this.lastMinusOne);
                    edg.remove(a3.get(0));
                    g.adjList.put(this.lastMinusOne,edg);
                    g.edges.remove(a3.get(0));
                    g.edges.remove(a3.get(0));
                }
            }
        }
        this.removeVertex(g,this.last);
        g.adjList.remove(this.last);
        g.vertex.remove(this.last.name);
        return g;
    }
}

/*

6
0
1
2
3
4
5


7
0 1 6
1 2 8
1 3 3
2 4 1
3 4 20
3 5 5
4 5 2

10
0
1
2
3
4
5
6
7
8
9

33
0 2 69
0 3 26
0 4 86
0 5 26
0 8 19
0 9 9
1 2 63
1 3 25
1 4 13
1 5 33
1 6 55
1 8 134
1 9 32
2 3 27
2 5 53
2 7 19
2 9 39
3 5 27
3 6 65
3 7 97
3 8 37
4 5 23
4 6 41
4 8 39
5 6 63
5 7 15
5 8 25
5 9 42
6 8 38
6 9 50
7 8 63
7 9 35
8 9 51

* */