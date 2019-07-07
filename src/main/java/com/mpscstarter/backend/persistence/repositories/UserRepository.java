package com.mpscstarter.backend.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mpscstarter.backend.persistence.domain.backend.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * returns a User Entity given a username or null if not found
	 * @param username
	 * @return a User Entity given a username or null if not found
	 */
	public User findByUsername(String username);
}
