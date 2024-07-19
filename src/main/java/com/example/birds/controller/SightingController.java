package com.example.birds.controller;

import com.example.birds.dto.SightingResponseDto;
import com.example.birds.dto.SightingSaveDto;
import com.example.birds.service.SightingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Sightings Controller class.
 * Useful links:
 * @see <a href="http://localhost:8080/swagger-ui/index.html">API Dcoumentation</a>
 * @see <a href="http://localhost:8080/v3/api-docs">JSON Informations</a>
 * <br>
 */
@RestController
@RequestMapping("/api/sightings")
@Tag(name ="Sightings API", description = "Collection of API(s) for sightings.")
public class SightingController {
    private final SightingService sightingService;

    @Autowired
    public SightingController(SightingService sightingService) {
        this.sightingService = sightingService;
    }

    /**
     * Creates a sighting.
     * @param sighting {@link SightingSaveDto}
     * @return a {@link SightingResponseDto} object
     */
    @Operation(summary = "Creates a sighting.")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A sighting was created"),
            @ApiResponse(responseCode = "404", description = "Not found - invalid data provided", content = @Content),
    })
    public SightingResponseDto createSighting(@RequestBody SightingSaveDto sighting) {
        return sightingService.saveSighting(sighting);
    }

    /**
     * Updates a sighting.
     * @param id {@link String}
     * @param sightingSaveDto {@link SightingSaveDto}
     * @return a {@link SightingResponseDto} object
     */
    @Operation(summary = "Updates a sighting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A sighting was updated successfully."),
            @ApiResponse(responseCode = "404", description = "Not Found - invalid data provided", content = @Content),
    })
    @PutMapping("/{id}")
    public SightingResponseDto updateSighting(@PathVariable @Parameter(description = "The sighting id")  String id, @RequestBody SightingSaveDto sightingSaveDto) {
        return sightingService.updateSighting(sightingSaveDto, id);
    }

    /**
     * Deletes a sighting.
     * @param id {@link String}
     */
    @Operation(summary = "Deletes a sighting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A sighting was updated successfully."),
    })
    @DeleteMapping("/{id}")
    public void deleteSighting(@PathVariable @Parameter(description = "The sighting id") String id) {
        sightingService.deleteSighting(id);
    }

    /**
     * Gets a sighting by id.
     * @param id {@link String}
     * @return a {@link SightingResponseDto} object
     */
    @Operation(summary = "Gets a sighting by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A sighting was updated successfully."),
            @ApiResponse(responseCode  = "404", description = "Not Found - A sighting was not found."),
    })
    @GetMapping("/{id}")
    public SightingResponseDto getSightingById(@PathVariable @Parameter(description = "The sighting id") String id) {
        return sightingService.getSightingById(id);
    }

    /**
     * Gets all sightings.
     * @return a List of {@link SightingResponseDto} objects
     */
    @Operation(summary = "Gets all sightings.")
    @GetMapping
    public List<SightingResponseDto> getAllSightings() {
        return sightingService.getAllSightings();
    }

    /**
     * Gets all sightings filtered by the provided criteria.
     * If no criteria is provided it will return all the sightings.
     * Example of valid startDateTime format: "2023-07-18T10:00:00"
     * @param birdId {@link String}
     * @param location {@link String}
     * @param startDateTime {@link String}
     * @param endDateTime {@link String}
     * @return a List of {@link SightingResponseDto} objects
     */
    @Operation(summary = "Filters a sighting by criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A sighting was updated successfully."),
            @ApiResponse(responseCode  = "400", description = "Bad Command - For example bad DateTime string."),
            @ApiResponse(responseCode  = "404", description = "Not Found - A sighting was not found."),
    })
    @GetMapping("/search")
    public List<SightingResponseDto> getSightingsByCriteria(@RequestParam(required = false) String birdId,
                                                 @RequestParam(required = false) String location,
                                                 @RequestParam(required = false) String startDateTime,
                                                 @RequestParam(required = false) String endDateTime) {
        return sightingService.getSightingsByCriteria(birdId, location, startDateTime, endDateTime);
    }
}
