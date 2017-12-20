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
import java.util.Random;
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
 * @author AMAR YASSA M1 Informatique Paris 8
 */

public class TheorieDesGraphesM1InformatiqueAmarYassa extends Application {
    
    double orgSceneX, orgSceneY, myX;
    private final Random rng = new Random();
    int a=0;
    int b=0;
    int k=0;
    boolean relie=false;
    Map<Integer,Circle> CircleMap = new HashMap<Integer,Circle>();
    Map<Integer,Circle> CircleSelect = new HashMap<Integer,Circle>();
    Map<Integer,Line> LineMap = new HashMap<Integer,Line>();
    Map<Integer,Integer> SommetColoree = new HashMap<Integer,Integer>();
    Map<Integer,Integer> CircleOrderByArret = new HashMap<Integer,Integer>();
    
    private Color randomColor() {
          return new Color(rng.nextDouble(), rng.nextDouble(), rng.nextDouble(), 1);
      }
    private Text createText(Line line, String valeur){
            Text text = new Text();
            text.xProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
            text.yProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
            text.setText(valeur);
            text.setFont(Font.font ("Verdana", 20));
            text.setFill(Color.RED);
            return text;
    }




    private void AfficherMatrice(int [][] maMatrice, int taille ){
        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){
            System.out.print(" "+maMatrice[i][j]);}
            System.out.println("");
            }
   }
    private void   AfficheTable(Integer [] tableau, int taille){
     for (int i=0; i<taille;i++){
         System.out.print("Tableau:    ");
          System.out.print(" "+tableau[i]);
               }
     System.out.println("");
    }
    private int [][] RemplirLaMatriceAvecLesCouleurs(int [][] maMatrice,String allId, int taille){
        System.out.println("allId: "+allId);
        allId=allId.substring(0,allId.length()-1);
        System.out.println("allId: "+allId);
        String []  values2=allId.split(",");
        for(int i=0; i< values2.length ;i++){
            String [] values3= values2[i].split("_");
            int monI= Integer.parseInt(values3[0]);
            int monJ=Integer.parseInt(values3[1]);
            maMatrice[monI][monJ]=1;
            maMatrice[monJ][monI]=1;
        }
                
        System.out.println("-----------Mat Après------------");      
        AfficherMatrice(maMatrice,taille);  
    
        return maMatrice;
    }
    private Integer[]  GetNombreArretPourChaqueSommet(Map<Integer,Circle> CircleMap, String maChaine){
          Integer TableauSommetNonOrdonne[]=new Integer[CircleMap.size()];
          for (HashMap.Entry entry : CircleMap.entrySet())
            {
                Pattern pattern = Pattern.compile(""+entry.getKey());
                Matcher  matcher = pattern.matcher(maChaine);
                int count = 0;
                while (matcher.find())
                    count++;
                
                int numCercle=(int)entry.getKey();
                TableauSommetNonOrdonne[numCercle]=count;
                System.out.println(entry.getKey()+" "+count);
                //  CircleOrderByArret.put(numCercle,count );
             }
          return TableauSommetNonOrdonne;
            }
    private Integer[]  ordreDecroissantDesSommetParRapportauNombreArrete(Integer[] TableauOrgine,Integer [] TableauTempo,int taille){
            Integer TableauCercleOrdonne[]=new Integer[taille];
            Arrays.sort(TableauTempo, Collections.reverseOrder());
            System.out.print("-----------TableauOrgine------------");
            AfficheTable(TableauOrgine,taille);
            System.out.print("-----------TableauTempo------------");
            AfficheTable(TableauTempo,taille);
            
             for (int i=0; i<taille;i++){
                 int j=0;
                 while(TableauTempo[i]!=TableauOrgine[j] && j<taille ){
                     j++;
                 }
                 TableauOrgine[j]=-999;
                 TableauCercleOrdonne[i]=j;
             }
            System.out.print("-----------TableauCercleOrdonnee------------");
            AfficheTable(TableauCercleOrdonne,taille);

            return TableauCercleOrdonne;
    }
    private void sommetOrdonneSelonLeNombreDearrete(Map<Integer,Line> lineMap){
            int maMatrice[][] = new int[CircleMap.size()][CircleMap.size()];
            String monId="";
            String allId="";
            Integer TableauOrgine[]=new Integer[CircleMap.size()];
            Integer TableauTempo[]=new Integer[CircleMap.size()];
            Integer TableauCercleOrdonnee[]=new Integer[CircleMap.size()];
           
              //initialisatin de la matrice avec des 0
            for(int i=0;i<CircleMap.size();i++){
                for(int j=0;j<CircleMap.size();j++){
                 maMatrice[i][j]=0;
                }
            } 
            AfficherMatrice(maMatrice,CircleMap.size());
              //Aucun sommet n'est coloré
            for(int j=0;j<CircleMap.size();j++){
                SommetColoree.put(j,0);
            }

              //Loop sur le HashMap des Line pour avoir les id de toutes les lignes dans un String
            for (Map.Entry mapentry : lineMap.entrySet()) {
               monId=monId+lineMap.get(mapentry.getKey()).getId()+"_";
               allId=allId+lineMap.get(mapentry.getKey()).getId()+",";
            }
            System.out.println("Liste: "+monId.length());
            if(monId.length()>0){
                TableauOrgine=GetNombreArretPourChaqueSommet(CircleMap,monId );
                TableauTempo=GetNombreArretPourChaqueSommet(CircleMap,monId );
                TableauCercleOrdonnee=ordreDecroissantDesSommetParRapportauNombreArrete(TableauOrgine,TableauTempo,CircleMap.size());
            }                    
            System.out.println("------------MAt Avant-----------");
            AfficherMatrice(maMatrice,CircleMap.size());
            if(allId.length()>0){
                maMatrice=RemplirLaMatriceAvecLesCouleurs(maMatrice,allId,CircleMap.size() )  ;
            }        
            
           for(int i=0;i<CircleMap.size();i++){
               if(allId.length()>0){
                k=TableauCercleOrdonnee[i];
               }else{
               k=i;
               }
               Color couleur=randomColor();
                  for(int j=0;j<CircleMap.size();j++){
                      if(maMatrice[k][j]==0 && SommetColoree.get(j)!=1){
                        CircleMap.get(j).setFill(couleur);
                        SommetColoree.put(j, 1);
                      }
                  }     
              }
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
          line.setStrokeWidth(2);
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
      Scene scene = new Scene(root, 500, 500);
      
      //appelle au Fichier CSS 
      final URL cssURL = getClass().getResource("style.css"); 
      scene.getStylesheets().add(cssURL.toExternalForm());
      Pane paneGauche = new Pane();
      paneGauche.setPrefSize(120, 500);
      paneGauche.getStyleClass().add("paneGauche");
      Button AjouterS = new Button("Ajouter Sommet");
      AjouterS.setId("AjouterS");
      AjouterS.setPrefWidth(120);
      AjouterS.setPrefHeight(40);
      AjouterS.setLayoutY(50);
      AjouterS.getStyleClass().add("AjouterS");
      AjouterS.setOnAction(new EventHandler<ActionEvent>() { 
              @Override
              public void handle(ActionEvent event) {
                 
                  int MinX=150,MaxX=400,MinY=30,MaxY=400;
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
                          }
                          System.out.println("id cercle select: "+cercle);
                         
                      }
                 });       
              }
          }); 
      Button AjouterA = new Button("Ajouter arrêt");
      AjouterA.setId("AjouterA");
      AjouterA.getStyleClass().add("AjouterA");
      AjouterA.setLayoutY(200);
      AjouterA.setPrefWidth(120);
      AjouterA.setPrefHeight(40);
      Button ColoreGraphe = new Button("Colore Graphe");
      ColoreGraphe.setId("ColoreGraphe");
      ColoreGraphe.getStyleClass().add("ColoreGraphe");
      ColoreGraphe.setLayoutY(350);
      ColoreGraphe.setPrefWidth(120);
      ColoreGraphe.setPrefHeight(40);
     
      AjouterA.setOnAction(new EventHandler<ActionEvent>() { 
          @Override
          public void handle(ActionEvent event) {
              
            CircleSelect.clear();
            relie=true;
            b=0;
              //System.out.println(CircleMap.get(0));
          }
      });
      ColoreGraphe.setOnAction(new EventHandler<ActionEvent>() { 
        @Override
        public void handle(ActionEvent event) {
            System.out.println("lineMap: "+LineMap);
            sommetOrdonneSelonLeNombreDearrete(LineMap);
          }
      });


      paneGauche.getChildren().add(AjouterS);
      paneGauche.getChildren().add(AjouterA);
      paneGauche.getChildren().add(ColoreGraphe);

      root.getChildren().add(paneGauche);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Amar YASSA Théorie Des Graphes Coloration Sommet");
      primaryStage.show();
    } 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
