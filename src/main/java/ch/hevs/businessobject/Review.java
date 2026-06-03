package ch.hevs.businessobject;
import jakarta.persistence.*;

import javax.swing.text.View;
import java.time.LocalDateTime;

// Contrainte en DB qui interdit deux reviews du même Viewer sur la même série.
// Pointe vers viewer uniquement et pas account car seulement les viewer peuvent review et pas les admin
@Entity
@Table(name = "review", uniqueConstraints = @UniqueConstraint(columnNames = {"viewer_id", "series_id"}))
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer rating;

    @Column(length = 2000)
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime uptadedAt;

    @ManyToOne
    @JoinColumn(name = "viewer_id", nullable = false)
    private Viewer viewer;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    public Review(){}

    public Review(Integer rating, String comment, Viewer viewer, Series series){
        this.rating = rating;
        this.comment = comment;
        this.viewer = viewer;
        this.series = series;
        this.createdAt = LocalDateTime.now();
        this.uptadedAt = LocalDateTime.now();
    }

   //Getters
    public Long getId() { return id; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUptadedAt() { return uptadedAt; }
    public Viewer getViewer() { return viewer; }
    public Series getSeries() { return series; }

    // Setters
    public void setRating(Integer rating) {
        this.rating = rating;
        this.uptadedAt = LocalDateTime.now();
    }
    public void setComment(String comment) {
        this.comment = comment;
        this.uptadedAt = LocalDateTime.now();
    }
}
