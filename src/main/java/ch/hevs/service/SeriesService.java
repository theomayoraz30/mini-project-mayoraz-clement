package ch.hevs.service;

import ch.hevs.businessobject.*;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class SeriesService implements Serializable {

    @PersistenceContext(unitName = "seriesPU")
    private EntityManager em;

    @Transactional
    public List<Series> findAll() {
        return em.createQuery("SELECT s FROM Series s ORDER BY s.title", Series.class)
                .getResultList();
    }

    @Transactional
    public List<Series> findByTitle(String title) {
        return em.createQuery(
                        "SELECT s FROM Series s WHERE LOWER(s.title) LIKE :title ORDER BY s.title",
                        Series.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }

    @Transactional
    public Series findById(Long id) {
        Series series = em.find(Series.class, id);
        if (series == null) return null;
        series.getSeasons().size();
        for (Season season : series.getSeasons()) {
            season.getEpisodes().size();
        }
        series.getReviews().size();
        return series;
    }

    public List<Series> findByYear(Integer year) {
        return em.createQuery(
                        "SELECT s FROM Series s WHERE s.releaseYear = :year", Series.class)
                .setParameter("year", year)
                .getResultList();
    }

    @Transactional
    public void save(Series series) {
        em.persist(series);
    }

    @Transactional
    public void update(Series series) {
        em.merge(series);
    }

    @Transactional
    public void delete(Long id) {
        Series s = em.find(Series.class, id);
        if (s != null) em.remove(s);
    }

    @Transactional
    public void saveSeason(Season season) {
        em.persist(season);
    }

    @Transactional
    public Season findSeasonById(Long id) {
        return em.find(Season.class, id);
    }

    public List<Season> findAllSeasons() {
        return em.createQuery("SELECT s FROM Season s ORDER BY s.series.title, s.seasonNumber", Season.class)
                .getResultList();
    }

    @Transactional
    public void saveEpisode(Episode episode) {
        em.persist(episode);
    }
}