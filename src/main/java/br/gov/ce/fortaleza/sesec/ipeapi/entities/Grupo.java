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
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "grupos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByGrupoId", query = "SELECT g FROM Grupo g WHERE g.grupoId = :grupoId"),
    @NamedQuery(name = "Grupo.findByDescricao", query = "SELECT g FROM Grupo g WHERE g.descricao = :descricao"),
    @NamedQuery(name = "Grupo.findByUsuarioPadrao", query = "SELECT g FROM Grupo g WHERE g.usuarioPadrao = :usuarioPadrao")})
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "grupoId")
    private String grupoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "usuarioPadrao")
    private Boolean usuarioPadrao;
    @ManyToMany(mappedBy = "grupoCollection")
    private Collection<Usuario> usuarioCollection;

    public Grupo() {
    }

    public Grupo(String grupoId) {
        this.grupoId = grupoId;
    }

    public Grupo(String grupoId, String descricao) {
        this.grupoId = grupoId;
        this.descricao = descricao;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getUsuarioPadrao() {
        return usuarioPadrao;
    }

    public void setUsuarioPadrao(Boolean usuarioPadrao) {
        this.usuarioPadrao = usuarioPadrao;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoId != null ? grupoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoId == null && other.grupoId != null) || (this.grupoId != null && !this.grupoId.equals(other.grupoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.fortaleza.sesec.ipeapi.entities.Grupo[ grupoId=" + grupoId + " ]";
    }
    
}
