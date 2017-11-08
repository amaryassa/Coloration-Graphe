/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoriedesgraphesm1informatiqueamaryassa;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    double orgSceneX, orgSceneY;
    int a=0;
    Map<Integer,Circle> CircleMap = new HashMap<Integer,Circle>();
    
     private Circle createCircle(double x, double y, double r, Color color){
     Circle circle = new Circle(x, y, r, color);
    
     CircleMap.put(a, circle);
     a++;
 

    circle.setCursor(Cursor.HAND);
    
     circle.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                circle.setFill(Color.YELLOW);
                System.out.println("id: "+circle.getId());
            }
        });

    circle.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

      Circle c = (Circle) (t.getSource());
      c.toFront();
    });
    circle.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Circle c = (Circle) (t.getSource());

      c.setCenterX(c.getCenterX() + offsetX);
      c.setCenterY(c.getCenterY() + offsetY);

      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
    });  
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
    
    //appelle au Fichier CSS 
    final URL cssURL = getClass().getResource("style.css"); 
    scene.getStylesheets().add(cssURL.toExternalForm());
     Pane paneGauche = new Pane();
    paneGauche.setPrefSize(100, 260);
    paneGauche.getStyleClass().add("paneGauche");

 Button b1 = new Button("B1");
    b1.setId("b1");
    b1.getStyleClass().add("b1");
     b1.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
               
                int MinX=100,MaxX=350,MinY=30,MaxY=300;
                int X = MinX + (int)(Math.random() * ((MaxX - MinX) + 1));
                int Y = MinY + (int)(Math.random() * ((MaxY - MinY) + 1));
                Circle cercle = createCircle(X, Y, 30, Color.RED);
                root.getChildren().add(cercle);
                cercle.toFront();
                
                System.out.println(" Lala!"+CircleMap.get(0));
                
            }
        });
    paneGauche.getChildren().add(b1);
    root.getChildren().add(paneGauche);
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
