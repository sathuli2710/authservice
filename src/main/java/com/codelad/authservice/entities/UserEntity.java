package com.codelad.authservice.entities;


import com.codelad.authservice.ApplicationConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, unique = true)
    private UUID uid;
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
    @Column(name = "is_verified")
    private boolean isVerified;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    public void setDefaultValues(){
        if(Objects.isNull(uid)){
            uid = UUID.fromString(getUsername()+getUsername());
        }
        if(Objects.isNull(isVerified)){
            isVerified = ApplicationConstants.IS_VERIFIED_DEFAULT;
        }
        createdAt = Timestamp.from(Instant.now());
    }

    @PostUpdate
    public void setUpdateTime() {
        updatedAt = Timestamp.from(Instant.now());
    }

}
