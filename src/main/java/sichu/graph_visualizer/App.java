package sichu.graph_visualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sichu.graph_visualizer.gui.HomePage;

import java.io.IOException;

/**
 * Entry of the application
 * 
 * @author SICHU
 *
 */
public class App extends Application {
	public static Scene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		var container = new HomePage();

		scene = new Scene(container, 960, 640);

		primaryStage.setTitle("Random Graph Generator");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Called after scene is displayed to be able to access width and height values
		HomePage.graphView.init();
	}
}
