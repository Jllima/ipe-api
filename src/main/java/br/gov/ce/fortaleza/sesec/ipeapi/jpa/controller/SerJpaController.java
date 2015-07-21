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
import java.util.ArrayList;
import java.util.Collection;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Bairro;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Ser;
import br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jorge
 */
public class SerJpaController implements Serializable {

    public SerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ser ser) {
        if (ser.getAtendimentoCollection() == null) {
            ser.setAtendimentoCollection(new ArrayList<Atendimento>());
        }
        if (ser.getBairroCollection() == null) {
            ser.setBairroCollection(new ArrayList<Bairro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Atendimento> attachedAtendimentoCollection = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionAtendimentoToAttach : ser.getAtendimentoCollection()) {
                atendimentoCollectionAtendimentoToAttach = em.getReference(atendimentoCollectionAtendimentoToAttach.getClass(), atendimentoCollectionAtendimentoToAttach.getId());
                attachedAtendimentoCollection.add(atendimentoCollectionAtendimentoToAttach);
            }
            ser.setAtendimentoCollection(attachedAtendimentoCollection);
            Collection<Bairro> attachedBairroCollection = new ArrayList<Bairro>();
            for (Bairro bairroCollectionBairroToAttach : ser.getBairroCollection()) {
                bairroCollectionBairroToAttach = em.getReference(bairroCollectionBairroToAttach.getClass(), bairroCollectionBairroToAttach.getId());
                attachedBairroCollection.add(bairroCollectionBairroToAttach);
            }
            ser.setBairroCollection(attachedBairroCollection);
            em.persist(ser);
            for (Atendimento atendimentoCollectionAtendimento : ser.getAtendimentoCollection()) {
                Ser oldIdSerOfAtendimentoCollectionAtendimento = atendimentoCollectionAtendimento.getIdSer();
                atendimentoCollectionAtendimento.setIdSer(ser);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
                if (oldIdSerOfAtendimentoCollectionAtendimento != null) {
                    oldIdSerOfAtendimentoCollectionAtendimento.getAtendimentoCollection().remove(atendimentoCollectionAtendimento);
                    oldIdSerOfAtendimentoCollectionAtendimento = em.merge(oldIdSerOfAtendimentoCollectionAtendimento);
                }
            }
            for (Bairro bairroCollectionBairro : ser.getBairroCollection()) {
                Ser oldIdSerOfBairroCollectionBairro = bairroCollectionBairro.getIdSer();
                bairroCollectionBairro.setIdSer(ser);
                bairroCollectionBairro = em.merge(bairroCollectionBairro);
                if (oldIdSerOfBairroCollectionBairro != null) {
                    oldIdSerOfBairroCollectionBairro.getBairroCollection().remove(bairroCollectionBairro);
                    oldIdSerOfBairroCollectionBairro = em.merge(oldIdSerOfBairroCollectionBairro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ser ser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ser persistentSer = em.find(Ser.class, ser.getId());
            Collection<Atendimento> atendimentoCollectionOld = persistentSer.getAtendimentoCollection();
            Collection<Atendimento> atendimentoCollectionNew = ser.getAtendimentoCollection();
            Collection<Bairro> bairroCollectionOld = persistentSer.getBairroCollection();
            Collection<Bairro> bairroCollectionNew = ser.getBairroCollection();
            Collection<Atendimento> attachedAtendimentoCollectionNew = new ArrayList<Atendimento>();
            for (Atendimento atendimentoCollectionNewAtendimentoToAttach : atendimentoCollectionNew) {
                atendimentoCollectionNewAtendimentoToAttach = em.getReference(atendimentoCollectionNewAtendimentoToAttach.getClass(), atendimentoCollectionNewAtendimentoToAttach.getId());
                attachedAtendimentoCollectionNew.add(atendimentoCollectionNewAtendimentoToAttach);
            }
            atendimentoCollectionNew = attachedAtendimentoCollectionNew;
            ser.setAtendimentoCollection(atendimentoCollectionNew);
            Collection<Bairro> attachedBairroCollectionNew = new ArrayList<Bairro>();
            for (Bairro bairroCollectionNewBairroToAttach : bairroCollectionNew) {
                bairroCollectionNewBairroToAttach = em.getReference(bairroCollectionNewBairroToAttach.getClass(), bairroCollectionNewBairroToAttach.getId());
                attachedBairroCollectionNew.add(bairroCollectionNewBairroToAttach);
            }
            bairroCollectionNew = attachedBairroCollectionNew;
            ser.setBairroCollection(bairroCollectionNew);
            ser = em.merge(ser);
            for (Atendimento atendimentoCollectionOldAtendimento : atendimentoCollectionOld) {
                if (!atendimentoCollectionNew.contains(atendimentoCollectionOldAtendimento)) {
                    atendimentoCollectionOldAtendimento.setIdSer(null);
                    atendimentoCollectionOldAtendimento = em.merge(atendimentoCollectionOldAtendimento);
                }
            }
            for (Atendimento atendimentoCollectionNewAtendimento : atendimentoCollectionNew) {
                if (!atendimentoCollectionOld.contains(atendimentoCollectionNewAtendimento)) {
                    Ser oldIdSerOfAtendimentoCollectionNewAtendimento = atendimentoCollectionNewAtendimento.getIdSer();
                    atendimentoCollectionNewAtendimento.setIdSer(ser);
                    atendimentoCollectionNewAtendimento = em.merge(atendimentoCollectionNewAtendimento);
                    if (oldIdSerOfAtendimentoCollectionNewAtendimento != null && !oldIdSerOfAtendimentoCollectionNewAtendimento.equals(ser)) {
                        oldIdSerOfAtendimentoCollectionNewAtendimento.getAtendimentoCollection().remove(atendimentoCollectionNewAtendimento);
                        oldIdSerOfAtendimentoCollectionNewAtendimento = em.merge(oldIdSerOfAtendimentoCollectionNewAtendimento);
                    }
                }
            }
            for (Bairro bairroCollectionOldBairro : bairroCollectionOld) {
                if (!bairroCollectionNew.contains(bairroCollectionOldBairro)) {
                    bairroCollectionOldBairro.setIdSer(null);
                    bairroCollectionOldBairro = em.merge(bairroCollectionOldBairro);
                }
            }
            for (Bairro bairroCollectionNewBairro : bairroCollectionNew) {
                if (!bairroCollectionOld.contains(bairroCollectionNewBairro)) {
                    Ser oldIdSerOfBairroCollectionNewBairro = bairroCollectionNewBairro.getIdSer();
                    bairroCollectionNewBairro.setIdSer(ser);
                    bairroCollectionNewBairro = em.merge(bairroCollectionNewBairro);
                    if (oldIdSerOfBairroCollectionNewBairro != null && !oldIdSerOfBairroCollectionNewBairro.equals(ser)) {
                        oldIdSerOfBairroCollectionNewBairro.getBairroCollection().remove(bairroCollectionNewBairro);
                        oldIdSerOfBairroCollectionNewBairro = em.merge(oldIdSerOfBairroCollectionNewBairro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ser.getId();
                if (findSer(id) == null) {
                    throw new NonexistentEntityException("The ser with id " + id + " no longer exists.");
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
            Ser ser;
            try {
                ser = em.getReference(Ser.class, id);
                ser.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ser with id " + id + " no longer exists.", enfe);
            }
            Collection<Atendimento> atendimentoCollection = ser.getAtendimentoCollection();
            for (Atendimento atendimentoCollectionAtendimento : atendimentoCollection) {
                atendimentoCollectionAtendimento.setIdSer(null);
                atendimentoCollectionAtendimento = em.merge(atendimentoCollectionAtendimento);
            }
            Collection<Bairro> bairroCollection = ser.getBairroCollection();
            for (Bairro bairroCollectionBairro : bairroCollection) {
                bairroCollectionBairro.setIdSer(null);
                bairroCollectionBairro = em.merge(bairroCollectionBairro);
            }
            em.remove(ser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ser> findSerEntities() {
        return findSerEntities(true, -1, -1);
    }

    public List<Ser> findSerEntities(int maxResults, int firstResult) {
        return findSerEntities(false, maxResults, firstResult);
    }

    private List<Ser> findSerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ser.class));
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

    public Ser findSer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ser.class, id);
        } finally {
            em.close();
        }
    }

    public int getSerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ser> rt = cq.from(Ser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
