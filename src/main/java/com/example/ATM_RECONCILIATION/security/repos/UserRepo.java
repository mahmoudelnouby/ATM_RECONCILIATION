package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.User;
import com.example.ATM_RECONCILIATION.security.models.request.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
//    @Query("SELECT new tgh.SystemAdministration.payload.request.UserDto(u.userId, u.username, u.emailAddress, u.displayName,0) FROM User u")
//    List<UserDto> findAllUser();
    Optional<User> findByUsername(String username);
    @Transactional
    @Query(value = "select DISPLAY_NAME "
            +  "from sc_users where "
            + "lower(user_name) = lower(:username)", nativeQuery = true)
    String getUserDisplayNameByUsername(String username);
    @Query(value = "Select * from SC_USERS where USER_NAME=?1 AND PASSWORD=?2", nativeQuery = true)
    Optional<User> authenticate(String username,String password);

    Optional<User> findByEmailAddress(String emailAddress);
    @Query(value = "select USER_NAME,EMAIL_ADDRESS,USER_ID,DISPLAY_NAME,LAST_MODIFIED_BY,ORG_UNIT_ID,USER_PASSWORD,ENABLED,ACTIVE,CHANGE_PASSWORD_FLAG,PASSWORD_ENC,USER_TYPE,PASSWORD,CREATION_DATE,LAST_LOGIN,USER_TOKEN,WORKER_ID from SC_USERS where EMAIL_ADDRESS=?1 and USER_ID != ?2",nativeQuery = true)
    Optional<User> findByEmailAddressAndId(String emailAddress,Integer userId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE SC_USERS SET PASSWORD = ?2 , PASSWORD_ENC = ?3 WHERE USER_NAME =?1",nativeQuery = true)
    void saveNewPassword(String userName,String password,String encodedPw);
    @Modifying
    @Transactional
    @Query(value = "update SC_USERS set EMAIL_ADDRESS=?2,DISPLAY_NAME=?3 where USER_ID=?1",nativeQuery = true)
    void updateUserdata(Integer userId,String emailAddress,String displayName);
}
