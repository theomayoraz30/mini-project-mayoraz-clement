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
public class AccountService implements Serializable {

    @PersistenceContext(unitName = "seriesPU")
    private EntityManager em;

    private Account loggedAccount = null;

    public boolean login(String username, String password) {
        List<Account> results = em.createQuery(
                        "SELECT a FROM Account a WHERE a.username = :u AND a.passwordHash = :p",
                        Account.class)
                .setParameter("u", username)
                .setParameter("p", password)
                .getResultList();

        if (!results.isEmpty()) {
            loggedAccount = results.get(0);
            return true;
        }
        return false;
    }

    public void logout() {
        loggedAccount = null;
    }

    @Transactional
    public boolean register(String username, String password, String email, String displayName) {
        List<Account> existing = em.createQuery(
                        "SELECT a FROM Account a WHERE a.username = :u", Account.class)
                .setParameter("u", username)
                .getResultList();

        if (!existing.isEmpty()) return false;

        Viewer viewer = new Viewer(username, password, email, displayName);
        em.persist(viewer);
        return true;
    }

    public Account getLoggedAccount() { return loggedAccount; }
    public boolean isLoggedIn() { return loggedAccount != null; }
    public boolean isViewer() { return loggedAccount instanceof Viewer; }
    public boolean isAdmin() { return loggedAccount instanceof Administrator; }

    public Viewer getLoggedViewer() {
        if (isViewer()) return (Viewer) loggedAccount;
        return null;
    }
}