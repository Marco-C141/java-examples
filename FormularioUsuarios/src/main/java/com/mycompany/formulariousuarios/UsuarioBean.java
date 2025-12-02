package com.mycompany.formulariousuarios;

import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.util.List;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;

@Named // Le dice a JSF que esta clase se puede usar en las páginas XHTML
@ViewScoped //// Mantiene los datos mientras la página esté abierta
public class UsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L; //// Requerido para ViewScoped

    // Se crea un usuario vacío para que se llene el formulario
    private Usuario usuario = new Usuario();
    // Se guarda el texto que se mostrará en el textarea con la lista de usuarios
    private String textoUsuarios = "";

    // Cuando se crea este Bean, cargamos los usuarios guardados en la base de datos
    public UsuarioBean() {
        cargarUsuarios();
    }

    // Getter y Setter del objeto usuario para que JSF pueda usarlo
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTextoUsuarios() {
        return textoUsuarios;
    }

    // Registrar
    public String registrar() {
        // Obtenemos el contexto de JSF para mostrar mensajes en pantalla
        FacesContext fc = FacesContext.getCurrentInstance();
        
        if(!aceptaTerminos){
            FacesContext.getCurrentInstance().addMessage("Acepto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debes aceptar los términos y condiciones para registrate.",
                    null)
                    );
            return null;
        }

        // Validación extra en el servidor: se revisa que todos los campos tengan datos
        if (isVacio(usuario.getUsuario()) ||
            isVacio(usuario.getContraseña()) ||
            isVacio(usuario.getNombre()) ||
            isVacio(usuario.getCorreo()) ||
            isVacio(usuario.getTelefono())) {

            // Si falta algo, mostramos error
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Todos los campos son obligatorios", null));
            return null; //No cambia de página
        }

        JPAUtil jpa = new JPAUtil(); // Creamos nuestro ayudante JPA

        // Verificamos si el usuario ya existe en la base de datos
        if (jpa.usuarioExiste(usuario.getUsuario())) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "❌ El usuario ya existe", null));
            return null;
        }

        // Guardamos al usuario
        try {
            jpa.guardar(usuario);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "✔ Usuario registrado correctamente", null));
        } catch (Exception e) {
            // Si algo sale mal, mostramos error
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar: " + e.getMessage(), null));
            return null;
        }

        // Recargamos la lista del textarea
        cargarUsuarios();
         // Limpiamos los campos del formulario
        limpiar();
        return null; // Nos quedamos en la misma página
    }

    // Limpiar
    public void limpiar() {
        // Crear un objeto nuevo vacía el formulario
        usuario = new Usuario();
    }
    
    // Cargar usuarios para el textarea
    private List<Usuario> listaUsuarios;

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    
    // Cargar usuarios para el textarea
    public void cargarUsuarios() {
        JPAUtil jpa = new JPAUtil();
        listaUsuarios =jpa.listarUsuarios();
        //List<Usuario> lista = jpa.listarUsuarios(); // obtenemos todos los usuarios
        StringBuilder sb = new StringBuilder();
        // Se arma un texto ordenado con los usuarios
        for (Usuario u : listaUsuarios) {
            sb.append("Usuario: ").append(u.getUsuario())
              .append(" | Nombre: ").append(u.getNombre())
              .append(" | Correo: ").append(u.getCorreo())
              .append("\n");
        }
        // Guardamos el texto listo para mostrar en el textarea
        textoUsuarios = sb.toString();
    }

    // Revisa si una cadena esta vacía
    private boolean isVacio(String s) {
        return s == null || s.trim().isEmpty();
    }    
    private boolean aceptaTerminos;
    public boolean isAceptaTerminos(){
     return aceptaTerminos;   
    } 
    public void setAceptaTerminos(boolean aceptaTerminos){
        this.aceptaTerminos = aceptaTerminos;
    }
    
}
