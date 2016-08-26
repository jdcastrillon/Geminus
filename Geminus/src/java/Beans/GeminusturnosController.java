package Beans;

import Entity.Geminusturnos;
import Control.GeminusturnosFacade;
import Model.GeminusTurnosBool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("geminusturnosController")
@SessionScoped
public class GeminusturnosController implements Serializable {

    private Geminusturnos geminus;
    int valor;
    List<String> list_geminus = new ArrayList();
    List<GeminusTurnosBool> list_geminus_temp = new ArrayList();
    List<Geminusturnos> list_geminus_t = new ArrayList();
    @EJB
    private Control.GeminusturnosFacade gem_manager;

    private boolean t;
    private boolean m;

    public GeminusturnosController() {
    }

    @PostConstruct
    public void init() {
        list_geminus_temp.clear();
        list_geminus.clear();
        cargarTurnos();
    }

    public void cargarTurnos() {
        valor = 1;
        System.out.println("iniciooooooooooooooooooooooooooooo");
        list_geminus = (List) gem_manager.todo();
        for (String list_geminu : list_geminus) {
            String[] objG = list_geminu.split(",");
            GeminusTurnosBool g = new GeminusTurnosBool(objG[0].trim(), objG[1].trim(), objG[2].trim(), objG[3].trim());
            System.out.println("-- "+objG[0].trim()+ "--"+ g.getMedianochebool());
            g.setT(objG[2].trim().equalsIgnoreCase("true") ? true : false);
            g.setM(objG[3].trim().equalsIgnoreCase("true") ? true : false);
            System.out.println(": " + g.isM());
            list_geminus_temp.add(g);
        }
    }

    public void updateGminusTrurnoState(GeminusTurnosBool geminus, int condicion) {
        System.out.println("_ " + geminus.toString());
        int a = 0;
        if (list_geminus_temp.contains(geminus)) {
            a = list_geminus_temp.indexOf(geminus);
            if (condicion == 1) {
                list_geminus_temp.get(a).setM(geminus.isM());
                list_geminus_temp.get(a).setMedianochebool(list_geminus_temp.get(a).isM() == true ? "true" : "false");
            } else if (condicion == 2) {
                list_geminus_temp.get(a).setT(geminus.isT());
                list_geminus_temp.get(a).setTransnochobool(list_geminus_temp.get(a).isT() == true ? "true" : "false");
            }
        } else {
            System.out.println("nada");
        }
    }

    public void update() {
        try {
            for (GeminusTurnosBool list_geminu : list_geminus_temp) {
                list_geminus_t.add(new Geminusturnos(list_geminu.getTurno(), list_geminu.getProgramacion(),
                        list_geminu.getTransnochobool(), list_geminu.getMedianochebool()));
            }
            for (Geminusturnos list_geminu2 : list_geminus_t) {
                gem_manager.edit(list_geminu2);
            }
            System.out.println("Existoso");
        } catch (Exception ex) {

        }
    }

    public List<String> getList_geminus() {
        return list_geminus;
    }

    public void setList_geminus(List<String> list_geminus) {
        this.list_geminus = list_geminus;
    }

    public Geminusturnos getGeminus() {
        return geminus;
    }

    public void setGeminus(Geminusturnos geminus) {
        this.geminus = geminus;
    }

    public GeminusturnosFacade getGem_manager() {
        return gem_manager;
    }

    public void setGem_manager(GeminusturnosFacade gem_manager) {
        this.gem_manager = gem_manager;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isT() {
        return t;
    }

    public void setT(boolean t) {
        this.t = t;
    }

    public boolean isM() {
        return m;
    }

    public void setM(boolean m) {
        this.m = m;
    }

    public List<GeminusTurnosBool> getList_geminus_temp() {
        return list_geminus_temp;
    }

    public void setList_geminus_temp(List<GeminusTurnosBool> list_geminus_temp) {
        this.list_geminus_temp = list_geminus_temp;
    }

}
