package edu.grsu.karina.library.repository;

import edu.grsu.karina.library.model.Role;
import edu.grsu.karina.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByRoles(Set<Role> roles);

}
