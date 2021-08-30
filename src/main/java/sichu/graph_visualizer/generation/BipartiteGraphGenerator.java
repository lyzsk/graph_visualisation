package sichu.graph_visualizer.generation;

import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteBipartiteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * Class for bipartite graph generation.
 * 
 * @author SICHU
 *
 */
public class BipartiteGraphGenerator {
	public static ThreadLocalRandom random = ThreadLocalRandom.current();

	public static Graph<String, DefaultEdge> createRandomCompleteBipartiteGraph(int vertices) {
		// Create the VertexFactory so the generator can create vertices
		Supplier<String> vSupplier = new Supplier<String>() {
			private int id = 0;

			@Override
			public String get() {
				char ch = (char) (65 + id++);
				return String.valueOf(ch);
			}
		};
		var graph = Generator.getInstance().isDirected()
				? new SimpleDirectedGraph<String, DefaultEdge>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(),
						false)
				: new SimpleGraph<String, DefaultEdge>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

		var part1Vs = vertices - random.nextInt(1, vertices);
		var part2Vs = vertices - part1Vs;
		var gen = new CompleteBipartiteGraphGenerator<String, DefaultEdge>(part1Vs, part2Vs);

		gen.generateGraph(graph);
		return graph;
	}
}
