package tn.esprit.PIDEV.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.PIDEV.controllers.ReclamationController;
import tn.esprit.PIDEV.entities.Candidature;
import tn.esprit.PIDEV.entities.Offre;
import tn.esprit.PIDEV.entities.Status;
import tn.esprit.PIDEV.entities.User;
import tn.esprit.PIDEV.repositories.CandidatureRepository;
import tn.esprit.PIDEV.repositories.OffreRepository;
import tn.esprit.PIDEV.repositories.UserRepository;
import tn.esprit.PIDEV.services.ICandidatureService;

import java.util.List;


@Service
@AllArgsConstructor
public class CandidatureServiceImp implements ICandidatureService {
    CandidatureRepository candidatureRepository;
    OffreRepository offreRepository;
    UserRepository userRepository;
    @Override
    public Candidature addCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public Candidature updateCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public List<Candidature> getAllCandidature() {
        return candidatureRepository.findAll();
    }

    @Override
    public void deleteCandidature(long idCandidature) {
        candidatureRepository.deleteById(idCandidature);

    }
    public Candidature addCandidatureAndAssignToOfferAndUser (Candidature candidature , Long idOffre, Long idUser) {
        Offre offre = offreRepository.findById(idOffre).orElse(null);
        User user = userRepository.findById(idUser).orElse(null);
        if (offre != null || user!=null) {
            candidature.setOffre(offre);
            offre.getCandidaturess().add(candidature);
            offreRepository.save(offre);
            candidature.setUser(user);
            user.getCandidatures().add(candidature);
            userRepository.save(user);
            return candidatureRepository.save(candidature);
        } else {
            return null;
        }
    }


    public List<Candidature> getCandidaturesByUserId(Long idUser) {
        return  candidatureRepository.findByUserId(idUser);
    }

    public Status getStatusByCandidatureId(Long candidatureId) {
        return candidatureRepository.findById(candidatureId)
                .map(Candidature::getStatus)
                .orElse(null);
    }



}


