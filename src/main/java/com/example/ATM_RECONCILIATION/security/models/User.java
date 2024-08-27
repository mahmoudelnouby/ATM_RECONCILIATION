package com.example.ATM_RECONCILIATION.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "SC_USERS",schema = "bpm_adminstration")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User implements Serializable, UserDetails {

        public User(String username, String emailAddress, String displayName, String lastModifiedBy, String password, String passwordEnc, Integer enabled, Integer active) {
                this.username = username;
                this.emailAddress = emailAddress;
                this.displayName = displayName;
                this.lastModifiedBy = lastModifiedBy;
                this.password=password;
                this.passwordEnc=passwordEnc;
                this.enabled=enabled;
                this.active=active;
        }
        public User(Integer userId){
                this.userId = userId;
        }

        @Column(name = "USER_ID")
        @Id
        @SequenceGenerator(name = "userSeq", sequenceName = "bpm_adminstration.SC_SEQ", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userSeq")
        private Integer userId;
        @Column(name = "USER_NAME")
        private String username;
        @Column(name = "EMAIL_ADDRESS")
        private String emailAddress;
        @Column(name = "DISPLAY_NAME")
        private String displayName;
        @Column(name = "PASSWORD")
        private String password;
        @Column(name = "PASSWORD_ENC")
        private String passwordEnc;
        @Column(name = "ENABLED")
        private Integer enabled;
        @Column(name = "ACTIVE")
        private Integer active;
//        @Column(name = "LOGIN_STATUS")
//        private Integer loginStatus;
//        @Column(name = "SESSION_ID")
//        private String sessionId;
        @Transient
        private String EMP_ORG;
//        @Enumerated(EnumType.STRING)
//        private Role role;
        //        @Column(name = "PASSWORD")
//        private String password;
//        @Column(name = "USER_IMG")
//        private String userImg;
//        @Column(name = "OWNERSHIP_ID")
//        private Integer ownershipId;
        @Column(name = "LAST_MODIFIED_BY")
        @JsonIgnore
        private String lastModifiedBy;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
        }

        @Override
        public String getPassword() {
                return null;
        }

        @Override
        public boolean isAccountNonExpired() {
                return false;
        }

        @Override
        public boolean isAccountNonLocked() {
                return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return false;
        }

        @Override
        public boolean isEnabled() {
                return false;
        }
//        @Column(name = "BU_CODE")
//        private String buCode;
//        @Column(name = "TGH_COUNT")
//        private Integer tghCount;

}
