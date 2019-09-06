package edu.grsu.karina.library.repository;

import edu.grsu.karina.library.model.Author;
import edu.grsu.karina.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
