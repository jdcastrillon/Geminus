/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Control.ConecionOracle;
import Model.Compensado;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.growl.Growl;

/**
 *
 * @author admin
 */
@Named(value = "geminusCompen")
//@SessionScoped
@javax.faces.view.ViewScoped
public class GeminusCompen implements Serializable {

    /**
     * Creates a new instance of GeminusCompen
     */
    ArrayList<Compensado> Listcomp = new ArrayList();
    private Date fecha1;
    private Date fecha2;
    private String f1;
    private String f2;
    private String fechaQuery1;
    private String fechaQuery2;
    Growl growl = new Growl();

    @PostConstruct
    public void init() {        
        growl.setLife(5000);
        Listcomp.clear();
    }

    public void postProcessXLS(Object document) throws IOException {
        HSSFWorkbook wb = (HSSFWorkbook) document;

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void RecuperarFechas() {
        SimpleDateFormat format2 = new SimpleDateFormat("dd/M/yyyy");
        this.f1 = format2.format(fecha1);
        this.f2 = format2.format(fecha2);
        this.fechaQuery1 = "";
        this.fechaQuery2 = "";
        int condicion = 0;
        String datosF1[] = f1.split("/");
        String datosF2[] = f2.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(datosF1[2]), Integer.parseInt(datosF1[1]), Integer.parseInt(datosF1[0]));

        while (fechaQuery1.length() == 0 || fechaQuery2.length() == 0) {
            if (c.get(Calendar.DAY_OF_WEEK) == 4 && condicion == 0) {
                condicion = 1;
                c.add(Calendar.MONTH, -1);
                this.fechaQuery1 = "" + format2.format(c.getTime());
                c.add(Calendar.MONTH, 1);
            } else if (c.get(Calendar.DAY_OF_WEEK) == 4 && condicion == 1) {
                c.add(Calendar.MONTH, -1);
                this.fechaQuery2 = "" + format2.format(c.getTime());
                c.add(Calendar.MONTH, 1);
            }
            c.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    public void reporteCompensado() throws SQLException {
        RecuperarFechas();
        Listcomp.clear();
        int num = 1;
        try {
            ConecionOracle.conectar();
            int b = 0;
            ConecionOracle.ejecuteQuery("select A.dl_cod_empleado,B.em_nombre,count(*) from re_descansos_laborados A ,  re_empleados B where\n"
                    + "A.dl_cod_empleado=B.em_codigo and \n"
                    + " A.DL_fecha between '" + f1 + "' and '" + f2 + "' \n"
                    + "group by A.dl_cod_empleado,B.em_nombre");
            while (ConecionOracle.rs.next()) {
                b++;
                Listcomp.add(new Compensado(num, ConecionOracle.rs.getString(1), ConecionOracle.rs.getString(2), ConecionOracle.rs.getInt(3)));
                num++;
            }

        } catch (ClassNotFoundException ex) {

        } finally {
            ConecionOracle.cerrarConexion();
        }
    }

    public void cargarCompensados() throws SQLException {
        RecuperarFechas();
        Listcomp.clear();
        int num = 1;
        try {
            ConecionOracle.conectar();
            int b = 0;
            ConecionOracle.ejecuteQuery("select  A.ma_codigo,B.em_nombre,case when count(distinct A.ma_fecha)=14 then 1 when count(distinct A.ma_fecha)>=15 then 2 end \n"
                    + " from re_marcaciones A , re_empleados B where A.ma_codigo=B.Em_codigo and \n"
                    + " A.ma_fecha between '" + f1 + "'\n"
                    + "and '" + f2 + "' \n"
                    + "and A.ma_tipo='E'\n"
                    + " group by A.ma_codigo,B.em_nombre\n"
                    + "having count(distinct A.ma_fecha)>=14 ");
            while (ConecionOracle.rs.next()) {
                b++;
                Listcomp.add(new Compensado(num, ConecionOracle.rs.getString(1), ConecionOracle.rs.getString(2), ConecionOracle.rs.getInt(3)));
                num++;
            }

        } catch (ClassNotFoundException ex) {

        } finally {
            ConecionOracle.cerrarConexion();
        }
    }

    public void liquidarCompensados() throws IOException {
        RecuperarFechas();
        boolean r = false;
        try {
            ConecionOracle.conectar();
            for (Compensado LC : Listcomp) {
                System.out.println("entro a Borrar " + f1 + " FEcha 2 : " + f2 + " trabajador " + LC.getTrabajador());
                ConecionOracle.ejecuteUpdate("delete from re_descansos_laborados where DL_fecha between '" + f1 + "' and '" + f2 + "'  and DL_COD_EMPLEADO='" + LC.getTrabajador() + "'");
            }
            int a = 0;
            for (Compensado LC : Listcomp) {
                a = 0;
                while (a < LC.getDias()) {
                    a++;
                    if (a == 1) {
                        ConecionOracle.ejecuteUpdate("insert into re_descansos_laborados values('" + LC.getTrabajador() + "',"
                                + "to_date('" + fechaQuery1 + "','DD/MM/YYYY'))");
                    } else if (a == 2) {
                        ConecionOracle.ejecuteUpdate("insert into re_descansos_laborados values('" + LC.getTrabajador() + "',"
                                + "to_date('" + fechaQuery2 + "','DD/MM/YYYY'))");
                    }
                }
            }
            ConecionOracle.ejecuteUpdate("commit");
            r = true;
        } catch (ClassNotFoundException ex) {
            r = false;
        } finally {
            ConecionOracle.cerrarConexion();
        }
        Listcomp.clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Liquidacion Exitosa"));

    }

    public void limpiarLista() {
        Listcomp.clear();
    }

    public ArrayList<Compensado> getListcomp() {
        return Listcomp;
    }

    public void setListcomp(ArrayList<Compensado> Listcomp) {
        this.Listcomp = Listcomp;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getFechaQuery1() {
        return fechaQuery1;
    }

    public void setFechaQuery1(String fechaQuery1) {
        this.fechaQuery1 = fechaQuery1;
    }

    public String getFechaQuery2() {
        return fechaQuery2;
    }

    public void setFechaQuery2(String fechaQuery2) {
        this.fechaQuery2 = fechaQuery2;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

}
