package tn.esprit.PIDEV.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.PIDEV.entities.Offre;
import tn.esprit.PIDEV.entities.User;
import tn.esprit.PIDEV.repositories.OffreRepository;
import tn.esprit.PIDEV.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OffreServiceImp implements IOffreService{
    private OffreRepository offreRepository;
    private UserRepository userRepository;

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
    public Offre addOffreAndAssignOffreToUser(long idUser, Offre offre) {
        User user = userRepository.findById(idUser).orElse(null);
        if (user != null) {
            offre.setUser(user);
            userRepository.save(user);
            return offreRepository.save(offre); // Save Offre directly through offreRepository
        } else {
            return null;
        }
    }



}
