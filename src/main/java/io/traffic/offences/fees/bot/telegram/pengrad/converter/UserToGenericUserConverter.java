package io.traffic.offences.fees.bot.telegram.pengrad.converter;

import com.pengrad.telegrambot.model.User;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToGenericUserConverter implements Converter<User, GenericUser> {

    @Override
    public GenericUser convert(User source) {
        return new GenericUser(
                source.id(), source.isBot(), source.firstName(), source.lastName(), source.username(),
                source.languageCode(), source.canJoinGroups(),  source.canReadAllGroupMessages(),
                source.supportsInlineQueries());
    }
}
