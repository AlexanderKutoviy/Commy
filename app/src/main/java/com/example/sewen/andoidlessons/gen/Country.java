package com.example.sewen.andoidlessons.gen;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private final List<City> cities;
    private final double maxCoordinate;

    public Country(int cities, double maxCoordinate) {
        this.cities = new ArrayList<>();
        this.maxCoordinate = maxCoordinate;

        for (int i = 0; i < cities; i++) {
            double x = Math.random() * maxCoordinate * 2 - maxCoordinate;
            double y = Math.sqrt(Math.pow(maxCoordinate, 2) - Math.pow(x, 2));
            int ySign = (int) (Math.random() * 100 - 50);
            this.cities.add(new City(x, y * ySign / Math.abs(ySign)));
        }
    }

    public City getCity(int index) {
        return this.cities.get(index);
    }

    public int size() {
        return cities.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (Double.compare(country.maxCoordinate, maxCoordinate) != 0) return false;
        return cities.equals(country.cities);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = cities.hashCode();
        temp = Double.doubleToLongBits(maxCoordinate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
