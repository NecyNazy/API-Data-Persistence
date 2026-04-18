package com.backend_torch.Data.Persistence.API.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String genderizeApiUrl;
    private String nationalizeApiUrl;
    private String agifyApiUrl;
}
