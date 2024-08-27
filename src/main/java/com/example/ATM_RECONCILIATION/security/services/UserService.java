package com.example.ATM_RECONCILIATION.security.services;

import com.example.ATM_RECONCILIATION.Repos.AuditRepo;
import com.example.ATM_RECONCILIATION.Repos.UserRoleRepo;
import com.example.ATM_RECONCILIATION.payload.request.UserSearchRoleDto;
import com.example.ATM_RECONCILIATION.security.configuration.Decryption;
import com.example.ATM_RECONCILIATION.security.configuration.JwtAuthenticationFilter;
import com.example.ATM_RECONCILIATION.security.models.Audit;
import com.example.ATM_RECONCILIATION.security.models.User;
import com.example.ATM_RECONCILIATION.security.models.request.UserDto;
import com.example.ATM_RECONCILIATION.security.models.request.UserSearchDto;
import com.example.ATM_RECONCILIATION.security.models.request.changePasswordRequest;
import com.example.ATM_RECONCILIATION.security.repos.UserRepo;
import com.example.ATM_RECONCILIATION.services.GlobalService;
import com.example.ATM_RECONCILIATION.specification.UserSpec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    UserRepo userRepo;
    AuditRepo auditRepo;
    UserRoleRepo userRoleRepo;
    JwtAuthenticationFilter jwtAuthenticationFilter;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, AuditRepo auditRepo, UserRoleRepo userRoleRepo, JwtAuthenticationFilter jwtAuthenticationFilter, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.auditRepo = auditRepo;
        this.userRoleRepo = userRoleRepo;
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
        this.passwordEncoder=passwordEncoder;
    }

    //get all users
