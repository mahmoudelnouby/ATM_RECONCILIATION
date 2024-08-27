package com.example.ATM_RECONCILIATION.security.repos;


import jakarta.transaction.Transactional;
import com.example.ATM_RECONCILIATION.security.models.MTSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<MTSUser, Long>{

	@Query(value = "select USER_ID, USER_NAME, EMAIL_ADDRESS, DISPLAY_NAME, LAST_MODIFIED_BY "
				+  "from bpm_adminstration.sc_users "
				+  "where lower(user_name) = lower(:username)", nativeQuery = true)
	MTSUser getUserDetailsByUsername(String username);

    @Transactional
	@Query(value = "select DISPLAY_NAME "
				+  "from bpm_adminstration.sc_users where "
				+ "lower(user_name) = lower(:username)", nativeQuery = true)
	String getUserDisplayNameByUsername(String username);


}
