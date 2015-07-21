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
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Ser;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Atendimento;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Bairro;
import br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller.exceptions.IllegalOrphanException;
import br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller.exceptions.NonexistentEntityException;
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
public class BairroJpaController implements Serializable {

    public BairroJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bairro bairro) {
        if (bairro.getAtendimentoCollection() == null) {
            bairro.setAtendimentoCollection(new ArrayList<Atendimento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ser idSer = bairro.getIdSer();
            if (idSer != null) {
                idSer = em.getReference(idSer.getClass(), idSer.getId());
                bairro.setIdSer(idSer);
            }
            Collection<Atendimento> attachedAtendimentoCollection = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionAtendimentoToAttach : bairro.getAtendimentoCollection()) {
                atendimentoCollectionAtendimentoToAttach = em.getReference(atendimentoCollectionAtendimentoToAttach.getClass(), atendimentoCollectionAtendimentoToAttach.getId());
                attachedAtendimentoCollection.add(atendimentoCollectionAtendimentoToAttach);
            }
            bairro.setAtendimentoCollection(attachedAtendimentoCollection);
            em.persist(bairro);
            if (idSer != null) {
                idSer.getBairroCollection().add(bairro);
                idSer = em.merge(idSer);
            }
            for (Atendimento atendimentoCollectionAtendimento : bairro.getAtendimentoCollection()) {
                Bairro oldIdBairroOfAtendimentoCollectionAtendimento = atendimentoCollectionAtendimento.getIdBairro();
                atendimentoCollectionAtendimento.setIdBairro(bairro);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
                if (oldIdBairroOfAtendimentoCollectionAtendimento != null) {
                    oldIdBairroOfAtendimentoCollectionAtendimento.getAtendimentoCollection().remove(atendimentoCollectionAtendimento);
                    oldIdBairroOfAtendimentoCollectionAtendimento = em.merge(oldIdBairroOfAtendimentoCollectionAtendimento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bairro bairro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bairro persistentBairro = em.find(Bairro.class, bairro.getId());
            Ser idSerOld = persistentBairro.getIdSer();
            Ser idSerNew = bairro.getIdSer();
            Collection<Atendimento> atendimentoCollectionOld = persistentBairro.getAtendimentoCollection();
            Collection<Atendimento> atendimentoCollectionNew = bairro.getAtendimentoCollection();
            List<String> illegalOrphanMessages = null;
            for (Atendimento atendimentoCollectionOldAtendimento : atendimentoCollectionOld) {
                if (!atendimentoCollectionNew.contains(atendimentoCollectionOldAtendimento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Atendimento " + atendimentoCollectionOldAtendimento + " since its idBairro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSerNew != null) {
                idSerNew = em.getReference(idSerNew.getClass(), idSerNew.getId());
                bairro.setIdSer(idSerNew);
            }
            Collection<Atendimento> attachedAtendimentoCollectionNew = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionNewAtendimentoToAttach : atendimentoCollectionNew) {
                atendimentoCollectionNewAtendimentoToAttach = em.getReference(atendimentoCollectionNewAtendimentoToAttach.getClass(), atendimentoCollectionNewAtendimentoToAttach.getId());
                attachedAtendimentoCollectionNew.add(atendimentoCollectionNewAtendimentoToAttach);
            }
            atendimentoCollectionNew = attachedAtendimentoCollectionNew;
            bairro.setAtendimentoCollection(atendimentoCollectionNew);
            bairro = em.merge(bairro);
            if (idSerOld != null && !idSerOld.equals(idSerNew)) {
                idSerOld.getBairroCollection().remove(bairro);
                idSerOld = em.merge(idSerOld);
            }
            if (idSerNew != null && !idSerNew.equals(idSerOld)) {
                idSerNew.getBairroCollection().add(bairro);
                idSerNew = em.merge(idSerNew);
            }
            for (Atendimento atendimentoCollectionNewAtendimento : atendimentoCollectionNew) {
                if (!atendimentoCollectionOld.contains(atendimentoCollectionNewAtendimento)) {
                    Bairro oldIdBairroOfAtendimentoCollectionNewAtendimento = atendimentoCollectionNewAtendimento.getIdBairro();
                    atendimentoCollectionNewAtendimento.setIdBairro(bairro);
                    atendimentoCollectionNewAtendimento = em.merge(atendimentoCollectionNewAtendimento);
                    if (oldIdBairroOfAtendimentoCollectionNewAtendimento != null && !oldIdBairroOfAtendimentoCollectionNewAtendimento.equals(bairro)) {
                        oldIdBairroOfAtendimentoCollectionNewAtendimento.getAtendimentoCollection().remove(atendimentoCollectionNewAtendimento);
                        oldIdBairroOfAtendimentoCollectionNewAtendimento = em.merge(oldIdBairroOfAtendimentoCollectionNewAtendimento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bairro.getId();
                if (findBairro(id) == null) {
                    throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bairro bairro;
            try {
                bairro = em.getReference(Bairro.class, id);
                bairro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Atendimento> atendimentoCollectionOrphanCheck = bairro.getAtendimentoCollection();
            for (Atendimento atendimentoCollectionOrphanCheckAtendimento : atendimentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bairro (" + bairro + ") cannot be destroyed since the Atendimento " + atendimentoCollectionOrphanCheckAtendimento + " in its atendimentoCollection field has a non-nullable idBairro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ser idSer = bairro.getIdSer();
            if (idSer != null) {
                idSer.getBairroCollection().remove(bairro);
                idSer = em.merge(idSer);
            }
            em.remove(bairro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bairro> findBairroEntities() {
        return findBairroEntities(true, -1, -1);
    }

    public List<Bairro> findBairroEntities(int maxResults, int firstResult) {
        return findBairroEntities(false, maxResults, firstResult);
    }

    private List<Bairro> findBairroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bairro.class));
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

    public Bairro findBairro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bairro.class, id);
        } finally {
            em.close();
        }
    }

    public int getBairroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bairro> rt = cq.from(Bairro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
