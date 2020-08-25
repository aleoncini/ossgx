package org.ossg.store.model.util;

import org.ossg.store.model.Score;

public class Scorecard {

    public Score setScore(int phcp, int hcp, int par, int strokes){
        Score score = new Score().setHcp(hcp).setPar(par).setStrokes(strokes);
        score.setAdditionalStrokes(calculateAdditionalStrokes(phcp, hcp));
        score.setPoints(calculatePoints(par, score.getAdditionalStrokes(), strokes));
		return score;
    }

    public int calculateAdditionalStrokes(int phcp, int hcp){
        if (phcp == 18){
            return 1;
        }
        if (phcp > 18){
            if ((phcp - 18) >= hcp) {
                return 2;
            } else {
                return 1;
            }
        }
        if (phcp >= hcp){
            return 1;
        }
        return 0;
    }

    public int calculatePoints(int par, int additionalStrokes, int strokes){
        int stb = ((par + additionalStrokes) - strokes) + 2;
        if (stb < 0){
            stb = 0;
        }
        return stb;
    }

}