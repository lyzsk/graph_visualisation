package sichu.graph_visualizer.algorithms;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import sichu.graph_visualizer.generation.Generator;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A complete tripartite graph is the k = 3 case of a complete k-partite graph.
 * 
 * @author SICHU
 *
 */
public class Tripartite {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    public ArrayList<String> set1 = new ArrayList<>();
    public ArrayList<String> set2 = new ArrayList<>();
    public ArrayList<String> set3 = new ArrayList<>();
    Graph<String, DefaultEdge> graph = Generator.getInstance().isDirected()
            ? new SimpleGraph<String, DefaultEdge>(DefaultEdge.class)
            : new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

    public Tripartite(int vertices) {
        if (vertices < 3) return;

        graph.addVertex("A");
        graph.addVertex("C");
        graph.addVertex("B");

        this.set1.add("A");
        this.set2.add("C");
        this.set3.add("B");

        for (var i = 3; i < vertices; i++) {
            var vertexName = String.valueOf((char) (65 + i));
            graph.addVertex(vertexName);
            switch (random.nextInt(1, 4)) {
                case 1 -> this.set1.add(vertexName);
                case 2 -> this.set2.add(vertexName);
                case 3 -> this.set3.add(vertexName);
            }
        }
    }

    public Graph<String,DefaultEdge> gen() {

        // set1 to set2 & set3
        set1.forEach(vertexSet1 -> {
            for (var vertexSet2 : set2) {
                if (random.nextBoolean())
                    graph.addEdge(vertexSet1, vertexSet2);
                else
                    graph.addEdge(vertexSet2,vertexSet1);
            }
            for (var vertexSet3 : set3) {
                if (random.nextBoolean())
                    graph.addEdge(vertexSet1, vertexSet3);
                else
                    graph.addEdge(vertexSet3,vertexSet1);
            }
        });
        // set2 to set3
        set2.forEach(vertexSet2->{
            for (var vertexSet3: set3){
                if (random.nextBoolean())
                    graph.addEdge(vertexSet2,vertexSet3);
                else
                    graph.addEdge(vertexSet3,vertexSet2);
            }
        });
        return this.graph;
    }
}
