package com.example.sewen.andoidlessons.gen;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.IntStream;

public class GeneticAlgorithm<T> {
    private final ObjDoubleConsumer<T> mutation;
    private final UnaryOperator<Pair<T, T>> crossover;
    private final Supplier<T> generator;
    private final Function<T, Double> fitness;
    private final BiConsumer<List<T>, List<T>> merger;
    private final Function<List<T>, T> fittest;
    private final int populationSize;
    private final int iterations;
    private final double mutationChance;
    private List<T> result;

    public GeneticAlgorithm(ObjDoubleConsumer<T> mutation, UnaryOperator<Pair<T, T>> crossover, Supplier<T> generator,
                            Function<T, Double> fitness, BiConsumer<List<T>, List<T>> merger, Function<List<T>, T> fittest,
                            int populationSize, int iterations, double mutationChance) {
        this.mutation = mutation;
        this.crossover = crossover;
        this.generator = generator;
        this.merger = merger;
        this.fittest = fittest;
        this.populationSize = populationSize;
        this.iterations = iterations;
        this.fitness = fitness;
        this.mutationChance = mutationChance;
    }

    public void execute() {
        List<T> population = new ArrayList<>();
        result = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(generator.get());
        }

        List<Double> probabilities = new ArrayList<>();

        double sum = 0.0;
        for (T genome : population) {
            double val = fitness.apply(genome);
            probabilities.add(val);
            sum += val;
        }

        probabilities.set(0, probabilities.get(0) / sum);
        for (int i = 1; i < probabilities.size(); i++) {
            probabilities.set(i, probabilities.get(i - 1) + probabilities.get(i) / sum);
        }
        probabilities.add(0, 0.0);

        int totalCrossovers = 0;
        int relevantCrossovers = 0;

        for (int i = 0; i < iterations; i++) {
            if (i % 100 == 0)
                result.add(fittest.apply(population));
//            System.out.println(fittest.apply(population));

            List<T> children = new ArrayList<>();

            for (int j = 0; j < populationSize / 4; j++) {
                int i1 = flipRange(probabilities, Math.random());
                int i2 = flipRange(probabilities, Math.random());

                Pair<T, T> parents = new Pair<>(population.get(i1), population.get(i2));
                Pair<T, T> newChildren = crossover.apply(parents);
                totalCrossovers++;
                if (!newChildren.equals(parents))
                    relevantCrossovers++;

                children.add(newChildren.getKey());
                children.add(newChildren.getValue());
            }

            for (T child : children) {
                mutation.accept(child, mutationChance);
            }

            merger.accept(population, children);
        }

        this.result.add(fittest.apply(population));
    }

    private int flipRange(List<Double> probabilities, double flip) {
        if ((flip < probabilities.get(0)) || (flip >= probabilities.get(probabilities.size() - 1)))
            throw new IllegalArgumentException();

        return IntStream.range(0, probabilities.size() - 1).filter(i -> (probabilities.get(i) <= flip) &&
                (probabilities.get(i + 1) > flip)).findFirst().getAsInt();
    }

    public List<T> getResult() {
        return result;
    }
}
