package com.project.book_inventory_api.Repository;

import com.project.book_inventory_api.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
}
