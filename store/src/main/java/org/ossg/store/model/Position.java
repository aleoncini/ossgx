package org.ossg.store.model;

import java.util.Objects;

public class Position {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public Position setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Position setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }

        Position other = (Position) obj;

        return Objects.equals(other.latitude, this.latitude)
        && (Objects.equals(other.longitude, this.longitude));
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }


    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"latitude\": ").append(latitude);
        buffer.append(", ");
        buffer.append("\"longitude\": ").append(longitude);
        buffer.append(" }");
        return  buffer.toString();
    }

}