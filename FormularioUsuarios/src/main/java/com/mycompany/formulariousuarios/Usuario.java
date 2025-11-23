package com.mycompany.formulariousuarios;

// Importaciones necesarias para trabajar con JPA
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

// Marca la clase como una entidad
@Entity
// Indica el nombre de la tabla en la base de datos: "usuarios"
@Table(name = "usuarios")
public class Usuario {

    public Usuario() {} // Constructor vacío requerido por JPA

    //Se generará automáticamente el ID con el autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Columnas y variables
    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;

    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    // Getters y Setters
    // Son necesarios para acceder al contenido de la entidad.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}

