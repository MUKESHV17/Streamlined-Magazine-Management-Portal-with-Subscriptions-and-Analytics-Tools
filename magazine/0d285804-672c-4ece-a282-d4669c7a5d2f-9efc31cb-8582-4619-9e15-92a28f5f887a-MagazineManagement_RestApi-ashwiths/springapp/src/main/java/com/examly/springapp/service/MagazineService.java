package com.examly.springapp.service;

import com.examly.springapp.entity.Magazine;
import com.examly.springapp.entity.Publisher;
import com.examly.springapp.repository.MagazineRepository;
import com.examly.springapp.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MagazineService {

    private final MagazineRepository magazineRepository;
    private final PublisherRepository publisherRepository;

    // ✅ Get all magazines with sorting
    public List<Magazine> getAllMagazines(String sortBy, String order) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return magazineRepository.findAll(Sort.by(direction, sortBy));
    }

    // ✅ Get a magazine by ID
    public Optional<Magazine> getMagazineById(Integer id) {
        return magazineRepository.findById(id);
    }

    // ✅ Save a magazine with a publisher
    public Magazine saveMagazine(Integer publisherId, Magazine magazine) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        magazine.setPublisher(publisher);
        return magazineRepository.save(magazine);
    }

    // ✅ Update an existing magazine
    public Optional<Magazine> updateMagazine(Integer id, Magazine magazineDetails) {
        return magazineRepository.findById(id).map(existingMagazine -> {
            existingMagazine.setTitle(magazineDetails.getTitle());
            existingMagazine.setGenre(magazineDetails.getGenre());
            existingMagazine.setIssueNumber(magazineDetails.getIssueNumber());
            existingMagazine.setReleaseYear(magazineDetails.getReleaseYear());
            existingMagazine.setPrice(magazineDetails.getPrice());

            if (magazineDetails.getPublisher() != null) {
                Publisher publisher = publisherRepository.findById(magazineDetails.getPublisher().getId())
                        .orElseThrow(() -> new RuntimeException("Publisher not found"));
                existingMagazine.setPublisher(publisher);
            }

            return magazineRepository.save(existingMagazine);
        });
    }

    // ✅ Delete a magazine by ID
    public boolean deleteMagazine(Integer id) {
        if (magazineRepository.existsById(id)) {
            magazineRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ✅ Get magazines by publisher ID using JPQL
    public List<Magazine> getMagazinesByPublisher(Integer publisherId) {
        return magazineRepository.findByPublisherId(publisherId);
    }

    // ✅ Get magazines by genre using JPQL
    public List<Magazine> getMagazinesByGenre(String genre) {
        return magazineRepository.findByGenre(genre);
    }

    // ✅ Get magazines released after a certain year using JPQL
    public List<Magazine> getMagazinesReleasedAfter(Integer year) {
        return magazineRepository.findByReleaseYearAfter(year);
    }

    // ✅ Get magazines within a price range using JPQL
    public List<Magazine> getMagazinesByPriceRange(Double minPrice, Double maxPrice) {
        return magazineRepository.findByPriceRange(minPrice, maxPrice);
    }
}
