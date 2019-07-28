package NagamochiIbarakiAlgorithm;

public class Main {
    public static void main(String[] args){
        NaagamochiIbarakiAlgorithm nia = new NaagamochiIbarakiAlgorithm();
        for(int edges = 50;edges <= 550;edges+=5){
            Graph g = new Graph();
            System.out.println("For Graph with vertices "+g.N+" and Edges "+edges);
            g.generateVertices();
            g.generateEdges(edges);
            g.printGraph();
            nia.nagamochiIbaraki(g);
            System.out.println();
        }

    }
}
