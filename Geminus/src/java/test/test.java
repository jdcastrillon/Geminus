/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Control.ConecionOracle;
import Model.Compensado;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class test {

    public static void main(String[] juan) throws SQLException {
        SimpleDateFormat format2 = new SimpleDateFormat("dd/M/yyyy");
        String f1 = "01/06/2016";
        String f2 = "15/06/2016";
        String fechaQuery1 = "";
        String fechaQuery2 = "";
        String fechaQuery3 = "";
        int condicion = 0;
        String datosF1[] = f1.split("/");
        String datosF2[] = f2.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(datosF1[2]), Integer.parseInt(datosF1[1]), Integer.parseInt(datosF1[0]));

        while (fechaQuery1.length() == 0 || fechaQuery2.length() == 0) {
            if (c.get(Calendar.DAY_OF_WEEK) == 4 && condicion == 0) {
                condicion = 1;
                c.add(Calendar.MONTH, -1);
                fechaQuery1 = "" + format2.format(c.getTime());
                c.add(Calendar.MONTH, 1);
            } else if (c.get(Calendar.DAY_OF_WEEK) == 4 && condicion == 1) {
                c.add(Calendar.MONTH, -1);
                fechaQuery2 = "" + format2.format(c.getTime());
                c.add(Calendar.MONTH, 1);
            }
            c.add(Calendar.DAY_OF_YEAR, 1);
        }
        System.out.println("f1 : " + fechaQuery1);
        System.out.println("f2 : " + fechaQuery2);

        Calendar c2 = Calendar.getInstance();
        c2.set(Integer.parseInt(datosF2[2]), Integer.parseInt(datosF2[1]), Integer.parseInt(datosF2[0]));
        c2.add(Calendar.DAY_OF_YEAR, -1);
        c2.add(Calendar.MONTH, -1);
        fechaQuery3 = "" + format2.format(c2.getTime());
        try {
            ConecionOracle.conectar();
            int b = 0;
            ArrayList<Compensado> listaCompensados = new ArrayList();
            ConecionOracle.ejecuteQuery("select  ma_codigo,case when count(distinct ma_fecha)=14 then 1 when count(distinct ma_fecha)>=15 then 2 end  from re_marcaciones where ma_fecha between '"+f1+"' "
                    + "and '"+f2+"' \n"
                    + "and ma_tipo='E' \n"
                    + "group by ma_codigo \n"
                    + "having count(distinct ma_fecha)>=14 ");
            while (ConecionOracle.rs.next()) {
                b++;
//                listaCompensados.add(new Compensado(ConecionOracle.rs.getString(1),ConecionOracle.rs.getInt(2)));
                System.out.println("- Num : " + b);
            }
            for (Compensado LC : listaCompensados) {
                System.out.println("entro a Borrar "+f1+" FEcha 2 : "+ f2+ " trabajador " +LC.getTrabajador() );
                ConecionOracle.ejecuteUpdate("delete from re_descansos_laborados where DL_fecha between '" + f1 + "' and '" + f2 + "'  and DL_COD_EMPLEADO='" + LC.getTrabajador() + "'");
            }
            int a = 0;
            for (Compensado LC : listaCompensados) {
                System.out.println("entro a compensado");
                a = 0;
                System.out.println("- " + a);
                while (a < LC.getDias()) {
                    a++;
                    System.out.println("--------- : " + a);
                    if (a == 1) {
                        ConecionOracle.ejecuteUpdate("insert into re_descansos_laborados values('" + LC.getTrabajador() + "',"
                                + "to_date('" + fechaQuery1 + "','DD/MM/YYYY'))");
                    } else if (a == 2) {
                        ConecionOracle.ejecuteUpdate("insert into re_descansos_laborados values('" + LC.getTrabajador() + "',"
                                + "to_date('" +fechaQuery2+ "','DD/MM/YYYY'))");
                    }
                }                
            }
            ConecionOracle.ejecuteUpdate("commit");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConecionOracle.cerrarConexion();
        }
    }

}
