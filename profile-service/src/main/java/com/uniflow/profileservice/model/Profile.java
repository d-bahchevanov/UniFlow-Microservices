package com.uniflow.profileservice.model;

import com.uniflow.profileservice.enums.Role;
import com.uniflow.profileservice.enums.AcademicTitle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long facultyId;
    private Long specializationId;
    private int yearOfStudy;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Enumerated(EnumType.STRING)
    private AcademicTitle academicTitle;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    public Profile(Long userId, String firstName, String lastName, String username, String email, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
