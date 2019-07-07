package com.mpscstarter.backend.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mpscstarter.backend.persistence.domain.backend.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

}
