package tn.esprit.PIDEV.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.PIDEV.entities.Offre;
import tn.esprit.PIDEV.entities.Session;
import tn.esprit.PIDEV.entities.User;
import tn.esprit.PIDEV.repositories.OffreRepository;
import tn.esprit.PIDEV.repositories.SessionRepository;
import tn.esprit.PIDEV.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OffreServiceImp implements IOffreService{
    private OffreRepository offreRepository;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    @Override
    public Offre addOffre(Offre o) {
        return offreRepository.save(o);
    }

    @Override
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    @Override
    public Offre getOffreById(long idOffre) {return offreRepository.findById(idOffre).orElse(null);}

    @Override
    public void deleteOffre(long idOffre) {offreRepository.deleteById(idOffre);}

    @Override
    public Offre updateOffre(Offre o) {return offreRepository.save(o);}

    @Override
    public Offre addOffreAndAssignToUserAndToSession(Long idUser, Offre offre, Long idSession) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + idUser));
               Session session = sessionRepository.findById(idSession)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + idSession));
        offre.setUser(user);
        offre.setSessions(session);
        return offreRepository.save(offre);
    }
    public List<Offre> getOffresByUserId(Long idUser) {
        return  offreRepository.findByUserId(idUser);
    }
}
