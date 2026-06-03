package ch.hevs.presentation;

import ch.hevs.businessobject.*;
import ch.hevs.service.AccountService;
import ch.hevs.service.ReviewService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ReviewBean {

    @Inject
    private ReviewService reviewService;

    @Inject
    private AccountService accountService;

    private Integer rating = 5;
    private String comment = "";

    public String addReview(Series series) {
        Viewer viewer = accountService.getLoggedViewer();
        if (viewer == null) return "login?faces-redirect=true";
        reviewService.addReview(viewer, series, rating, comment);
        rating = 5;
        comment = "";
        return null;
    }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}