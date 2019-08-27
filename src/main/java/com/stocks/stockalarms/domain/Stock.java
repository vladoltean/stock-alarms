package com.stocks.stockalarms.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-19.
 */
@Entity
@Data
public class Stock {
// TODO: keep it or search for stocks? consider using symbol as PK

    @Id
    @Column(unique = true)
    private String symbol;

    private String companyName;

    private Double price;

    private Double changePercent;

    @OneToMany(mappedBy = "stock")
    private Set<MonitoredStock> monitoredStocks;
}
