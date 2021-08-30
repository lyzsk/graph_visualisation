package sichu.graph_visualizer.generation;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.graphml.GraphMLExporter;
import sichu.graph_visualizer.gui.HomePage;
import sichu.graph_visualizer.gui.MainController;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class contains all graph generation actions.
 * 
 * @author SICHU
 *
 */
public class Generator {
	private static Generator _gen;

	public static Generator getInstance() {
		if (_gen == null)
			_gen = new Generator();
		return _gen;
	}

	public static int vertices = 5;
	public static int edges = 5;
	public static ThreadLocalRandom random = ThreadLocalRandom.current();
	public static int numOfTrees = 2;
	public static Graph<String, DefaultEdge> currentGraph;

	public Generator() {
	}

	/**
	 * Use snapshot function in JavaFX to save graph into png format.
	 * Use GraphMLExprter in JGraphT to save graph into xml format.
	 * 
	 * @param file
	 * @param ext
	 */
	public void saveToFile(File file, String ext) {
		if (ext.equals("png")) {
			var height = (int) HomePage.graphView.getHeight();
			var width = (int) HomePage.graphView.getWidth();
			var img = new WritableImage(width, height);
			var param = new SnapshotParameters();
			HomePage.graphView.snapshot(param, img);

			var renderedImg = SwingFXUtils.fromFXImage(img, null);

			try {
				ImageIO.write(renderedImg, ext, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (ext.equals("xml")) {
			var exporter = new GraphMLExporter<String, DefaultEdge>();
			exporter.exportGraph(currentGraph, file);
		}
	}

	public boolean isDirected() {
		return MainController.main == null || MainController.main.checkBoxDirected.isSelected();
	}

	public boolean isComplete() {
		return MainController.main.radioBtnComplete.isSelected();
	}

	public boolean isBipartite() {
		return MainController.main.radioBtnBipartite.isSelected();
	}

	public boolean isTripartite() {
		return MainController.main.radioBtnTripartite.isSelected();
	}

	public boolean isCyclic() {
		return MainController.main.radioBtnCyclic.isSelected();
	}

	public boolean isConnected() {
		return MainController.main.checkBoxConnected.isSelected();
	}

	public boolean isTree() {
		return MainController.main.radioBtnTree.isSelected();
	}

	public boolean isCompleteTree() {
		return MainController.main.radioBtnForest.isSelected();
	}

	public boolean isGnm() {
		return MainController.main.radioBtnGnm.isSelected();
	}

	public void generate(int vertices, int edges) {
		MainController.main.labelMsg.setVisible(false);

		Generator.vertices = vertices;
		Generator.edges = edges;

		if (vertices > 26) {
			Generator.vertices = 26;
			MainController.main.textFieldVertices.setText("26");
		}

		if (isBipartite())
			getRandomCompleteBipartiteGraph();
		else if (isComplete())
			getRandomCompleteGraph();
		else if (isCyclic())
			getCyclicGraph();
		else if (isTree())
			getTreeGraph();
		else if (isCompleteTree())
			getCompleteTreeGraph();
		else if (isTripartite())
			getTripartiteGraph();
		else if (isGnm())
			getGnmGraph();
		;
	}

	public void getTripartiteGraph() {
		currentGraph = TripartiteGenerator.createCompleteTripartite(vertices);
		processGraph(currentGraph);
	}

	public void getRandomCompleteGraph() {
		currentGraph = CompleteGraphGenerator.createRandomCompleteGraph(vertices);
		processGraph(currentGraph);
	}

	public void getRandomCompleteBipartiteGraph() {
		currentGraph = BipartiteGraphGenerator.createRandomCompleteBipartiteGraph(vertices);
		processGraph(currentGraph);
	}

	public void getCyclicGraph() {
		currentGraph = CyclicGraphGenerator.createRandomCyclicGraph(vertices, edges);
		processGraph(currentGraph);
	}

	public void getTreeGraph() {
		currentGraph = TreeGenerator.createRandomTree(vertices);
		processGraph(currentGraph);
	}

	private void getCompleteTreeGraph() {
		if (numOfTrees * 2 > vertices) {
			MainController.main.labelMsg.setVisible(true);
			MainController.main.labelMsg
					.setText("Can't generate\n%d tree(s) with %d vertices.".formatted(numOfTrees, vertices));
			return;
		}
		currentGraph = TreeGenerator.createRandomCompleteTree(numOfTrees, vertices);
		processGraph(currentGraph);
	}

	/**
	 * Transform graph type from JGraphT into JavaFXSmartGraph for visualization.
	 * 
	 * @param graph
	 */
	public void processGraph(Graph<String, DefaultEdge> graph) {
		var result = new HashMap<String, Set>();

		result.put("edges", graph.edgeSet());
		result.put("vertices", graph.vertexSet());

		var vertexList = new ArrayList<String>();
		var resultVertices = (Set) result.get("vertices");
		for (var item : resultVertices) {
			vertexList.add(item.toString());
		}

		var edges = new ArrayList<Generator.Edge>();
		var resultEdges = (Set) result.get("edges");

		for (var edge : resultEdges) {
			var tmp = Arrays.asList(edge.toString().split("[^\\w']+")).subList(1, 3);

			edges.add(new Generator.Edge(tmp.get(0), tmp.get(1)));
		}

		for (var item : HomePage.graph.vertices()) {
			HomePage.graph.removeVertex(item);
		}

		for (var vertex : vertexList) {
			HomePage.graph.insertVertex(vertex);
		}

		for (Generator.Edge edge : edges) {
			HomePage.graph.insertEdge(edge.src, edge.target, random.nextInt(20));
		}
	}

	public void getGnmGraph() {

		currentGraph = GnmGenerator.CreateGnmGraph(vertices, edges);
		processGraph(currentGraph);
	}

	public static class Edge {
		public String src;
		public String target;

		public Edge(String src, String target) {
			this.src = src;
			this.target = target;
		}
	}
}
