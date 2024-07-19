package com.example.birds.controller;

import com.example.birds.dto.BirdResponseDto;
import com.example.birds.dto.BirdSaveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.birds.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Birds Controller class.
 * Useful links:
 * @see <a href="http://localhost:8080/swagger-ui/index.html">API Dcoumentation</a>
 * @see <a href="http://localhost:8080/v3/api-docs">JSON Informations</a>
 * <br>
 */
@RestController
@RequestMapping("/api/birds")
@Tag(name ="Birds API", description = "Collection of API(s) for birds.")
public class BirdController {
    private final BirdService birdService;

    @Autowired
    public BirdController(BirdService birdService) {
        this.birdService = birdService;
    }

    /**
     * Creates a bird.
     * @param birdSaveDto {@link BirdSaveDto}
     * @return a {@link BirdResponseDto} object.
     */
    @Operation(summary = "Creates a bird.")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A bird was created"),
            @ApiResponse(responseCode = "400", description = "Bad Request - invalid data provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
    })
    public BirdResponseDto createBird(@RequestBody BirdSaveDto birdSaveDto) {
        return birdService.saveBird(birdSaveDto);
    }


    /**
     * Updates a bird.
     * @param id {@link String}
     * @param birdSaveDto {@link BirdSaveDto}
     * @return a {@link BirdResponseDto} object.
     */
    @Operation(summary = "Updates a bird by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A bird was updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad Request - invalid data provided", content = @Content),
    })
    @PutMapping("/{id}")
    public BirdResponseDto updateBird(@PathVariable @Parameter(description = "The bird id") String id,
                           @RequestBody BirdSaveDto birdSaveDto) {
        return birdService.updateBird(id, birdSaveDto);
    }

    /**
     * Deletes a bird by id. This also deletes all the related sightings.
     * @param id {@link String}
     */
    @Operation(summary = "Deletes a bird by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A bird was deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Bad Request - invalid data provided", content = @Content),
    })
    @DeleteMapping("/{id}")
    public void deleteBird(@PathVariable @Parameter(description = "The bird id") String id) {
        birdService.deleteBird(id);
    }

    /**
     * Gets a bird by id.
     * @param id {@link String}
     * @return a {@link BirdResponseDto} object
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK - A bird was successfully found."),
            @ApiResponse(responseCode = "404", description = "Not found - invalid data provided", content = @Content),
    })
    @Operation(summary = "Gets a bird by id.")
    @GetMapping("/{id}")
    public BirdResponseDto getBirdById(@PathVariable @Parameter(description = "The bird id") String id) {
        return birdService.getBirdById(id);
    }

    /**
     * Gets all the birds.
     * @return a List of {@link BirdResponseDto} objects
     */
    @Operation(summary = "Gets all the birds.")
    @GetMapping
    public List<BirdResponseDto> getAllBirds() {
        return birdService.getAllBirds();
    }

    /**
     * Gets birds filtered by various parameters.
     * If no parameters are provided we will return all birds.
     * @param name {@link String}
     * @param color {@link String}
     * @return a List of {@link BirdResponseDto} objects
     */
    @Operation(summary = "Gets the birds by name or by color.")
    @GetMapping("/search")
    public List<BirdResponseDto> getBirdsByCriteria(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String color) {
        return birdService.getBirdsByCriteria(name, color);
    }
}