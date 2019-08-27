package com.stocks.stockalarms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.Role;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
