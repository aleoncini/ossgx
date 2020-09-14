package org.ossg.store.model;

import java.util.Objects;

public class Score {
    private int par = 0;
    private int hcp = 0;
    private int strokes = -1;
    private int additionalStrokes = -1;
    private int points = -1;

    public int getPar() {
        return par;
    }

    public Score setPar(int par) {
        this.par = par;
        return this;
    }

    public int getHcp() {
        return hcp;
    }

    public Score setHcp(int hcp) {
        this.hcp = hcp;
        return this;
    }

    public int getStrokes() {
        return strokes;
    }

    public Score setStrokes(int strokes) {
        this.strokes = strokes;
        return this;
    }

    public int getAdditionalStrokes() {
        return additionalStrokes;
    }

    public Score setAdditionalStrokes(int strokes) {
        this.additionalStrokes = strokes;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public Score setPoints(int points) {
        this.points = points;
        return this;
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        if (strokes >= 0){
            buffer.append("\"strokes\": ").append(strokes);
            buffer.append(", ").append("\"par\": ").append(par);
            buffer.append(", ").append("\"hcp\": ").append(hcp);
            buffer.append(", ").append("\"additionalStrokes\": ").append(additionalStrokes);
            buffer.append(", ").append("\"points\": ").append(points);
        }
        buffer.append(" }");
        return  buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Score)) {
            return false;
        }

        Score other = (Score) obj;

        return Objects.equals(other.hcp, this.hcp)
        && (Objects.equals(other.par, this.par))
        && (Objects.equals(other.strokes, this.strokes))
        && (Objects.equals(other.additionalStrokes, this.additionalStrokes))
        && (Objects.equals(other.points, this.points));
    }

    @Override
    public int hashCode() {
        return Objects.hash(par, hcp, strokes);
    }

}