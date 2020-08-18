package org.ossg.store.model;

import java.util.Objects;

public class Hole {
    private int hcp=0;
    private int par=0;

    public int getHcp() {
        return hcp;
    }

    public Hole setHcp(int hcp) {
        this.hcp = hcp;
        return this;
    }

    public int getPar() {
        return par;
    }

    public Hole setPar(int par) {
        this.par = par;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Hole)) {
            return false;
        }

        Hole other = (Hole) obj;

        return Objects.equals(other.hcp, this.hcp)
        && (Objects.equals(other.par, this.par));
    }

    @Override
    public int hashCode() {
        return Objects.hash(hcp, par);
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"par\": ").append(par);
        buffer.append(", ");
        buffer.append("\"hcp\": ").append(hcp);
        buffer.append(" }");
        return  buffer.toString();
    }

}