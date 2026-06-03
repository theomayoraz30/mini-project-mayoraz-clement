package ch.hevs.businessobject;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seasonNumber;
    private String title;

    @Column(length = 2000)
    private String description;

    //un saison appartient a une serie uniquement
    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Episode> episodes = new ArrayList<>();
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <SeasonProgress> seasonProgressList = new ArrayList<>();

    public Season(){}

    public Season(Integer seasonNumber, String title, String description, Series series){
        this.seasonNumber = seasonNumber;
        this.title = title;
        this.description = description;
        this.series = series;
    }

    //Getters
    public Long getId() { return id; }
    public Integer getSeasonNumber() { return seasonNumber; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Series getSeries() { return series; }
    public List<Episode> getEpisodes() { return episodes; }
    public List<SeasonProgress> getSeasonProgressList() { return seasonProgressList; }

    //Setters
    public void setSeasonNumber(Integer seasonNumber) { this.seasonNumber = seasonNumber; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setSeries(Series series) { this.series = series; }
}
