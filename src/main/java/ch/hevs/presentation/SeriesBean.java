package ch.hevs.presentation;

import ch.hevs.businessobject.Series;
import ch.hevs.service.SeriesService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class SeriesBean implements Serializable {

    @Inject
    private SeriesService seriesService;

    private List<Series> seriesList;
    private Long selectedSeriesId;
    private String searchQuery = "";
    private Series newSeries = new Series();

    public void loadAll() {
        seriesList = seriesService.findAll();
    }

    public String search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            seriesList = seriesService.findAll();
        } else {
            seriesList = seriesService.findByTitle(searchQuery.trim());
        }
        return null;
    }

    public String selectSeries(Series series) {
        this.selectedSeriesId = series.getId();
        return "seriesDetail?faces-redirect=true";
    }

    public String addSeries() {
        seriesService.save(newSeries);
        newSeries = new Series();
        seriesList = seriesService.findAll();
        return "admin?faces-redirect=true";
    }

    public String deleteSeries(Series series) {
        seriesService.delete(series.getId());
        seriesList = seriesService.findAll();
        return null;
    }

    public List<Series> getSeriesList() {
        if (seriesList == null) loadAll();
        return seriesList;
    }

    public Series getSelectedSeries() {
        if (selectedSeriesId == null) return null;
        return seriesService.findById(selectedSeriesId);
    }

    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public Series getNewSeries() { return newSeries; }
    public void setNewSeries(Series s) { this.newSeries = s; }
}