//    public List<UserDto> getAllUsers() {
//        List<UserDto> userRecords = userRepo.findAllUser();
//        if(userRecords.isEmpty()) {
//            throw new NotFoundException("No users found");
//        }
//        //check that all user is existed on web logic or not if it is active
//        return userRecords;
//    }
    //convert user on model to add another attributes(active)
    public List<UserDto> getUsersDto(List<User> users) {
        if(users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users){
            UserDto userDto = new UserDto(user.getUserId(),user.getUsername(), user.getEmailAddress(),user.getDisplayName(),1);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
    public List<UserSearchRoleDto> getUsersSearchRoleDto(List<User> users) {

        if(users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        List<UserSearchRoleDto> userDtoList = new ArrayList<>();
        for (User user : users){
            UserSearchRoleDto userDto = new UserSearchRoleDto(user.getUserId(), user.getUsername(),user.getDisplayName());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
    public Object search(UserSearchDto userDto){
        Specification<User> spec = UserSpec.withAllConditions(userDto);
        List<User> userRecords = userRepo.findAll(spec);
        if(userDto.getSearchRole().equals("user"))
            return getUsersDto(userRecords);
        else if(userDto.getSearchRole().equals("role"))
            return getUsersSearchRoleDto(userRecords);
        else
            throw new RuntimeException("Error in search role");
    }
//    public User createUser(UserCreationRequest user) {
//        String password = Objects.requireNonNull(GlobalService.decrypt(user.getPassword())).trim();
//        String confirmPassword = Objects.requireNonNull(GlobalService.decrypt(user.getPassword())).trim();
//        System.out.println("password : "+password);
//        if(getUserByUsername(user.getUsername()) != null) {
//            throw new RuntimeException("Username already exists");
//        }
//        if(findByEmailAddress(user.getEmailAddress()) != null) {
//            throw new RuntimeException("Email already exists");
//        }
//        if(!Objects.equals(password, confirmPassword))
//            throw new RuntimeException("Password don't match");
//        assert password != null;
//        Decryption decryption=new Decryption();
//        String plainPw=(decryption.decrypt(user.getPassword()).trim());
//        String encodedPw=passwordEncoder.encode(plainPw);
//        auditRepo.save(new Audit("User Created" + user.getUsername(), jwtAuthenticationFilter.userName()));
//        return userRepo.save(new User(user.getUsername(),user.getEmailAddress(),user.getDisplayName(), jwtAuthenticationFilter.userName(),user.getPassword(),encodedPw,1,1));
//    }
    public User getUserByUsername(String username){
        return userRepo.findByUsername(username).orElse(null);
    }
    public User findByEmailAddress(String emailAddress){
        return userRepo.findByEmailAddress(emailAddress).orElse(null);
    }
//    public boolean addedToServer(UserServerRequest user) {
//        String password = Objects.requireNonNull(GlobalService.decrypt(user.getPassword())).trim();
//        String confirmPassword = Objects.requireNonNull(GlobalService.decrypt(user.getPassword())).trim();
//        User addedUser = userRepo.findByUsername(user.getUsername()).orElse(null);
//        if(addedUser == null) {
//            throw new RuntimeException("User doesn't exist");
//        }
//        if(!Objects.equals(password,confirmPassword))
//            throw new RuntimeException("Password don't match");
//        if(password.length() < 8)
//            throw new RuntimeException("Password must be at least 8 characters");
//        if(password.chars().noneMatch(Character::isDigit))
//            throw new RuntimeException("Password must contain at least one digit");
//        String name = "authService.currentUser()";
//        auditRepo.save(new Audit("Added to server user " + addedUser.getUsername(),name));
//        return true;
//    }
    public boolean deleteUser(String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        if(user == null) {
            throw new RuntimeException("User doesn't exist");
        }
        userRoleRepo.deleteRoleOfUser(user.getUserId());
        userRepo.delete(user);
        auditRepo.save(new Audit("User Deleted" + user.getUsername(), jwtAuthenticationFilter.userName()));
        return true;
    }

    public boolean changePass(changePasswordRequest request) {
        User user=userRepo.findByUsername(jwtAuthenticationFilter.userName()).orElse(null);
        user = userRepo.authenticate(jwtAuthenticationFilter.userName(), request.getOldPassword())
                .orElseThrow(() -> new IllegalArgumentException("Invalid UserName or password."));
        String confirmPassword = Objects.requireNonNull(GlobalService.decrypt(request.getNewPassword())).trim();
        if(user == null)
            throw new RuntimeException("User doesn't exist");
        if(confirmPassword.chars().noneMatch(Character::isDigit))
            throw new RuntimeException("Password must contain at least one digit");
        if(user == null) {
            throw new RuntimeException("User doesn't exist");
        }
        Decryption decryption=new Decryption();
        String plainPw=(decryption.decrypt(request.getNewPassword()).trim());
        String encodedPw=passwordEncoder.encode(plainPw);
        userRepo.saveNewPassword(jwtAuthenticationFilter.userName(), request.getNewPassword(),encodedPw);

        return true;
    }




//    public boolean removeUserFromServer(String username){
//        User user = userRepo.findByUsername(username).orElse(null);
//        if(user == null)
//            throw new RuntimeException("User doesn't exist");
//        String name = "authService.currentUser()";
//        auditRepo.save(new Audit("Disable user " + username,name));
//        return true;
//    }
    /**
     *
     */
//    public boolean resetPassword(UserServerRequest userCreationRequest){
//        User user = userRepo.findByUsername(userCreationRequest.getUsername()).orElse(null);
//        String password = Objects.requireNonNull(GlobalService.decrypt(userCreationRequest.getPassword())).trim();
//        String confirmPassword = Objects.requireNonNull(GlobalService.decrypt(userCreationRequest.getPassword())).trim();
//        if(user == null)
//            throw new RuntimeException("User doesn't exist");
//        if(!Objects.equals(password,confirmPassword))
//            throw new RuntimeException("Password don't match");
//        if(password.chars().noneMatch(Character::isDigit))
//            throw new RuntimeException("Password must contain at least one digit");
//        String name = jwtAuthenticationFilter.userName();
//        Decryption decryption=new Decryption();
//        String plainPw=(decryption.decrypt(userCreationRequest.getPassword()).trim());
//        String encodedPw=passwordEncoder.encode(plainPw);
//        System.out.println(encodedPw);
//        System.out.println(plainPw);
//        auditRepo.save(new Audit("Reset Password for user " + user.getUsername(),name));
//        userRepo.saveNewPassword(user.getUsername(),userCreationRequest.getPassword(),encodedPw);
//        return true;
//    }
}
