package ch.hevs.presentation;

import ch.hevs.service.AccountService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class AccountBean {

    @Inject
    private AccountService accountService;

    private String username;
    private String password;
    private String email;
    private String displayName;
    private String errorMessage;

    public String login() {
        boolean success = accountService.login(username, password);
        if (success) {
            return "index?faces-redirect=true";
        }
        errorMessage = "Nom d'utilisateur ou mot de passe incorrect.";
        return null;
    }

    public String register() {
        boolean success = accountService.register(username, password, email, displayName);
        if (success) {
            return "login?faces-redirect=true";
        }
        errorMessage = "Ce nom d'utilisateur est déjà pris.";
        return null;
    }

    public String logout() {
        accountService.logout();
        return "login?faces-redirect=true";
    }

    public void requireLogin() throws IOException {
        if (!accountService.isLoggedIn()) {
            FacesContext.getCurrentInstance()
                .getExternalContext()
                .redirect("login.xhtml");
        }
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String d) { this.displayName = d; }
    public String getErrorMessage() { return errorMessage; }
}