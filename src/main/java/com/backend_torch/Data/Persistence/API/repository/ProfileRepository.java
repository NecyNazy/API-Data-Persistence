package com.backend_torch.Data.Persistence.API.repository;

import com.backend_torch.Data.Persistence.API.model.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profiles,UUID> {
    Optional<Profiles> findByName(String name);

    Boolean existsByName(String name);

    Optional<Profiles> findByGenderAndCountryIdAndAgeGroup(String gender, String countryId , String ageGroup);
}
