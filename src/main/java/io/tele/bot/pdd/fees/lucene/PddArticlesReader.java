package io.tele.bot.pdd.fees.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PddArticlesReader implements PddArticlesProvider {

    public List<PddArticle> articles() {
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
