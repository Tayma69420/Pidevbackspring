package tn.esprit.PIDEV.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.PIDEV.entities.Reclamation;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
}
