package org.ossg.store.importing;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.Round;
import org.ossg.store.model.Score;
import org.ossg.store.model.util.Scorecard;

@ApplicationScoped
public class H19Parser {
    private static final String BASE_URL = "https://clubhouse.hole19golf.com/rounds/";
    private static final Logger LOG = Logger.getLogger("org.ossg");

    public Round parse(String id){
        String url = BASE_URL + id;
        Round round = new Round().setId(id);
        Document doc;
		try {
            doc = Jsoup.connect(url).get();
            Element playerElement = doc.getElementById("player-name");
            String playerName = playerElement.text().trim();
            String playerId = getPlayerId(playerName);
            round.setPlayerName(playerName);
            round.setPlayerId(playerId);

            Element mainContainer = doc.getElementById("main-container");

            Elements blks = mainContainer.getElementsByClass("blk");
            String courseName = blks.first().text().trim();
            String courseId = getCourseId(courseName);
            round.setCourseName(courseName);
            round.setCourseId(courseId);

            Elements times = mainContainer.getElementsByTag("time");
            Element time = times.first();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.from(formatter.parse(time.attr("datetime").substring(0, 10)));
            DayOfEvent dayOfEvent = new DayOfEvent()
                                            .setDay(date.getDayOfMonth())
                                            .setMonth(date.getMonthValue())
                                            .setYear(date.getYear());

            round.setDayOfEvent(dayOfEvent);

            Elements grosses = mainContainer.getElementsByClass("stroke_play_gross_score");
            int gross = Integer.parseInt(grosses.first().text());
            Elements nets = mainContainer.getElementsByClass("stroke_play_net_score");
            int net = Integer.parseInt(nets.first().text());
            int phcp = gross - net;
            round.setPhcp(phcp);

            Elements tables = mainContainer.getElementsByClass("table-striped");
            Element table = tables.get(1);
            Element tbody = table.getElementsByTag("tbody").first();
            Elements rows = tbody.getElementsByTag("tr");

            // first row contains hole hcps
            Elements hcpTDs = rows.get(0).getElementsByTag("td");
            // second row contains hole pars
            Elements parTDs = rows.get(1).getElementsByTag("td");
            // third row contains strokes
            Elements strTDs = rows.get(2).getElementsByTag("td");

            for (int i=0; i<18; i++) {
                Element div = hcpTDs.get(i);
                int hcp = Integer.parseInt(div.text());

                div = parTDs.get(i);
                int par = Integer.parseInt(div.text());

                div = strTDs.get(i).getElementsByTag("div").first().getElementsByTag("div").first();
                int strokes = Integer.parseInt(div.text());

                Scorecard sc = new Scorecard();
                int additionalStrokes = sc.calculateAdditionalStrokes(phcp, hcp);
                int points = sc.calculatePoints(par, additionalStrokes, strokes);

                round.setScore((i +1), new Score()
                                    .setHcp(hcp)
                                    .setPar(par)
                                    .setStrokes(strokes)
                                    .setAdditionalStrokes(additionalStrokes)
                                    .setPoints(points)
                );
            }

		} catch (IOException e) {
            LOG.error(e.getLocalizedMessage() + " " + url);
            return null;
		}
        return round;
    }

    private String getPlayerId(String name){
        return "666";
    }
    
    private String getCourseId(String name){
        return "999";
    }

}
