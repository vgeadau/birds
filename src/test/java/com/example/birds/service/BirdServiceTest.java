package com.example.birds.service;

import com.example.birds.utdata.BirdDataProvider;
import com.example.birds.utdata.BirdResponseDtoDataProvider;
import com.example.birds.utdata.BirdSaveDtoDataProvider;
import com.example.birds.utdata.BirdWithoutIdDataProvider;
import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.BirdSaveDto;
import com.example.birds.model.Bird;
import com.example.birds.repository.BirdRepository;
import com.example.birds.repository.SightingRepository;
import com.example.birds.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BirdService}.
 */
@ExtendWith(MockitoExtension.class)
public class BirdServiceTest implements BirdDataProvider, BirdSaveDtoDataProvider, BirdResponseDtoDataProvider, BirdWithoutIdDataProvider {

    @Mock
    private final BirdRepository birdRepository = Mockito.mock(BirdRepository.class);
    @Mock
    private final ModelService modelService = Mockito.mock(ModelService.class);
    @Mock
    private final SightingRepository sightingRepository = Mockito.mock(SightingRepository.class);

    @InjectMocks
    private BirdService birdService;

    @Test
    void saveBird_should_succeed() {
        // given
        final BirdSaveDto birdSaveDto = buildBirdSaveDto();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();
        final Bird bird = buildBirdWithoutId();
        final Bird persistedBird = buildBird();

        when(modelService.getBird(birdSaveDto)).thenReturn(bird);
        when(birdRepository.save(bird)).thenReturn(persistedBird);
        when(modelService.getBirdResponseDto(persistedBird)).thenReturn(birdResponseDto);

        // when
        final BirdResponseDto result = birdService.saveBird(birdSaveDto);

        // then
        verify(modelService).getBird(birdSaveDto);
        verify(birdRepository).save(bird);
        verify(modelService).getBirdResponseDto(persistedBird);
        verifyNoMoreInteractions(modelService, birdRepository);
        verifyNoInteractions(sightingRepository);

        assertEquals(birdResponseDto, result);
    }

