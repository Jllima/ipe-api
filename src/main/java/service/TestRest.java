/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Jorge
 */
@Path("/hello")
public class TestRest {
    
    @GET
    @Produces("text/plain")
    public String hello(){
        return "Hello Rest";
    }
}
