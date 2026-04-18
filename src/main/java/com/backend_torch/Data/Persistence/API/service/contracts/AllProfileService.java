package com.backend_torch.Data.Persistence.API.service.contracts;

import com.backend_torch.Data.Persistence.API.dtos.AllProfileResponseDto;
import org.springframework.http.ResponseEntity;

public interface AllProfileService {
    ResponseEntity<AllProfileResponseDto> getProfiles(String gender, String countryId, String ageGroup);
}
