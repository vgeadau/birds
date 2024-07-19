package com.example.birds.service;

import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.SightingResponseDto;
import com.example.birds.dto.SightingSaveDto;
import com.example.birds.model.Sighting;
import com.example.birds.repository.SightingRepository;
import com.example.birds.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Sighting Service responsible for CRUD and GET operations regarding sightings.
 */
@Service
public class SightingService {

    private final SightingRepository sightingRepository;

    private final ModelService modelService;

    private final BirdService birdService;

    private final ValidationService validationService;

    @Autowired
    public SightingService(SightingRepository sightingRepository, ModelService modelService,
                           BirdService birdService, ValidationService validationService) {
        this.sightingRepository = sightingRepository;
        this.modelService = modelService;
        this.birdService = birdService;
        this.validationService = validationService;
    }

    /**
     * Saves a sighting.
     * @param sightingSaveDto {@link SightingSaveDto}
     * @return a {@link SightingResponseDto} object
     */
    @Transactional
    public SightingResponseDto saveSighting(SightingSaveDto sightingSaveDto) {
        // bird must be persisted in order to create sighting
        final BirdResponseDto birdResponseDto = birdService.getBirdById(sightingSaveDto.getBirdId());

        final Sighting sighting = modelService.getSighting(sightingSaveDto);
        final Sighting savedSighting = sightingRepository.save(sighting);
        return modelService.getSightingResponseDto(savedSighting, birdResponseDto);
    }

    /**
     * Updates a sighting.
     * @param sightingSaveDto {@link SightingSaveDto}
     * @param sightingId {@link String}
     * @return a {@link SightingResponseDto} object
     */
    @Transactional
    public SightingResponseDto updateSighting(SightingSaveDto sightingSaveDto, String sightingId) {
        // bird must be persisted in order to update sighting
        final BirdResponseDto birdResponseDto = birdService.getBirdById(sightingSaveDto.getBirdId());

        final Sighting sighting = modelService.getSighting(sightingSaveDto);
        sighting.setId(sightingId);
        final Sighting updatedSighting = sightingRepository.save(sighting);
        return modelService.getSightingResponseDto(updatedSighting, birdResponseDto);
    }

    /**
     * Deletes a sighting.
     * @param id {@link String}
     */
    @Transactional
    public void deleteSighting(String id) {
        sightingRepository.deleteById(id);
    }

    /**
     * Gets a sighting by id.
     * @param id {@link String}
     * @return a {@link SightingResponseDto} object
     */
    public SightingResponseDto getSightingById(String id) {
        final Sighting sighting = sightingRepository.findById(id).orElse(null);
        if (Objects.isNull(sighting)) {
            throw new IllegalStateException(ErrorMessages.SIGHTING_NOT_FOUND);
        }
        final BirdResponseDto birdResponseDto = birdService.getBirdById(sighting.getBirdId());
        return modelService.getSightingResponseDto(sighting, birdResponseDto);
    }

    /**
     * Gets all sightings.
     * @return a List of {@link SightingResponseDto} objects
     */
    public List<SightingResponseDto> getAllSightings() {

        // rather than performing multiple queries and possible to get the same bird
        // over and over again for its multiple sighting, we:
        // 1. get all birds.
        // 2. we match the birds with the sightings
        final List<BirdResponseDto> birdResponseDTOs = birdService.getAllBirds();
        final List<Sighting> sightings = sightingRepository.findAll();

        // this is code is not intended for production. it exists here as POC.
        // this exists here as proof as I considered checking the consistency of the app.
        // if the database is not consistent the application will fail returning sightings.
        validationService.verifyOrphanRecords(sightings, birdResponseDTOs);

        return modelService.getSightingResponseDTOs(sightings, birdResponseDTOs);
    }

    /**
     * Gets all sighting filtered by provided criteria.
     * If no criteria is provided, all sightings will be returned.
     * Example of a valid LocalDateTime string "2023-07-18T10:00:00".
     * @param birdId {@link String}
     * @param location {@link String}
     * @param startDateTime {@link String}
     * @param endDateTime {@link String}
     * @return a List of {@link SightingResponseDto} objects
     */
    public List<SightingResponseDto> getSightingsByCriteria(String birdId, String location, String startDateTime, String endDateTime) {
        if (birdId != null) {
            return getSightingsByBirdId(birdId);
        } else if (location != null) {
            return getSightingsByLocation(location);
        } else if (startDateTime != null && endDateTime != null) {
            return getSightingsByDateTimeInterval(startDateTime, endDateTime);
        } else {
            return getAllSightings();
        }
    }

    /**
     * Gets all sightings of a bird.
     * @param birdId {@link String}
     * @return a List of {@link SightingResponseDto} objects
     */
    private List<SightingResponseDto> getSightingsByBirdId(String birdId) {
        final List<BirdResponseDto> birdResponseDTOs = List.of(birdService.getBirdById(birdId));
        final List<Sighting> sightings = sightingRepository.findByBirdId(birdId);
        validationService.verifyOrphanRecords(sightings, birdResponseDTOs);
        return modelService.getSightingResponseDTOs(sightings, birdResponseDTOs);
    }

    /**
     * Gets all sightings for a location.
     * @param location {@link String}
     * @return a List of {@link SightingResponseDto} objects
     */
    private List<SightingResponseDto> getSightingsByLocation(String location) {
        final List<BirdResponseDto> birdResponseDTOs = birdService.getAllBirds();
        final List<Sighting> sightings = sightingRepository.findByLocation(location);
        validationService.verifyOrphanRecords(sightings, birdResponseDTOs);
        return modelService.getSightingResponseDTOs(sightings, birdResponseDTOs);
    }

    /**
     * Gets all sightings for an interval of LocalDateTime strings.
     * Example of a valid LocalDateTime string "2023-07-18T10:00:00".
     * @param startDateTimeString {@link String}
     * @param endDateTimeString {@link String}
     * @return a List of {@link SightingResponseDto} objects
     */
    private List<SightingResponseDto> getSightingsByDateTimeInterval(String startDateTimeString, String endDateTimeString) {
        final LocalDateTime startDateTime = modelService.getDateTime(startDateTimeString);
        final LocalDateTime endDateTime = modelService.getDateTime(endDateTimeString);
        final List<BirdResponseDto> birdResponseDTOs = birdService.getAllBirds();
        final List<Sighting> sightings = sightingRepository.findByDateTimeBetween(startDateTime, endDateTime);
        validationService.verifyOrphanRecords(sightings, birdResponseDTOs);
        return modelService.getSightingResponseDTOs(sightings, birdResponseDTOs);
    }
}