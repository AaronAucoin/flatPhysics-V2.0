package com.example.flatphysics;

public class Vector {
    private float x;
    private float y;

    public static Vector zeroVector = new Vector(0, 0);

    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }
    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector multiply(float u){
        return new Vector(x * u, y * u);
    }

    public Vector addition(Vector u){
        return new Vector(x + u.x, y + u.y);
    }

    public Vector subtraction(Vector u){
        return new Vector(this.x = u.x, this.y - u.y);
    }

    public float dot(Vector other){
        return this.x * other.x + this.y * other.y;
    }
}
