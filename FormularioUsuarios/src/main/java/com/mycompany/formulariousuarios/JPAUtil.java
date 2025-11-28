package com.mycompany.formulariousuarios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class JPAUtil {

    //Se conecta el programa con la BD
    //El nombre "Ejemplo" debe coincidir con el que está en persistence.xml
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("supabase");

    //Método que sirve para escribir o leer en la BD
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para guardar usuarios en la BD
    public void guardar(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            //Se abre la conexión
            em.getTransaction().begin();
            //Se guarda el usuario
            em.persist(usuario);
            //Guardamos lo escrito
            em.getTransaction().commit();
        } finally {
            //Cerramos la conexión
            em.close();
        }
    }

    // Revisar si un usuario ya existe
    public boolean usuarioExiste(String nombreUsuario) {
        EntityManager em = getEntityManager();
        try {
            // Cuenta cuántas veces aparece ese nombre de usuario
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.usuario = :usuario", 
                Long.class
            )
            //Le damos el valor que estamos buscando
            .setParameter("usuario", nombreUsuario)
            //Obtenemos el numero contado
            .getSingleResult();

            // Si el número es mayor que 0, significa que sí existe
            return count > 0;
        } finally {
            em.close();
        }
    }

    // Listar todos los usuarios registrados
    public List<Usuario> listarUsuarios() {
        EntityManager em = getEntityManager();
        try {
            //Busca todos los usuarios y los ordena por nombre
            return em.createQuery(
                "SELECT u FROM Usuario u ORDER BY u.nombre", 
                Usuario.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
    
    
    
    // ------------------------------------< Parte del login + perfil de usuario >--------------------------------------------
    
    public Usuario buscarPorNombreUsuario(String username) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :us", Usuario.class)
                     .setParameter("us", username)
                     .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Return null if user not found
        } finally {
            em.close();
        }
    }
    
    
    public Usuario buscarPorId(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id", Usuario.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Return null if user not found
        } finally {
            em.close();
        }
    }
    
    // This is the one actually useful to update the user
    public void actualizar(Usuario user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    

    
    
    
    
        // --------------------------Espacio para nuevas versiones FAVOR DE NO CAMBIAR NADA ARRIBA ---------------------------------
    
}

