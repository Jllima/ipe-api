package br.gov.ce.fortaleza.sesec.ipeapi.entities;

import br.gov.ce.fortaleza.sesec.ipeapi.entities.Atendimento;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-07-14T16:13:38")
@StaticMetamodel(Tipologia.class)
public class Tipologia_ { 

    public static volatile SingularAttribute<Tipologia, Integer> id;
    public static volatile CollectionAttribute<Tipologia, Atendimento> atendimentoCollection;
    public static volatile SingularAttribute<Tipologia, String> nome;

}