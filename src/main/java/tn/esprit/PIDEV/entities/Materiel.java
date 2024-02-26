package tn.esprit.PIDEV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Materiel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMateriel ;

    private String intitule;

    private int nbPieces;

    @JsonIgnore
    @ManyToOne
    private Session sessions;
}
