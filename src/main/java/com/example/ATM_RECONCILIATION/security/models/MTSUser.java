package com.example.ATM_RECONCILIATION.security.models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="SC_USERS", schema = "bpm_adminstration")
public class MTSUser implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long USER_ID;
	@Column(name="USER_NAME")
	private String USERNAME;
	private String EMAIL_ADDRESS;
	private String DISPLAY_NAME;
	private String LAST_MODIFIED_BY;
	@Transient
	private String PASSWORD_ENC;
	@Transient
	private String EMP_ORG;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "SC_USERROLE", schema = "bpm_adminstration",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	public Set<Role> ROLES;

	@Transient
	public Set<UserPermission> PERMISSIONS;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return USERNAME;
	}
	
	public String getDisplayName() {
		return DISPLAY_NAME;
	}
	
	@Override
	public String getPassword() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof User) {
			return ((User)principal).getPassword();
		}
		else {
			return principal.toString();
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();

		PERMISSIONS.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getPERMISSION_NAME()));
		});

		return authorities;
	}
	
}
