package com.examly.springapp.service;

import com.examly.springapp.entity.Publisher;
import com.examly.springapp.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherService {

    private final PublisherRepository publisherRepository;

    // ✅ Get all publishers with sorting
    public List<Publisher> getAllPublishers(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return publisherRepository.findAll(sort);
    }

    // ✅ Get a publisher by ID
    public Optional<Publisher> getPublisherById(Integer id) {
        return publisherRepository.findById(id);
    }

    // ✅ Save a new publisher
    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    // ✅ Update an existing publisher
    public Publisher updatePublisher(Integer id, Publisher updatedPublisher) {
        return publisherRepository.findById(id).map(existingPublisher -> {
            existingPublisher.setName(updatedPublisher.getName());
            existingPublisher.setEmail(updatedPublisher.getEmail());
            existingPublisher.setContactNumber(updatedPublisher.getContactNumber());
            existingPublisher.setAddress(updatedPublisher.getAddress());
            return publisherRepository.save(existingPublisher);
        }).orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
    }

    // ✅ Delete a publisher by ID
    public void deletePublisher(Integer id) {
        if (publisherRepository.existsById(id)) {
            publisherRepository.deleteById(id);
        } else {
            throw new RuntimeException("Publisher not found with id: " + id);
        }
    }

    // ✅ Custom JPQL Queries Handled in Service Layer
    public List<Publisher> findByName(String name) {
        return publisherRepository.findByName(name);
    }

    public List<Publisher> searchByName(String keyword) {
        return publisherRepository.searchByName(keyword);
    }

    public Long countPublishers() {
        return publisherRepository.countPublishers();
    }

    public List<Publisher> findByEmailDomain(String domain) {
        return publisherRepository.findByEmailDomain(domain);
    }

    public List<Publisher> findPublishersWithMagazines() {
        return publisherRepository.findPublishersWithMagazines();
    }

    public List<Object[]> getPublisherMagazineCount() {
        return publisherRepository.getPublisherMagazineCount();
    }

    public List<Publisher> findPublishersWithMinMagazines(int minMagazines) {
        return publisherRepository.findPublishersWithMinMagazines(minMagazines);
    }

    public void updateEmailByName(String email, String name) {
        publisherRepository.updateEmailByName(email, name);
    }

    public void deleteByName(String name) {
        publisherRepository.deleteByName(name);
    }
}
