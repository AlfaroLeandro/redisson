package com.curso.rediscurso.city.dto;

public class City{
    private String zip;
    private String city;
    private String stateName;
    private int temperature;

    public City(String zip, String city, String stateName, int temperature) {
        this.zip = zip;
        this.city = city;
        this.stateName = stateName;
        this.temperature = temperature;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", stateName='" + stateName + '\'' +
                ", temperature=" + temperature +
                '}';
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
