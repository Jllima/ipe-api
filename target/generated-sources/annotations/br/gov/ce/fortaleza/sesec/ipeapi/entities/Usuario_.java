package br.gov.ce.fortaleza.sesec.ipeapi.entities;

import br.gov.ce.fortaleza.sesec.ipeapi.entities.Grupo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-07-14T16:13:38")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile SingularAttribute<Usuario, String> telefone;
    public static volatile CollectionAttribute<Usuario, Grupo> grupoCollection;
    public static volatile SingularAttribute<Usuario, String> nome;
    public static volatile SingularAttribute<Usuario, String> login;
    public static volatile SingularAttribute<Usuario, String> senha;
    public static volatile SingularAttribute<Usuario, String> matricula;
    public static volatile SingularAttribute<Usuario, String> celular;

}