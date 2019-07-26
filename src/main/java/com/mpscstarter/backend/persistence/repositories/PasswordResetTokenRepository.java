package com.mpscstarter.backend.persistence.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mpscstarter.backend.persistence.domain.backend.PasswordResetToken;

/**
 * 
 * Created by @author Pradipkumar Hinge
 *
 */

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String Token);
	
	@Query("select prt from PasswordResetToken prt inner join prt.user u where prt.user.id = ?1")
	Set<PasswordResetToken> findAllByUserId(long userId);

	
	
}
