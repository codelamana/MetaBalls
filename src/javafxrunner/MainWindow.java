package javafxrunner;

import com.jogamp.newt.awt.NewtCanvasAWT;
import grids.PerlinGrid;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import processing.core.PApplet;
import processing.core.PSurface;
import processing.opengl.PSurfaceJOGL;

import javax.swing.*;

public class MainWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    PApplet p;
    NewtCanvasAWT canvasAWT;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        SwingNode canvas = new SwingNode();
        p = new PerlinGrid();
        PSurfaceJOGL surf = (PSurfaceJOGL) p.getSurface();
        canvasAWT = (NewtCanvasAWT) surf.getComponent();
        SwingNode swingNode = new SwingNode();


        Runnable r = new Runnable (){
            JPanel pan;
            @Override
            public void run() {
                pan = new JPanel();
                pan.add(canvasAWT);
                swingNode.setContent(pan);
            }

        };

        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}