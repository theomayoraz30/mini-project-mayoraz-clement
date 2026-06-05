package ch.hevs.service;

import ch.hevs.businessobject.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class ReviewService {

    @PersistenceContext(unitName = "seriesPU")
    private EntityManager em;

    @Transactional
    public void addReview(Viewer viewer, Series series, Integer rating, String comment) {
        viewer = em.merge(viewer);
        series = em.merge(series);
        List<Review> existing = em.createQuery(
                        "SELECT r FROM Review r WHERE r.viewer = :v AND r.series = :s", Review.class)
                .setParameter("v", viewer)
                .setParameter("s", series)
                .getResultList();

        if (!existing.isEmpty()) {
            Review r = existing.get(0);
            r.setRating(rating);
            r.setComment(comment);
            em.merge(r);
        } else {
            Review r = new Review(rating, comment, viewer, series);
            em.persist(r);
        }
    }

    public List<Review> getReviewsForSeries(Series series) {
        return em.createQuery(
                        "SELECT r FROM Review r WHERE r.series = :s ORDER BY r.createdAt DESC",
                        Review.class)
                .setParameter("s", series)
                .getResultList();
    }
}