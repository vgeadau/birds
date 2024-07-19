package com.example.birds.service;

import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.BirdSaveDto;
import com.example.birds.dto.SightingResponseDto;
import com.example.birds.dto.SightingSaveDto;
import com.example.birds.model.Bird;
import com.example.birds.model.Sighting;
import com.example.birds.util.ErrorMessages;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for handling conversion.
 * I avoided adding a new dependency such as ObjectMapper to keep the project lighter.
 */
@Service
public class ModelService {

    /**
     * Constructs a {@link Bird} object from the provided parameter.
     * @param birdSaveDto {@link BirdSaveDto}
     * @return a {@link Bird} object
     */
    public Bird getBird(BirdSaveDto birdSaveDto) {
        return new Bird(birdSaveDto.getName(),
                birdSaveDto.getColor(), birdSaveDto.getWeight(), birdSaveDto.getHeight());
    }

    /**
     * Constructs a {@link BirdResponseDto} from the provided parameter.
     * @param bird {@link Bird}
     * @return a {@link BirdResponseDto} object
     */
    public BirdResponseDto getBirdResponseDto(Bird bird) {
        return new BirdResponseDto(bird.getId(), bird.getName(), bird.getColor(), bird.getWeight(), bird.getHeight());
    }

    /**
     * Constructs a list of {@link BirdResponseDto} from the provided parameter.
     * No lambda steams were used for extra 50% performance.
     * @param birds List of {@link Bird} objects
     * @return List of {@link BirdResponseDto}
     */
    public List<BirdResponseDto> getBirdResponseDTOs(List<Bird> birds) {
        final List<BirdResponseDto> result = new ArrayList<>();
        for (Bird bird : birds) {
            result.add(getBirdResponseDto(bird));
        }
        return result;
    }

    /**
     * Constructs a {@link Sighting} using provided parameter.
     * @param sightingSaveDto {@link SightingSaveDto}
     * @return a {@link Sighting} object
     */
    public Sighting getSighting(SightingSaveDto sightingSaveDto) {
        return new Sighting(sightingSaveDto.getBirdId(), sightingSaveDto.getLocation(), getDateTime(sightingSaveDto.getDateTime()));
    }

    /**
     * Constructs a {@link SightingResponseDto} from the provided parameters.
     * @param sighting {@link Sighting}
     * @param birdResponseDto {@link BirdResponseDto}
     * @return a {@link SightingResponseDto} object
     */
    public SightingResponseDto getSightingResponseDto(Sighting sighting, BirdResponseDto birdResponseDto) {
        return new SightingResponseDto(sighting.getId(), birdResponseDto, sighting.getLocation(), sighting.getDateTime());
    }

    /**
     * Parses a dateTimeString attempting conversion into {@link LocalDateTime}.
     * @param dateTimeString {@link String}
     * @return a {@link LocalDateTime} object if no errors, an {@link IllegalArgumentException} if the parsing throws errors.
     */
    public LocalDateTime getDateTime(String dateTimeString) {
        try {
            return dateTimeString != null ? LocalDateTime.parse(dateTimeString) : null;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_DATETIME, exception);
        }
    }

    /**
     * Constructs a list of SightingResponseDto from the provided parameters.
     * @param sightings List of {@link Sighting}
     * @param birdResponseDTOs List of {@link BirdResponseDto}
     * @return a List of {@link SightingResponseDto} objects.
     */
    public List<SightingResponseDto> getSightingResponseDTOs(List<Sighting> sightings, List<BirdResponseDto> birdResponseDTOs) {
        final List<SightingResponseDto> result = new ArrayList<>();
        for (Sighting sighting : sightings) {
            BirdResponseDto birdResponseDto = birdResponseDTOs.get(0);
            for (BirdResponseDto dto : birdResponseDTOs) {
                if (dto.getId().equals(sighting.getBirdId())) {
                    birdResponseDto = dto;
                }
            }
            result.add(getSightingResponseDto(sighting, birdResponseDto));
        }
        return result;
    }
}
