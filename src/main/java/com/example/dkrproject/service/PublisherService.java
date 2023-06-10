package com.example.dkrproject.service;

import com.example.dkrproject.dto.PublisherDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Location;
import com.example.dkrproject.model.Publisher;
import com.example.dkrproject.repository.PublisherRepository;
import com.example.dkrproject.transformer.PublisherTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PublisherTransformer publisherTransformer;

    public List<PublisherDTO> getPublisherList() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream().map(publisherTransformer::transformPublisherToResp).toList();
    }

    public Publisher findPublisherByName(String name) {
        return publisherRepository.findByName(name).stream().findFirst().orElse(null);
    }

    @Transactional(rollbackFor = Throwable.class)
    public PublisherDTO save(PublisherDTO publisherDTO) {
        Location location = locationService.getLocation(publisherDTO.getLocation());
        if (location == null) {
            location = locationService.save(new Location(publisherDTO.getLocation()));
        }
        Publisher publisher = Publisher.builder()
                .name(publisherDTO.getName())
                .email(publisherDTO.getEmail())
                .phone(publisherDTO.getPhone())
                .location(location)
                .build();
        publisher = publisherRepository.save(publisher);

        return publisherTransformer.transformPublisherWithoutBooks(publisher);
    }

    public Publisher findById(Long depId) throws ResourceNotFoundException  {
        return publisherRepository.findById(depId)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found for this id :: " + depId));
    }

    public String savePublisher(PublisherDTO departmentRequest) {
        Publisher dep = new Publisher();
        dep.setName(departmentRequest.getName());
        publisherRepository.save(dep);
        return "OK";
    }

    public PublisherDTO updatePublisher(long id, PublisherDTO departmentRequest) throws ResourceNotFoundException {
        Publisher departmentUpdated = findById(id);
        departmentUpdated.setName(departmentRequest.getName());
        return publisherTransformer.transformPublisherWithoutBooks(publisherRepository.save(departmentUpdated));
    }

    public void deletePublisher(long id) {
        publisherRepository.deleteById(id);
    }
}
