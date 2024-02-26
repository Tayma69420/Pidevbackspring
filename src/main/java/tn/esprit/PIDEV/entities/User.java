package tn.esprit.PIDEV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String tel;
    private String image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Candidature> candidatures;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Offre> offres;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Reclamation> reclamations;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Session> sessions;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Messagerie> messageries;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    public User(String username, String email, String password,String tel,String image) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.tel=tel;
        this.image=image;
    }


}
