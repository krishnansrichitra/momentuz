package com.momentus.foundation.profile.repository;

import com.momentus.foundation.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {}
