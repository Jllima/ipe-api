/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.fortaleza.sesec.ipeapi.entities.rest;

import br.gov.ce.fortaleza.sesec.ipeapi.entities.Atendimento;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jorge
 */
@XmlRootElement
public class Atendimentos {

     private List<Atendimento> atendimentos = new ArrayList<Atendimento>();

    public Atendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public Atendimentos() {
    }
    
    @XmlElement(name="atendimento")
    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }
    
    
}
