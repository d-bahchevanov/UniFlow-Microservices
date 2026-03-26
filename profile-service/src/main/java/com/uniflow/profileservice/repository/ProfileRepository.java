package com.uniflow.profileservice.repository;

import com.uniflow.profileservice.enums.Role;
import com.uniflow.profileservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByUsername(String username);
    List<Profile> findAllByFirstNameAndLastName(String firstName, String lastName);
    Optional<Profile> findByUserId(Long userId);
    void deleteProfileByUserId(Long userId);
    boolean existsProfileByUserId(Long userId);
}
