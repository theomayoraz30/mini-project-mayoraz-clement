package ch.hevs.businessobject;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "series_progress")
public class SeriesProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "viewer", nullable = false)
    private Viewer viewer;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    public SeriesProgress(){}

    public SeriesProgress(Viewer viewer, Series series){
        this.viewer = viewer;
        this.series = series;
        this.status = ProgressStatus.NOT_STARTED;
        this.updatedAt = LocalDateTime.now();
    }

    //Getter

    public Long getId() { return id; }
    public ProgressStatus getStatus() { return status; }
    public LocalDateTime getUpdatedAt(){ return updatedAt; }
    public Viewer getViewer() {return viewer;}
    public Series getSeries() {return series;}

    //Setter
    public void setStatus(ProgressStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }


}
