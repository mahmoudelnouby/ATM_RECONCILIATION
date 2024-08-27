package com.example.ATM_RECONCILIATION.security.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.ATM_RECONCILIATION.security.enums.SecurityResponseMessage;
import com.example.ATM_RECONCILIATION.security.models.MTSUser;
import com.example.ATM_RECONCILIATION.security.repos.UserPermissionsRepository;
import com.example.ATM_RECONCILIATION.security.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	@Value("${module.id}")
	private Long module_id;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserPermissionsRepository userPermRepo;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
		MTSUser user = userRepo.getUserDetailsByUsername(username);
		
		if(user != null) {
			user.setPERMISSIONS(userPermRepo.getUserPermissionsByUserName(username, module_id));
		}
		else
			throw new UsernameNotFoundException(SecurityResponseMessage.User_Not_Found.getMessageByUsername(username));
		
		return user;
	}

}
