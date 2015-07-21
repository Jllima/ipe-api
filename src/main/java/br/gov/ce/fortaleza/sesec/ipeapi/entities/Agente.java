/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "agentes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agente.findAll", query = "SELECT a FROM Agente a"),
    @NamedQuery(name = "Agente.findById", query = "SELECT a FROM Agente a WHERE a.id = :id"),
    @NamedQuery(name = "Agente.findByMatricula", query = "SELECT a FROM Agente a WHERE a.matricula = :matricula"),
    @NamedQuery(name = "Agente.findByNome", query = "SELECT a FROM Agente a WHERE a.nome = :nome"),
    @NamedQuery(name = "Agente.findByNomeGuerra", query = "SELECT a FROM Agente a WHERE a.nomeGuerra = :nomeGuerra"),
    @NamedQuery(name = "Agente.findByTelefone", query = "SELECT a FROM Agente a WHERE a.telefone = :telefone")})
public class Agente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome_guerra")
    private String nomeGuerra;
    @Size(max = 45)
    @Column(name = "telefone")
    private String telefone;

    public Agente() {
    }

    public Agente(Integer id) {
        this.id = id;
    }

    public Agente(Integer id, String matricula, String nome, String nomeGuerra) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.nomeGuerra = nomeGuerra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeGuerra() {
        return nomeGuerra;
    }

    public void setNomeGuerra(String nomeGuerra) {
        this.nomeGuerra = nomeGuerra;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        if (!(object instanceof Agente)) {
            return false;
        }
        Agente other = (Agente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.fortaleza.sesec.ipeapi.entities.Agente[ id=" + id + " ]";
    }
    
}
