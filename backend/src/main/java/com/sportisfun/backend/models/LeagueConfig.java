package com.sportisfun.backend.models;

public enum LeagueConfig {
    EPL("Premier League", "England"),
    LA_LIGA("La Liga", "Spain"),
    BUNDESLIGA("Bundesliga", "Germany"),
    SERIE_A("Serie A", "Italy"),
    LIGUE_ONE("Ligue 1", "France");

    private final String name;
    private final String country;

    LeagueConfig(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
