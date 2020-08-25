package org.ossg.store.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Round {
    private String id;
    private DayOfEvent dayOfEvent;
    private String playerId;
    private String playerName;
    private String tournamentId;
    private String tournamentName;
    private String courseId;
    private String courseName;
    private int phcp;
    private Map<String, Score> scores = new HashMap<String, Score>();

    
    public String getId() {
        return id;
    }

    public Round setId(String id) {
        this.id = id;
        return this;
    }

    public DayOfEvent getDayOfEvent(){
        return this.dayOfEvent;
    }

    public Round setDayOfEvent(DayOfEvent dayOfEvent) {
        this.dayOfEvent = dayOfEvent;
        return this;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Round setPlayerId(String id) {
        this.playerId = id;
        return this;
    }

    public String getCourseId() {
        return courseId;
    }

    public Round setCourseId(String id) {
        this.courseId = id;
        return this;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Round setPlayerName(String name) {
        this.playerName = name;
        return this;
    }

    public String getCourseName() {
        return courseName;
    }

    public Round setCourseName(String name) {
        this.courseName = name;
        return this;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public Round setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public Round setTournamentName(String name) {
        this.tournamentName = name;
        return this;
    }

    public int getPhcp() {
        return phcp;
    }

    public Round setPhcp(int phcp) {
        this.phcp = phcp;
        return this;
    }

    public Map<String, Score> getScores() {
        return this.scores;
    }

    public Round setScores(Map<String, Score> scores) {
        this.scores = scores;
        return this;
    }

    public Score getScore(int holeNumber) {
        return (scores == null) ? null : scores.get("" + holeNumber);
    }

    public Round setScore(int holeNumber, int hcp, int par, int strokes, int additionalStrokes, int points) {
        return this.setScore(holeNumber, new Score()
                        .setHcp(hcp)
                        .setPar(par)
                        .setStrokes(strokes)
                        .setAdditionalStrokes(additionalStrokes)
                        .setPoints(points)
        );
    }

    public Round setScore(int holeNumber, Score score) {
        scores.put("" + holeNumber, score);
        return this;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"id\": \"").append(id).append("\"");
        buffer.append(", ");
        buffer.append("\"dayOfEvent\": ").append(dayOfEvent.toString());
        buffer.append(", ");
        buffer.append("\"playerId\": \"").append(playerId).append("\"");
        buffer.append(", ");
        buffer.append("\"playerName\": \"").append(playerName).append("\"");
        if (tournamentId != null){
            buffer.append(", ");
            buffer.append("\"tournamentId\": \"").append(tournamentId).append("\"");
            buffer.append(", \"tournamentName\": \"").append(tournamentName).append("\"");
        }
        buffer.append(", ");
        buffer.append("\"courseId\": \"").append(courseId).append("\"");
        buffer.append(", ");
        buffer.append("\"courseName\": \"").append(courseName).append("\"");
        buffer.append(", ");
        buffer.append("\"scores\": { ");

        buffer.append("\"1\": ").append(getScore(1).toString());
        for(int i = 2; i<= 18; i++){
            buffer.append(", ");
            buffer.append("\"").append(i).append("\": ").append(getScore(i).toString());
        }    
        buffer.append(" }");
        buffer.append(" }");
        return  buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Round)) {
            return false;
        }

        Round other = (Round) obj;

        return Objects.equals(other.id, this.id)
        && (Objects.equals(other.playerId, this.playerId))
        && (Objects.equals(other.courseId, courseId))
        && (Objects.equals(other.dayOfEvent, this.dayOfEvent));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerId, courseId, dayOfEvent);
    }

}