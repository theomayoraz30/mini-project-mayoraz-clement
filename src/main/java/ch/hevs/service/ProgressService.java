package ch.hevs.service;

import ch.hevs.businessobject.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Named
@ApplicationScoped
public class ProgressService{

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

        long totalEpisodes = em.createQuery(
                "SELECT COUNT(e) FROM Episode e WHERE e.season.series = :s", Long.class)
                .setParameter("s", series).getSingleResult();
        long watchedEpisodes = em.createQuery(
                "SELECT COUNT(ep) FROM EpisodeProgress ep WHERE ep.viewer = :v AND ep.episode.season.series = :s AND ep.status = :ws", Long.class)
                .setParameter("v", viewer).setParameter("s", series).setParameter("ws", WatchStatus.WATCHED)
                .getSingleResult();

        if (watchedEpisodes >= totalEpisodes) {
            sp.setStatus(ProgressStatus.WATCHED);
        } else {
            sp.setStatus(ProgressStatus.IN_PROGRESS);
        }
        em.merge(sp);
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

    public ProgressStatus computeSeriesStatus(Viewer viewer, Series series) {
        long total = em.createQuery(
                "SELECT COUNT(e) FROM Episode e WHERE e.season.series = :s", Long.class)
                .setParameter("s", series).getSingleResult();

        if (total == 0) return ProgressStatus.NOT_STARTED;

        long watched = em.createQuery(
                "SELECT COUNT(ep) FROM EpisodeProgress ep WHERE ep.viewer = :v AND ep.episode.season.series = :s AND ep.status = :ws", Long.class)
                .setParameter("v", viewer).setParameter("s", series).setParameter("ws", WatchStatus.WATCHED)
                .getSingleResult();

        if (watched == 0) return ProgressStatus.NOT_STARTED;
        if (watched >= total) return ProgressStatus.WATCHED;
        return ProgressStatus.IN_PROGRESS;
    }

    public List<SeriesProgress> getProgressForViewer(Viewer viewer) {
        return em.createQuery(
                        "SELECT sp FROM SeriesProgress sp WHERE sp.viewer = :v",
                        SeriesProgress.class)
                .setParameter("v", viewer)
                .getResultList();
    }
}