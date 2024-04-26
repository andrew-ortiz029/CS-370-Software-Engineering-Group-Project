package com.c4n.c4n_weather.Locations;

import java.util.List;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;  
import java.time.format.TextStyle;
import java.util.Locale;

// define weather class to store weather data, define getters and setters

public class Weather {
    private double lat;
    private double lon;
    private String timezone;
    private Current current;
    private List<Hourly> hourly;
    private List<Daily> daily;

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
    public List<Hourly> getHourly() {
        return hourly;
    }
    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }
    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }


    public static class Current {
        private double dt;
        private long sunrise;
        private long sunset;
        private double temp;
        private double feels_like;
        private long humidity;
        private double clouds;
        private double uvi;
        private double visibility;
        private int wind_speed;
        private int wind_gust;
        private Rain rain;
        private Snow snow;


        // getters and setters for weather metadata
        public double getDt() {
            return dt;
        }
        public void setDt(double dt) {
            this.dt = dt;
        }

        public String getSunrise() {
            return Utils.convertUnixToHumanReadable(sunrise);
        }
        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return Utils.convertUnixToHumanReadable(sunset);
        }
        public void setSunset(long sunset) {
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


        public long getHumidity() {
            return humidity;
        }
        public void setHumidity(long humidity) {
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

        public int getWind_speed() {
            return wind_speed;
        }
        public void setWind_speed(int wind_speed) {
            this.wind_speed = wind_speed;
        }

        public int getWind_gust() {
            return wind_gust;
        }
        public void setWind_gust(int wind_gust) {
            this.wind_gust = wind_gust;
        }

        public Rain getRain() {
            return rain;
        }
        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public Snow getSnow() {
            return snow;
        }
        public void setSnow(Snow snow) {
            this.snow = snow;
        }

    }

    public static class Rain {
        private double h1;
    
        // getters and setters
        public double getH1() {
            return h1;
        }
        public void setH1(double h1) {
            this.h1 = h1;
        }
    }

    public static class Snow {
        private double h1;
    
        // getters and setters
        public double getH1() {
            return h1;
        }
        public void setH1(double h1) {
            this.h1 = h1;
        }
    }

    public static class Hourly {
        private double dt;
        private double temp;
        private double humidity;
        private double dew_point;
        private double clouds;
        private double visibility;
        private double wind_speed;
        private double wind_gust;
        private double wind_deg;
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


        public double getSnow() {
            return snow;
        }
        public void setSnow(double snow) {
            this.snow = snow;
        }

    }

    public static class Daily {

        public long dt;
        public Temp temp;
        public weather icon;

        public Temp getTemp() {
            return temp;
        }
        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public String getDt() {
            return Utils.unixToDayOfWeek(dt);
        }
        public void setDt(long dt) {
            this.dt = dt;
        }
        public weather getIcon() {
            return icon;
        }
// this needs to be fixed in order to get the appropriate icon
        public static class weather{
            public String icon;
        }

        public class Temp {
            private double day;
            private double min;
            private double max;

            public double getDay() {
                return day;
            }
            public void setDay(double day) {
                this.day = day;
            }

            public double getMin() {
                return min;
            }
            public void setMin(double min) {
                this.min = min;
            }

            public double getMax() {
                return max;
            }
            public void setMax(double max) {
                this.max = max;
            }
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
        sb.append("\t•humidity=").append(current.humidity).append("\n");
        sb.append("\t•clouds=").append(current.clouds).append("\n");
        sb.append("\t•uvi=").append(current.uvi).append("\n");
        sb.append("\t•visibility=").append(current.visibility).append("\n");
        sb.append("\t•wind_speed=").append(current.wind_speed).append("\n");
        sb.append("\t•wind_gust=").append(current.wind_gust).append("\n");



        sb.append("Daily Temperatures: \n");
        for (Daily dailyItem : daily) {
            sb.append("\tMax Temp: ").append(dailyItem.getTemp().getMax()).append("\n");
        }
        
        return sb.toString();
        }

    // helper functions class for converting any data in the api response
    public class Utils {
        // converting unix timestamps to readable format, call this in any api response getter method above
        public static String convertUnixToHumanReadable(long unixSeconds) {
            Instant instant = Instant.ofEpochSecond(unixSeconds);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a")
                .withZone(ZoneId.systemDefault());
            return formatter.format(instant);
        }
        // convert unix time to day of week,
        public static String unixToDayOfWeek(long unixSeconds) {
            Instant instant = Instant.ofEpochSecond(unixSeconds);
            String dayOfWeek = instant.atZone(ZoneId.systemDefault()).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            return dayOfWeek;
        }
    }
}


