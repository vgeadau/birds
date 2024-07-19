package com.example.birds.controller;

import com.example.birds.utdata.SightingResponseDtoDataProvider;
import com.example.birds.utdata.SightingSaveDtoDataProvider;
import com.example.birds.dto.SightingResponseDto;
import com.example.birds.dto.SightingSaveDto;
import com.example.birds.service.SightingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SightingController}.
 */
@ExtendWith(MockitoExtension.class)
public class SightingControllerTest implements SightingSaveDtoDataProvider, SightingResponseDtoDataProvider {

    @Mock
    private final SightingService sightingService = Mockito.mock(SightingService.class);

    @InjectMocks
    private SightingController sightingController;


    @Test
    void createSighting_should_succeed() {
        // given
        final SightingSaveDto sightingSaveDto = buildSightingSaveDto();
        final SightingResponseDto sightingResponseDto = buildSightingResponseDto();

        when(sightingService.saveSighting(sightingSaveDto)).thenReturn(sightingResponseDto);

        // when
        final SightingResponseDto result = sightingController.createSighting(sightingSaveDto);

        // then
        verify(sightingService).saveSighting(sightingSaveDto);
        verifyNoMoreInteractions(sightingService);

        assertEquals(sightingResponseDto, result);
    }

    @Test
    void updateSighting_should_succeed() {
        // given
        final SightingSaveDto sightingSaveDto = buildSightingSaveDto();
        final String id = "2";
        final SightingResponseDto sightingResponseDto = buildSightingResponseDto();

        when(sightingService.updateSighting(sightingSaveDto, id)).thenReturn(sightingResponseDto);

        // when
        final SightingResponseDto result = sightingController.updateSighting(id, sightingSaveDto);

        // then
        verify(sightingService).updateSighting(sightingSaveDto, id);
        verifyNoMoreInteractions(sightingService);

        assertEquals(sightingResponseDto, result);
    }

    @Test
    void deleteSighting_should_succeed() {
        // given
        final String id = "2";

        // when
        sightingController.deleteSighting(id);

        // then
        verify(sightingService).deleteSighting(id);
        verifyNoMoreInteractions(sightingService);
    }

    @Test
    void getSightingById_should_succeed() {
        // given
        final String id = "2";
        final SightingResponseDto sightingResponseDto = buildSightingResponseDto();

        when(sightingService.getSightingById(id)).thenReturn(sightingResponseDto);

        // when
        final SightingResponseDto result = sightingController.getSightingById(id);

        // then
        verify(sightingService).getSightingById(id);
        verifyNoMoreInteractions(sightingService);

        assertEquals(sightingResponseDto, result);
    }

    @Test
    void getAllSightings_should_succeed() {
        // given
        final List<SightingResponseDto> sightingResponseDTOs = List.of(buildSightingResponseDto());

        when(sightingService.getAllSightings()).thenReturn(sightingResponseDTOs);

        // when
        final List<SightingResponseDto> result = sightingController.getAllSightings();

        // then
        verify(sightingService).getAllSightings();
        verifyNoMoreInteractions(sightingService);

        assertEquals(sightingResponseDTOs, result);
    }

    @Test
    void getSightingsByCriteria_should_succeed() {
        // given
        final List<SightingResponseDto> sightingResponseDTOs = List.of(buildSightingResponseDto());
        final String birdId = "1";
        final String location = "location";
        final String startDateTime = "2023-07-18T10:00:00";
        final String endDateTime = "2023-07-18T15:00:00";

        when(sightingService.getSightingsByCriteria(birdId, location, startDateTime, endDateTime))
                .thenReturn(sightingResponseDTOs);

        // when
        final List<SightingResponseDto> result = sightingController.getSightingsByCriteria(birdId,
                location,startDateTime, endDateTime);

        // then
        verify(sightingService).getSightingsByCriteria(birdId, location, startDateTime, endDateTime);
        verifyNoMoreInteractions(sightingService);

        assertEquals(sightingResponseDTOs, result);
    }
}
