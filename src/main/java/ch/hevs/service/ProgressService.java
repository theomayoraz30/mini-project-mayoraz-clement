package ch.hevs.service;

import ch.hevs.businessobject.*;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named
@SessionScoped
public class ProgressService implements Serializable {

    @PersistenceContext(unitName = "seriesPU")
    private EntityManager em;

    @Transactional
    public void markEpisodeWatched(Viewer viewer, Episode episode) {
        EpisodeProgress ep = findOrCreateEpisodeProgress(viewer, episode);
        ep.setStatus(WatchStatus.WATCHED);
        ep.setWatchedAt(LocalDateTime.now());
        em.merge(ep);

        Series series = episode.getSeason().getSeries();
        SeriesProgress sp = findOrCreateSeriesProgress(viewer, series);
        if (sp.getStatus() == ProgressStatus.NOT_STARTED) {
            sp.setStatus(ProgressStatus.IN_PROGRESS);
            em.merge(sp);
        }
    }

    @Transactional
    public EpisodeProgress findOrCreateEpisodeProgress(Viewer viewer, Episode episode) {
        List<EpisodeProgress> results = em.createQuery(
                        "SELECT ep FROM EpisodeProgress ep WHERE ep.viewer = :v AND ep.episode = :e",
                        EpisodeProgress.class)
                .setParameter("v", viewer)
                .setParameter("e", episode)
                .getResultList();

        if (!results.isEmpty()) return results.get(0);
        EpisodeProgress ep = new EpisodeProgress(viewer, episode);
        em.persist(ep);
        return ep;
    }

    @Transactional
    public SeriesProgress findOrCreateSeriesProgress(Viewer viewer, Series series) {
        List<SeriesProgress> results = em.createQuery(
                        "SELECT sp FROM SeriesProgress sp WHERE sp.viewer = :v AND sp.series = :s",
                        SeriesProgress.class)
                .setParameter("v", viewer)
                .setParameter("s", series)
                .getResultList();

        if (!results.isEmpty()) return results.get(0);
        SeriesProgress sp = new SeriesProgress(viewer, series);
        em.persist(sp);
        return sp;
    }

    public List<SeriesProgress> getProgressForViewer(Viewer viewer) {
        return em.createQuery(
                        "SELECT sp FROM SeriesProgress sp WHERE sp.viewer = :v",
                        SeriesProgress.class)
                .setParameter("v", viewer)
                .getResultList();
    }
}