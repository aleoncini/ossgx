package org.ossg.store.model.serializer.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.Round;
import org.ossg.store.model.Score;
import org.ossg.store.model.serializer.DayOfEventSerializer;
import org.ossg.store.model.serializer.RoundSerializer;
import org.ossg.store.model.serializer.ScoreSerializer;

@ApplicationScoped
public class DocumentRoundSerializer implements RoundSerializer<Document>{

    @Inject
    DayOfEventSerializer<Document> dayOfEventSerializer;
    @Inject
    ScoreSerializer<Document> scoreSerializer;

	@Override
	public Document serialize(Round round) {
		if (round == null
            || (round.getId() == null) 
            || (round.getPlayerId() == null) 
            || (round.getCourseId() == null) 
            || (round.getDayOfEvent() == null) 
            || (round.getPhcp() == 0) 
        ) {
            throw new IllegalArgumentException("Object contains invalid data");
        }

        Document document = new Document("id", round.getId())
                    .append("playerId", round.getPlayerId())
                    .append("playerName", round.getPlayerName())
                    .append("courseId", round.getCourseId())
                    .append("courseName", round.getCourseName())
                    .append("dayOfEvent", dayOfEventSerializer.serialize(round.getDayOfEvent()))
                    .append("phcp", round.getPhcp());

        if(round.getTournamentId() != null){
            document.append("tournamentId", round.getTournamentId())
                    .append("tournamentName", round.getTournamentName());
        }

        List<Document> scoreDocuments = new ArrayList<Document>();
        for (Score score : round.getScores()) {
            scoreDocuments.add( scoreSerializer.serialize(score) );                
        }
        document.append("scores",scoreDocuments);

        return document;
	}

	@Override
	public Round deserialize(Document document) {
        String id = document.getString("id");
        String playerId = document.getString("playerId");
        String playerName = document.getString("playerName");
        String courseId = document.getString("courseId");
        String courseName = document.getString("courseName");
        int phcp = document.getInteger("phcp");
        Document dayDocument = (Document) document.get("dayOfEvent");
        DayOfEvent dayOfEvent = dayOfEventSerializer.deserialize(dayDocument);

        if (document == null
				|| (id == null)
				|| (playerId == null)
				|| (courseId == null)
				|| (dayOfEvent == null)
			) {
			throw new IllegalArgumentException("Unable to deserialize the Round object. Document contains invalid data");
        }

        Round round = new Round()
                    .setId(id)
                    .setPlayerId(playerId)
                    .setPlayerName(playerName)
                    .setCourseId(courseId)
                    .setCourseName(courseName)
                    .setPhcp(phcp)
                    .setDayOfEvent(dayOfEvent);
        if(document.containsKey("tournamentId")){
            round.setTournamentId(document.getString("tournamentId")).setTournamentName(document.getString("tournamentName"));
        }
        if(!document.containsKey("scores")){
			throw new IllegalArgumentException("Unable to deserialize the Round object. Scores not available");
        }
        List<Document> scoreDocuments = (List<Document>) document.get("scores");
        Score[] scores = new Score[18];
        int index = 0;
        for (Document scoreDocument : scoreDocuments){
            Score score = scoreSerializer.deserialize(scoreDocument);
            scores[index++] = score;
        }
        round.setScores(scores);

        return round;
	}
    
}