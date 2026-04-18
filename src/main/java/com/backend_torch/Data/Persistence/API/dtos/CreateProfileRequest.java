package com.backend_torch.Data.Persistence.API.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProfileRequest {
    private String name;
}
