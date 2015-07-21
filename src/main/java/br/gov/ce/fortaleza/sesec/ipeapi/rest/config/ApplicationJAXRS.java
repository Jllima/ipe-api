/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.rest.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author Jorge
 */
public class ApplicationJAXRS extends Application{

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("jersey.config.server.provider.packages", "service;br.gov.ce.fortaleza.sesec.ipeapi.rest.filter");
        return properties;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<Object>();
        singletons.add(new JacksonFeature());
        return singletons;
    }
    
        
}
