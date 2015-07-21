/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "tipologias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipologia.findAll", query = "SELECT t FROM Tipologia t"),
    @NamedQuery(name = "Tipologia.findById", query = "SELECT t FROM Tipologia t WHERE t.id = :id"),
    @NamedQuery(name = "Tipologia.findByNome", query = "SELECT t FROM Tipologia t WHERE t.nome = :nome")})
public class Tipologia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "idTipologia")
    private Collection<Atendimento> atendimentoCollection;

    public Tipologia() {
    }

    public Tipologia(Integer id) {
        this.id = id;
    }

    public Tipologia(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Atendimento> getAtendimentoCollection() {
        return atendimentoCollection;
    }

    public void setAtendimentoCollection(Collection<Atendimento> atendimentoCollection) {
        this.atendimentoCollection = atendimentoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipologia)) {
            return false;
        }
        Tipologia other = (Tipologia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.fortaleza.sesec.ipeapi.entities.Tipologia[ id=" + id + " ]";
    }
    
}
