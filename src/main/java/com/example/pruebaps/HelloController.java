package com.example.pruebaps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.text.SimpleDateFormat;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.scene.chart.NumberAxis;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class HelloController {
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private CalbackClient client;
    public int tiempoExtra = 0; //tiempo extra que se va a estar suscrito

    public boolean renovar = false;
    @FXML
    public final XYChart.Series<String, Float> puntos = new XYChart.Series<>();

    @FXML
    private Button botonRenovarSuscripcion;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<String, Float> lineChart;

    @FXML
    private TextField tiempoSegundos;

    @FXML
    private Label titulo;

    @FXML
    public void initialize() throws IOException, TimeoutException {
        this.client = new CalbackClient(this);
        this.client.start();
        XYChart.Series series = new XYChart.Series();
        lineChart.getData().add(puntos);
        // Ponemos eje y fijo
        y.setAutoRanging(false);
        y.setLowerBound(0.53);
        y.setUpperBound(0.69);
    }

    @FXML
    void renovarSuscripcion(ActionEvent event) {
        try{
            this.tiempoExtra = Integer.parseInt(this.tiempoSegundos.getText());
            this.renovar = true;
        }catch (NumberFormatException e){
            System.out.println("Valor no valido");
        }
    }

    public void anadirPunto(Float RR) throws IOException {
        //System.out.println("Numero " + RR);
        Platform.runLater(() -> {
            //si ya hay 60 representados eliminamos el mas antiguo
            if (this.puntos.getData().size() >= 60 ){
                this.puntos.getData().remove(0);
            }
            //incluimos el punto nuevo en la gráfica
            String xValue = simpleDateFormat.format(new Date()); // Convierte la fecha a String
            Float yValue = RR; // Mantén el valor RR como Float

            XYChart.Data<String, Float> dataPoint = new XYChart.Data<>(xValue, yValue);
            puntos.getData().add(dataPoint);
        }
        );
    }

}
