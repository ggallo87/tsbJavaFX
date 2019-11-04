package negocio;

import soporte.TSBHashtable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

public class Regiones {
    private TSBHashtable pais;

    public Regiones(String carpeta) {
        pais = new TSBHashtable();
        pais.put("00",new Region("00","Argentina"));
        identificarRegiones(carpeta + "\\descripcion_regiones.dsv");
    }

    public void identificarRegiones(String path) {
        String linea, campos[], codigo, nombre, distrito, seccion;
        Scanner scanner = null;
        Region reg, dis, secc, circ;
        try {
            scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                codigo = campos[0];
                nombre = campos[1];
                switch(codigo.length()){
                    case 2: // Distrito
                        reg = (Region) pais.get("00");
                        dis = reg.getOrPut(codigo);
                        dis.setNombre(nombre);
                        break;
                    case 5: // Seccion
                        distrito = codigo.substring(0,2);
                        dis = ((Region)pais.get("00")).getOrPut(distrito);
                        secc = dis.getOrPut(codigo);
                        secc.setNombre(nombre);
                        break;
                    case 11: // Circuitos
                        distrito = codigo.substring(0,2);
                        dis = ((Region)pais.get("00")).getOrPut(distrito);
                        seccion = codigo.substring(0,5);
                        secc = dis.getOrPut(seccion);
                        circ = secc.getOrPut(codigo);
                        circ.setNombre(nombre);
                        break;
                    default:
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado " + e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Collection getDistritos()
    {
        Region region = (Region) pais.get("00");
        return region.getSubregiones();
    }

    public void setMesa(String datosCircuito[]){
        Region circuito, mesa;
        circuito = (Region) pais.get("00");
        circuito = (Region) circuito.getOrPut(datosCircuito[0]);
        circuito = (Region) circuito.getOrPut(datosCircuito[1]);
        circuito = (Region) circuito.getOrPut(datosCircuito[2]);
        mesa = circuito.getOrPut(datosCircuito[3]);
        mesa.setNombre("");
    }
}
