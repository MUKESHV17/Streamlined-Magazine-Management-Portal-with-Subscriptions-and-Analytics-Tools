package com.examly.springapp.controller;

import com.examly.springapp.entity.Magazine;
import com.examly.springapp.service.MagazineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/magazines")
@RequiredArgsConstructor
@Tag(name = "Magazine Controller", description = "Endpoints for managing magazines")
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping
    @Operation(summary = "Get all magazines", description = "Returns a list of all magazines with optional sorting")
    public ResponseEntity<List<Magazine>> getAllMagazines(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        List<Magazine> magazines = magazineService.getAllMagazines(sortBy, order);
        return ResponseEntity.ok(magazines);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a magazine by ID", description = "Returns a magazine based on the provided ID")
    public ResponseEntity<Magazine> getMagazineById(@PathVariable Integer id) {
        return magazineService.getMagazineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/{publisherId}")
    @Operation(summary = "Create a new magazine", description = "Adds a new magazine under a specific publisher")
    public ResponseEntity<Magazine> createMagazine(
            @PathVariable Integer publisherId,
            @RequestBody Magazine magazine) {
        Magazine createdMagazine = magazineService.saveMagazine(publisherId, magazine);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMagazine);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a magazine", description = "Removes a magazine from the system")
    public ResponseEntity<Void> deleteMagazine(@PathVariable Integer id) {
        if (magazineService.deleteMagazine(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // ✅ Get magazines by publisher ID
    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<Magazine>> getMagazinesByPublisher(@PathVariable Integer publisherId) {
        return ResponseEntity.ok(magazineService.getMagazinesByPublisher(publisherId));
    }

    // ✅ Get magazines by genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Magazine>> getMagazinesByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(magazineService.getMagazinesByGenre(genre));
    }

    // ✅ Get magazines released after a certain year
    @GetMapping("/releasedAfter/{year}")
    public ResponseEntity<List<Magazine>> getMagazinesReleasedAfter(@PathVariable Integer year) {
        return ResponseEntity.ok(magazineService.getMagazinesReleasedAfter(year));
    }

    // ✅ Get magazines within a price range
    @GetMapping("/priceRange")
    public ResponseEntity<List<Magazine>> getMagazinesByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(magazineService.getMagazinesByPriceRange(minPrice, maxPrice));
    }
}
