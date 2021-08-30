package sichu.graph_visualizer.gui;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import sichu.graph_visualizer.App;
import sichu.graph_visualizer.generation.Generator;

import java.util.ArrayList;

/**
 * UI logics:
 * 1. Trees can not be directed.
 * 2. textFieldTreesInForest can only be enabled when forest is selected.
 * 3. textFieldEdges can only be enabled when Gnm is selected
 * 
 * @author SICHU
 *
 */
public class MainController {
	public static MainController main;

	public ToggleGroup groupGraphType;

	public CheckBox checkBoxWeighted;
	public CheckBox checkBoxDirected;
	public CheckBox checkBoxConnected;
	public RadioButton radioBtnCyclic;
	public RadioButton radioBtnComplete;
	public RadioButton radioBtnBipartite;
	public RadioButton radioBtnTripartite;
	public TextField textFieldVertices;
	public Button btnGenerate;
	public CheckBox automatic;
	public RadioButton radioBtnTree;
	public RadioButton radioBtnForest;
	public RadioButton radioBtnGnm;
	public HBox hBoxForestForm;
	public TextField textFieldTreesInForest;
	public Label labelMsg;
	public Button btnExport;
	public FileChooser fileChooser = new FileChooser();
	public TextField textFieldEdges;

	@FXML
	public void initialize() {
		textFieldVertices.setText("5");
		textFieldEdges.setText("5");
		textFieldTreesInForest.setText("2");
		radioBtnComplete.setSelected(true);
		checkBoxDirected.setSelected(true);
		checkBoxWeighted.setSelected(true);
		labelMsg.setVisible(false);
		checkBoxConnected.setSelected(true);
		var extListImage = new ArrayList<String>();
		extListImage.add("*.png");

		var extListXml = new ArrayList<String>();
		extListXml.add("*.xml");

		var extFilterImg = new FileChooser.ExtensionFilter("Image File", extListImage);
		var extFilterXml = new FileChooser.ExtensionFilter("Matrix File", extListXml);
		fileChooser.getExtensionFilters().add(extFilterImg);
		fileChooser.getExtensionFilters().add(extFilterXml);

		checkBoxConnected.setDisable(true);

		// Property bindings
		hBoxForestForm.disableProperty().bind(radioBtnForest.selectedProperty().not());
		checkBoxDirected.disableProperty().bind(radioBtnForest.selectedProperty());

		textFieldEdges.disableProperty()
				.bind(radioBtnCyclic.selectedProperty().or(radioBtnGnm.selectedProperty()).not());
		checkBoxDirected.disableProperty().bind(radioBtnForest.selectedProperty());
		automatic.selectedProperty().bindBidirectional(HomePage.graphView.automaticLayoutProperty());
		checkBoxDirected.disableProperty().bind(radioBtnCyclic.selectedProperty().or(radioBtnTree.selectedProperty())
				.or(radioBtnForest.selectedProperty()));

		// Onclick handlers
		radioBtnTree.setOnMouseClicked(event -> checkBoxDirected.setSelected(false));
		radioBtnForest.setOnMouseClicked(event -> checkBoxDirected.setSelected(false));
		radioBtnCyclic.setOnMouseClicked(event -> checkBoxDirected.setSelected(true));

		btnGenerate.setOnMouseClicked(mouseEvent -> {
			Generator.numOfTrees = Integer.parseInt(textFieldTreesInForest.getText());

			SmartGraphPanel.setDirected(checkBoxDirected.isSelected());
			SmartGraphPanel.setWeighted(checkBoxWeighted.isSelected());

			Generator.getInstance().generate(Integer.parseInt(textFieldVertices.getText()),
					Integer.parseInt(textFieldEdges.getText()));

			// Modify css and update view.
			// var css = getStylesheets().get(0);
			// var rules = css.getRules();
			// System.out.println(css);

			// Reload css
			// graphView.cssString = "";
			// graphView.reloadStyleSheet();

			// Set A as root node.
			// graphView.getStylableVertex("A").setStyleClass("myVertex");
			HomePage.graphView.update();
		});
		btnExport.setOnMouseClicked(event -> {
			fileChooser.setInitialFileName("graph");

			var file = fileChooser.showSaveDialog(App.scene.getWindow());
			var fileName = file.getName().split("\\.");
			var ext = fileName[fileName.length - 1];

			Generator.getInstance().saveToFile(file, ext);
		});
		main = this;
	}
}
