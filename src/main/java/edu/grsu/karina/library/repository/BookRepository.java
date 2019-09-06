package edu.grsu.karina.library.repository;

import edu.grsu.karina.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
List<Book> findByTitle(String title);
}
