package br.gov.ce.fortaleza.sesec.ipeapi.entities;

import br.gov.ce.fortaleza.sesec.ipeapi.entities.Bairro;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Encaminhamento;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Estatu;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Ser;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.Tipologia;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-07-14T16:13:38")
@StaticMetamodel(Atendimento.class)
public class Atendimento_ { 

    public static volatile SingularAttribute<Atendimento, String> proprietarioResidencia;
    public static volatile SingularAttribute<Atendimento, String> solicitante;
    public static volatile SingularAttribute<Atendimento, String> descFinal;
    public static volatile SingularAttribute<Atendimento, Date> dataSolicitacao;
    public static volatile SingularAttribute<Atendimento, String> pontoReferencia;
    public static volatile SingularAttribute<Atendimento, Integer> qtdPessoasAtingidas;
    public static volatile SingularAttribute<Atendimento, Date> dataConclusao;
    public static volatile SingularAttribute<Atendimento, Long> id;
    public static volatile SingularAttribute<Atendimento, String> procedencia;
    public static volatile SingularAttribute<Atendimento, Date> horaConclusao;
    public static volatile SingularAttribute<Atendimento, String> numCasa;
    public static volatile SingularAttribute<Atendimento, String> longitude;
    public static volatile SingularAttribute<Atendimento, Ser> idSer;
    public static volatile SingularAttribute<Atendimento, Estatu> idEstatus;
    public static volatile SingularAttribute<Atendimento, String> descInicial;
    public static volatile SingularAttribute<Atendimento, Bairro> idBairro;
    public static volatile CollectionAttribute<Atendimento, Encaminhamento> encaminhamentoCollection;
    public static volatile SingularAttribute<Atendimento, Tipologia> idTipologia;
    public static volatile SingularAttribute<Atendimento, String> protocolo;
    public static volatile SingularAttribute<Atendimento, String> vtr;
    public static volatile SingularAttribute<Atendimento, String> observacao;
    public static volatile SingularAttribute<Atendimento, String> titulo;
    public static volatile SingularAttribute<Atendimento, Date> horaSolicitacao;
    public static volatile SingularAttribute<Atendimento, String> telSolicitante;
    public static volatile SingularAttribute<Atendimento, Integer> qtdComodosCasa;
    public static volatile SingularAttribute<Atendimento, String> responsavel;
    public static volatile SingularAttribute<Atendimento, String> equipe;
    public static volatile SingularAttribute<Atendimento, String> latitude;
    public static volatile SingularAttribute<Atendimento, String> protProcedencia;
    public static volatile SingularAttribute<Atendimento, String> localizacao;

}