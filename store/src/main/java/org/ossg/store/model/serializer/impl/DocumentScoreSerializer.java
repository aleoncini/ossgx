package org.ossg.store.model.serializer.impl;

import javax.enterprise.context.ApplicationScoped;

import org.bson.Document;
import org.ossg.store.model.Score;
import org.ossg.store.model.serializer.ScoreSerializer;

@ApplicationScoped
public class DocumentScoreSerializer implements ScoreSerializer<Document> {

	@Override
	public Document serialize(Score score) {
		if (score == null
            || (score.getStrokes() == 0) 
            || (score.getHcp() == 0) 
            || (score.getPar() == 0) 
        ) {
	            throw new IllegalArgumentException("Object contains invalid data");
	        }

	        return new Document("hcp", score.getHcp())
                    .append("par", score.getPar())
                    .append("strokes", score.getStrokes())
                    .append("additionalStrokes", score.getAdditionalStrokes())
                    .append("points", score.getPoints());
	}

	@Override
	public Score deserialize(Document document) {
        int hcp = document.getInteger("hcp");
        int par = document.getInteger("par");
        int strokes = document.getInteger("strokes");
        int additionalStrokes = document.getInteger("additionalStrokes");
        int points = document.getInteger("points");

        if (document == null
				|| (hcp == 0)
				|| (par == 0)
				|| (strokes == 0)
				|| (additionalStrokes < 0)
				|| (points < 0)
			) {
			throw new IllegalArgumentException("Unable to deserialize the Score object. Document contains invalid data");
        }

        return new Score().setHcp(hcp).setPar(par).setStrokes(strokes).setAdditionalStrokes(additionalStrokes).setPoints(points);
	}
    
}