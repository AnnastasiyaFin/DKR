package com.example.dkrproject.controller;

import com.example.dkrproject.dto.PublisherDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Publisher;
import com.example.dkrproject.service.PublisherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/publisher")
@Tag(name="Издательства", description="Получение данных о книжных издательствах")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getAllPublishers() {
        return new ResponseEntity<>(publisherService.getPublisherList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> addPublisher(@RequestBody PublisherDTO publisher) {
        return new ResponseEntity<>(publisherService.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> updatePublisher(@PathVariable("id") long id, @RequestBody PublisherDTO publisher) throws ResourceNotFoundException {
        PublisherDTO publisherUpd = publisherService.updatePublisher(id, publisher);
        return ResponseEntity.ok().body(publisherUpd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePublisher(@PathVariable("id") long id) {
        publisherService.deletePublisher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
