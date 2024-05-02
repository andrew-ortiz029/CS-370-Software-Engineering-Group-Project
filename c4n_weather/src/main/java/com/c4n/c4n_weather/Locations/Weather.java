package com.c4n.c4n_weather.Locations;

import java.util.List;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private List<Alerts> alerts;

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

    public List<Alerts> getAlerts() {
        return alerts;
    }
    public void setAlerts(List<Alerts> alerts) {
        this.alerts = alerts;
    }


    public static class Current {
        private double dt;
        private long sunrise;
        private long sunset;
        private long temp;
        private long feels_like;
        private long humidity;
        private long clouds;
        private long uvi;
        private long visibility;
        private int wind_speed;
        private int wind_gust;


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

        public long getTemp() {
            return temp;
        }
        public void setTemp(long temp) {
            this.temp = temp;
        }


        public long getFeels_like() {
            return feels_like;
        }
        public void setFeels_like(long feels_like) {
            this.feels_like = feels_like;
        }


        public long getHumidity() {
            return humidity;
        }
        public void setHumidity(long humidity) {
            this.humidity = humidity;
        }

        public long getClouds() {
            return clouds;
        }
        public void setClouds(long clouds) {
            this.clouds = clouds;
        }

        public long getUvi() {
            return uvi;
        }
        public void setUvi(long uvi) {
            this.uvi = uvi;
        }

        public long getVisibility() {
            return visibility;
        }
        public void setVisibility(long visibility) {
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
    }

    public static class Hourly {
        private long dt;
        private long temp;

        private long clouds;
        private long visibility;
        private long wind_speed;

        public List<WeatherDetails> weather;


        public String getDt() {   
            return Utils.unixToHour(dt);
        }
        public void setDt(long dt) {
            this.dt = dt;
        }

        public long getTemp() {
            return temp;
        }
        public void setTemp(long temp) {
            this.temp = temp;
        }


        public long getClouds() {
            return clouds;
        }
        public void setClouds(long clouds) {
            this.clouds = clouds;
        }

        public long getVisibility() {
            return visibility;
        }
        public void setVisibility(long visibility) {
            this.visibility = visibility;
        }

        public long getWind_speed() {
            return wind_speed;
        }
        public void setWind_speed(long wind_speed) {
            this.wind_speed = wind_speed;
        }

        public static class WeatherDetails {
            public String icon;
        
            public String getIcon() {
                return icon;
            }
            public void setIcon(String icon) {
                this.icon = icon;
            }
    }

    }

    public static class Daily {

        public long dt;
        public String summary;
        public Temp temp;
        
        
        public List<WeatherDetails> weather;

        public String getDt() {
            return Utils.unixToDayOfWeek(dt);
        }
        public void setDt(long dt) {
            this.dt = dt;
        }

        public String getSummary() {
            return summary;
        }
        public void setSummary(String summary) {
            this.summary = summary;
        }

        public Temp getTemp() {
            return temp;
        }
        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public List<WeatherDetails> getWeather() {
            return weather;
        }
        public void setWeather(List<WeatherDetails> weather) {
            this.weather = weather;
        }

// this needs to be fixed in order to get the appropriate icon

        public class Temp {
            private long day;
            private long min;
            private long max;

            public long getDay() {
                return day;
            }
            public void setDay(long day) {
                this.day = day;
            }

            public long getMin() {
                return min;
            }
            public void setMin(long min) {
                this.min = min;
            }

            public long getMax() {
                return max;
            }
            public void setMax(long max) {
                this.max = max;
            }
        }

        public static class WeatherDetails {
            public String icon;
        
            public String getIcon() {
                return icon;
            }
            public void setIcon(String icon) {
                this.icon = icon;
            }
    }
}


    public static class Alerts {

        public String sender_name;
        public String event;
        public long start;
        public long end;
        public String description;

        public String getSender_name() {
            return sender_name;
        }
        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getEvent() {
            return event;
        }
        public void setEvent(String event) {
            this.event = event;
        }

        public String getStart() {
            return Utils.convertUnixToHumanReadable(start);
        }
        public void setStart(long start) {
            this.start = start;
        }

        public String getEnd() {
            return Utils.convertUnixToHumanReadable(end);
        }
        public void setEnd(long end) {
            this.end = end;
        }

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
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

        sb.append("Alerts: \n");
        if (alerts != null) {
            for (Alerts alert : alerts) {
                sb.append("\tSender Name: ").append(alert.getSender_name()).append("\n");
                sb.append("\tStart: ").append(alert.getStart()).append("\n");
                sb.append("\tEnd: ").append(alert.getEnd()).append("\n");
                sb.append("\tDescription: ").append(alert.getDescription()).append("\n");
            }
        }
        
        return sb.toString();
        }

    // helper functions class for converting any data in the api response
    public class Utils {
        // converting unix timestamps to readable format, call this in any api response getter method above
        // TODO - add timezone offet to apply to unix time conversion
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

        public static String unixToHour(long unixSeconds) {
            Instant instant = Instant.ofEpochSecond(unixSeconds);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh a")
                .withZone(ZoneId.systemDefault());
            return formatter.format(instant);
        }
    }
}


