package ch.hevs.presentation;

import ch.hevs.businessobject.*;
import ch.hevs.service.AccountService;
import ch.hevs.service.ProgressService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ProgressBean {

    @Inject
    private ProgressService progressService;

    @Inject
    private AccountService accountService;

    public String markWatched(Episode episode) {
        Viewer viewer = accountService.getLoggedViewer();
        if (viewer != null) {
            progressService.markEpisodeWatched(viewer, episode);
        }
        return null;
    }

    public WatchStatus getEpisodeStatus(Episode episode) {
        Viewer viewer = accountService.getLoggedViewer();
        if (viewer == null) return WatchStatus.NOT_WATCHED;
        EpisodeProgress ep = progressService.findOrCreateEpisodeProgress(viewer, episode);
        return ep.getStatus();
    }
}