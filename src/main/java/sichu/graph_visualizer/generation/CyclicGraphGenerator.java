package sichu.graph_visualizer.generation;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for cyclic graph generation.
 * 
 * @author SICHU
 *
 */
public class CyclicGraphGenerator {

	public static ThreadLocalRandom random = ThreadLocalRandom.current();

	public static Graph<String, DefaultEdge> createRandomCyclicGraph(int vertices, int edges) {
		Graph<String, DefaultEdge> graph = GraphTypeBuilder.<String, DefaultEdge>directed().allowingMultipleEdges(true)
				.allowingSelfLoops(true).edgeClass(DefaultEdge.class).buildGraph();

		for (int i = 0; i < vertices; i++) {
			var vertexName = String.valueOf((char) (65 + i));
			graph.addVertex(vertexName);
		}

		var start = random.nextInt(0, vertices / 2);
		var end = random.nextInt(1, vertices);

		var vertexArr = graph.vertexSet().toArray();

		var nodeM = vertexArr[start].toString();
		var nodeN = vertexArr[end].toString();
		// end -> start
		graph.addEdge(nodeN, nodeM);
		edges--;

		for (int i = start + 1; i < end; i++) {
			graph.addEdge(vertexArr[i].toString(), vertexArr[i + 1].toString());
		}
		for (var i = 0; i < random.nextInt(1, vertices / 2 + 1); i++) {
			var vertex = vertexArr[i].toString();
			// random v -> all
			if (graph.edgesOf(vertex).size() > vertices / 2)
				continue;

			for (var v : vertexArr) {
				var target = v.toString();
				if (edges == 0)
					continue;
				graph.addEdge(vertex, target);
				edges--;
			}
		}
		return graph;
	}
}
