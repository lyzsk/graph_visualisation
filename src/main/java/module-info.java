module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.swing;
    requires org.jgrapht.core;
    requires org.jgrapht.io;

    exports sichu.graph_visualizer;
    exports sichu.graph_visualizer.generation;
    exports sichu.graph_visualizer.gui;
    exports sichu.graph_visualizer.algorithms;
}
