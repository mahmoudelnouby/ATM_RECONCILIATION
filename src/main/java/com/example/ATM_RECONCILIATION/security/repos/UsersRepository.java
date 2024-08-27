package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository  extends JpaRepository<User, Integer> {

	@Query(value="SELECT u.USER_NAME from bpm_adminstration.SC_USERS u "
			+ "	where u.BU_CODE = :office",nativeQuery = true)
	List<String> userName(String office);
	
	@Query(value="SELECT USER_NAME,"
			+ "EMAIL_ADDRESS,"
			+ "USER_ID,"
			+ "DISPLAY_NAME,"
//			+ "USER_IMG,"
//			+ "OWNERSHIP_ID,"
			+ "LAST_MODIFIED_BY,"
//			+ "BU_CODE,"
//			+ "TGH_COUNT,"
			+ "PASSWORD,"
			+ "PASSWORD_ENC,"
			+ "ENABLED,"
			+ "ACTIVE"
//			"SESSION_ID," +
//			"LOGIN_STATUS "
			+ " from bpm_adminstration.SC_USERS where USER_NAME=:username",nativeQuery = true)
	Optional<User> findByUserName(String username);

	@Query(value = "SELECT p.OFFICE_NAME\n" +
			"   from bpm_adminstration.SC_USERS sc ,TGH_POST_OFFICE p\n" +
			"   where USER_NAME=:username and sc.BU_CODE = p.OFFICE_CODE",nativeQuery = true)
	String getOfficeName(String username);

	@Transactional
	@Modifying
	@Query(value = "update bpm_adminstration.sc_users set login_status= :loginStatus, session_id= :sessionId where user_id= :userId",nativeQuery = true)
	void updateSession(Integer loginStatus, String sessionId, Integer userId);
}
