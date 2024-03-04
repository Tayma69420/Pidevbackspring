package tn.esprit.PIDEV.services;


import tn.esprit.PIDEV.entities.Offre;
import tn.esprit.PIDEV.entities.Reclamation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IReclamationService {
    Reclamation addReclamation(Reclamation r) ;
    public List<Reclamation> getAllReclamations();


    public Reclamation getReclamationById(long idReclamation);
    public void deleteReclamation(long idReclamation);
    public Reclamation updateReclamation(Reclamation r);

}