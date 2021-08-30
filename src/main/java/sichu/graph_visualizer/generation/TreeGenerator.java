package sichu.graph_visualizer.generation;

import org.jgrapht.Graph;
import org.jgrapht.generate.BarabasiAlbertForestGenerator;
import org.jgrapht.generate.PruferTreeGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * Class for Tree graph generation.
 * 
 * @author SICHU
 *
 */
public class TreeGenerator {
	public static ThreadLocalRandom random = ThreadLocalRandom.current();

	public static Graph<String, DefaultEdge> createRandomTree(int verticesNumber) {
		Supplier<String> vSupplier = new Supplier<String>() {
			private int id = 0;

			@Override
			public String get() {
				char ch = (char) (65 + id++);
				return String.valueOf(ch);
			}
		};
		var graph = new SimpleGraph<String, DefaultEdge>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

		var gen = new PruferTreeGenerator<String, DefaultEdge>(verticesNumber);
		gen.generateGraph(graph);

		return graph;
	}

	public static Graph<String, DefaultEdge> createRandomCompleteTree(int numOfTrees, int verticesNumber) {
		Supplier<String> vSupplier = new Supplier<String>() {
			private int id = 0;

			@Override
			public String get() {
				char ch = (char) (65 + id++);
				return String.valueOf(ch);
			}
		};

		var graph = new SimpleGraph<String, DefaultEdge>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

		var gen = new BarabasiAlbertForestGenerator<String, DefaultEdge>(numOfTrees, verticesNumber);
		gen.generateGraph(graph);

		return graph;
	}
}
