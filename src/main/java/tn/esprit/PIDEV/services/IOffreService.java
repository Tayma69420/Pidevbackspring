package tn.esprit.PIDEV.services;

import tn.esprit.PIDEV.entities.Offre;

import java.util.List;

public interface IOffreService {
    public Offre addOffre(Offre o);
    public List<Offre> getAllOffres();
    public Offre getOffreById(long idOffre);
    public void deleteOffre(long idOffre);
    public Offre updateOffre(Offre o);
    public Offre addOffreAndAssignOffreToUser (long idUser , Offre offre);

}

