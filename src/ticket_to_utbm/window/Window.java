package ticket_to_utbm.window;

import javafx.application.Application;

import java.lang.String;

import javafx.stage.Stage;

import java.io.FileInputStream; 
 
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Window extends Apllication{
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane pane = new BorderPane();
			
			final Rectangle piocheCachee = new Rectangle(100, 50);
			piocheCachee.setFill(Color.BROWN);
			piocheCachee.setTranslateY(75); 
			piocheCachee.setTranslateX(50); 
			
			final Rectangle carteVisible1 = new Rectangle(100, 50);
			carteVisible1.setFill(Color.RED);
			carteVisible1.setTranslateY(175); 
			carteVisible1.setTranslateX(50); 
			
			final Rectangle carteVisible2 = new Rectangle(100, 50);
			carteVisible2.setFill(Color.BLUE);
			carteVisible2.setTranslateY(250); 
			carteVisible2.setTranslateX(50); 
			
			final Rectangle carteVisible3 = new Rectangle(100, 50);
			carteVisible3.setFill(Color.GREEN);
			carteVisible3.setTranslateY(325); 
			carteVisible3.setTranslateX(50); 
			
			final Rectangle carteVisible4 = new Rectangle(100, 50);
			carteVisible4.setFill(Color.BLACK);
			carteVisible4.setTranslateY(400); 
			carteVisible4.setTranslateX(50); 
			
			final Rectangle carteVisible5 = new Rectangle(100, 50);
			carteVisible5.setFill(Color.PINK);
			carteVisible5.setTranslateY(475); 
			carteVisible5.setTranslateX(50);
			
			final Rectangle carteObjectif = new Rectangle(100, 50);
			carteObjectif.setFill(Color.BROWN);
			carteObjectif.setTranslateY(75); 
			carteObjectif.setTranslateX(1125);
			
			
			ImageView map = new ImageView("map.png");

	        map.setPreserveRatio(true);
	        map.setFitWidth(600);
	        map.setFitHeight(600);
			
			pane.setCenter(map);
			
			pane.getChildren().add(piocheCachee);
			pane.getChildren().add(carteVisible1);
			pane.getChildren().add(carteVisible2);
			pane.getChildren().add(carteVisible3);
			pane.getChildren().add(carteVisible4);
			pane.getChildren().add(carteVisible5);
			pane.getChildren().add(carteObjectif);
			
			
			Scene scene = new Scene(pane,1280,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Tickets to UTBM");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

}
