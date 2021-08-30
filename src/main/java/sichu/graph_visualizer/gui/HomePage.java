package sichu.graph_visualizer.gui;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import sichu.graph_visualizer.generation.Generator;

import java.io.IOException;
import java.util.Objects;

/**
 * Defines the structure of the main page. 
 * GraphView is organised by JavaFXSmartGraph library. 
 * Control Pane is defined in main.fxml.
 * These two parts are loaded and combined into a single view HomePage.
 * 
 * @author SICHU
 *
 */
public class HomePage extends BorderPane {

	// Data
	public static Graph<String, Integer> graph = new GraphEdgeList<String, Integer>();
	public static SmartGraphPanel graphView;
	public static SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();

	// States check Generator
	public boolean isCompletedGraph = false;
	public boolean isCyclicGraph = false;

	public HomePage() throws IOException {
		graphView = getGraphView();

		setCenter(graphView);

		// Load left pane from FXML
		VBox leftPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));

		setLeft(leftPane);
	}

	public SmartGraphPanel<String, Integer> getGraphView() {
		Generator.getInstance().getRandomCompleteGraph();

		SmartGraphPanel<String, Integer> graphView = new SmartGraphPanel<>(getGraph(), strategy);

		// var container = new MainContainer(graphView);
		return graphView;
	}

	public Graph<String, Integer> getGraph() {
		return graph;
	}
}
