/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "atendimentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atendimento.findAll", query = "SELECT a FROM Atendimento a"),
    @NamedQuery(name = "Atendimento.findById", query = "SELECT a FROM Atendimento a WHERE a.id = :id"),
    @NamedQuery(name = "Atendimento.findByProtocolo", query = "SELECT a FROM Atendimento a WHERE a.protocolo = :protocolo"),
    @NamedQuery(name = "Atendimento.findByTitulo", query = "SELECT a FROM Atendimento a WHERE a.titulo = :titulo"),
    @NamedQuery(name = "Atendimento.findByProcedencia", query = "SELECT a FROM Atendimento a WHERE a.procedencia = :procedencia"),
    @NamedQuery(name = "Atendimento.findByProtProcedencia", query = "SELECT a FROM Atendimento a WHERE a.protProcedencia = :protProcedencia"),
    @NamedQuery(name = "Atendimento.findByDataSolicitacao", query = "SELECT a FROM Atendimento a WHERE a.dataSolicitacao = :dataSolicitacao"),
    @NamedQuery(name = "Atendimento.findByHoraSolicitacao", query = "SELECT a FROM Atendimento a WHERE a.horaSolicitacao = :horaSolicitacao"),
    @NamedQuery(name = "Atendimento.findByLocalizacao", query = "SELECT a FROM Atendimento a WHERE a.localizacao = :localizacao"),
    @NamedQuery(name = "Atendimento.findByNumCasa", query = "SELECT a FROM Atendimento a WHERE a.numCasa = :numCasa"),
    @NamedQuery(name = "Atendimento.findByLatitude", query = "SELECT a FROM Atendimento a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "Atendimento.findByLongitude", query = "SELECT a FROM Atendimento a WHERE a.longitude = :longitude"),
    @NamedQuery(name = "Atendimento.findByPontoReferencia", query = "SELECT a FROM Atendimento a WHERE a.pontoReferencia = :pontoReferencia"),
    @NamedQuery(name = "Atendimento.findBySolicitante", query = "SELECT a FROM Atendimento a WHERE a.solicitante = :solicitante"),
    @NamedQuery(name = "Atendimento.findByTelSolicitante", query = "SELECT a FROM Atendimento a WHERE a.telSolicitante = :telSolicitante"),
    @NamedQuery(name = "Atendimento.findByDataConclusao", query = "SELECT a FROM Atendimento a WHERE a.dataConclusao = :dataConclusao"),
    @NamedQuery(name = "Atendimento.findByHoraConclusao", query = "SELECT a FROM Atendimento a WHERE a.horaConclusao = :horaConclusao"),
    @NamedQuery(name = "Atendimento.findByObservacao", query = "SELECT a FROM Atendimento a WHERE a.observacao = :observacao"),
    @NamedQuery(name = "Atendimento.findByEquipe", query = "SELECT a FROM Atendimento a WHERE a.equipe = :equipe"),
    @NamedQuery(name = "Atendimento.findByResponsavel", query = "SELECT a FROM Atendimento a WHERE a.responsavel = :responsavel"),
    @NamedQuery(name = "Atendimento.findByVtr", query = "SELECT a FROM Atendimento a WHERE a.vtr = :vtr"),
    @NamedQuery(name = "Atendimento.findByProprietarioResidencia", query = "SELECT a FROM Atendimento a WHERE a.proprietarioResidencia = :proprietarioResidencia"),
    @NamedQuery(name = "Atendimento.findByQtdComodosCasa", query = "SELECT a FROM Atendimento a WHERE a.qtdComodosCasa = :qtdComodosCasa"),
    @NamedQuery(name = "Atendimento.findByQtdPessoasAtingidas", query = "SELECT a FROM Atendimento a WHERE a.qtdPessoasAtingidas = :qtdPessoasAtingidas")})
