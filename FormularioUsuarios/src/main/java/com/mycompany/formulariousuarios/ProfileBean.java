package com.mycompany.formulariousuarios;

import jakarta.faces.application.FacesMessage;
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
    
    public ProfileBean() {
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
    
    
    
    public void guardarCambios() {
        try {
            jpautil.actualizar(this.currentUser);
            
             FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil actualizado",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
