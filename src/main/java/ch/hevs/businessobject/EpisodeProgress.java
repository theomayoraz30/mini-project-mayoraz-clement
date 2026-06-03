package ch.hevs.businessobject;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "episode_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"viewer_id", "episode_id"}))
public class EpisodeProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WatchStatus status;

    private LocalDateTime watchedAt;

    @ManyToOne
    @JoinColumn(name = "viewer_id", nullable = false)
    private Viewer viewer;

    @ManyToOne
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    public EpisodeProgress(){}

    public EpisodeProgress(Viewer viewer, Episode episode){
        this.viewer = viewer;
        this.episode = episode;
        this.status = WatchStatus.NOT_WATCHED;
    }

    //Getters
    public Long getId() { return id; }
    public WatchStatus getStatus() { return status; }
    public LocalDateTime getWatchedAt() { return watchedAt; }
    public Viewer getViewer() { return viewer; }
    public Episode getEpisode() { return episode; }

    //Setters
    public void setStatus(WatchStatus status) { this.status = status; }
    public void setWatchedAt(LocalDateTime watchedAt) { this.watchedAt = watchedAt; }
}
