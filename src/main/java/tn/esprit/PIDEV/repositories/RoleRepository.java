package tn.esprit.PIDEV.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.PIDEV.entities.ERole;
import tn.esprit.PIDEV.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