public class Atendimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 45)
    @Column(name = "protocolo")
    private String protocolo;
    @Size(max = 45)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "procedencia")
    private String procedencia;
    @Size(max = 45)
    @Column(name = "prot_procedencia")
    private String protProcedencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_solicitacao")
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_solicitacao")
    @Temporal(TemporalType.TIME)
    private Date horaSolicitacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "localizacao")
    private String localizacao;
    @Size(max = 45)
    @Column(name = "num_casa")
    private String numCasa;
    @Size(max = 20)
    @Column(name = "latitude")
    private String latitude;
    @Size(max = 20)
    @Column(name = "longitude")
    private String longitude;
    @Size(max = 255)
    @Column(name = "ponto_referencia")
    private String pontoReferencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "solicitante")
    private String solicitante;
    @Size(max = 45)
    @Column(name = "tel_solicitante")
    private String telSolicitante;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "desc_inicial")
    private String descInicial;
    @Lob
    @Size(max = 16777215)
    @Column(name = "desc_final")
    private String descFinal;
    @Column(name = "data_conclusao")
    @Temporal(TemporalType.DATE)
    private Date dataConclusao;
    @Column(name = "hora_conclusao")
    @Temporal(TemporalType.TIME)
    private Date horaConclusao;
    @Size(max = 255)
    @Column(name = "observacao")
    private String observacao;
    @Size(max = 255)
    @Column(name = "equipe")
    private String equipe;
    @Size(max = 45)
    @Column(name = "responsavel")
    private String responsavel;
    @Size(max = 45)
    @Column(name = "vtr")
    private String vtr;
    @Size(max = 45)
    @Column(name = "proprietario_residencia")
    private String proprietarioResidencia;
    @Column(name = "qtd_comodos_casa")
    private Integer qtdComodosCasa;
    @Column(name = "qtd_pessoas_atingidas")
    private Integer qtdPessoasAtingidas;
    @JoinTable(name = "atd_x_enc", joinColumns = {
        @JoinColumn(name = "id_atendimento", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_encaminhamento", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Encaminhamento> encaminhamentoCollection;
    @JoinColumn(name = "id_tipologia", referencedColumnName = "id")
    @ManyToOne
    private Tipologia idTipologia;
    @JoinColumn(name = "id_ser", referencedColumnName = "id")
    @ManyToOne
    private Ser idSer;
    @JoinColumn(name = "id_estatus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estatu idEstatus;
    @JoinColumn(name = "id_bairro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bairro idBairro;

    public Atendimento() {
    }

    public Atendimento(Long id) {
        this.id = id;
    }

    public Atendimento(Long id, String procedencia, Date dataSolicitacao, Date horaSolicitacao, String localizacao, String solicitante, String descInicial) {
        this.id = id;
        this.procedencia = procedencia;
        this.dataSolicitacao = dataSolicitacao;
        this.horaSolicitacao = horaSolicitacao;
        this.localizacao = localizacao;
        this.solicitante = solicitante;
        this.descInicial = descInicial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getProtProcedencia() {
        return protProcedencia;
    }

    public void setProtProcedencia(String protProcedencia) {
        this.protProcedencia = protProcedencia;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Date getHoraSolicitacao() {
        return horaSolicitacao;
    }

    public void setHoraSolicitacao(Date horaSolicitacao) {
        this.horaSolicitacao = horaSolicitacao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(String numCasa) {
        this.numCasa = numCasa;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getTelSolicitante() {
        return telSolicitante;
    }

    public void setTelSolicitante(String telSolicitante) {
        this.telSolicitante = telSolicitante;
    }

    public String getDescInicial() {
        return descInicial;
    }

    public void setDescInicial(String descInicial) {
        this.descInicial = descInicial;
    }

    public String getDescFinal() {
        return descFinal;
    }

    public void setDescFinal(String descFinal) {
        this.descFinal = descFinal;
    }

    public Date getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Date getHoraConclusao() {
        return horaConclusao;
    }

    public void setHoraConclusao(Date horaConclusao) {
        this.horaConclusao = horaConclusao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getVtr() {
        return vtr;
    }

    public void setVtr(String vtr) {
        this.vtr = vtr;
    }

    public String getProprietarioResidencia() {
        return proprietarioResidencia;
    }

    public void setProprietarioResidencia(String proprietarioResidencia) {
        this.proprietarioResidencia = proprietarioResidencia;
    }

    public Integer getQtdComodosCasa() {
        return qtdComodosCasa;
    }

    public void setQtdComodosCasa(Integer qtdComodosCasa) {
        this.qtdComodosCasa = qtdComodosCasa;
    }

    public Integer getQtdPessoasAtingidas() {
        return qtdPessoasAtingidas;
    }

    public void setQtdPessoasAtingidas(Integer qtdPessoasAtingidas) {
        this.qtdPessoasAtingidas = qtdPessoasAtingidas;
    }

    @XmlTransient
    public Collection<Encaminhamento> getEncaminhamentoCollection() {
        return encaminhamentoCollection;
    }

    public void setEncaminhamentoCollection(Collection<Encaminhamento> encaminhamentoCollection) {
        this.encaminhamentoCollection = encaminhamentoCollection;
    }

    public Tipologia getIdTipologia() {
        return idTipologia;
    }

    public void setIdTipologia(Tipologia idTipologia) {
        this.idTipologia = idTipologia;
    }

    public Ser getIdSer() {
        return idSer;
    }

    public void setIdSer(Ser idSer) {
        this.idSer = idSer;
    }

    public Estatu getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Estatu idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Bairro getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Bairro idBairro) {
        this.idBairro = idBairro;
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
        if (!(object instanceof Atendimento)) {
            return false;
        }
        Atendimento other = (Atendimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.fortaleza.sesec.ipeapi.entities.Atendimento[ id=" + id + " ]";
    }
    
}
