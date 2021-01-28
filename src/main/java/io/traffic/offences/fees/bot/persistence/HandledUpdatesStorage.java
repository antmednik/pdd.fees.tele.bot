package io.traffic.offences.fees.bot.persistence;

import io.traffic.offences.fees.bot.persistence.entity.LastHandledUpdate;
import io.traffic.offences.fees.bot.persistence.repository.LastHandledUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HandledUpdatesStorage {

    private final LastHandledUpdateRepository lastHandledUpdateRepository;

    @Transactional(readOnly = true)
    public Optional<LastHandledUpdate> lastHandledUpdate() {
        List<LastHandledUpdate> candidates = lastHandledUpdateRepository.findAll();
        if (candidates.size() == 0) {
            return Optional.empty();
        }
        if (candidates.size() == 1) {
            return Optional.of(candidates.get(0));
        }
        throw new DataIntegrityViolationException("Multiple entities found.");
    }

    @Transactional
    public void setLastHandledUpdate(Integer updateId) {
        LastHandledUpdate lhu = lastHandledUpdate().orElse(new LastHandledUpdate());
        lhu.setUpdateId(updateId);
        lastHandledUpdateRepository.save(lhu);
    }
}
