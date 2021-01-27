package io.traffic.offences.fees.bot.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "handled_updates")
@Getter
@Setter
public class HandledUpdate {

    @Id
    @Column(name = "id")
    private Integer id;

}
