package tn.esprit.PIDEV.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.PIDEV.entities.Offre;
import tn.esprit.PIDEV.services.IOffreService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class OffreController {

    private IOffreService iOffreService;

    @PostMapping("/addOffre")
    public Offre addOffre(@RequestBody Offre o){
        return iOffreService.addOffre(o);
    }

    @GetMapping("/getAllO")
    public List<Offre> getAllOffre(){
        return iOffreService.getAllOffres();
    }

    @PutMapping("/modifierOffre")
    public Offre updateOffre(@RequestBody Offre o){return iOffreService.updateOffre(o);}

    @GetMapping("/getOffre/{idOffre}")
    public Offre findById(@PathVariable long idOffre){
        return iOffreService.getOffreById(idOffre);
    }

    @DeleteMapping("/supprimerOffre/{idOffre}")
    public void deleteOffre(@PathVariable long idOffre){
        iOffreService.deleteOffre(idOffre);
    }

}
