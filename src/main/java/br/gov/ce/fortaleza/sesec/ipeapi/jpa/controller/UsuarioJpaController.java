/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Grupo;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Usuario;
import br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller.exceptions.NonexistentEntityException;
import br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jorge
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getGrupoCollection() == null) {
            usuario.setGrupoCollection(new ArrayList<Grupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : usuario.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getGrupoId());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            usuario.setGrupoCollection(attachedGrupoCollection);
            em.persist(usuario);
            for (Grupo grupoCollectionGrupo : usuario.getGrupoCollection()) {
                grupoCollectionGrupo.getUsuarioCollection().add(usuario);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getMatricula()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getMatricula());
            Collection<Grupo> grupoCollectionOld = persistentUsuario.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = usuario.getGrupoCollection();
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getGrupoId());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            usuario.setGrupoCollection(grupoCollectionNew);
            usuario = em.merge(usuario);
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    grupoCollectionOldGrupo.getUsuarioCollection().remove(usuario);
                    grupoCollectionOldGrupo = em.merge(grupoCollectionOldGrupo);
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    grupoCollectionNewGrupo.getUsuarioCollection().add(usuario);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getMatricula();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Collection<Grupo> grupoCollection = usuario.getGrupoCollection();
            for (Grupo grupoCollectionGrupo : grupoCollection) {
                grupoCollectionGrupo.getUsuarioCollection().remove(usuario);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
