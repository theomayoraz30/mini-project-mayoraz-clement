package ch.hevs.businessobject;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "viewer")
public class Viewer extends Account{
    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeriesProgress> seriesProgressList = new ArrayList<>();
    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeasonProgress> seasonProgressList = new ArrayList<>();

    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EpisodeProgress> episodeProgressList = new ArrayList<>();

    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Viewer(){}

    //"super" permet de prendre les attibuts de la classe parent
    public Viewer(String username, String passwordHash, String email, String displayName) {
        super(username, passwordHash, email, displayName);
    }

    public List<SeriesProgress> getSeriesProgressList() { return seriesProgressList; }
    public List<SeasonProgress> getSeasonProgressList() { return seasonProgressList; }
    public List<EpisodeProgress> getEpisodeProgressList() { return episodeProgressList; }
    public List<Review> getReviews() { return reviews; }


}
