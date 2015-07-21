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
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Atendimento;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Estatu;
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
public class EstatuJpaController implements Serializable {

    public EstatuJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estatu estatu) {
        if (estatu.getAtendimentoCollection() == null) {
            estatu.setAtendimentoCollection(new ArrayList<Atendimento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Atendimento> attachedAtendimentoCollection = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionAtendimentoToAttach : estatu.getAtendimentoCollection()) {
                atendimentoCollectionAtendimentoToAttach = em.getReference(atendimentoCollectionAtendimentoToAttach.getClass(), atendimentoCollectionAtendimentoToAttach.getId());
                attachedAtendimentoCollection.add(atendimentoCollectionAtendimentoToAttach);
            }
            estatu.setAtendimentoCollection(attachedAtendimentoCollection);
            em.persist(estatu);
            for (Atendimento atendimentoCollectionAtendimento : estatu.getAtendimentoCollection()) {
                Estatu oldIdEstatusOfAtendimentoCollectionAtendimento = atendimentoCollectionAtendimento.getIdEstatus();
                atendimentoCollectionAtendimento.setIdEstatus(estatu);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
                if (oldIdEstatusOfAtendimentoCollectionAtendimento != null) {
                    oldIdEstatusOfAtendimentoCollectionAtendimento.getAtendimentoCollection().remove(atendimentoCollectionAtendimento);
                    oldIdEstatusOfAtendimentoCollectionAtendimento = em.merge(oldIdEstatusOfAtendimentoCollectionAtendimento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estatu estatu) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estatu persistentEstatu = em.find(Estatu.class, estatu.getId());
            Collection<Atendimento> atendimentoCollectionOld = persistentEstatu.getAtendimentoCollection();
            Collection<Atendimento> atendimentoCollectionNew = estatu.getAtendimentoCollection();
            List<String> illegalOrphanMessages = null;
            for (Atendimento atendimentoCollectionOldAtendimento : atendimentoCollectionOld) {
                if (!atendimentoCollectionNew.contains(atendimentoCollectionOldAtendimento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Atendimento " + atendimentoCollectionOldAtendimento + " since its idEstatus field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Atendimento> attachedAtendimentoCollectionNew = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionNewAtendimentoToAttach : atendimentoCollectionNew) {
                atendimentoCollectionNewAtendimentoToAttach = em.getReference(atendimentoCollectionNewAtendimentoToAttach.getClass(), atendimentoCollectionNewAtendimentoToAttach.getId());
                attachedAtendimentoCollectionNew.add(atendimentoCollectionNewAtendimentoToAttach);
            }
            atendimentoCollectionNew = attachedAtendimentoCollectionNew;
            estatu.setAtendimentoCollection(atendimentoCollectionNew);
            estatu = em.merge(estatu);
            for (Atendimento atendimentoCollectionNewAtendimento : atendimentoCollectionNew) {
                if (!atendimentoCollectionOld.contains(atendimentoCollectionNewAtendimento)) {
                    Estatu oldIdEstatusOfAtendimentoCollectionNewAtendimento = atendimentoCollectionNewAtendimento.getIdEstatus();
                    atendimentoCollectionNewAtendimento.setIdEstatus(estatu);
                    atendimentoCollectionNewAtendimento = em.merge(atendimentoCollectionNewAtendimento);
                    if (oldIdEstatusOfAtendimentoCollectionNewAtendimento != null && !oldIdEstatusOfAtendimentoCollectionNewAtendimento.equals(estatu)) {
                        oldIdEstatusOfAtendimentoCollectionNewAtendimento.getAtendimentoCollection().remove(atendimentoCollectionNewAtendimento);
                        oldIdEstatusOfAtendimentoCollectionNewAtendimento = em.merge(oldIdEstatusOfAtendimentoCollectionNewAtendimento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estatu.getId();
                if (findEstatu(id) == null) {
                    throw new NonexistentEntityException("The estatu with id " + id + " no longer exists.");
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
            Estatu estatu;
            try {
                estatu = em.getReference(Estatu.class, id);
                estatu.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estatu with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Atendimento> atendimentoCollectionOrphanCheck = estatu.getAtendimentoCollection();
            for (Atendimento atendimentoCollectionOrphanCheckAtendimento : atendimentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estatu (" + estatu + ") cannot be destroyed since the Atendimento " + atendimentoCollectionOrphanCheckAtendimento + " in its atendimentoCollection field has a non-nullable idEstatus field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estatu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estatu> findEstatuEntities() {
        return findEstatuEntities(true, -1, -1);
    }

    public List<Estatu> findEstatuEntities(int maxResults, int firstResult) {
        return findEstatuEntities(false, maxResults, firstResult);
    }

    private List<Estatu> findEstatuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estatu.class));
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

    public Estatu findEstatu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estatu.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstatuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estatu> rt = cq.from(Estatu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
