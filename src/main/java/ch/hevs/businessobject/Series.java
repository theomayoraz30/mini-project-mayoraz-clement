package ch.hevs.businessobject;
import jakarta.ejb.Local;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer tvdbId;
    @Column(nullable = false)
    private String title;
    @Column(length = 2000)
    private String description;

    private Integer releaseYear;
    private String posterUrl;
    private String posterThumbailUrl;
    private LocalDateTime apiImportedAt;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons = new ArrayList<>();
    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeriesProgress> seriesProgressList = new ArrayList<>();

    public Series() {}

    public Series(String title, String description, Integer releaseYear){
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.apiImportedAt = LocalDateTime.now();
    }

    //Getters
    public Long getId() { return id; }
    public Integer getTvdbId() { return tvdbId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getReleaseYear() { return releaseYear; }
    public String getPosterUrl() { return posterUrl; }
    public String getPosterThumbnailUrl() { return posterThumbailUrl; }
    public LocalDateTime getApiImportedAt() { return apiImportedAt; }
    public List<Season> getSeasons() { return seasons; }
    public List<Review> getReviews() { return reviews; }
    public List<SeriesProgress> getSeriesProgressList() { return seriesProgressList; }

    //Setters
    public void setTvdbId(Integer tvdbId) { this.tvdbId = tvdbId; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public void setPosterThumbnailUrl(String url) { this.posterThumbailUrl = url; }
}
