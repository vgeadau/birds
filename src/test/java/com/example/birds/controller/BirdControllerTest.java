package com.example.birds.controller;

import com.example.birds.utdata.BirdResponseDtoDataProvider;
import com.example.birds.utdata.BirdSaveDtoDataProvider;
import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.BirdSaveDto;
import com.example.birds.service.BirdService;
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
 * Unit tests for {@link BirdController}.
 */
@ExtendWith(MockitoExtension.class)
public class BirdControllerTest implements BirdSaveDtoDataProvider, BirdResponseDtoDataProvider {

    @Mock
    private final BirdService birdService = Mockito.mock(BirdService.class);

    @InjectMocks
    private BirdController birdController;

    @Test
    void createBird_should_succeed() {
        // given
        final BirdSaveDto birdSaveDto = buildBirdSaveDto();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();

        when(birdService.saveBird(birdSaveDto)).thenReturn(birdResponseDto);

        // when
        final BirdResponseDto result = birdController.createBird(birdSaveDto);

        // then
        verify(birdService).saveBird(birdSaveDto);
        verifyNoMoreInteractions(birdService);

        assertEquals(birdResponseDto, result);
    }

    @Test
    void updateBird_should_succeed() {
        // given
        final BirdSaveDto birdSaveDto = buildBirdSaveDto();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();

        when(birdService.updateBird("1", birdSaveDto)).thenReturn(birdResponseDto);

        // when
        final BirdResponseDto result = birdController.updateBird("1", birdSaveDto);

        // then
        verify(birdService).updateBird("1", birdSaveDto);
        verifyNoMoreInteractions(birdService);

        assertEquals(birdResponseDto, result);
    }

    @Test
    void deleteBird_should_succeed() {
        // given
        final String id = "1";

        // when
        birdController.deleteBird(id);

        // then
        verify(birdService).deleteBird(id);
        verifyNoMoreInteractions(birdService);
    }

    @Test
    void getBirdById_should_succeed() {
        // given
        final String id = "1";
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();

        when(birdService.getBirdById(id)).thenReturn(birdResponseDto);

        // when
        final BirdResponseDto result = birdController.getBirdById(id);

        // then
        verify(birdService).getBirdById(id);
        verifyNoMoreInteractions(birdService);

        assertEquals(birdResponseDto, result);
    }

    @Test
    void getAllBirds_should_succeed() {
        // given
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto());

        when(birdService.getAllBirds()).thenReturn(birdResponseDTOs);

        // when
        final List<BirdResponseDto> result = birdController.getAllBirds();

        // then
        verify(birdService).getAllBirds();
        verifyNoMoreInteractions(birdService);

        assertEquals(birdResponseDTOs, result);
    }

    @Test
    void getBirdsByCriteria_should_succeed() {
        // given
        final String name = "name";
        final String color = "color";
        final List<BirdResponseDto> birdResponseDTOs = List.of(buildBirdResponseDto());

        when(birdService.getBirdsByCriteria(name, color)).thenReturn(birdResponseDTOs);

        // when
        final List<BirdResponseDto> result = birdController.getBirdsByCriteria(name, color);

        // then
        verify(birdService).getBirdsByCriteria(name, color);
        verifyNoMoreInteractions(birdService);

        assertEquals(birdResponseDTOs, result);
    }
}
