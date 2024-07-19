package com.example.birds.service;

import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.BirdSaveDto;
import com.example.birds.dto.SightingResponseDto;
import com.example.birds.dto.SightingSaveDto;
import com.example.birds.model.Bird;
import com.example.birds.model.Sighting;
import com.example.birds.utdata.*;
import com.example.birds.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ModelService}.
 */
@ExtendWith(MockitoExtension.class)
public class ModelServiceTest implements BirdSaveDtoDataProvider, BirdDataProvider, BirdResponseDtoDataProvider, SightingDataProvider, SightingSaveDtoDataProvider {

    private final ModelService modelService = new ModelService();

    @Test
    void getBird_should_succeed() {
        // given
        final BirdSaveDto birdSaveDto = buildBirdSaveDto();

        // when
        final Bird bird = modelService.getBird(birdSaveDto);

        // then
        assertEquals(birdSaveDto.getColor(), bird.getColor());
        assertEquals(birdSaveDto.getName(), bird.getName());
        assertEquals(birdSaveDto.getHeight(), bird.getHeight());
        assertEquals(birdSaveDto.getWeight(), bird.getWeight());
    }

    @Test
    void getBirdResponseDto_should_succeed() {
        // given
        final Bird bird = buildBird();

        // when
        final BirdResponseDto result = modelService.getBirdResponseDto(bird);

        // then
        assertEquals(bird.getId(), result.getId());
    }

    @Test
    void getBirdResponseDTOs_should_succeed() {
        // given
        final List<Bird> birds = List.of(buildBird());

        // when
        final List<BirdResponseDto> result = modelService.getBirdResponseDTOs(birds);

        // then
        assertEquals(birds.size(), result.size());
        assertEquals(birds.get(0).getId(), result.get(0).getId());
    }

    @Test
    void getSighting_should_succeed() {
        // given
        final SightingSaveDto sightingSaveDto = buildSightingSaveDto();

        // when
        final Sighting result = modelService.getSighting(sightingSaveDto);

        // then
        assertEquals(sightingSaveDto.getBirdId(), result.getBirdId());
    }

    @Test
    void getSightingResponseDto_should_succeed() {
        // given
        final Sighting sighting = buildSighting();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();

        // when
        SightingResponseDto result = modelService.getSightingResponseDto(sighting, birdResponseDto);

        // then
        assertEquals(sighting.getId(), result.getId());
        assertEquals(sighting.getBirdId(), result.getBirdResponseDto().getId());
    }

    @Test
    void getDateTime_should_succeed() {
        // given
        final String dateTimeString = "2023-07-18T10:00:00";
        final LocalDateTime localDateTime = LocalDateTime.parse("2023-07-18T10:00:00");

        // when
        final LocalDateTime result = modelService.getDateTime(dateTimeString);

        // then
        assertEquals(localDateTime, result);
    }

    @Test
    void getDateTime_withInvalidDateTime_should_fail() {
        // given
        final String dateTimeString = "2023-07-18T10:00:aa";

        // when
        final Exception exception = assertThrows(IllegalArgumentException.class,
                () -> modelService.getDateTime(dateTimeString));

        // then
        assertEquals(ErrorMessages.INVALID_DATETIME, exception.getMessage());
    }

    @Test
    void getDateTime_withNullDateTime_should_succeed() {
        // given

        // when
        final LocalDateTime result = modelService.getDateTime(null);

        // then
        assertNull(result);
    }

    @Test
    void getSightingResponseDTOs_should_succeed() {
        // given
        final List<Sighting> sightings = List.of(buildSighting());
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto());

        // when
        final List<SightingResponseDto> result = modelService.getSightingResponseDTOs(sightings, birdResponseDTOs);

        // then
        assertEquals(sightings.size(), result.size());
        assertEquals(sightings.get(0).getId(), result.get(0).getId());
        assertEquals(sightings.get(0).getBirdId(), result.get(0).getBirdResponseDto().getId());
    }
}
