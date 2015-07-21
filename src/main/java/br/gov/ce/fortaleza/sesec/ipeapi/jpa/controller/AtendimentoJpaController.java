/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller;

import br.gov.ce.fortaleza.sesec.ipeapi.entities.Atendimento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Tipologia;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Ser;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Estatu;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Bairro;
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
public class AtendimentoJpaController implements Serializable {

    public AtendimentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    
    public AtendimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atendimento atendimento) {
        if (atendimento.getEncaminhamentoCollection() == null) {
            atendimento.setEncaminhamentoCollection(new ArrayList<Encaminhamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipologia idTipologia = atendimento.getIdTipologia();
            if (idTipologia != null) {
                idTipologia = em.getReference(idTipologia.getClass(), idTipologia.getId());
                atendimento.setIdTipologia(idTipologia);
            }
            Ser idSer = atendimento.getIdSer();
            if (idSer != null) {
                idSer = em.getReference(idSer.getClass(), idSer.getId());
                atendimento.setIdSer(idSer);
            }
            Estatu idEstatus = atendimento.getIdEstatus();
            if (idEstatus != null) {
                idEstatus = em.getReference(idEstatus.getClass(), idEstatus.getId());
                atendimento.setIdEstatus(idEstatus);
            }
            Bairro idBairro = atendimento.getIdBairro();
            if (idBairro != null) {
                idBairro = em.getReference(idBairro.getClass(), idBairro.getId());
                atendimento.setIdBairro(idBairro);
            }
            Collection<Encaminhamento> attachedEncaminhamentoCollection = new ArrayList<Encaminhamento>();
            for (Encaminhamento encaminhamentoCollectionEncaminhamentoToAttach : atendimento.getEncaminhamentoCollection()) {
                encaminhamentoCollectionEncaminhamentoToAttach = em.getReference(encaminhamentoCollectionEncaminhamentoToAttach.getClass(), encaminhamentoCollectionEncaminhamentoToAttach.getId());
                attachedEncaminhamentoCollection.add(encaminhamentoCollectionEncaminhamentoToAttach);
            }
            atendimento.setEncaminhamentoCollection(attachedEncaminhamentoCollection);
            em.persist(atendimento);
            if (idTipologia != null) {
                idTipologia.getAtendimentoCollection().add(atendimento);
                idTipologia = em.merge(idTipologia);
            }
            if (idSer != null) {
                idSer.getAtendimentoCollection().add(atendimento);
                idSer = em.merge(idSer);
            }
            if (idEstatus != null) {
                idEstatus.getAtendimentoCollection().add(atendimento);
                idEstatus = em.merge(idEstatus);
            }
            if (idBairro != null) {
                idBairro.getAtendimentoCollection().add(atendimento);
                idBairro = em.merge(idBairro);
            }
            for (Encaminhamento encaminhamentoCollectionEncaminhamento : atendimento.getEncaminhamentoCollection()) {
                encaminhamentoCollectionEncaminhamento.getAtendimentoCollection().add(atendimento);
                encaminhamentoCollectionEncaminhamento = em.merge(encaminhamentoCollectionEncaminhamento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atendimento atendimento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atendimento persistentAtendimento = em.find(Atendimento.class, atendimento.getId());
            Tipologia idTipologiaOld = persistentAtendimento.getIdTipologia();
            Tipologia idTipologiaNew = atendimento.getIdTipologia();
            Ser idSerOld = persistentAtendimento.getIdSer();
            Ser idSerNew = atendimento.getIdSer();
            Estatu idEstatusOld = persistentAtendimento.getIdEstatus();
            Estatu idEstatusNew = atendimento.getIdEstatus();
            Bairro idBairroOld = persistentAtendimento.getIdBairro();
            Bairro idBairroNew = atendimento.getIdBairro();
            Collection<Encaminhamento> encaminhamentoCollectionOld = persistentAtendimento.getEncaminhamentoCollection();
            Collection<Encaminhamento> encaminhamentoCollectionNew = atendimento.getEncaminhamentoCollection();
            if (idTipologiaNew != null) {
                idTipologiaNew = em.getReference(idTipologiaNew.getClass(), idTipologiaNew.getId());
                atendimento.setIdTipologia(idTipologiaNew);
            }
            if (idSerNew != null) {
                idSerNew = em.getReference(idSerNew.getClass(), idSerNew.getId());
                atendimento.setIdSer(idSerNew);
            }
            if (idEstatusNew != null) {
                idEstatusNew = em.getReference(idEstatusNew.getClass(), idEstatusNew.getId());
                atendimento.setIdEstatus(idEstatusNew);
            }
            if (idBairroNew != null) {
                idBairroNew = em.getReference(idBairroNew.getClass(), idBairroNew.getId());
                atendimento.setIdBairro(idBairroNew);
            }
            Collection<Encaminhamento> attachedEncaminhamentoCollectionNew = new ArrayList<Encaminhamento>();
            for (Encaminhamento encaminhamentoCollectionNewEncaminhamentoToAttach : encaminhamentoCollectionNew) {
                encaminhamentoCollectionNewEncaminhamentoToAttach = em.getReference(encaminhamentoCollectionNewEncaminhamentoToAttach.getClass(), encaminhamentoCollectionNewEncaminhamentoToAttach.getId());
                attachedEncaminhamentoCollectionNew.add(encaminhamentoCollectionNewEncaminhamentoToAttach);
            }
            encaminhamentoCollectionNew = attachedEncaminhamentoCollectionNew;
            atendimento.setEncaminhamentoCollection(encaminhamentoCollectionNew);
            atendimento = em.merge(atendimento);
            if (idTipologiaOld != null && !idTipologiaOld.equals(idTipologiaNew)) {
                idTipologiaOld.getAtendimentoCollection().remove(atendimento);
                idTipologiaOld = em.merge(idTipologiaOld);
            }
            if (idTipologiaNew != null && !idTipologiaNew.equals(idTipologiaOld)) {
                idTipologiaNew.getAtendimentoCollection().add(atendimento);
                idTipologiaNew = em.merge(idTipologiaNew);
            }
            if (idSerOld != null && !idSerOld.equals(idSerNew)) {
                idSerOld.getAtendimentoCollection().remove(atendimento);
                idSerOld = em.merge(idSerOld);
            }
            if (idSerNew != null && !idSerNew.equals(idSerOld)) {
                idSerNew.getAtendimentoCollection().add(atendimento);
                idSerNew = em.merge(idSerNew);
            }
            if (idEstatusOld != null && !idEstatusOld.equals(idEstatusNew)) {
                idEstatusOld.getAtendimentoCollection().remove(atendimento);
                idEstatusOld = em.merge(idEstatusOld);
            }
            if (idEstatusNew != null && !idEstatusNew.equals(idEstatusOld)) {
                idEstatusNew.getAtendimentoCollection().add(atendimento);
                idEstatusNew = em.merge(idEstatusNew);
            }
            if (idBairroOld != null && !idBairroOld.equals(idBairroNew)) {
                idBairroOld.getAtendimentoCollection().remove(atendimento);
                idBairroOld = em.merge(idBairroOld);
            }
            if (idBairroNew != null && !idBairroNew.equals(idBairroOld)) {
                idBairroNew.getAtendimentoCollection().add(atendimento);
                idBairroNew = em.merge(idBairroNew);
            }
            for (Encaminhamento encaminhamentoCollectionOldEncaminhamento : encaminhamentoCollectionOld) {
                if (!encaminhamentoCollectionNew.contains(encaminhamentoCollectionOldEncaminhamento)) {
                    encaminhamentoCollectionOldEncaminhamento.getAtendimentoCollection().remove(atendimento);
                    encaminhamentoCollectionOldEncaminhamento = em.merge(encaminhamentoCollectionOldEncaminhamento);
                }
            }
            for (Encaminhamento encaminhamentoCollectionNewEncaminhamento : encaminhamentoCollectionNew) {
                if (!encaminhamentoCollectionOld.contains(encaminhamentoCollectionNewEncaminhamento)) {
                    encaminhamentoCollectionNewEncaminhamento.getAtendimentoCollection().add(atendimento);
                    encaminhamentoCollectionNewEncaminhamento = em.merge(encaminhamentoCollectionNewEncaminhamento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = atendimento.getId();
                if (findAtendimento(id) == null) {
                    throw new NonexistentEntityException("The atendimento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atendimento atendimento;
            try {
                atendimento = em.getReference(Atendimento.class, id);
                atendimento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atendimento with id " + id + " no longer exists.", enfe);
            }
            Tipologia idTipologia = atendimento.getIdTipologia();
            if (idTipologia != null) {
                idTipologia.getAtendimentoCollection().remove(atendimento);
                idTipologia = em.merge(idTipologia);
            }
            Ser idSer = atendimento.getIdSer();
            if (idSer != null) {
                idSer.getAtendimentoCollection().remove(atendimento);
                idSer = em.merge(idSer);
            }
            Estatu idEstatus = atendimento.getIdEstatus();
            if (idEstatus != null) {
                idEstatus.getAtendimentoCollection().remove(atendimento);
                idEstatus = em.merge(idEstatus);
            }
            Bairro idBairro = atendimento.getIdBairro();
            if (idBairro != null) {
                idBairro.getAtendimentoCollection().remove(atendimento);
                idBairro = em.merge(idBairro);
            }
            Collection<Encaminhamento> encaminhamentoCollection = atendimento.getEncaminhamentoCollection();
            for (Encaminhamento encaminhamentoCollectionEncaminhamento : encaminhamentoCollection) {
                encaminhamentoCollectionEncaminhamento.getAtendimentoCollection().remove(atendimento);
                encaminhamentoCollectionEncaminhamento = em.merge(encaminhamentoCollectionEncaminhamento);
            }
            em.remove(atendimento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atendimento> findAtendimentoEntities() {
        return findAtendimentoEntities(true, -1, -1);
    }

    public List<Atendimento> findAtendimentoEntities(int maxResults, int firstResult) {
        return findAtendimentoEntities(false, maxResults, firstResult);
    }

    private List<Atendimento> findAtendimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atendimento.class));
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

    public Atendimento findAtendimento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atendimento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtendimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atendimento> rt = cq.from(Atendimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
