package com.examly.springapp.repository;

import com.examly.springapp.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

    // ✅ Find Publisher by Name (Case Insensitive)
    @Query("SELECT p FROM Publisher p WHERE LOWER(p.name) = LOWER(:name)")
    List<Publisher> findByName(@Param("name") String name);

    // ✅ Search Publishers by Name Containing Keyword
    @Query("SELECT p FROM Publisher p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Publisher> searchByName(@Param("keyword") String keyword);

    // ✅ Count Total Number of Publishers
    @Query("SELECT COUNT(p) FROM Publisher p")
    Long countPublishers();

    // ✅ Find Publishers with Email Ending in Specific Domain
    @Query("SELECT p FROM Publisher p WHERE p.email LIKE %:domain")
    List<Publisher> findByEmailDomain(@Param("domain") String domain);

    // ✅ Find Publishers Who Have Magazines
    @Query("SELECT p FROM Publisher p WHERE SIZE(p.magazines) > 0")
    List<Publisher> findPublishersWithMagazines();

    // ✅ Get Publishers with Magazine Count
    @Query("SELECT p.name, COUNT(m) FROM Publisher p LEFT JOIN p.magazines m GROUP BY p.name")
    List<Object[]> getPublisherMagazineCount();

    // ✅ Find Publishers Who Have More Than X Magazines
    @Query("SELECT p FROM Publisher p WHERE SIZE(p.magazines) > :minMagazines")
    List<Publisher> findPublishersWithMinMagazines(@Param("minMagazines") int minMagazines);

    // ✅ Update Email by Name
    @Modifying
    @Transactional
    @Query("UPDATE Publisher p SET p.email = :email WHERE p.name = :name")
    void updateEmailByName(@Param("email") String email, @Param("name") String name);

    // ✅ Delete Publisher by Name
    @Modifying
    @Transactional
    @Query("DELETE FROM Publisher p WHERE p.name = :name")
    void deleteByName(@Param("name") String name);
}
