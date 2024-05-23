package com.example.flatphysics;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class flatPhysics extends Application {
    private Ball draggedBall = null;
    private double mouseX, mouseY;
    int HEIGHT = 500;
    static int WIDTH = 540;
    private ArrayList<Ball> balls = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        //canvas
        Canvas canvas = new Canvas(HEIGHT, WIDTH-40);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        

        //Reset Button
        Button reset = new Button("Clear");
        reset.setOnAction(e -> {
            balls.clear();
        });

        Button list = new Button("List");
        list.setOnAction(e -> {
            printList();
        });

        Button New = new Button("New Ball");
        New.setOnAction(e -> {
            Ball b = new Ball(Ball.center.getX(),Ball.center.getY(), 25, Color.RED);
            balls.add(b);
        });

        //hbox that holds all buttons and sliders in control pane
        HBox controlPane = new HBox(reset, list, New);
        controlPane.setAlignment(Pos.CENTER);
        controlPane.setSpacing(5);

        //base stack pane
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane, 500, 540);
        stage.setTitle("flatPhysics");
        stage.setScene(scene);
        stage.show();
        pane.setStyle("-fx-background-color : lightgray");

        //vbox that holds the canvas above the control pane
        VBox vbox = new VBox(canvas, controlPane);
        pane.getChildren().add(vbox);

        Text dist = new Text("");
        dist.setTranslateY(-250);
        pane.getChildren().add(dist);

        new AnimationTimer() {
            @Override
            public void handle(long now){
                gc.clearRect(0,0,WIDTH,HEIGHT);
                for(Ball ball: balls){
                    ball.update(balls);
                    ball.drawBall(gc);
                    ball.drawLine(gc, balls, dist);

                    gc.strokeLine(ball.getX(),ball.getY(), ball.getX()+ball.getr(), ball.getY());
                }
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(5);
                gc.strokeLine(0,HEIGHT,HEIGHT,HEIGHT);
            }
        }.start();
        new CanvasHandler(canvas, balls, gc);
    }

    public void printList(){
        if(balls.isEmpty()) System.out.println("Empty List");
        for(Ball ball: balls){
            System.out.println(ball.getr());
        }
    }


    public static void main(String[] args) {
        launch();
    }

    public static void mouseLine(double x, double y, Vector pos, GraphicsContext gc) {
        gc.strokeLine(x,y,pos.getX(), pos.getY());
    }  
}