import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WelcomeLayoutI extends BorderPane {

	public WelcomeLayoutI() {
		
		VBox imageBox = new VBox();
		Image image = new Image("Tlogo.png");
		ImageView imageView = new ImageView(image);
		imageBox.setAlignment(Pos.CENTER);
		imageBox.setMargin(imageView, new Insets(20));
		imageBox.setStyle("-fx-background-color: #ffffff;");
		
		Image infoImage = new Image("information-icon.png");
		ImageView infoImageView = new ImageView(infoImage);
		Label label = new Label();
		label.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		label.setGraphic(infoImageView);
		label.setTooltip(new Tooltip("This project is developed by Andreas Gkesos"));

		imageBox.getChildren().addAll(imageView, label);
		this.setLeft(imageBox);
		
		VBox labelBox = new VBox();
		Label welcomeLabel = new Label("Welcome to T Installer !!!");
		labelBox.setAlignment(Pos.CENTER);
		labelBox.setStyle("-fx-background-color: #ffffff;");
		welcomeLabel.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 20));
	    final Reflection reflection = new Reflection();
	    reflection.setFraction(1.0);
	    welcomeLabel.setEffect(reflection);
	    
	    Label infoLabel = new Label();
	    infoLabel.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 12));
	    // Read application name and author name from data.txt
	    BufferedReader in = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("data.txt")));
	    StringTokenizer st = null;
	    try {
	    	st = new StringTokenizer(in.readLine(), "*");
		} catch (IOException e) {
		}
	    infoLabel.setText("Application: " + st.nextToken() + "\nCreated by: " + st.nextToken());
	    
	    labelBox.setMargin(infoLabel, new Insets(50, 0, 0, 0));
		labelBox.getChildren().addAll(welcomeLabel, infoLabel);
		
		this.setCenter(labelBox);
	}
}