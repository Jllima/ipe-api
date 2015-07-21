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
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Encaminhamento;
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
public class EncaminhamentoJpaController implements Serializable {

    public EncaminhamentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Encaminhamento encaminhamento) {
        if (encaminhamento.getAtendimentoCollection() == null) {
            encaminhamento.setAtendimentoCollection(new ArrayList<Atendimento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Atendimento> attachedAtendimentoCollection = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionAtendimentoToAttach : encaminhamento.getAtendimentoCollection()) {
                atendimentoCollectionAtendimentoToAttach = em.getReference(atendimentoCollectionAtendimentoToAttach.getClass(), atendimentoCollectionAtendimentoToAttach.getId());
                attachedAtendimentoCollection.add(atendimentoCollectionAtendimentoToAttach);
            }
            encaminhamento.setAtendimentoCollection(attachedAtendimentoCollection);
            em.persist(encaminhamento);
            for (Atendimento atendimentoCollectionAtendimento : encaminhamento.getAtendimentoCollection()) {
                atendimentoCollectionAtendimento.getEncaminhamentoCollection().add(encaminhamento);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Encaminhamento encaminhamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encaminhamento persistentEncaminhamento = em.find(Encaminhamento.class, encaminhamento.getId());
            Collection<Atendimento> atendimentoCollectionOld = persistentEncaminhamento.getAtendimentoCollection();
            Collection<Atendimento> atendimentoCollectionNew = encaminhamento.getAtendimentoCollection();
            Collection<Atendimento> attachedAtendimentoCollectionNew = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionNewAtendimentoToAttach : atendimentoCollectionNew) {
                atendimentoCollectionNewAtendimentoToAttach = em.getReference(atendimentoCollectionNewAtendimentoToAttach.getClass(), atendimentoCollectionNewAtendimentoToAttach.getId());
                attachedAtendimentoCollectionNew.add(atendimentoCollectionNewAtendimentoToAttach);
            }
            atendimentoCollectionNew = attachedAtendimentoCollectionNew;
            encaminhamento.setAtendimentoCollection(atendimentoCollectionNew);
            encaminhamento = em.merge(encaminhamento);
            for (Atendimento atendimentoCollectionOldAtendimento : atendimentoCollectionOld) {
                if (!atendimentoCollectionNew.contains(atendimentoCollectionOldAtendimento)) {
                    atendimentoCollectionOldAtendimento.getEncaminhamentoCollection().remove(encaminhamento);
                    atendimentoCollectionOldAtendimento = em.merge(atendimentoCollectionOldAtendimento);
                }
            }
            for (Atendimento atendimentoCollectionNewAtendimento : atendimentoCollectionNew) {
                if (!atendimentoCollectionOld.contains(atendimentoCollectionNewAtendimento)) {
                    atendimentoCollectionNewAtendimento.getEncaminhamentoCollection().add(encaminhamento);
                    atendimentoCollectionNewAtendimento = em.merge(atendimentoCollectionNewAtendimento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encaminhamento.getId();
                if (findEncaminhamento(id) == null) {
                    throw new NonexistentEntityException("The encaminhamento with id " + id + " no longer exists.");
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
            Encaminhamento encaminhamento;
            try {
                encaminhamento = em.getReference(Encaminhamento.class, id);
                encaminhamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encaminhamento with id " + id + " no longer exists.", enfe);
            }
            Collection<Atendimento> atendimentoCollection = encaminhamento.getAtendimentoCollection();
            for (Atendimento atendimentoCollectionAtendimento : atendimentoCollection) {
                atendimentoCollectionAtendimento.getEncaminhamentoCollection().remove(encaminhamento);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
            }
            em.remove(encaminhamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Encaminhamento> findEncaminhamentoEntities() {
        return findEncaminhamentoEntities(true, -1, -1);
    }

    public List<Encaminhamento> findEncaminhamentoEntities(int maxResults, int firstResult) {
        return findEncaminhamentoEntities(false, maxResults, firstResult);
    }

    private List<Encaminhamento> findEncaminhamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encaminhamento.class));
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

    public Encaminhamento findEncaminhamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encaminhamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncaminhamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Encaminhamento> rt = cq.from(Encaminhamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
