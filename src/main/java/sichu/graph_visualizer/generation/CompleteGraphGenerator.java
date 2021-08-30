package sichu.graph_visualizer.generation;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * Class for Complete graph generation.
 * 
 * @author SICHU
 *
 */
public class CompleteGraphGenerator {
	public static ThreadLocalRandom random = ThreadLocalRandom.current();

	public static Graph<String, DefaultEdge> createRandomCompleteGraph(int vertices) {
		// Create the VertexFactory so the generator can create vertices
		Supplier<String> vSupplier = new Supplier<String>() {
			private int id = 0;

			@Override
			public String get() {
				char ch = (char) (65 + id++);
				return String.valueOf(ch);
			}
		};

		// Create the graph object
		var graph = Generator.getInstance().isDirected()
				? new SimpleDirectedGraph<String, DefaultEdge>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(),
						false)
				: new SimpleGraph<String, DefaultEdge>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

		// Create the CompleteGraphGenerator object
		org.jgrapht.generate.CompleteGraphGenerator<String, DefaultEdge> completeGenerator = new org.jgrapht.generate.CompleteGraphGenerator<>(
				vertices);

		// Use the CompleteGraphGenerator object to make completeGraph a complete graph
		// with [size] number of vertices
		completeGenerator.generateGraph(graph);

		return graph;
	}
}
