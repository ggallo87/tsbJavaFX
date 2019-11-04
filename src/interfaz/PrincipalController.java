package interfaz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import negocio.Agrupaciones;
import negocio.Region;
import negocio.Regiones;
import negocio.Resultados;

import java.io.File;

public class PrincipalController {
    public Button btnCargar;
    public Button btnCambiar;
    public Label lblCarpeta;
    public ListView lvwResultados;
    public ComboBox cboDistritos;
    public ComboBox cboSecciones;
    public ComboBox cboCircuitos;
    public ComboBox cboMesas;

    private Resultados resultados;

    public void cargar(ActionEvent actionEvent) {

        ObservableList ol;

        String carpeta = lblCarpeta.getText();
        Agrupaciones agrupaciones = new Agrupaciones(carpeta);
        Regiones regiones = new Regiones(carpeta);

        ol = FXCollections.observableArrayList(regiones.getDistritos());
        cboDistritos.setItems(ol);

        resultados = new Resultados(agrupaciones, carpeta, regiones);

        ol = FXCollections.observableArrayList(resultados.getResultados("00"));
        lvwResultados.setItems(ol);


    }

    public void cambiarUbicacion(ActionEvent actionEvent) {
        DirectoryChooser fc = new DirectoryChooser();
        File dir = fc.showDialog(null);
        if (dir != null) {
            lblCarpeta.setText(dir.getPath());
        }
    }

   public void elegirDistrito(ActionEvent actionEvent) {

        Region region = (Region)cboDistritos.getValue();
        if(region !=null)
            {
            ObservableList ol = FXCollections.observableArrayList(region.getSubregiones());
            cboSecciones.setItems(ol);

            ol = FXCollections.observableArrayList(resultados.getResultados(region.getCodigo()));
            lvwResultados.setItems(ol);
            }
    }

    public void elegirSeccion(ActionEvent actionEvent){
        Region region = (Region)cboSecciones.getValue();
        if(region != null){
            ObservableList ol = FXCollections.observableArrayList(region.getSubregiones());
            cboCircuitos.setItems(ol);

            ol = FXCollections.observableArrayList(resultados.getResultados(region.getCodigo()));
            lvwResultados.setItems(ol);
        }
    }

    public void elegirCircuito(ActionEvent actionEvent){
        Region region = (Region)cboCircuitos.getValue();
        if(region != null){
            ObservableList ol = FXCollections.observableArrayList(region.getSubregiones());
            cboMesas.setItems(ol);

            ol = FXCollections.observableArrayList(resultados.getResultados(region.getCodigo()));
            lvwResultados.setItems(ol);
        }

    }

    public void elegirMesa(ActionEvent actionEvent){

        Region region = (Region) cboMesas.getValue();
        if (region != null){
            ObservableList ol = FXCollections.observableArrayList(resultados.getResultados(region.getCodigo()));
            lvwResultados.setItems(ol);
        }
    }
}
