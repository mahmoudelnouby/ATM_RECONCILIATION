package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.UserModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserModulesRepository extends JpaRepository<UserModule, String> {
	
	@Query(value="select USER_ID,USER_NAME,MODULE_ID,MODULE_NAME,MODULE_NAME_AR,"
			+ "PATH from bpm_adminstration.SC_USER_MODULE where "
			+ "lower(USER_NAME)=lower(:username)", nativeQuery = true)
	public List<UserModule> getUserModulesByUsername(String username);

}
