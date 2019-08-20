package com.stocks.stockalarms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.Person;

/**
 * By vlad.oltean on 2019-08-16.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByUsername(String username);

}
