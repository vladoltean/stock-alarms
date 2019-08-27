package com.stocks.stockalarms.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.Person;

/**
 * By vlad.oltean on 2019-08-16.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByUsername(String username);

    @Query(value = "select p from Person p join p.monitoredStocks m where m.id in (?1)")
    Set<Person> findAllByMonitoredStocks(Collection<Long> monitoredStockIds);

}
