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
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Tipologia;
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
public class TipologiaJpaController implements Serializable {

    public TipologiaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipologia tipologia) {
        if (tipologia.getAtendimentoCollection() == null) {
            tipologia.setAtendimentoCollection(new ArrayList<Atendimento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Atendimento> attachedAtendimentoCollection = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionAtendimentoToAttach : tipologia.getAtendimentoCollection()) {
                atendimentoCollectionAtendimentoToAttach = em.getReference(atendimentoCollectionAtendimentoToAttach.getClass(), atendimentoCollectionAtendimentoToAttach.getId());
                attachedAtendimentoCollection.add(atendimentoCollectionAtendimentoToAttach);
            }
            tipologia.setAtendimentoCollection(attachedAtendimentoCollection);
            em.persist(tipologia);
            for (Atendimento atendimentoCollectionAtendimento : tipologia.getAtendimentoCollection()) {
                Tipologia oldIdTipologiaOfAtendimentoCollectionAtendimento = atendimentoCollectionAtendimento.getIdTipologia();
                atendimentoCollectionAtendimento.setIdTipologia(tipologia);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
                if (oldIdTipologiaOfAtendimentoCollectionAtendimento != null) {
                    oldIdTipologiaOfAtendimentoCollectionAtendimento.getAtendimentoCollection().remove(atendimentoCollectionAtendimento);
                    oldIdTipologiaOfAtendimentoCollectionAtendimento = em.merge(oldIdTipologiaOfAtendimentoCollectionAtendimento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipologia tipologia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipologia persistentTipologia = em.find(Tipologia.class, tipologia.getId());
            Collection<Atendimento> atendimentoCollectionOld = persistentTipologia.getAtendimentoCollection();
            Collection<Atendimento> atendimentoCollectionNew = tipologia.getAtendimentoCollection();
            Collection<Atendimento> attachedAtendimentoCollectionNew = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionNewAtendimentoToAttach : atendimentoCollectionNew) {
                atendimentoCollectionNewAtendimentoToAttach = em.getReference(atendimentoCollectionNewAtendimentoToAttach.getClass(), atendimentoCollectionNewAtendimentoToAttach.getId());
                attachedAtendimentoCollectionNew.add(atendimentoCollectionNewAtendimentoToAttach);
            }
            atendimentoCollectionNew = attachedAtendimentoCollectionNew;
            tipologia.setAtendimentoCollection(atendimentoCollectionNew);
            tipologia = em.merge(tipologia);
            for (Atendimento atendimentoCollectionOldAtendimento : atendimentoCollectionOld) {
                if (!atendimentoCollectionNew.contains(atendimentoCollectionOldAtendimento)) {
                    atendimentoCollectionOldAtendimento.setIdTipologia(null);
                    atendimentoCollectionOldAtendimento = em.merge(atendimentoCollectionOldAtendimento);
                }
            }
            for (Atendimento atendimentoCollectionNewAtendimento : atendimentoCollectionNew) {
                if (!atendimentoCollectionOld.contains(atendimentoCollectionNewAtendimento)) {
                    Tipologia oldIdTipologiaOfAtendimentoCollectionNewAtendimento = atendimentoCollectionNewAtendimento.getIdTipologia();
                    atendimentoCollectionNewAtendimento.setIdTipologia(tipologia);
                    atendimentoCollectionNewAtendimento = em.merge(atendimentoCollectionNewAtendimento);
                    if (oldIdTipologiaOfAtendimentoCollectionNewAtendimento != null && !oldIdTipologiaOfAtendimentoCollectionNewAtendimento.equals(tipologia)) {
                        oldIdTipologiaOfAtendimentoCollectionNewAtendimento.getAtendimentoCollection().remove(atendimentoCollectionNewAtendimento);
                        oldIdTipologiaOfAtendimentoCollectionNewAtendimento = em.merge(oldIdTipologiaOfAtendimentoCollectionNewAtendimento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipologia.getId();
                if (findTipologia(id) == null) {
                    throw new NonexistentEntityException("The tipologia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipologia tipologia;
            try {
                tipologia = em.getReference(Tipologia.class, id);
                tipologia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipologia with id " + id + " no longer exists.", enfe);
            }
            Collection<Atendimento> atendimentoCollection = tipologia.getAtendimentoCollection();
            for (Atendimento atendimentoCollectionAtendimento : atendimentoCollection) {
                atendimentoCollectionAtendimento.setIdTipologia(null);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
            }
            em.remove(tipologia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipologia> findTipologiaEntities() {
        return findTipologiaEntities(true, -1, -1);
    }

    public List<Tipologia> findTipologiaEntities(int maxResults, int firstResult) {
        return findTipologiaEntities(false, maxResults, firstResult);
    }

    private List<Tipologia> findTipologiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipologia.class));
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

    public Tipologia findTipologia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipologia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipologiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipologia> rt = cq.from(Tipologia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
