package tn.esprit.PIDEV.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.PIDEV.entities.Offre;

public interface OffreRepository extends JpaRepository<Offre, Long> {
}
