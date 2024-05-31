package com.example.flatphysics;

public class Collisions {
    private static Vector normalVector;
    private static float depth = 0f;
    
    public static void resolve(Ball ballA, Ball ballB){
        normalVector = ballA.getPos().subtraction(ballB.getPos());
        depth = ballA.getr() + ballB.getr() - Equations.distance(ballA.getPos(), ballB.getPos());
    }
}
