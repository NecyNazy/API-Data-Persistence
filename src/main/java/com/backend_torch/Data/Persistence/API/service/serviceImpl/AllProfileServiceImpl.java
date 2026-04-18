package com.backend_torch.Data.Persistence.API.service.serviceImpl;

import com.backend_torch.Data.Persistence.API.dtos.AllProfileResponseDto;
import com.backend_torch.Data.Persistence.API.model.Profiles;
import com.backend_torch.Data.Persistence.API.repository.ProfileRepository;
import com.backend_torch.Data.Persistence.API.service.contracts.AllProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllProfileServiceImpl implements AllProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public ResponseEntity<AllProfileResponseDto> getProfiles(String gender, String countryId, String ageGroup) {
        // 1. Fetch all profiles
        List<Profiles> allProfiles = profileRepository.findAll();
        log.info("Total records in DB: {}", allProfiles.size());

//        // 2. Apply optional filters (Fixed to handle empty strings from Postman)
//        List<Profiles> filtered = allProfiles.stream()
//                .filter(p -> isNullOrBlank(gender) || (p.getGender() != null && p.getGender().equalsIgnoreCase(gender.trim())))
//                .filter(p -> isNullOrBlank(countryId) || (p.getCountryId() != null && p.getCountryId().equalsIgnoreCase(countryId.trim())))
//                .filter(p -> isNullOrBlank(ageGroup) || (p.getAgeGroup() != null && p.getAgeGroup().equalsIgnoreCase(ageGroup.trim())))
//                .toList();
        // 2. Apply optional filters
        List<Profiles> filtered = allProfiles.stream()
                .filter(p -> gender == null || gender.isBlank() || p.getGender().equalsIgnoreCase(gender.trim()))
                .filter(p -> countryId == null || countryId.isBlank() || p.getCountryId().equalsIgnoreCase(countryId.trim()))
                .filter(p -> ageGroup == null || ageGroup.isBlank() || p.getAgeGroup().equalsIgnoreCase(ageGroup.trim()))
                .toList();

        log.info("Filtered records count: {}", filtered.size());

        // 3. Map to DTO List (Ensuring UUID is converted to String for standard JSON)
        List<AllProfileResponseDto.Data> dataList = filtered.stream()
                .map(p -> AllProfileResponseDto.Data.builder()
                        .id(p.getId()) // Ensure your DTO field is UUID or String
                        .name(p.getName())
                        .gender(p.getGender())
                        .age(p.getAge())
                        .ageGroup(p.getAgeGroup())
                        .countryId(p.getCountryId())
                        .build())
                .collect(Collectors.toList());

        // 4. Build Response (REMOVED the hardcoded data.get(1) index which caused errors)
        AllProfileResponseDto response = AllProfileResponseDto.builder()
                .status("success")
                .count(dataList.size())
                .data(dataList) // Passing the whole list correctly
                .build();
        System.out.println("response: " + response);

        return ResponseEntity.ok(response);
    }

//    // Helper to prevent the "Empty String" filter trap
//    private boolean isNullOrBlank(String str) {
//        return str == null || str.trim().isEmpty();
//    }
}