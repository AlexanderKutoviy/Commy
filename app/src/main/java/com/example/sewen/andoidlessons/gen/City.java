package com.example.sewen.andoidlessons.gen;

public class City {
    private final double X;
    private final double Y;

    public City(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public static double dist(City a, City b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }
}
