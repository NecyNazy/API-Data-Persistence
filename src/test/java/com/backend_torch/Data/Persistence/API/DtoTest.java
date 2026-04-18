package com.backend_torch.Data.Persistence.API;

import com.backend_torch.Data.Persistence.API.dtos.GenderizeResponse;
import com.backend_torch.Data.Persistence.API.dtos.NationalizeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class DtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGenderize() throws Exception {
        String json = """
                {
                  "name": "john",
                  "gender": "male",
                  "probability": 0.99,
                  "count": 12345
                }
                """;

        GenderizeResponse res = mapper.readValue(json, GenderizeResponse.class);

        System.out.println(res.getGender()); // should be "male"
    }
    @Test
    void testNationalize() throws Exception {
        String json = """
        {
          "name": "john",
          "country": [
            { "country_id": "US", "probability": 0.65 },
            { "country_id": "GB", "probability": 0.20 }
          ]
        }
        """;

        NationalizeResponse res = mapper.readValue(json, NationalizeResponse.class);

        System.out.println(res.getCountry().size()); // should be 2
    }
}
