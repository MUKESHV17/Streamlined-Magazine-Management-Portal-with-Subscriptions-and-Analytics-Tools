package com.examly.springapp.controller;

import com.examly.springapp.entity.Publisher;
import com.examly.springapp.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
@Tag(name = "Publisher Controller", description = "Endpoints for managing publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    @Operation(summary = "Get all publishers", description = "Returns a list of all publishers with sorting")
    public List<Publisher> getAllPublishers(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return publisherService.getAllPublishers(sortBy, direction);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a publisher by ID")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Integer id) {
        return publisherService.getPublisherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new publisher")
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.savePublisher(publisher));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a publisher")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Integer id, @RequestBody Publisher publisher) {
        return ResponseEntity.ok(publisherService.updatePublisher(id, publisher));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a publisher")
    public ResponseEntity<Void> deletePublisher(@PathVariable Integer id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
