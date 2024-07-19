package com.example.birds.service;

import com.example.birds.utdata.SightingSaveDtoDataProvider;
import com.example.birds.utdata.SightingDataProvider;
import com.example.birds.utdata.SightingResponseDtoDataProvider;
import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.SightingResponseDto;
import com.example.birds.dto.SightingSaveDto;
import com.example.birds.model.Sighting;
import com.example.birds.repository.SightingRepository;
import com.example.birds.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SightingService}.
 */
@ExtendWith(MockitoExtension.class)
public class SightingServiceTest implements SightingSaveDtoDataProvider, SightingResponseDtoDataProvider, SightingDataProvider {

    @Mock
    private final SightingRepository sightingRepository = Mockito.mock(SightingRepository.class);
    @Mock
    private final ModelService modelService = Mockito.mock(ModelService.class);
    @Mock
    private final BirdService birdService = Mockito.mock(BirdService.class);
    @Mock
    private final ValidationService validationService = Mockito.mock(ValidationService.class);

    @InjectMocks
    private SightingService sightingService;

    @Test
    void saveSighting_should_succeed() {
        // given
        final SightingSaveDto sightingSaveDto = buildSightingSaveDto();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();
        final Sighting sighting = buildSighting();
        final Sighting savedSighting = buildSighting();
        final SightingResponseDto sightingResponseDto = buildSightingResponseDto();

        when(birdService.getBirdById(sightingSaveDto.getBirdId())).thenReturn(birdResponseDto);
        when(modelService.getSighting(sightingSaveDto)).thenReturn(sighting);
        when(sightingRepository.save(sighting)).thenReturn(savedSighting);
        when(modelService.getSightingResponseDto(savedSighting, birdResponseDto)).thenReturn(sightingResponseDto);

        // when
        final SightingResponseDto result = sightingService.saveSighting(sightingSaveDto);

        // then
        verify(birdService).getBirdById(sightingSaveDto.getBirdId());
        verify(modelService).getSighting(sightingSaveDto);
        verify(sightingRepository).save(sighting);
        verify(modelService).getSightingResponseDto(savedSighting, birdResponseDto);
        verifyNoMoreInteractions(birdService, modelService, sightingRepository);
        verifyNoInteractions(validationService);

        assertEquals(sightingResponseDto, result);
    }

    @Test
    void updateSighting_should_succeed() {
        // given
        final String sightingId = "2";
        final SightingSaveDto sightingSaveDto = buildSightingSaveDto();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();
        final Sighting sighting = buildSighting();
        final Sighting updatedSighting = buildSighting();
        final SightingResponseDto sightingResponseDto = buildSightingResponseDto();

        when(birdService.getBirdById(sightingSaveDto.getBirdId())).thenReturn(birdResponseDto);
        when(modelService.getSighting(sightingSaveDto)).thenReturn(sighting);
        when(sightingRepository.save(sighting)).thenReturn(updatedSighting);
        when(modelService.getSightingResponseDto(updatedSighting, birdResponseDto)).thenReturn(sightingResponseDto);

        // when
        final SightingResponseDto result = sightingService.updateSighting(sightingSaveDto, sightingId);

        // then
        verify(birdService).getBirdById(sightingSaveDto.getBirdId());
        verify(modelService).getSighting(sightingSaveDto);
        verify(sightingRepository).save(sighting);
        verify(modelService).getSightingResponseDto(updatedSighting, birdResponseDto);
        verifyNoMoreInteractions(birdService, modelService, sightingRepository);
        verifyNoInteractions(validationService);

        assertEquals(sightingResponseDto, result);
    }

    @Test
    void deleteSighting_should_succeed() {
        // given
        final String id = "2";

        // when
        sightingService.deleteSighting(id);

        // then
        verify(sightingRepository).deleteById(id);
    }

