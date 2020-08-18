package org.ossg.store.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Course {
    private String id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String cap;
    private String website;
    private Position position;
    private Map<String, Hole> holes = new HashMap<String, Hole>();

    public String getId() {
        return id;
    }

    public Course setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Course setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Course setAddress(String address) {
        this.address = address;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public Course setPosition(Position position) {
        this.position = position;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Course setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Course setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCap() {
        return cap;
    }

    public Course setCap(String cap) {
        this.cap = cap;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Course setWebsite(String website) {
        this.website = website;
        return this;
    }

    public Map<String, Hole> getHoles() {
        return holes;
    }

    public Hole getHole(int holeNumber) {
        String holeName = String.valueOf(holeNumber);
        return holes.get(holeName);
    }

    public Course setHoles(Hole[] holes) {
        for (int i=1; i<=18; i++){
            String holeName = String.valueOf(i);
            this.holes.put(holeName, holes[i]);
        }
        return this;
    }

    public Course setHole(int holeNumber, Hole hole) {
        String holeName = String.valueOf(holeNumber);
        holes.put(holeName, hole);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Course)) {
            return false;
        }

        Course other = (Course) obj;

        return Objects.equals(other.name, this.name)
                &&(Objects.equals(other.address, this.address))
                &&(Objects.equals(other.cap, this.cap))
                &&(Objects.equals(other.position, this.position));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, position);
    }

    @Override
    public String toString(){
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");

        if (id != null){
            buffer.append("\"id\": \"").append(id).append("\"");
            notFirst = true;
        }

        if (name != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"name\": \"").append(name).append("\"");
            notFirst = true;
        }

        if (address != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"address\": \"").append(address).append("\"");
            notFirst = true;
        }

        if (city != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"city\": \"").append(city).append("\"");
            notFirst = true;
        }

        if (country != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"country\": \"").append(country).append("\"");
            notFirst = true;
        }

        if (cap != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"cap\": \"").append(cap).append("\"");
            notFirst = true;
        }

        if (website != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"website\": \"").append(website).append("\"");
            notFirst = true;
        }

        if (position != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"position\": \"").append(position.toString()).append("\"");
            notFirst = true;
        }

        if (holes != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"holes\": { ");
            for (int i=1; i<=18; i++) {
                String holeName = String.valueOf(i);
                if (i>1){
                    buffer.append(", ");
                }
                buffer.append("\"").append(holeName).append("\": ").append(holes.get(holeName).toString());
            }
            buffer.append(" }");
        }
        buffer.append(" }");
        return  buffer.toString();
    }

}