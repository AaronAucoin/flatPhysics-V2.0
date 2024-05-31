package com.example.flatphysics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

public class Ball {
    private final Vector G = new Vector(0, 9.8f);
    public static float dt = (float) 1/60;
    private Vector pos;
    private float r;
    private Vector velocity;
    private Vector acceleration = new Vector(0, 0);
    private float mass;
    private float density;
    private final Color color;
    final static Vector center = new Vector(250,250);
    private final float FLOORLEVEL = 500;
    private final float dampingFactor = 1-1/1000f;

    public Ball(float x, float y, float r, Color color) {
        this.pos = new Vector(x, y);
        this.r = r;
        this.color = color;
        this.velocity = new Vector(0,0);
        this.acceleration = new Vector(0,0);
    }

    public void setAcceleration(Vector acceleration){
        this.acceleration = acceleration;
    }

    public void addAcceleration(Vector newAcceleration){
        acceleration.addition(newAcceleration);
    }

    public Vector getAcceleration(){
        return acceleration;
    }

    public float getX() {
        return pos.getX();
    }

    public float getY() {
        return pos.getY();
    }
    public float getr() {return r;}

    public Vector getPos(){
        return pos;
    }
    public float getRadi(){return r;}


    public void drawBall(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(pos.getX() - r, pos.getY() - r, 2 * r, 2 * r);
    }


    public boolean contains(double mouseX, double mouseY) {
        double dx = mouseX - (pos.getX());
        double dy = mouseY - (pos.getY());
        double distanceSquared = dx * dx + dy * dy;
        return distanceSquared <= r * r;
    }

    public void WallCollision() {
        if(pos.getX() <= 0 + r || pos.getX() >= 500 - r){
            velocity.setX(velocity.getX() * -1);
            System.out.println("WALLX");
        }
        if(pos.getY() <= 0 + r || pos.getY() >= 500 - r){
            velocity.setY(velocity.getY() * -1);
            System.out.println("WALL-E");
        }
}

public boolean colliding(Ball b){
    return Equations.distance(pos, b.pos) <= r + b.r;
}
public void resolve(Ball b){
    Collisions.resolve(this, b);
    System.out.println("Colliding");
}

    public void update(ArrayList<Ball> balls) {
        handleAcceleration();
        if(!balls.isEmpty()) {
            for (Ball b : balls) {
                if (this != b && colliding(b)) {
                    resolve(b);
                }
            }
        }
        handleVelocity();
    }

        private void handleAcceleration() {
            velocity = velocity.addition(acceleration);
            setAcceleration(Vector.zeroVector);
        }

        private void handleVelocity(){
            pos = pos.addition(velocity.multiply(dt));
            velocity = velocity.multiply(dampingFactor);
            WallCollision();
        }

        public void move(double x, double y){
        pos.setX((float)x);
        pos.setY((float)y);
        }

    public void drawLine(GraphicsContext gc, ArrayList<Ball> balls, Text dist) {
        gc.setLineWidth(2);
        if (balls.isEmpty()) {
            dist.setText("0");
        } else {
            StringBuilder distances = new StringBuilder();
            for (Ball b : balls) {
                gc.strokeLine(pos.getX(),pos.getY(), b.pos.getX(), b.pos.getY());
            }
        }
    }

}
