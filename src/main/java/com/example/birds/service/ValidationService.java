package com.example.birds.service;

import com.example.birds.dto.BirdResponseDto;
import com.example.birds.model.Sighting;
import com.example.birds.util.ErrorMessages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service class used to check the integrity of 'MONGODB records'.
 * If orphan children detected (sighting records with missing birds) this application will fail.
 */
@Service
public class ValidationService {

    /**
     * This method verifies the integrity of sightings by attempting to match sighting.birdId with bird.id(s).
     * If we don't have a match an {@link IllegalStateException} will be thrown.
     * If we call this method with null parameters an {@link RuntimeException} will be thrown.
     * @param sightings List of {@link Sighting}
     * @param birdResponseDTOs list of {@link BirdResponseDto}
     */
    public void verifyOrphanRecords(List<Sighting> sightings, List<BirdResponseDto> birdResponseDTOs) {
        if (Objects.isNull(sightings) || Objects.isNull(birdResponseDTOs)) {
            throw new RuntimeException(ErrorMessages.NULL_PARAMETER_ERROR);
        }

        for (Sighting sighting : sightings) {
            boolean found = false;
            for (BirdResponseDto birdResponseDto : birdResponseDTOs) {
                if (sighting.getBirdId().equals(birdResponseDto.getId())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalStateException(ErrorMessages.ORPHAN_RECORDS_ERROR);
            }
        }
    }
}
