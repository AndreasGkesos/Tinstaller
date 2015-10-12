import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class End extends BorderPane {
	
	TextArea textArea;
	
	public End() {
		
		VBox installetionInfo = new VBox(10);
		installetionInfo.setAlignment(Pos.CENTER);
		installetionInfo.setMaxWidth(Double.MAX_VALUE);
		installetionInfo.setMaxHeight(Double.MAX_VALUE);
		
		Label label = new Label("Installing...");
		
		textArea = new TextArea();
		textArea = new TextArea();
		textArea.setPadding(new Insets(0, 5 , 0, 5));
		textArea.setStyle("-fx-font-size: 20;");
		textArea.setEditable(false);
		
		installetionInfo.getChildren().addAll(label, textArea);
		this.setCenter(installetionInfo);
	}

	public void appendText(String text) {
		this.textArea.appendText(text);;
	}	
}