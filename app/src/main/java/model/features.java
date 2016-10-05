package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by krrish on 2/10/2016.
 */

public class features {
    private properties properties;

    public features.properties getProperties() {
        return properties;
    }

    public void setProperties(features.properties properties) {
        this.properties = properties;
    }

    public class properties {

        @SerializedName("mag")
        private double mag;
        @SerializedName("time")
        private long date;
        @SerializedName("place")
        private String location;
        @SerializedName("url")
        private String url;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public double getMag() {
            return mag;
        }

        public void setMag(double mag) {
            this.mag = mag;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
