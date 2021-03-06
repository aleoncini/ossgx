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
            if(! exists(doc)){
                return null;
            }
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

            Elements hivals = mainContainer.getElementsByClass("highlights-value");
            int holes = Integer.parseInt(hivals.first().text());
            boolean isNineHole = (holes == 9) ? true : false;

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
            // fourth row contains points
            Elements netTDs = rows.get(3).getElementsByTag("td");

            for (int i=0; i<18; i++) {
                Element hcpTD = hcpTDs.get(i);
                int hcp = Integer.parseInt(hcpTD.text());

                Element parTD = parTDs.get(i);
                int par = Integer.parseInt(parTD.text());

                if(isNineHole && (i > 8)){
                    round.setScore((i +1), new Score()
                        .setHcp(hcp)
                        .setPar(par)
                        .setStrokes(0)
                        .setAdditionalStrokes(0)
                        .setPoints(0)
                    );
                } else {
                    Element div = strTDs.get(i).getElementsByTag("div").first().getElementsByTag("div").first();
                    int strokes = Integer.parseInt(div.text());
    
                    Element netTD = netTDs.get(i * 2);
                    int points = Integer.parseInt(netTD.text());

                    int additionalStrokes = calculateAdditionalStrokes(par, strokes, points);    
                    round.setScore((i +1), new Score()
                                        .setHcp(hcp)
                                        .setPar(par)
                                        .setStrokes(strokes)
                                        .setAdditionalStrokes(additionalStrokes)
                                        .setPoints(points)
                    );
                }
            }

		} catch (IOException e) {
            LOG.error(e.getLocalizedMessage() + " " + url);
            return null;
		}
        return round;
    }

    private String getPlayerId(String name){
        String id = "666";
        switch(name) {
            case "Francesco Sanetti":
              id = "7bb48a57b4d04d5c90e0a66cf167f4b4";
              break;
            case "Stefano Mauti":
              id = "976a3e3c049940ceb0c230a9e0016779";
              break;
            case "fabio armellini":
              id = "fcd40a9ef30a429896329a2ea0dee88f";
              break;
            case "Andrea Leoncini":
              id = "31488c1cd84b482ab6115ebb7ab9fe32";
              break;
            case "Lorenzo Scomparin":
              id = "2ddeda86cdfd46b9a94c794db97ae8b6";
              break;
            case "Andy Richardson":
              id = "d34a45a7b27e4d449ecadf691a02935c";
              break;
            case "Andrea Brancaccio":
              id = "brankac";
              break;
            case "Primo Marchini":
              id = "bca8614c90a641199c57510c63eee799";
              break;
            case "Federico Morena":
              id = "4a320dd3b5fc4699adba10085f6f6af8";
              break;
            case "Valerio Garofoli":
              id = "8637567bf51044729308abf4fdfaafb2";
              break;
        }
        return id;
    }
    
    private String getCourseId(String name){
        return "999";
    }

    private boolean exists(Document doc){
        Elements divNoRounds = doc.getElementsByClass("no-rounds");
        return (divNoRounds.size() > 0) ? false : true;
    }

    private int calculateAdditionalStrokes(int par, int strokes, int points){
        if(points > 2){
            return (strokes - par) + (points - 2) ;
        }
        if(points == 2){
            return strokes - par;
        }
        if(points == 1){
            return (strokes - par) - 1;
        }
        return 0;
    }

}
