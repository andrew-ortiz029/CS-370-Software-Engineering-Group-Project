package com.c4n.c4n_weather.Locations;

// define weather class to store weather data, define getters and setters

public class Weather {
    private double lat;
    private double lon;
    private String timezone;
    private Current current;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Current {
        private double temp;
        private double feels_like;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(double feels_like) {
            this.feels_like = feels_like;
        }
    }
    // override toString method within weather class only to print out the weather data as needed
    public String toString() {
        // will add more return data to parse as needed, this works for now
        return "Weather{" +
            "lat=" + lat +
            ", lon=" + lon +
            ", current=" + current +
            ", timezone=" + timezone +
            ", temp=" + current.getTemp() +
            '}';
    }
}
