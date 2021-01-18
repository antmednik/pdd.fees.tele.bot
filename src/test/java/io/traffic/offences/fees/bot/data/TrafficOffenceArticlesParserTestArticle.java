package io.traffic.offences.fees.bot.data;

import io.traffic.offences.fees.bot.data.text.TrafficOffenceArticlesParser;
import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TrafficOffenceArticlesParserTestArticle {

    @Test
    void givenCorrectDataWhenParsedThenPddArticlesBuildedCorrectly() {
        // given
        TrafficOffenceArticlesParser sut = new TrafficOffenceArticlesParser();
        List<String> pddArticlesInput = List.of(
                "01", "title 01", "text 01", "text 11", "text 21", "---",
                "02", "title 02", "text 02", "---",
                "03", "title 03", "text 03", "text 13", "text 23", "text 33", "---");

        // when
        for (String line : pddArticlesInput) {
            sut.add(line);
        }
        List<TrafficOffenceArticle> articles = sut.articles();

        // then
        assertThat(articles).isNotNull().hasSize(3);

        TrafficOffenceArticle article1 = articles.get(0);
        assertThat(article1).isNotNull();
        assertThat(article1.number()).isEqualTo("01");
        assertThat(article1.title()).isEqualTo("title 01");
        assertThat(article1.text()).isEqualTo(
                Stream.of("text 01", "text 11", "text 21").collect(Collectors.joining(System.lineSeparator())));

        TrafficOffenceArticle article2 = articles.get(1);
        assertThat(article2).isNotNull();
        assertThat(article2.number()).isEqualTo("02");
        assertThat(article2.title()).isEqualTo("title 02");
        assertThat(article2.text()).isEqualTo("text 02");

        TrafficOffenceArticle article3 = articles.get(2);
        assertThat(article3).isNotNull();
        assertThat(article3.number()).isEqualTo("03");
        assertThat(article3.title()).isEqualTo("title 03");
        assertThat(article3.text()).isEqualTo(
                Stream.of("text 03", "text 13", "text 23", "text 33").collect(Collectors.joining(System.lineSeparator())));
    }

    @Test
    void givenIncorrectDataWhenParsedThenExceptionThrown() {
        // given
        TrafficOffenceArticlesParser sut = new TrafficOffenceArticlesParser();
        List<String> pddArticlesInput = List.of(
                "01", "title 01", "text 01", "text 11", "text 21", "---",
                "02", "title 02");

        // when
        for (String line : pddArticlesInput) {
            sut.add(line);
        }

        // then
        assertThatThrownBy(sut::articles).isInstanceOf(RuntimeException.class);
    }
}
