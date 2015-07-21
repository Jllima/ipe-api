/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.rest.security;

import service.*;
import java.io.Serializable;
import javax.ejb.Local;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jorge
 */
@Local
@Path("/resource1")
public interface RestResourceProxy extends Serializable{
    
    /*A anotacao @Context injeta uma variedade de recursos,
     * ao servico RestFull. o HttpHearders com essa anotacao
     * disponibliza os cabecalhos da requisicao
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(
            @Context HttpHeaders httHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password);
    
    @GET
    @Path("/demo-get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response demoGetMethod();
    
    @POST
    @Path("/demo-post")
    @Produces(MediaType.APPLICATION_JSON)
    public Response demoPostMethod();
    
    @POST
    @Path("/logout")
    public Response logout(@Context HttpHeaders httpHeaders);
    
}
