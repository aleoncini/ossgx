package org.ossg.store.model;

import java.util.Objects;

public class Player {
    private String id = "";
    private String name = "";
    private String email = "";

    public String getId() {
        return id;
    }

    public Player setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Player setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }

        Player other = (Player) obj;

        return Objects.equals(other.id, this.id)
        		&& (Objects.equals(other.email, this.email));
        
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"id\": \"").append(id).append("\", ");
        buffer.append("\"name\": \"").append(name).append("\"");
        if (email != null){
            buffer.append(", \"email\": \"").append(email).append("\"");
        }
        buffer.append(" }");
        return  buffer.toString();
    }

}