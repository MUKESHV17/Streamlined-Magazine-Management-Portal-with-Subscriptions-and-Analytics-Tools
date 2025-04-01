package com.examly.springapp.repository;

import com.examly.springapp.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {

    // ✅ Get magazines by publisher ID
    @Query("SELECT m FROM Magazine m WHERE m.publisher.id = :publisherId")
    List<Magazine> findByPublisherId(@Param("publisherId") Integer publisherId);

    // ✅ Get magazines by genre
    @Query("SELECT m FROM Magazine m WHERE m.genre = :genre")
    List<Magazine> findByGenre(@Param("genre") String genre);

    // ✅ Get magazines released after a specific year
    @Query("SELECT m FROM Magazine m WHERE m.releaseYear > :year")
    List<Magazine> findByReleaseYearAfter(@Param("year") Integer year);

    // ✅ Get magazines within a price range
    @Query("SELECT m FROM Magazine m WHERE m.price BETWEEN :minPrice AND :maxPrice")
    List<Magazine> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
}
