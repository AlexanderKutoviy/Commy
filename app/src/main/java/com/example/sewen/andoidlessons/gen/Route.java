package com.example.sewen.andoidlessons.gen;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Route {
    private List<Integer> cities;
    private Country country;

    public Route(Country country, Integer... cities) {
        this.cities = new ArrayList<>(Arrays.asList(cities));
        this.country = country;

        if (this.cities.size() != this.country.size()) {
            throw new IllegalArgumentException();
        }
    }

    public Route getCopy() {
        return new Route(this.country, this.cities);
    }

    public Route(Country country, List<Integer> cities) {
        this.country = country;
        this.cities = cities;

        if (this.cities.size() != this.country.size()) {
            throw new IllegalArgumentException();
        }
    }

    public void set(int index, int value) {
        cities.set(index, value);
    }

    public int get(int index) {
        return cities.get(index);
    }

    public double getFitness() {
        double fitness = 0.0;
        List<Integer> l = getRealIndices();

        for (int i = 0; i < l.size() - 1; i++) {
            fitness += City.dist(country.getCity(l.get(i)), country.getCity(l.get(i + 1)));
        }

        return 1 / fitness;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int index : getRealIndices()) {
//            builder.append(index + ", ");
            builder.append("(" + country.getCity(index).getX() + ", " + country.getCity(index).getY() + "), ");
        }
        builder.delete(builder.length() - 2, builder.length());
        return builder.toString();
//        return Double.toString(1 / getFitness());
    }

    private List<Integer> getRealIndices() {
        List<Integer> realCities = IntStream.range(0, country.size()).boxed().collect(Collectors.toList());
        List<Integer> result = this.cities.stream().map(index -> realCities.remove(index % realCities.size())).collect(Collectors.toList());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (!cities.equals(route.cities)) return false;
        return country.equals(route.country);

    }

    @Override
    public int hashCode() {
        int result = cities.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }
}
