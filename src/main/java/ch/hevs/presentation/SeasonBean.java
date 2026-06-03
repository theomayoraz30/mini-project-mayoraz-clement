package ch.hevs.presentation;

import ch.hevs.businessobject.Episode;
import ch.hevs.businessobject.Season;
import ch.hevs.businessobject.Series;
import ch.hevs.service.SeriesService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class SeasonBean implements Serializable {

    @Inject
    private SeriesService seriesService;

    private Season newSeason = new Season();
    private Long selectedSeriesId;

    private Episode newEpisode = new Episode();
    private Long selectedSeasonId;

    public String addSeason() {
        Series series = seriesService.findById(selectedSeriesId);
        if (series == null) return null;
        newSeason.setSeries(series);
        seriesService.saveSeason(newSeason);
        return "admin?faces-redirect=true";
    }

    public String addEpisode() {
        Season season = seriesService.findSeasonById(selectedSeasonId);
        if (season == null) return null;
        newEpisode.setSeason(season);
        seriesService.saveEpisode(newEpisode);
        return "admin?faces-redirect=true";
    }

    public List<Series> getSeriesList() {
        return seriesService.findAll();
    }

    public List<Season> getAllSeasons() {
        return seriesService.findAllSeasons();
    }

    public Season getNewSeason() { return newSeason; }
    public void setNewSeason(Season newSeason) { this.newSeason = newSeason; }
    public Long getSelectedSeriesId() { return selectedSeriesId; }
    public void setSelectedSeriesId(Long selectedSeriesId) { this.selectedSeriesId = selectedSeriesId; }

    public Episode getNewEpisode() { return newEpisode; }
    public void setNewEpisode(Episode newEpisode) { this.newEpisode = newEpisode; }
    public Long getSelectedSeasonId() { return selectedSeasonId; }
    public void setSelectedSeasonId(Long selectedSeasonId) { this.selectedSeasonId = selectedSeasonId; }
}
