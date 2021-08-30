package sichu.graph_visualizer.generation;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import sichu.graph_visualizer.algorithms.Tripartite;

/**
 * Class for tripartite graph generation.
 * 
 * @author SICHU
 *
 */
public class TripartiteGenerator {
	public static Graph<String, DefaultEdge> createCompleteTripartite(int vertices) {
		return new Tripartite(vertices).gen();
	}
}
