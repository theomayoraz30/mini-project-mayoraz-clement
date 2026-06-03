package ch.hevs.businessobject;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer episodeNumber;
    private String title;

    @Column(length = 2000)
    private String description;

    private Integer durationMinutes;
    private LocalDate airDate;

    //un episode appartient a une saison uniquement
    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EpisodeProgress> episodeProgressList = new ArrayList<>();

    public Episode(){}

    public Episode(Integer episodeNumber, String title, Season season){
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.season = season;
    }

    //Getters
    public Long getId() { return id; }
    public Integer getEpisodeNumber() { return episodeNumber; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public LocalDate getAirDate() { return airDate; }
    public Season getSeason() { return season; } 
    public List<EpisodeProgress> getEpisodeProgressList() { return episodeProgressList; }
    
    //Setters
    public void setEpisodeNumber(Integer episodeNumber) { this.episodeNumber = episodeNumber; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setAirDate(LocalDate airDate) { this.airDate = airDate; }
    public void setSeason(Season season) { this.season = season; }
}
