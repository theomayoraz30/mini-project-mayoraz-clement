package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "account")
public abstract class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	private String passwordHash;
	private String email;
	private String displayName;
	private LocalDateTime createdAt;

	//Constructeur vide (required pour le JPA)
	public Account(){}

	public Account(String username, String passwordHash, String email, String displayName){
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;
		this.displayName = displayName;
		this.createdAt = LocalDateTime.now();
	}

	//Getters et les setters
	public Long getId(){ return id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public LocalDateTime getCreatedAt() { return createdAt; }
}

