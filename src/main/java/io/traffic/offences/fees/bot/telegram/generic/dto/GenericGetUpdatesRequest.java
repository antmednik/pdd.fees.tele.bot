package io.traffic.offences.fees.bot.telegram.generic.dto;

import lombok.Getter;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
@Getter
public class GenericGetUpdatesRequest {

    private final int offset;
    private final int limit;
    private final Optional<Integer> timeoutInSeconds;

    public GenericGetUpdatesRequest(int offset, int limit, @Nullable Integer timeoutInSeconds) {
        this.offset = offset;
        this.limit = limit;
        this.timeoutInSeconds = Optional.ofNullable(timeoutInSeconds);
    }

    public GenericGetUpdatesRequest(int offset, int limit) {
        this(offset, limit, null);
    }

    public GenericGetUpdatesRequest() {
        this(0, 100, null);
    }
}
