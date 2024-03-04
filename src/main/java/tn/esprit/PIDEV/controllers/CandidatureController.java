package tn.esprit.PIDEV.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.PIDEV.entities.Candidature;
import tn.esprit.PIDEV.entities.Offre;
import tn.esprit.PIDEV.entities.Status;
import tn.esprit.PIDEV.services.ICandidatureService;

import java.util.List;

@AllArgsConstructor

@RestController
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/auth")
public class CandidatureController {
    ICandidatureService iCandidatureService;

    @GetMapping("/getAllC")
    public List<Candidature> getAllCandidature() {
        return iCandidatureService.getAllCandidature();
    }

    @PostMapping(value = "/addCandidature")
    public Candidature addCandidature(@RequestBody Candidature candidature) {
        return iCandidatureService.addCandidature(candidature);
    }

    @DeleteMapping("/supprimerCandidature/{idCandidature}")
    public void deleteCandidature(@PathVariable long idCandidature) {
        iCandidatureService.deleteCandidature(idCandidature);
    }

    @PostMapping(value = "/addCandidatureAndAssignToOffer/{idOffre}")

    public Candidature addCandidatureAndAssignToOffer(@PathVariable Long idUser, @PathVariable Long idOffre, @RequestBody Candidature candidature) {
        return iCandidatureService.addCandidatureAndAssignToOfferAndUser (candidature , idOffre, idUser );
    }


    @GetMapping(value = "/getCandidaturesByUserId/{idUser}")

    public List<Candidature> getCandidaturesByUser(@PathVariable Long idUser) {
        return iCandidatureService.getCandidaturesByUserId(idUser);
    }

    @PutMapping("/modifierCandidature")
    public Candidature updateCandidature(@RequestBody Candidature candidature ) {
        return iCandidatureService.updateCandidature(candidature);
    }

    @GetMapping(value="/getStatusByCandidId/{candidatureId}")

    public Status getStatusByCandidatureId (@PathVariable Long candidatureId){
        return  iCandidatureService.getStatusByCandidatureId(candidatureId);
    }
}


