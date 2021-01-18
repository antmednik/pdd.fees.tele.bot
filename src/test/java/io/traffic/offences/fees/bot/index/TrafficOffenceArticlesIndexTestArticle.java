package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TrafficOffenceArticlesIndexTestArticle {

    static class SearchTopTenArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context)  {
            return Stream.of(
                    Arguments.of("12.1", List.of("Статья 12.1.")),
                    Arguments.of("13.3", List.of()),
                    Arguments.of("нем государственных регистрационных знаков", List.of("Статья 12.2.")),
                    Arguments.of("неисправностей", List.of()),
                    Arguments.of("14 октября 2014", List.of("Статья 12.1.")),
                    Arguments.of("2017", List.of()),

                    Arguments.of("водителя", List.of("Статья 12.1.")),
                    Arguments.of("водитель", List.of()),
                    Arguments.of("вадитель", List.of()),
                    Arguments.of("ватитель", List.of()),
                    Arguments.of("осмотра", List.of("Статья 12.1.")),
                    Arguments.of("осмотр", List.of("Статья 12.1.")),
                    Arguments.of("смотр", List.of()),
                    Arguments.of("асмотр", List.of()),
                    Arguments.of("осмотрела111", List.of())
            );
        }
    }

    static class FuzzySearchTopTenArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context)  {
            return Stream.of(
                    Arguments.of("12.1", List.of("Статья 12.1.", "Статья 12.2.")),
                    Arguments.of("13.3", List.of("Статья 12.1.", "Статья 12.2.")),
                    Arguments.of("нем государственных регистрационных знаков", List.of()),
                    Arguments.of("неисправностей", List.of()),
                    Arguments.of("14 октября 2014", List.of()),
                    Arguments.of("2017", List.of("Статья 12.1.", "Статья 12.2.")),

                    Arguments.of("водителя", List.of("Статья 12.1.")),
                    Arguments.of("водитель", List.of("Статья 12.1.")),
                    Arguments.of("вадитель", List.of("Статья 12.1.")),
                    Arguments.of("ватитель", List.of()),
                    Arguments.of("осмотра", List.of("Статья 12.1.")),
                    Arguments.of("осмотр", List.of("Статья 12.1.")),
                    Arguments.of("смотр", List.of("Статья 12.1.")),
                    Arguments.of("асмотр", List.of("Статья 12.1.")),
                    Arguments.of("осмотрела111", List.of())
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(SearchTopTenArgumentsProvider.class)
    public void givenIndexWhenSearchTopTenCalledThenGotExpectedResults(String query, List<String> foundTrafficRulesNumbers) {
        // given
        TrafficOffenceArticlesIndex sut = new TrafficOffenceArticlesIndex();
        sut.build(testArticles());

        // when
        List<TrafficOffenceArticle> results = sut.searchTopTen(query);

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(foundTrafficRulesNumbers.size());
        for (int idx = 0; idx < foundTrafficRulesNumbers.size(); idx++) {
            String actualTrafficRuleNumber = results.get(idx).number();
            String expectedTrafficRuleNumber = foundTrafficRulesNumbers.get(idx);
            assertThat(actualTrafficRuleNumber).isEqualTo(expectedTrafficRuleNumber);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(value = FuzzySearchTopTenArgumentsProvider.class)
    public void givenIndexWhenFuzzySearchTopTenCalledThenGotExpectedResults(String query, List<String> foundTrafficRulesNumbers) {
        // given
        TrafficOffenceArticlesIndex sut = new TrafficOffenceArticlesIndex();
        sut.build(testArticles());

        // when
        List<TrafficOffenceArticle> results = sut.fuzzySearchTopTen(query);

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(foundTrafficRulesNumbers.size());
        for (int idx = 0; idx < foundTrafficRulesNumbers.size(); idx++) {
            String actualTrafficRuleNumber = results.get(idx).number();
            String expectedTrafficRuleNumber = foundTrafficRulesNumbers.get(idx);
            assertThat(actualTrafficRuleNumber).isEqualTo(expectedTrafficRuleNumber);
        }
    }

    private List<TrafficOffenceArticle> testArticles() {
        return List.of(new TrafficOffenceArticle("Статья 12.1.",
                        "Управление транспортным средством, не зарегистрированным в установленном порядке, транспортным средством, не прошедшим государственного технического осмотра или технического осмотра",
                        """
                                1. Управление транспортным средством, не зарегистрированным в установленном порядке, -
                                влечет наложение административного штрафа в размере от пятисот до восьмисот рублей.
                                1.1. Повторное совершение административного правонарушения, предусмотренного частью
                                1 настоящей статьи, -
                                влечет наложение административного штрафа в размере пяти тысяч рублей или лишение
                                права управления транспортными средствами на срок от одного до трех месяцев.
                                2. Управление легковым такси, автобусом или грузовым автомобилем, предназначенным и
                                оборудованным для перевозок людей, с числом мест для сидения более чем восемь (кроме места
                                для водителя), специализированным транспортным средством, предназначенным и оборудованным
                                для перевозок опасных грузов, которые не прошли государственный технический осмотр или
                                технический осмотр, -
                                влечет наложение административного штрафа в размере от пятисот до восьмисот рублей.
                                Примечания: Утратили силу по истечении тридцати дней после дня официального
                                опубликования Федерального закона от 14 октября 2014 г. N 307-ФЗ.
                                Примечание. Под транспортным средством в настоящей статье следует понимать
                                автомототранспортное средство с рабочим объемом двигателя внутреннего сгорания более 50
                                кубических сантиметров или максимальной мощностью электродвигателя более 4 киловатт и
                                максимальной конструктивной скоростью более 50 километров в час, а также прицепы к нему,
                                подлежащие государственной регистрации, а в других статьях настоящей главы также трактора,
                                самоходные дорожно-строительные и иные самоходные машины, транспортные средства, на
                                управление которыми в соответствии с законодательством Российской Федерации о безопасности
                                дорожного движения предоставляется специальное право."""),
                new TrafficOffenceArticle("Статья 12.2.",
                        "Управление транспортным средством с нарушением правил установки на нем государственных регистрационных знаков",
                        """
                                1. Управление транспортным средством с нечитаемыми, нестандартными или
                                установленными с нарушением требований государственного стандарта государственными
                                регистрационными знаками, за исключением случаев, предусмотренных частью 2 настоящей
                                статьи, -
                                влечет предупреждение или наложение административного штрафа в размере пятисот
                                рублей.
                                2. Управление транспортным средством без государственных регистрационных знаков, а
                                равно управление транспортным средством без установленных на предусмотренных для этого
                                местах государственных регистрационных знаков либо управление транспортным средством с
                                государственными регистрационными знаками, видоизмененными или оборудованными с
                                применением устройств или материалов, препятствующих идентификации государственных
                                регистрационных знаков либо позволяющих их видоизменить или скрыть, -
                                влечет наложение административного штрафа в размере пяти тысяч рублей или лишение
                                права управления транспортными средствами на срок от одного до трех месяцев.
                                3. Установка на транспортном средстве заведомо подложных государственных
                                регистрационных знаков - влечет наложение административного штрафа на граждан в размере двух тысяч пятисот
                                рублей; на должностных лиц, ответственных за эксплуатацию транспортных средств, - от
                                пятнадцати тысяч до двадцати тысяч рублей; на юридических лиц - от четырехсот тысяч до пятисот
                                тысяч рублей.
                                4. Управление транспортным средством с заведомо подложными государственными
                                регистрационными знаками -
                                влечет лишение права управления транспортными средствами на срок от шести месяцев до
                                одного года.
                                Примечание. Государственный регистрационный знак признается нестандартным, если он
                                не соответствует требованиям, установленным в соответствии с законодательством о техническом
                                регулировании, и нечитаемым, если с расстояния 20 метров не обеспечивается прочтение в темное
                                время суток хотя бы одной из букв или цифр заднего государственного регистрационного знака, а в
                                светлое время суток хотя бы одной из букв или цифр переднего или заднего государственного
                                регистрационного знака."""));
    }
}
