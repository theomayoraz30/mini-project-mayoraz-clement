package ch.hevs.businessobject;
import jakarta.persistence.*;

@Entity
@Table(name = "administator")
public class Administrator extends Account {

    public Administrator(){}

    //Aucun ajout supp., la classe admin n'a pas de champs en plus par rapport a account
    public Administrator(String username, String passwordHash, String email, String displayName){
        super(username, passwordHash, email, displayName);
    }
}
