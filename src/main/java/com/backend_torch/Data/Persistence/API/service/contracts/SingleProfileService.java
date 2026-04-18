package com.backend_torch.Data.Persistence.API.service.contracts;

import com.backend_torch.Data.Persistence.API.dtos.ProfileResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SingleProfileService {
    ResponseEntity<ProfileResponseDto> getSingleProfile(UUID id);
}
