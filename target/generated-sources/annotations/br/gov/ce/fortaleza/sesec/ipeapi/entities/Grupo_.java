package br.gov.ce.fortaleza.sesec.ipeapi.entities;

import br.gov.ce.fortaleza.sesec.ipeapi.entities.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-07-14T16:13:38")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile SingularAttribute<Grupo, Boolean> usuarioPadrao;
    public static volatile CollectionAttribute<Grupo, Usuario> usuarioCollection;
    public static volatile SingularAttribute<Grupo, String> grupoId;
    public static volatile SingularAttribute<Grupo, String> descricao;

}