/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoriedesgraphesm1informatiqueamaryassa;

import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author AMAR
 */
public class TheorieDesGraphesM1InformatiqueAmarYassa extends Application {
     private Circle createCircle(double x, double y, double r, Color color){
     Circle circle = new Circle(x, y, r, color);   
     return circle;
     }
     
     private Line connect(Circle c1, Circle c2){
        Line line = new Line();
         
        return line;
     }
  @Override
  public void start(Stage primaryStage) {
    Group root = new Group();
    Scene scene = new Scene(root, 500, 260);
    
    //Fichier CSS 
    final URL cssURL = getClass().getResource("style.css"); 
    scene.getStylesheets().add(cssURL.toExternalForm());
     

      primaryStage.setScene(scene);
    
    primaryStage.show();
  } 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
