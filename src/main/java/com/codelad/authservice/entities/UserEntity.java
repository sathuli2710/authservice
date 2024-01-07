package com.codelad.authservice.entities;

import com.codelad.authservice.ApplicationConstants;
import com.codelad.authservice.utils.VerificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="users_sequence", initialValue = 1, allocationSize = 1)
@Entity
@Table(name="users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="users_sequence")
    private Integer uid;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "verification_status", columnDefinition = "SMALLINT")
    private VerificationStatus verificationStatus;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    public void setDefaultValues(){
//        uuid = UUID.randomUUID();
        if(Objects.isNull(verificationStatus)){
            verificationStatus = VerificationStatus.not_verified;
        }
        createdAt = Timestamp.from(Instant.now());
    }

    @PostUpdate
    public void setUpdateTime() {
        updatedAt = Timestamp.from(Instant.now());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


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
}
