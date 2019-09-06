package edu.grsu.karina.library.repository;


import edu.grsu.karina.library.model.LiteratureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiteratureTypeRepository extends JpaRepository<LiteratureType, Integer> {

}
