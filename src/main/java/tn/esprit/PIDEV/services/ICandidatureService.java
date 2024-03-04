package tn.esprit.PIDEV.services;

import tn.esprit.PIDEV.entities.Candidature;
import tn.esprit.PIDEV.entities.Status;

import java.util.List;

public interface ICandidatureService {
    public Candidature addCandidature(Candidature candidature);
    public Candidature updateCandidature(Candidature candidature);
    public List<Candidature> getAllCandidature();
    public void deleteCandidature(long idCandidature);
    public Candidature  addCandidatureAndAssignToOfferAndUser (Candidature candidature , Long idOffre, Long idUser);
    public List<Candidature> getCandidaturesByUserId(Long idUser);
    public Status getStatusByCandidatureId(Long candidatureId);

}