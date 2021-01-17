package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffence;

import java.util.List;

public class PddArticlesReader implements PddArticlesProvider {

    public List<TrafficOffence> articles() {
       /* todo List<PddArticle> pddArticles = new ArrayList<>();
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStringBuilder.toString();*/
        return null;
    }
}
