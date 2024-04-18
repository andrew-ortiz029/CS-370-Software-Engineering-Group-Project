package com.c4n.c4n_weather.Locations;
// define weather class to store weather data, define getters and setters

public class Weather {
    private double lat;
    private double lon;
    private String timezone;
    private Current current;
    // private Minutely minutely;
    // private Hourly hourly;
    // private Daily daily;

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

    // public Minutely getMinutely() {
    //     return minutely;
    // }
    // public void setMinutely(Minutely minutely) {
    //     this.minutely = minutely;
    // }

    // public Hourly getHourly() {
    //     return hourly;
    // }
    // public void setHourly(Hourly hourly) {
    //     this.hourly = hourly;
    // }

    // public Daily getDaily() {
    //     return daily;
    // }
    // public void setDaily(Daily daily) {
    //     this.daily = daily;
    // }


    public static class Current {
        private double dt;
        private double sunrise;
        private double sunset;
        private double temp;
        private double feels_like;
        private double pressure;
        private double humidity;
        private double dew_point;
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



    // public static class Minutely {
    //     private int dt;
    //     private double precipitation;

    //     public int getDt() {
    //         return dt;
    //     }
    //     public void setDt(int dt) {
    //         this.dt = dt;
    //     }

    //     public double getPrecipitation() {
    //         return precipitation;
    //     }
    //     public void setPrecipitation(double precipitation) {
    //         this.precipitation = precipitation;
    //     }
    // }


    // public static class Hourly {
    //     private double dt;
    //     private double temp;
    //     private double feels_like;
    //     private double pressure;
    //     private double humidity;
    //     private double dew_point;
    //     private double clouds;
    //     private double visibility;
    //     private double wind_speed;
    //     private double wind_gust;
    //     private double wind_deg;
    //     private double pop;
    //     private double rain;
    //     private double snow;


    //     public double getDt() {
    //         return dt;
    //     }
    //     public void setDt(double dt) {
    //         this.dt = dt;
    //     }

    //     public double getTemp() {
    //         return temp;
    //     }
    //     public void setTemp(double temp) {
    //         this.temp = temp;
    //     }

    //     public double getFeels_like() {
    //         return feels_like;
    //     }
    //     public void setFeels_like(double feels_like) {
    //         this.feels_like = feels_like;
    //     }

    //     public double getPressure() {
    //         return pressure;
    //     }
    //     public void setPressure(double pressure) {
    //         this.pressure = pressure;
    //     }

    //     public double getHumidity() {
    //         return humidity;
    //     }
    //     public void setHumidity(double humidity) {
    //         this.humidity = humidity;
    //     }

    //     public double getDew_point() {
    //         return dew_point;
    //     }
    //     public void setDew_point(double dew_point) {
    //         this.dew_point = dew_point;
    //     }

    //     public double getClouds() {
    //         return clouds;
    //     }
    //     public void setClouds(double clouds) {
    //         this.clouds = clouds;
    //     }

    //     public double getVisibility() {
    //         return visibility;
    //     }
    //     public void setVisibility(double visibility) {
    //         this.visibility = visibility;
    //     }

    //     public double getWind_speed() {
    //         return wind_speed;
    //     }
    //     public void setWind_speed(double wind_speed) {
    //         this.wind_speed = wind_speed;
    //     }

    //     public double getWind_gust() {
    //         return wind_gust;
    //     }
    //     public void setWind_gust(double wind_gust) {
    //         this.wind_gust = wind_gust;
    //     }

    //     public double getWind_deg() {
    //         return wind_deg;
    //     }
    //     public void setWind_deg(double wind_deg) {
    //         this.wind_deg = wind_deg;
    //     }

    //     public double getPop() {
    //         return pop;
    //     }
    //     public void setPop(double pop) {
    //         this.pop = pop;
    //     }

    //     public double getRain() {
    //         return rain;
    //     }
    //     public void setRain(double rain) {
    //         this.rain = rain;
    //     }

    //     public double getSnow() {
    //         return snow;
    //     }
    //     public void setSnow(double snow) {
    //         this.snow = snow;
    //     }

    // }
        


    // public static class Daily {
    //     private double dt;
    //     private double sunrise;
    //     private double sunset;
    //     private double moonrise;
    //     private double moonset;
    //     private double moon_phase;
    //     private double temp;
    //     private double feels_like;
    //     private double pressure;
    //     private double humidity;
    //     private double dew_point;
    //     private double wind_speed;
    //     private double wind_gust;
    //     private double wind_deg;
    //     private double clouds;
    //     private double pop;
    //     private double rain;
    //     private double snow;
    //     private double uvi;

    //     public double getDt() {
    //         return dt;
    //     }
    //     public void setDt(double dt) {
    //         this.dt = dt;
    //     }

    //     public double getSunrise() {
    //         return sunrise;
    //     }
    //     public void setSunrise(double sunrise) {
    //         this.sunrise = sunrise;
    //     }

    //     public double getSunset() {
    //         return sunset;
    //     }
    //     public void setSunset(double sunset) {
    //         this.sunset = sunset;
    //     }

    //     public double getMoonrise() {
    //         return moonrise;
    //     }
    //     public void setMoonrise(double moonrise) {
    //         this.moonrise = moonrise;
    //     }

    //     public double getMoonset() {
    //         return moonset;
    //     }
    //     public void setMoonset(double moonset) {
    //         this.moonset = moonset;
    //     }

    //     public double getMoon_phase() {
    //         return moon_phase;
    //     }
    //     public void setMoon_phase(double moon_phase) {
    //         this.moon_phase = moon_phase;
    //     }

    //     public double getTemp() {
    //         return temp;
    //     }
    //     public void setTemp(double temp) {
    //         this.temp = temp;
    //     }

    //     public double getFeels_like() {
    //         return feels_like;
    //     }
    //     public void setFeels_like(double feels_like) {
    //         this.feels_like = feels_like;
    //     }

    //     public double getPressure() {
    //         return pressure;
    //     }
    //     public void setPressure(double pressure) {
    //         this.pressure = pressure;
    //     }

    //     public double getHumidity() {
    //         return humidity;
    //     }
    //     public void setHumidity(double humidity) {
    //         this.humidity = humidity;
    //     }

    //     public double getDew_point() {
    //         return dew_point;
    //     }
    //     public void setDew_point(double dew_point) {
    //         this.dew_point = dew_point;
    //     }
    // }


    // override toString method within weather class only to print out the weather data as needed
    public String toString() {
        // will add more return data to parse as needed, this works for now
        return "Weather{" +
            "lat=" + lat +
            ", lon=" + lon +
            ", timezone=" + timezone +
            ", current=" + current +
            ", sunrise=" + current.getSunrise() +
            ", sunset=" + current.getSunset() +
            ", temp=" + current.getTemp() +
            ", feels_like=" + current.getFeels_like() +
            ", pressure=" + current.getPressure() +
            ", humidity=" + current.getHumidity() +
            ", dew_point=" + current.getDew_point() +
            ", clouds=" + current.getClouds() +
            ", uvi=" + current.getUvi() +
            ", visibility=" + current.getVisibility() +
            ", wind_speed=" + current.getWind_speed() +
            ", wind_gust=" + current.getWind_gust() +
            ", wind_deg=" + current.getWind_deg() +
            ", rain=" + current.getRain() +
            ", snow=" + current.getSnow() +
            // ", minutely=" + minutely +
            // ", hourly=" + hourly +
            // ", daily=" + daily +

            '}';
        }
    }