    @Test
    void getBirdEntityById_withInvalidId_should_fail() {
        // given
        final String id = "0";
        when(birdRepository.findById(id)).thenReturn(Optional.empty());

        // when
        final Exception exception = assertThrows(IllegalStateException.class,
                () -> birdService.getBirdEntityById(id));

        // then
        verify(birdRepository).findById(id);
        verifyNoMoreInteractions(birdRepository);
        verifyNoInteractions(modelService, sightingRepository);
        assertEquals(ErrorMessages.BIRD_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getBirdEntityById_withValidId_should_succeed() {
        // given
        final String id = "1";
        final Bird persistedBird = buildBird();
        when(birdRepository.findById(id)).thenReturn(Optional.of(persistedBird));

        // when
        final Bird result = birdService.getBirdEntityById(id);

        // then
        verify(birdRepository).findById(id);
        verifyNoMoreInteractions(birdRepository);
        verifyNoInteractions(modelService, sightingRepository);

        assertEquals(persistedBird, result);
    }

    @Test
    void updateBird_should_succeed() {
        // given that getBirdEntityById is fully tested, we get full coverage by testing 1 scenario here
        final BirdSaveDto birdSaveDto = buildBirdSaveDto();
        final String id = "1";
        final Bird persistedBird = buildBird();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();

        when(birdRepository.findById(id)).thenReturn(Optional.of(persistedBird));
        when(birdRepository.save(persistedBird)).thenReturn(persistedBird);
        when(modelService.getBirdResponseDto(persistedBird)).thenReturn(birdResponseDto);

        // when
        final BirdResponseDto result = birdService.updateBird(id, birdSaveDto);

        // then
        verify(birdRepository).findById(id);
        verify(birdRepository).save(persistedBird);
        verify(modelService).getBirdResponseDto(persistedBird);
        verifyNoMoreInteractions(birdRepository, modelService);
        verifyNoInteractions(sightingRepository);

        assertEquals(birdResponseDto, result);
    }

    @Test
    void deleteBird_should_succeed() {
        // given
        final String id = "1";
        final List<String> ids = List.of("2", "3");

        when(sightingRepository.findSightingIdsByBirdId(id)).thenReturn(ids);

        // when
        birdService.deleteBird(id);

        // then
        verify(sightingRepository).findSightingIdsByBirdId(id);
        verify(sightingRepository).deleteAllById(ids);
        verify(birdRepository).deleteById(id);
        verifyNoMoreInteractions(sightingRepository, birdRepository);
        verifyNoInteractions(modelService);
    }

    @Test
    void getBirdById_should_succeed() {
        // given
        final String id = "1";
        final Bird bird = buildBird();
        final BirdResponseDto birdResponseDto = buildBirdResponseDto();

        when(birdRepository.findById(id)).thenReturn(Optional.of(bird));
        when(modelService.getBirdResponseDto(bird)).thenReturn(birdResponseDto);

        // when
        final BirdResponseDto result = birdService.getBirdById(id);

        // then
        verify(birdRepository).findById(id);
        verify(modelService).getBirdResponseDto(bird);
        verifyNoMoreInteractions(birdRepository, modelService);
        verifyNoInteractions(sightingRepository);

        assertEquals(birdResponseDto, result);
    }

    @Test
    void getAllBirds_should_succeed() {
        // given
        final List<Bird> birds = List.of();

        when(birdRepository.findAll()).thenReturn(birds);
        when(modelService.getBirdResponseDTOs(birds)).thenReturn(List.of());

        // when
        final List<BirdResponseDto> result = birdService.getAllBirds();

        // then
        verify(birdRepository).findAll();
        verify(modelService).getBirdResponseDTOs(birds);
        verifyNoMoreInteractions(birdRepository, modelService);
        verifyNoInteractions(sightingRepository);

        assertTrue(result.isEmpty());
    }

    @Test
    void getBirdsByCriteria_withNullNameAndColor_should_succeed() {
        // given
        final List<Bird> birds = List.of();

        when(birdRepository.findAll()).thenReturn(birds);
        when(modelService.getBirdResponseDTOs(birds)).thenReturn(List.of());

        // when
        final List<BirdResponseDto> result = birdService.getBirdsByCriteria(null, null);

        // then
        verify(birdRepository).findAll();
        verify(modelService).getBirdResponseDTOs(birds);
        verifyNoMoreInteractions(birdRepository, modelService);
        verifyNoInteractions(sightingRepository);

        assertTrue(result.isEmpty());
    }

    @Test
    void getBirdsByCriteria_withName_should_succeed() {
        // given
        final String name = "name";
        final List<Bird> birds = List.of();

        when(birdRepository.findByName(name)).thenReturn(birds);
        when(modelService.getBirdResponseDTOs(birds)).thenReturn(List.of());

        // when
        final List<BirdResponseDto> result = birdService.getBirdsByCriteria(name, null);

        // then
        verify(birdRepository).findByName(name);
        verify(modelService).getBirdResponseDTOs(birds);
        verifyNoMoreInteractions(birdRepository, modelService);
        verifyNoInteractions(sightingRepository);

        assertTrue(result.isEmpty());
    }

    @Test
    void getBirdsByCriteria_withColor_should_succeed() {
        // given
        final String color = "color";
        final List<Bird> birds = List.of();

        when(birdRepository.findByColor(color)).thenReturn(birds);
        when(modelService.getBirdResponseDTOs(birds)).thenReturn(List.of());

        // when
        final List<BirdResponseDto> result = birdService.getBirdsByCriteria(null, color);

        // then
        verify(birdRepository).findByColor(color);
        verify(modelService).getBirdResponseDTOs(birds);
        verifyNoMoreInteractions(birdRepository, modelService);
        verifyNoInteractions(sightingRepository);

        assertTrue(result.isEmpty());
    }
}
