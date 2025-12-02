package com.mycompany.formulariousuarios;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Map;


@Named
@RequestScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    
    //Abril
    private Usuario usuarioLogueado;
    
    private JPAUtil jpautil = new JPAUtil();

    private boolean mostrarMensajeAyuda = false;
            
    public String login() {
        Usuario user = jpautil.buscarPorNombreUsuario(username);
        
        if (user != null && user.getContraseña().equals(password)) {
            // Session map lets us store information in the user session, so we can later in another page
            // ask for the user ID
            Map<String, Object> session = FacesContext.getCurrentInstance()
                                                      .getExternalContext()
                                                      .getSessionMap();
            
            session.put("loggedUserId", user.getId());
            
            //Abril
            usuarioLogueado=user;
            
            return "principal?faces-redirect=true";
        } else {
            // Error message
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Datos de inicio de sesión incorrectos"));
            return null; // Stay on same page
        }
    }
    
    
    // MÉTODO DE AYUDA
    public void mostrarAyuda() {
        mostrarMensajeAyuda = true;
    }

    // GETTER 
    public boolean isMostrarMensajeAyuda() {
        return mostrarMensajeAyuda;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JPAUtil getJpautil() {
        return jpautil;
    }

    public void setJpautil(JPAUtil jpautil) {
        this.jpautil = jpautil;
    }
    
    //Abril
    // GETTER necesario para inicio.xhtml
    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    // SETTER necesario para evitar el error
    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }
    
    
}
