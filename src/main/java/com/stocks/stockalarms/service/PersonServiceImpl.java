package com.stocks.stockalarms.service;


import java.util.HashSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.repository.PersonRepository;
import com.stocks.stockalarms.repository.RoleRepository;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-16.
 */
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void save(Person person) {
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        person.setRoles(new HashSet<>(roleRepository.findAll()));

        new HashSet<>(roleRepository.findAll());

        personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person findByUSername(String username) {
        return personRepository.findByUsername(username);
    }
}
