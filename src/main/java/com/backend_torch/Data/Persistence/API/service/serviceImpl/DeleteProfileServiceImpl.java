package com.backend_torch.Data.Persistence.API.service.serviceImpl;

import com.backend_torch.Data.Persistence.API.dtos.ProfileResponseDto;
import com.backend_torch.Data.Persistence.API.exception.ApiException;
import com.backend_torch.Data.Persistence.API.model.Profiles;
import com.backend_torch.Data.Persistence.API.repository.ProfileRepository;
import com.backend_torch.Data.Persistence.API.service.contracts.DeleteProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteProfileServiceImpl implements DeleteProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public ResponseEntity<Void> deleteProfile(UUID id) {
        //1.Validate Input
        if (id == null || id.toString().isEmpty()) {
            throw new ApiException("error", "Profile id is required",400);
        }
        //2. Validate id
        Profiles profile = profileRepository.findById(id)
                .orElseThrow(() -> new ApiException("error", "Profile not found",404));

        //3. Delete profile
        profileRepository.delete(profile);
        return ResponseEntity.noContent().build();

    }
}
