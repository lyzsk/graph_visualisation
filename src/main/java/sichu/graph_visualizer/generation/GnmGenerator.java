package sichu.graph_visualizer.generation;

import org.jgrapht.Graph;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.function.Supplier;

/**
 * Class for Gnm generation.
 * 
 * @author SICHU
 *
 */
public class GnmGenerator {
	public static Graph<String, DefaultEdge> CreateGnmGraph(int vertices, int edges) {
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

		new GnmRandomGraphGenerator<String, DefaultEdge>(vertices, edges).generateGraph(graph);
		return graph;
	}
}
