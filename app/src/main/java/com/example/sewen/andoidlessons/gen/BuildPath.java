package com.example.sewen.andoidlessons.gen;

import android.app.IntentService;
import android.content.Intent;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuildPath extends IntentService {

    public BuildPath() {
        super(BuildPath.class.getCanonicalName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Country country = new Country(1000, 100);
        Country country = new Country(6, 10);

        ObjDoubleConsumer<Route> mutation;
        UnaryOperator<Pair<Route, Route>> crossover;
        Supplier<Route> generator;
        Function<Route, Double> fitness;
        BiConsumer<List<Route>, List<Route>> merger;
        Function<List<Route>, Route> fittest;
        int populationSize;
        int iterations;
        double mutationChance;

        GeneticAlgorithm<Route> ga = new GeneticAlgorithm<>(
                mutation = (route, chance) -> {
                    if (chance >= 1) {
                        throw new IllegalArgumentException();
                    }

                    if (Math.random() < chance) {
                        int i1 = (int) (Math.random() * (country.size() - 1));
                        int i2 = (int) (Math.random() * (country.size() - 1));

                        int t = route.get(i1);
                        route.set(i1, route.get(i2));
                        route.set(i2, t);
                    }
                },
                crossover = (pair) -> new Pair<>(
                        new Route(country,
                            IntStream.range(0, country.size()).map(i -> i % 2 == 0 ? pair.getKey().get(i) : pair.getValue().get(i))
                            .boxed().collect(Collectors.toList())),
                        new Route(country,
                            IntStream.range(0, country.size()).map(i -> i % 2 == 1 ? pair.getKey().get(i) : pair.getValue().get(i))
                            .boxed().collect(Collectors.toList()))),
                generator = () -> {
                    List<Integer> l = new ArrayList<>();

                    for (int i = 0; i < country.size(); i++) {
                        l.add((int) (Math.random() * country.size()));
                    }

                    return new Route(country, l);
                },
                fitness = (route) -> (route.getFitness()),
                merger = (parents, children) -> {
                    for (Route child : children) {
                        int worstParentIndex = IntStream.range(1, parents.size()).reduce(0, (x, y) -> (parents.get(x).getFitness() <
                                parents.get(y).getFitness() ? x : y));
                        parents.set(worstParentIndex, child);
                    }
                },
                fittest = (population) ->  population.get(IntStream.range(1, population.size()).reduce(0, (x, y) -> (population.get(x).getFitness() >
                        population.get(y).getFitness() ? x : y))),
                populationSize = 100,
                iterations = 1000,
                mutationChance = .15);

        ga.execute();

        for (Route r : ga.getResult())
            System.out.println(r.getFitness());

        System.out.println(ga.getResult().get(ga.getResult().size() - 1));
    }
}
