package com.backend_torch.Data.Persistence.API.helper;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Component;

import java.util.UUID;


public final class UUIDHelper {

    public static UUID generateUUID() {
        return UuidCreator.getTimeOrderedEpoch(); // UUID v7
    }
}
