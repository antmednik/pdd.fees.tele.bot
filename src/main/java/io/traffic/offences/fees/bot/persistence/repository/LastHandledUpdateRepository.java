package io.traffic.offences.fees.bot.persistence.repository;

import io.traffic.offences.fees.bot.persistence.entity.LastHandledUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastHandledUpdateRepository extends JpaRepository<LastHandledUpdate, Integer>  {
}
