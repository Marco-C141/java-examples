package com.mycompany.formulariousuarios;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("recuperarBean")
@RequestScoped
public class RecuperarBean {

    private String usuario;
    private String nuevaContrasena;

    // Getters y setters
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }
    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    // ---------------------------------------------------------
    // MÉTODO PARA ACTUALIZAR CONTRASEÑA
    // ---------------------------------------------------------
    public String actualizar() {

        JPAUtil jpa = new JPAUtil();
        FacesContext fc = FacesContext.getCurrentInstance();

        boolean ok = jpa.actualizarContrasena(usuario, nuevaContrasena);

        if (!ok) {
            fc.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "❌ El usuario no existe", null));
            return null;
        }

        fc.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
            "✔ Contraseña actualizada correctamente", null));

        return "login.xhtml?faces-redirect=true";
    }
}