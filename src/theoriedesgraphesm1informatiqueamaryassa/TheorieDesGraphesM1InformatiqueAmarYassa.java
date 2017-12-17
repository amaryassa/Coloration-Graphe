/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoriedesgraphesm1informatiqueamaryassa;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author AMAR
 */

public class TheorieDesGraphesM1InformatiqueAmarYassa extends Application {
    double orgSceneX, orgSceneY, myX;
    int a=0;
    int b=0;
    int k=0;
    boolean relie=false;
    Map<Integer,Circle> CircleMap = new HashMap<Integer,Circle>();

    Map<Integer,Circle> CircleSelect = new HashMap<Integer,Circle>();
    Map<Integer,Line> LineMap = new HashMap<Integer,Line>();
    
  private Text createText(Line line, String valeur){
          Text text = new Text();
          text.xProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
          text.yProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
          text.setText(valeur);
          text.setFont(Font.font ("Verdana", 20));
          text.setFill(Color.RED);

    return text;

  }

    private void sommetOrdonneSelonLeNombreDearrete(Map<Integer,Line> lineMap){
          
            String monId="";
            Integer Tableau[]=new Integer[lineMap.size()*2];
                        for (Map.Entry mapentry : lineMap.entrySet()) {
                           monId=monId+lineMap.get(mapentry.getKey()).getId()+"_";
                       
                        
                        }
 System.out.println("lineMap.size(): "+lineMap.size());
                  String values[]=monId.split("_");
 System.out.println("id: "+monId );
 for (HashMap.Entry entry : CircleMap.entrySet())
{

        Pattern pattern = Pattern.compile(""+entry.getKey());
        Matcher  matcher = pattern.matcher(monId);
         int count = 0;
        while (matcher.find())
            count++;

        System.out.println(count); 
 }
 /*
 for (int i=0; i<lineMap.size()*2;i++){

                Tableau[i]=Integer.parseInt(values[i]);
           System.out.println("tableau: "+Tableau[i]);
               }
            System.out.println("--------------");

            Arrays.sort(Tableau);
             for (int i=0; i<lineMap.size()*2;i++){
           System.out.println("tableau: "+Tableau[i]);
               }
*/
  }


     private Circle createCircle(double x, double y, double r, Color color){
          Circle circle = new Circle(x, y, r, color);
          circle.setId(a+"");
          CircleMap.put(a, circle);
          a++;
          circle.setCursor(Cursor.HAND);
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
          circle.toFront();
       return circle;
     }
     
     private Line connect(Circle c1, Circle c2){
         
        String idLine=c1.getId()+"_"+c2.getId();
        Line line = new Line();
          line.startXProperty().bind(c1.centerXProperty());
          line.startYProperty().bind(c1.centerYProperty());
          line.endXProperty().bind(c2.centerXProperty());
          line.endYProperty().bind(c2.centerYProperty());
          line.setStrokeWidth(1);
          //line.setStrokeLineCap(StrokeLineCap.BUTT);
          //line.getStrokeDashArray().setAll(2.0, 5.0);
          line.setStrokeWidth(5);
          line.setId(idLine);
          line.setCursor(Cursor.HAND);
          System.out.println("idLine: "+line.getId());

          LineMap.put(k, line);
          k++;
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

    Button AjouterS = new Button("Ajouter Sommet");
    AjouterS.setId("AjouterS");
    AjouterS.getStyleClass().add("AjouterS");
    
    Button SupprmierS = new Button("Supprimer Sommet");
    SupprmierS.setId("SupprmierS");
    SupprmierS.getStyleClass().add("SupprmierS");
    SupprmierS.setLayoutY(30);
    
    AjouterS.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
               
                int MinX=100,MaxX=350,MinY=30,MaxY=300;
                int X = MinX + (int)(Math.random() * ((MaxX - MinX) + 1));
                int Y = MinY + (int)(Math.random() * ((MaxY - MinY) + 1));
                Circle cercle = createCircle(X, Y, 30, Color.RED);
                root.getChildren().add(cercle);
                cercle.toFront();

                cercle.setOnMouseClicked(new EventHandler<MouseEvent>(){
                  @Override
                    public void handle(MouseEvent t) {
                        CircleSelect.put(b, cercle);
                        cercle.setFill(Color.YELLOW);
                        b++;
                        if (CircleSelect.size()==2 && relie==true){
                            Line line1 = connect(CircleSelect.get(0), CircleSelect.get(1));
                            root.getChildren().add(line1);


                             line1.setOnMouseClicked(new EventHandler<MouseEvent>(){
                            @Override
                              public void handle(MouseEvent k) {
                                System.out.println("id: line select "+line1);
                                String inputValue = JOptionPane.showInputDialog("Entrez une valeur");
                              Text text1 =   createText(line1, inputValue);
                              
                              root.getChildren().add(text1);
                              
                              
                                
                              }});
                            
                            // Text text1 = createText(line1);
                            // root.getChildren().add(text1);
                        }
                        System.out.println("id cercle select: "+cercle);
                        //System.out.println("tableauDeCercleSelectionnee: "+CircleSelect.size());
                        //for (Map.Entry mapentry : CircleSelect.entrySet()) {
                        //System.out.println("clé: "+mapentry.getKey() 
                        //  + " | valeur: " + mapentry.getValue());
                        //CircleSelect.clear();
                        // }
                    }
               });

                
               // System.out.println(" Lala!"+CircleMap);
                
            }

        });
    
    
    Button AjouterA = new Button("Ajouter arrêt");
    AjouterA.setId("AjouterA");
    AjouterA.getStyleClass().add("AjouterA");
    AjouterA.setLayoutY(70);
    
    Button SupprmierA = new Button("Supprimer arrêt");
    SupprmierA.setId("SupprmierA");
    SupprmierA.getStyleClass().add("SupprmierA");
    SupprmierA.setLayoutY(100);
   
    
        AjouterA.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                
              CircleSelect.clear();
              relie=true;
              b=0;
                //System.out.println(CircleMap.get(0));
            }
        });

           SupprmierA.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                

                System.out.println("lineMap: "+LineMap);
                sommetOrdonneSelonLeNombreDearrete(LineMap);
            // CircleMap.get(0).setFill(Color.BLACK);
             //CircleMap.get(1).setFill(Color.GREEN);
             //CircleMap.get(2).setFill(Color.YELLOW);
                //System.out.println(CircleMap.get(0));
            }
        });
     

     
     
   
    paneGauche.getChildren().add(AjouterS);
     paneGauche.getChildren().add(SupprmierS);
    paneGauche.getChildren().add(AjouterA);
    paneGauche.getChildren().add(SupprmierA);


    root.getChildren().add(paneGauche);

     TextField txtNum = new TextField();
    
    txtNum.setMaxWidth(50);
     root.getChildren().add(txtNum);
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
