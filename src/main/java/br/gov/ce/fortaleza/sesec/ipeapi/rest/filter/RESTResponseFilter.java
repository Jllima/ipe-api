/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.rest.filter;

import br.gov.ce.fortaleza.sesec.ipeapi.rest.security.HttpHeadersNames;
import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Jorge
 */
@Provider
//@PreMatching
public class RESTResponseFilter implements ContainerResponseFilter{
    
    private final static Logger log = Logger.getLogger(RESTResponseFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
        log.info("Filtering REST Response");

        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");    // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", HttpHeadersNames.SERVICE_KEY+","+HttpHeadersNames.AUTH_TOKEN);

    }
    
}
