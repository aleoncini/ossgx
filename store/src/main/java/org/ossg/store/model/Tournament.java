package org.ossg.store.model;

import java.util.Objects;

public class Tournament {
    private String id;
    private String title;
    private DayOfEvent dayOfEvent;

    public String getId() {
        return this.id;
    }

    public Tournament setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Tournament setTitle(String title) {
        this.title = title;
        return this;
    }

    public DayOfEvent getDayOfEvent() {
        return this.dayOfEvent;
    }

    public Tournament setDayOfEvent(DayOfEvent dayOfEvent) {
        this.dayOfEvent = dayOfEvent;
        return this;
    }

    @Override
    public String toString(){
        if (id == null || title == null || dayOfEvent == null){
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"id\":\"").append(id).append("\", ");
        buffer.append("\"title\": \"").append(title).append("\", ");
        buffer.append("\"dayOfEvent\": ").append(dayOfEvent.toString());
        buffer.append(" }");
        return  buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tournament)) {
            return false;
        }

        Tournament other = (Tournament) obj;

        return Objects.equals(other.id, this.id)
        && (Objects.equals(other.title, this.title))
        && (Objects.equals(other.dayOfEvent, this.dayOfEvent));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, dayOfEvent);
    }

}