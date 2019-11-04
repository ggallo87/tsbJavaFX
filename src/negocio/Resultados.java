package negocio;

import soporte.TSBArrayList;
import soporte.TSBHashtable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

public class Resultados {
    private Agrupaciones agrupaciones;
    private String mesa = "";
    private TSBHashtable resultados;
    private Regiones regiones;

    public Resultados(Agrupaciones agrupaciones, String carpeta, Regiones regiones) {
        this.agrupaciones = agrupaciones;
        resultados = new TSBHashtable();
        resultados.put("00",agrupaciones.generarVacia());
        this.regiones = regiones;
        cargarResultados(carpeta);
    }

    public void cargarResultados(String carpeta) {
        sumarPorAgrupacion(carpeta + "\\mesas_totales_agrp_politica.dsv");
    }

    public void sumarPorAgrupacion(String path) {
        TSBHashtable res;
        String linea, campos[], categoria, codAgrupacion;
        int votos;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                categoria = campos[4];
                if (categoria.compareTo("000100000000000") == 0) {
                    codAgrupacion = campos[5];
                    votos = Integer.parseInt(campos[6]);

                    //Pais
                    res = (TSBHashtable) resultados.get("00");
                    ((Agrupacion) res.get(codAgrupacion)).sumar(votos);


                    //Distrito
                    ((Agrupacion) getOrPut(campos[0]).get(codAgrupacion)).sumar(votos);

                    //SECCION
                    ((Agrupacion) getOrPut(campos[1]).get(codAgrupacion)).sumar(votos);

                    //CIRCUITO
                    ((Agrupacion) getOrPut(campos[2]).get(codAgrupacion)).sumar(votos);

                    //MESA
                    ((Agrupacion) getOrPut(campos[3]).get(codAgrupacion)).sumar(votos);
                    String datosCircuito[] = {campos[0],campos[1],campos[2],campos[3]};
                    if (mesa != campos[3]){
                        Region mes, circ;
                        regiones.setMesa(datosCircuito);

                        mesa = campos[3];
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Collection getResultados(String codRegion) {
        return((TSBHashtable) (resultados.get(codRegion))).values();
    }

    public TSBHashtable getMesas(String codCircuito) {

        return (TSBHashtable) (resultados.get(codCircuito));
    }

    public TSBHashtable getOrPut (String codRegion)
    {
        TSBHashtable table = (TSBHashtable) resultados.get(codRegion);
        if(table != null)
            return table;
        else
        {
            resultados.put(codRegion,agrupaciones.generarVacia());
            return (TSBHashtable) resultados.get(codRegion);
        }
    }

}
