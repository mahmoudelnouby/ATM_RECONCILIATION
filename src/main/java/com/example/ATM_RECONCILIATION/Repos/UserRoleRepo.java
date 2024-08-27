package com.example.ATM_RECONCILIATION.Repos;

import com.example.ATM_RECONCILIATION.Entities.Ids.UserRoleId;
import com.example.ATM_RECONCILIATION.Entities.UserRole;
import com.example.ATM_RECONCILIATION.payload.response.UsersRoleResponse;
import com.example.ATM_RECONCILIATION.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface UserRoleRepo extends JpaRepository<UserRole, UserRoleId> {

//   @Query("SELECT ur.userRoleId.roleId FROM UserRole ur WHERE ur.userRoleId.userId.userId = ?1")
//   List<Role> findByUserId(Integer userId);
//   @Modifying
//   @Query("delete from UserRole ur where ur.userRoleId.userId.userId = ?1 and ur.userRoleId.roleId.roleId = ?2")
//   @Transactional
//   void deleteUserRole(Integer userId, Integer roleId);
//   @Query("SELECT case when count(ur) > 0 then true else false end FROM UserRole ur WHERE ur.userRoleId.userId.userId = ?1 " +
//           "and ur.userRoleId.roleId.roleId = ?2")
//   boolean existsByUserRoleId(Integer userId, Integer roleId);
//   @Query("Select new tgh.SystemAdministration.payload.response.UsersRoleResponse(s.userRoleId.userId.userId ,s.userRoleId.userId.username) from UserRole s where s.userRoleId.roleId.roleId = :roleId")
//   List<UsersRoleResponse> getUsersOfRole(Integer roleId);
//   @Modifying
//   @Query("delete from UserRole ur where ur.userRoleId.roleId.roleId = :roleId and ur.userRoleId.userId.userId =:userId")
//   @Transactional
//   void deleteUsersOfRole( Integer roleId,Integer userId);

   @Modifying
   @Query("delete from UserRole ur where ur.userRoleId.userId.userId = :userId")
   @Transactional
   void deleteRoleOfUser(Integer userId);



}
