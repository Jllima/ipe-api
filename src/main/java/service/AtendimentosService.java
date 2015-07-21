/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.gov.ce.fortaleza.sesec.ipeapi.entities.Atendimento;
import br.gov.ce.fortaleza.sesec.ipeapi.entities.rest.Atendimentos;
import br.gov.ce.fortaleza.sesec.ipeapi.jpa.controller.AtendimentoJpaController;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
/**
 *
 * @author Jorge
 */

@Path("/atendimentos")
@Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML,
    MediaType.APPLICATION_JSON})
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML,
    MediaType.APPLICATION_JSON})
public class AtendimentosService {
    
    private AtendimentoJpaController jpaController = null;

    private AtendimentoJpaController getJpaController() {
        if (jpaController == null) {
            this.jpaController = new AtendimentoJpaController(Persistence.createEntityManagerFactory("ipePU"));
        }
        return jpaController;
    }
    @GET
    @Path("/object")
    public Atendimentos findAll() {
        List<Atendimento> atendimentos = getJpaController().findAtendimentoEntities();
        return new Atendimentos(atendimentos);
    }
    
    @GET
    @Path("/response")
    public Response findResponse(){
        List<Atendimento> atendimentos = getJpaController().findAtendimentoEntities();
        Atendimentos atendimentos_object = new Atendimentos(atendimentos);
        return Response.ok().entity(atendimentos_object).build();
    }
    
    @GET
    @Path("/list")
    public List<Atendimento> listAll(){
        List<Atendimento> atendimentos = getJpaController().findAtendimentoEntities();
        return atendimentos;
    }
    @POST
    @Path("/add")
    public Response add(Atendimento atendimento){
        try {
            getJpaController().create(atendimento);
        } catch (Exception e) {
            throw new WebApplicationException(Status.CONFLICT);
        }
        URI uri = UriBuilder.fromPath("atendimentos/{protocolo}").build(atendimento.getProtocolo());
        return Response.created(uri).entity(atendimento).build();
    }
    
    @GET
    @Path("/id")
    public Response findId(){
        Long id = new Long(333);
        Atendimento ate = getJpaController().findAtendimento(id);
        return Response.ok().entity(ate).build();
    }
}
