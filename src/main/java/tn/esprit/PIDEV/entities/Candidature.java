package tn.esprit.PIDEV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Candidature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCondidature ;

    @Lob
    private byte[] lettreDeMotivation;

    @Lob
    private byte [] cv;

    private LocalDateTime dateDeCandidature;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Offre offre;
}
