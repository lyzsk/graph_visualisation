package sichu.graph_visualizer;


import org.junit.jupiter.api.Test;
import sichu.graph_visualizer.generation.Generator;

public class GeneratorTest {
    Generator gen = new Generator();

    @Test
    public void bipartiteTest() {
        gen.getRandomCompleteBipartiteGraph();
    }
    @Test
    public void treeTest(){
        // gen.getTreeGraph();
    }
}
