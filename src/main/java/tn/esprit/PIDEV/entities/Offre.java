package tn.esprit.PIDEV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Offre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOffre ;

    private String intitule;

    private String description;

    private LocalDateTime dateDeCreation;

    private  int nbPlaces;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offre")
    private Set<Candidature> candidaturess;

    @JsonIgnore
    @ManyToOne
    private User user;
}
