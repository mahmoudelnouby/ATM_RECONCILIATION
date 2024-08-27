package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserPermissionsRepository extends JpaRepository<UserPermission, Long>{
	
	@Query("select u from UserPermission u "
		+  "where u.USER_ID = :userID and u.MODULE_ID = :moduleID and u.PERMISSION_NAME = :permissionName")
	Optional<UserPermission> findUserModulePermission(Long userID, Long moduleID, String permissionName);

	@Query(value= "SELECT * FROM bpm_adminstration.SC_USER_PERMISSION "
				+ "WHERE lower(USER_NAME) = lower(:username)"
				+ "and MODULE_ID = :moduleID", nativeQuery = true)
	public Set<UserPermission> getUserPermissionsByUserName(String username, Long moduleID);
	
	@Query(value = "SELECT * FROM bpm_adminstration.SC_USER_PERMISSION "
				+  "WHERE lower(USER_NAME) = lower(:username) "
				+  "and MODULE_ID = :moduleID "
				+ " and permission_name = :permissionName", nativeQuery = true)
	public List<UserPermission> checkUserPermission(String username, Long moduleID, String permissionName);


}
