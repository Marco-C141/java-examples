package com.mycompany.formulariousuarios;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@ViewScoped
public class ProfileBean implements Serializable{
    private Usuario currentUser;
    private JPAUtil jpautil;


    public ProfileBean() {}
    
    @PostConstruct
    public void init() {
        jpautil = new JPAUtil();
        
        // Retrieve the id from the session context
        Map<String, Object> session = FacesContext.getCurrentInstance()
                                                  .getExternalContext()
                                                  .getSessionMap();
        Long id = (Long) session.get("loggedUserId");
        
        // Redirect to login page if not logged in
        if (id == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (Exception e) { e.printStackTrace(); }
            return;
        }
        
        this.currentUser = jpautil.buscarPorId(id);
    }
    
    public String guardarCambios() {
        try {
            jpautil.actualizar(this.currentUser);
            
            return "principal?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public JPAUtil getJpautil() {
        return jpautil;
    }

    public void setJpautil(JPAUtil jpautil) {
        this.jpautil = jpautil;
    }
}
