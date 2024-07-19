package com.example.birds.service;

import com.example.birds.dto.BirdResponseDto;
import com.example.birds.model.Sighting;
import com.example.birds.utdata.BirdResponseDtoWithKeyDataProvider;
import com.example.birds.utdata.SightingWithKeyDataProvider;
import com.example.birds.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValidationService}.
 */
@ExtendWith(MockitoExtension.class)
public class ValidationServiceTest implements SightingWithKeyDataProvider, BirdResponseDtoWithKeyDataProvider {

    private static final String NO_ERROR = "no error";

    private final ValidationService validationService = new ValidationService();

    @Test
    void verifyOrphanRecords_withNullSightings_should_fail() {
        // given

        // when
        final Exception exception = assertThrows(RuntimeException.class,
                () -> validationService.verifyOrphanRecords(null, List.of()));

        // then
        assertEquals(ErrorMessages.NULL_PARAMETER_ERROR, exception.getMessage());
    }

    @Test
    void verifyOrphanRecords_withNullBirdResponseDTOs_should_fail() {
        // given

        // when
        final Exception exception = assertThrows(RuntimeException.class,
                () -> validationService.verifyOrphanRecords(List.of(), null));

        // then
        assertEquals(ErrorMessages.NULL_PARAMETER_ERROR, exception.getMessage());
    }

    @Test
    void verifyOrphanRecords_withEmptyLists_should_succeed() {
        String error = NO_ERROR;

        try {
            validationService.verifyOrphanRecords(List.of(), List.of());
        } catch (Exception exception) {
            error = exception.getMessage();
        }

        assertEquals(NO_ERROR, error);
    }

    @Test
    void verifyOrphanRecords_withOrphanRecords_should_fail() {
        // given
        final List<Sighting> sightings = List.of(buildSighting(1), buildSighting(2));
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto(3));

        // when
        final Exception exception = assertThrows(RuntimeException.class,
                () -> validationService.verifyOrphanRecords(sightings, birdResponseDTOs));

        // then
        assertEquals(ErrorMessages.ORPHAN_RECORDS_ERROR, exception.getMessage());
    }

    @Test
    void verifyOrphanRecords_withoutOrphanRecords_should_succeed() {
        // given
        final List<Sighting> sightings = List.of(buildSighting(1));
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto(1), buildBirdResponseDto(2));
        String error = NO_ERROR;

        // when
        try {
            validationService.verifyOrphanRecords(sightings, birdResponseDTOs);
        } catch (Exception exception) {
            error = exception.getMessage();
        }

        // then
        assertEquals(NO_ERROR, error);
    }
}
