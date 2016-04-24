package com.example.sewen.gommy.ga;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.PointF;

import com.example.sewen.gommy.TourActivity;

import java.util.ArrayList;

public class TSP_GA extends IntentService {

    public TSP_GA() {
        super(TSP_GA.class.getCanonicalName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Create and add our cities
        ArrayList<PointF> listOfDots = intent.getParcelableArrayListExtra("points");
        ArrayList<PointF> resultTour = new ArrayList<>();
        for (int i = 0; i < listOfDots.size(); i++) {
            City city = new City(listOfDots.get(i).x, listOfDots.get(i).y);
            TourManager.addCity(city);
        }
        // Initialize population
        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());
        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }
        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
        for (int i = 0; i < pop.getFittest().tourSize(); i++) {
            PointF pointF = new PointF((float) pop.getFittest().getCity(i).getX(), (float) pop.getFittest().getCity(i).getY());
            resultTour.add(pointF);
        }
        if (resultTour.size() == pop.getFittest().tourSize()) {
            Intent tourActivityIntent = new Intent(this, TourActivity.class);
            tourActivityIntent.putExtra("tour", resultTour);
            tourActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(tourActivityIntent);
        }
    }
}
