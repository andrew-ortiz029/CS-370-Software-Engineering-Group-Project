package com.c4n.c4n_weather.Locations;

import java.util.List;
// define weather class to store weather data, define getters and setters

public class Weather {
    private double lat;
    private double lon;
    private String timezone;
    private Current current;
    private List<Minutely> minutely;
    private List<Hourly> hourly;

    // latitude
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    // longitude
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }

    // timezone
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

    public List<Minutely> getMinutely() {
        return minutely;
    }

    public void setMinutely(List<Minutely> minutely) {
        this.minutely = minutely;
    }
    public List<Hourly> getHourly() {
        return hourly;
    }
    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }


    public static class Current {
        private double dt;
        private double sunrise;
        private double sunset;
        private double temp;
        private double feels_like;
        private double pressure;
        private double humidity;
        private double clouds;
        private double uvi;
        private double visibility;
        private double wind_speed;
        private double wind_gust;
        private double wind_deg;
        private double rain;
        private double snow;


        // getters and setters for weather metadata
        public double getDt() {
            return dt;
        }
        public void setDt(double dt) {
            this.dt = dt;
        }

        public double getSunrise() {
            return sunrise;
        }
        public void setSunrise(double sunrise) {
            this.sunrise = sunrise;
        }

        public double getSunset() {
            return sunset;
        }
        public void setSunset(double sunset) {
            this.sunset = sunset;
        }

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

        public double getPressure() {
            return pressure;
        }
        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getHumidity() {
            return humidity;
        }
        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getClouds() {
            return clouds;
        }
        public void setClouds(double clouds) {
            this.clouds = clouds;
        }

        public double getUvi() {
            return uvi;
        }
        public void setUvi(double uvi) {
            this.uvi = uvi;
        }

        public double getVisibility() {
            return visibility;
        }
        public void setVisibility(double visibility) {
            this.visibility = visibility;
        }

        public double getWind_speed() {
            return wind_speed;
        }
        public void setWind_speed(double wind_speed) {
            this.wind_speed = wind_speed;
        }

        public double getWind_gust() {
            return wind_gust;
        }
        public void setWind_gust(double wind_gust) {
            this.wind_gust = wind_gust;
        }

        public double getWind_deg() {
            return wind_deg;
        }
        public void setWind_deg(double wind_deg) {
            this.wind_deg = wind_deg;
        }

        public double getRain() {
            return rain;
        }
        public void setRain(double rain) {
            this.rain = rain;
        }

        public double getSnow() {
            return snow;
        }
        public void setSnow(double snow) {
            this.snow = snow;
        }    
    }
    public static class Minutely {
        private int dt;
        private float precipitation;

        public int getDt() {
            return dt;
        }
        public void setDt(int dt) {
            this.dt = dt;
        }

        public float getPrecipitation() {
            return precipitation;
        }
        public void setPrecipitation(float precipitation) {
            this.precipitation = precipitation;
        }
    }
    public static class Hourly {
        private double dt;
        private double temp;
        private double feels_like;
        private double pressure;
        private double humidity;
        private double dew_point;
        private double clouds;
        private double visibility;
        private double wind_speed;
        private double wind_gust;
        private double wind_deg;
        private double pop;
        private double rain;
        private double snow;


        public double getDt() {
            return dt;
        }
        public void setDt(double dt) {
            this.dt = dt;
        }

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

        public double getPressure() {
            return pressure;
        }
        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getHumidity() {
            return humidity;
        }
        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getDew_point() {
            return dew_point;
        }
        public void setDew_point(double dew_point) {
            this.dew_point = dew_point;
        }

        public double getClouds() {
            return clouds;
        }
        public void setClouds(double clouds) {
            this.clouds = clouds;
        }

        public double getVisibility() {
            return visibility;
        }
        public void setVisibility(double visibility) {
            this.visibility = visibility;
        }

        public double getWind_speed() {
            return wind_speed;
        }
        public void setWind_speed(double wind_speed) {
            this.wind_speed = wind_speed;
        }

        public double getWind_gust() {
            return wind_gust;
        }
        public void setWind_gust(double wind_gust) {
            this.wind_gust = wind_gust;
        }

        public double getWind_deg() {
            return wind_deg;
        }
        public void setWind_deg(double wind_deg) {
            this.wind_deg = wind_deg;
        }

        public double getPop() {
            return pop;
        }
        public void setPop(double pop) {
            this.pop = pop;
        }

        public double getRain() {
            return rain;
        }
        public void setRain(double rain) {
            this.rain = rain;
        }

        public double getSnow() {
            return snow;
        }
        public void setSnow(double snow) {
            this.snow = snow;
        }

    }

    // override toString method within weather class only to print out the weather data as needed
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weather{").append(":\n");
        sb.append("\t•lat=").append(lat).append("\n");
        sb.append("\t•lon=").append(lon).append("\n");
        sb.append("\t•timezone='").append(timezone).append('\'').append("\n");
        sb.append("\t•current=").append(current).append("\n");
        sb.append("\t•dt=").append(current.dt).append("\n");
        sb.append("\t•sunrise=").append(current.sunrise).append("\n");
        sb.append("\t•sunset=").append(current.sunset).append("\n");
        sb.append("\t•temp=").append(current.temp).append("\n");
        sb.append("\t•feels_like=").append(current.feels_like).append("\n");
        sb.append("\t•pressure=").append(current.pressure).append("\n");
        sb.append("\t•humidity=").append(current.humidity).append("\n");
        sb.append("\t•clouds=").append(current.clouds).append("\n");
        sb.append("\t•uvi=").append(current.uvi).append("\n");
        sb.append("\t•visibility=").append(current.visibility).append("\n");
        sb.append("\t•wind_speed=").append(current.wind_speed).append("\n");
        sb.append("\t•wind_gust=").append(current.wind_gust).append("\n");
        sb.append("\t•wind_deg=").append(current.wind_deg).append("\n");
        sb.append("\t•rain=").append(current.rain).append("\n");
        sb.append("\t•snow=").append(current.snow).append("\n");
        
        // sb.append("\t\t•minutely=").append(minutely).append("\n");

        // sb.append("\t\t•hourly=").append(hourly).append("\n");

        sb.append('}');
        return sb.toString();
        }
}
