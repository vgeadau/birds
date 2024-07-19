package com.example.birds.service;

import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.BirdSaveDto;
import com.example.birds.model.Bird;
import com.example.birds.repository.BirdRepository;
import com.example.birds.repository.SightingRepository;
import com.example.birds.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Bird Service responsible for CRUD and GET operations regarding birds.
 */
@Service
public class BirdService {

    private final BirdRepository birdRepository;

    private final ModelService modelService;

    private final SightingRepository sightingRepository;

    @Autowired
    public BirdService(BirdRepository birdRepository, ModelService modelService, SightingRepository sightingRepository) {
        this.birdRepository = birdRepository;
        this.modelService = modelService;
        this.sightingRepository = sightingRepository;
    }

    /**
     * Saves a bird.
     * @param birdSaveDto {@link BirdSaveDto}
     * @return a {@link BirdResponseDto} object
     */
    @Transactional
    public BirdResponseDto saveBird(BirdSaveDto birdSaveDto) {
        final Bird bird = modelService.getBird(birdSaveDto);
        final Bird createdBird = birdRepository.save(bird);
        return modelService.getBirdResponseDto(createdBird);
    }

    /**
     * Finds a {@link Bird} entity by the provided parameter.
     * @param id {@link String}
     * @return a {@link Bird} object
     */
    public Bird getBirdEntityById(String id) {
        final Bird bird = birdRepository.findById(id).orElse(null);
        if (Objects.isNull(bird)) {
            throw new IllegalStateException(ErrorMessages.BIRD_NOT_FOUND);
        }
        return bird;
    }

    /**
     * Updates a {@link Bird} object.
     * @param id {@link String}
     * @param birdSaveDto {@link BirdSaveDto}
     * @return a {@link BirdResponseDto} object
     */
    @Transactional
    public BirdResponseDto updateBird(String id, BirdSaveDto birdSaveDto) {
        // obtain the bird by the provided id
        final Bird bird = getBirdEntityById(id);
        bird.setColor(birdSaveDto.getColor());
        bird.setName(birdSaveDto.getName());
        bird.setWeight(birdSaveDto.getWeight());
        bird.setHeight(birdSaveDto.getHeight());
        Bird updatedBird = birdRepository.save(bird);
        return modelService.getBirdResponseDto(updatedBird);
    }

    /**
     * Deletes a {@link Bird} object and its children ({@link com.example.birds.model.Sighting} objects).
     * @param id {@link String}
     */
    @Transactional
    public void deleteBird(String id) {
        // we delete children first
        List<String> ids = sightingRepository.findSightingIdsByBirdId(id);
        sightingRepository.deleteAllById(ids);

        birdRepository.deleteById(id);
    }

    /**
     * Finds a {@link Bird} object by the provided parameter.
     * @param id {@link String}
     * @return a {@link BirdResponseDto} object
     */
    public BirdResponseDto getBirdById(String id) {
        final Bird bird = getBirdEntityById(id);
        return modelService.getBirdResponseDto(bird);
    }

    /**
     * Finds all {@link Bird} objects.
     * @return a list of {@link BirdResponseDto} objects
     */
    public List<BirdResponseDto> getAllBirds() {
        final List<Bird> birds = birdRepository.findAll();
        return modelService.getBirdResponseDTOs(birds);
    }

    /**
     * Finds all {@link Bird} objects by provided criteria.
     * @param name {@link String}
     * @param color {@link String}
     * @return a list of {@link BirdResponseDto} objects
     */
    public List<BirdResponseDto> getBirdsByCriteria(String name, String color) {
        if (name != null) {
            return getBirdsByName(name);
        } else if (color != null) {
            return getBirdsByColor(color);
        } else {
            return getAllBirds();
        }
    }

    /**
     * Finds all {@link Bird} objects having the same name.
     * @param name {@link String}
     * @return a list of {@link BirdResponseDto} objects
     */
    private List<BirdResponseDto> getBirdsByName(String name) {
        final List<Bird> birds = birdRepository.findByName(name);
        return modelService.getBirdResponseDTOs(birds);
    }

    /**
     * Finds all {@link Bird} objects having the same color.
     * @param color {@link String}
     * @return a list of {@link BirdResponseDto} objects
     */
    private List<BirdResponseDto> getBirdsByColor(String color) {
        final List<Bird> birds = birdRepository.findByColor(color);
        return modelService.getBirdResponseDTOs(birds);
    }
}