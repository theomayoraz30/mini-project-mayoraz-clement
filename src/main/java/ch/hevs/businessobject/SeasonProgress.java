package ch.hevs.businessobject;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "season_progress")
public class SeasonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "viewer_id", nullable = false)
    private Viewer viewer;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    public SeasonProgress(){}

    public SeasonProgress(Viewer viewer, Season season){
        this.viewer = viewer;
        this.season = season;
        this.status = ProgressStatus.NOT_STARTED;
        this.updatedAt = LocalDateTime.now();
    }
    //Getters
    public Long getId() { return id; }
    public ProgressStatus getStatus() { return status; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Viewer getViewer() { return viewer; }
    public Season getSeason() { return season; }

    //Setters
    public void setStatus(ProgressStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