    @Test
    void getSightingById_withInvalidId_should_fail() {
        // given
        final String id = "0";

        when(sightingRepository.findById(id)).thenReturn(Optional.empty());

        // when
        final Exception exception = assertThrows(IllegalStateException.class,
                () -> sightingService.getSightingById(id));

        // then
        verify(sightingRepository).findById(id);
        verifyNoMoreInteractions(sightingRepository);
        verifyNoInteractions(birdService, modelService, validationService);

        assertEquals(ErrorMessages.SIGHTING_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getSightingById_withValidId_should_succeed() {
        // given
        final String id = "2";
        final Sighting sighting = buildSighting();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();
        final SightingResponseDto sightingResponseDto = buildSightingResponseDto();

        when(sightingRepository.findById(id)).thenReturn(Optional.of(sighting));
        when(birdService.getBirdById(sighting.getBirdId())).thenReturn(birdResponseDto);
        when(modelService.getSightingResponseDto(sighting, birdResponseDto)).thenReturn(sightingResponseDto);

        // when
        final SightingResponseDto result = sightingService.getSightingById(id);

        // then
        verify(sightingRepository).findById(id);
        verify(birdService).getBirdById(sighting.getBirdId());
        verify(modelService).getSightingResponseDto(sighting, birdResponseDto);
        verifyNoMoreInteractions(sightingRepository, birdService, modelService);
        verifyNoInteractions(validationService);

        assertEquals(sightingResponseDto, result);
    }

    @Test
    void getAllSightings_should_succeed() {
        // given
        final List<SightingResponseDto> sightingResponseDTOs = List.of(buildSightingResponseDto());
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto());
        final List<Sighting> sightings = List.of(buildSighting());

        when(birdService.getAllBirds()).thenReturn(birdResponseDTOs);
        when(sightingRepository.findAll()).thenReturn(sightings);
        when(modelService.getSightingResponseDTOs(sightings, birdResponseDTOs)).thenReturn(sightingResponseDTOs);

        // when
        final List<SightingResponseDto> result = sightingService.getAllSightings();

        // then
        verify(birdService).getAllBirds();
        verify(sightingRepository).findAll();
        verify(validationService).verifyOrphanRecords(sightings, birdResponseDTOs);
        verify(modelService).getSightingResponseDTOs(sightings, birdResponseDTOs);
        verifyNoMoreInteractions(birdService, sightingRepository, validationService, modelService);

        assertEquals(sightingResponseDTOs, result);
    }

    @Test
    void getSightingsByCriteria_withBirdId_should_succeed() {
        // given
        final String birdId = "1";
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();
        final List<Sighting> sightings = List.of(buildSighting());
        final List<BirdResponseDto> birdResponseDTOs = List.of(birdResponseDto);
        final List<SightingResponseDto> sightingResponseDTOs = List.of(buildSightingResponseDto());

        when(birdService.getBirdById(birdId)).thenReturn(birdResponseDto);
        when(sightingRepository.findByBirdId(birdId)).thenReturn(sightings);
        when(modelService.getSightingResponseDTOs(sightings, birdResponseDTOs)).thenReturn(sightingResponseDTOs);

        // when
        final List<SightingResponseDto> result = sightingService.getSightingsByCriteria(birdId,
                null, null, null);

        // then
        verify(birdService).getBirdById(birdId);
        verify(sightingRepository).findByBirdId(birdId);
        verify(validationService).verifyOrphanRecords(sightings, birdResponseDTOs);
        verify(modelService).getSightingResponseDTOs(sightings, birdResponseDTOs);
        verifyNoMoreInteractions(birdService, sightingRepository, validationService, modelService);

        assertEquals(sightingResponseDTOs, result);
    }

    @Test
    void getSightingsByCriteria_withLocation_should_succeed() {
        // given
        final String location = "location";
        final List<SightingResponseDto> sightingResponseDTOs = List.of(buildSightingResponseDto());
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto());
        final List<Sighting> sightings = List.of(buildSighting());

        when(birdService.getAllBirds()).thenReturn(birdResponseDTOs);
        when(sightingRepository.findByLocation(location)).thenReturn(sightings);
        when(modelService.getSightingResponseDTOs(sightings, birdResponseDTOs)).thenReturn(sightingResponseDTOs);

        // when
        final List<SightingResponseDto> result = sightingService.getSightingsByCriteria(null,
                location, null, null);

        // then
        verify(birdService).getAllBirds();
        verify(sightingRepository).findByLocation(location);
        verify(validationService).verifyOrphanRecords(sightings, birdResponseDTOs);
        verify(modelService).getSightingResponseDTOs(sightings, birdResponseDTOs);
        verifyNoMoreInteractions(birdService, sightingRepository, validationService, modelService);

        assertEquals(sightingResponseDTOs, result);
    }

    @Test
    void getSightingsByCriteria_withTimeInterval_should_succeed() {
        // given
        final String startTimeString = "2024-07-18T09:30:00";
        final String endTimeString = "2024-07-18T10:30:00";
        final List<SightingResponseDto> sightingResponseDTOs = List.of(buildSightingResponseDto());
        final LocalDateTime startDateTime = LocalDateTime.parse(startTimeString);
        final LocalDateTime endDateTime = LocalDateTime.parse(endTimeString);
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto());
        final List<Sighting> sightings = List.of(buildSighting());

        when(modelService.getDateTime(startTimeString)).thenReturn(startDateTime);
        when(modelService.getDateTime(endTimeString)).thenReturn(endDateTime);
        when(birdService.getAllBirds()).thenReturn(birdResponseDTOs);
        when(sightingRepository.findByDateTimeBetween(startDateTime, endDateTime)).thenReturn(sightings);
        when(modelService.getSightingResponseDTOs(sightings, birdResponseDTOs)).thenReturn(sightingResponseDTOs);

        // when
        final List<SightingResponseDto> result = sightingService.getSightingsByCriteria(null,
                null, startTimeString, endTimeString);

        // then
        verify(modelService).getDateTime(startTimeString);
        verify(modelService).getDateTime(endTimeString);
        verify(birdService).getAllBirds();
        verify(sightingRepository).findByDateTimeBetween(startDateTime, endDateTime);
        verify(validationService).verifyOrphanRecords(sightings, birdResponseDTOs);
        verify(modelService).getSightingResponseDTOs(sightings, birdResponseDTOs);
        verifyNoMoreInteractions(birdService, sightingRepository, validationService, modelService);

        assertEquals(sightingResponseDTOs, result);
    }
}
