package com.example.flatphysics;

public class Collisions {
    public static void resolve(Ball ballA, Ball ballB) {
        if (ballA == null || ballB == null) {
            throw new IllegalArgumentException("Balls cannot be null");
        }

        float distance = Equations.distance(ballA.getPos(), ballB.getPos());

        // Check if balls are overlapping
        float radiiSum = ballA.getr() + ballB.getr();
        if (distance < radiiSum) {
            resolveStatic(ballA, ballB, distance, radiiSum);
        }
    }

    private static void resolveStatic(Ball ballA, Ball ballB, float distance, float radiiSum) {
        if (distance == 0) {
            // Handle the case where the balls are exactly at the same position
            // For simplicity, push them apart along the x-axis
            float overlap = 0.5f * radiiSum;
            ballA.push(overlap, 0);
            ballB.push(-overlap, 0);
            return;
        }

        float overlap = 0.5f * (radiiSum - distance);

        float dx = (ballA.getX() - ballB.getX()) / distance;
        float dy = (ballA.getY() - ballB.getY()) / distance;

        ballA.push(overlap * dx, overlap * dy);
        ballB.push(-overlap * dx, -overlap * dy);
    }
}
