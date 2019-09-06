package edu.grsu.karina.library.repository;

import edu.grsu.karina.library.model.IssuanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuanceRequestRepository extends JpaRepository<IssuanceRequest, Integer> {


        List<IssuanceRequest> findByName(String name);

}
