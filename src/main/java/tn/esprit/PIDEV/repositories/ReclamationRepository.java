package tn.esprit.PIDEV.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.PIDEV.entities.Reclamation;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    @Query("SELECT r FROM Reclamation r JOIN FETCH r.user")
    List<Reclamation> findAllReclamationsWithUser();
}
