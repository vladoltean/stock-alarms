package com.stocks.stockalarms.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-19.
 */
@Entity
@Data
public class MonitoredStock {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Stock stock;

    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "monitoredStock")
    private Set<Alarm> alarms;

}
