import java.io.File;
import java.nio.file.Path;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;

public class Save extends BorderPane {
	
	private DirectoryChooser directoryChooser;
	private Path saveFolderPath;
	private CheckBox shortcutCheckBox;

	public Save() {
		// Initialize 
		directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
		
		// Vertical Box
		VBox browseItems = new VBox(10);
		browseItems.setAlignment(Pos.CENTER);
		
		// First horizontal Box		
		HBox saveFolderBox = new HBox(10);
		saveFolderBox.setAlignment(Pos.BASELINE_LEFT);
		
		TextField saveFolderTextField = new TextField();
		saveFolderTextField.setMaxHeight(Double.MAX_VALUE);
		saveFolderTextField.setPrefColumnCount(25);
		Button saveFolderButton = new Button("Browse");
		saveFolderButton.setMaxHeight(Double.MAX_VALUE);
		Image saveFolderInfoImage = new Image("information-icon.png");
		ImageView saveFolderInfoImageView = new ImageView(saveFolderInfoImage);
		Label saveFolderLabel = new Label();
		saveFolderLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		saveFolderLabel.setGraphic(saveFolderInfoImageView);
		saveFolderLabel.setTooltip(new Tooltip("You must choose where you want the application to be installed"));
		
		saveFolderButton.setOnAction(e -> {
			saveFolderPath = directoryChooser.showDialog(null).toPath();
			saveFolderTextField.setText(saveFolderPath.toString());			
		});
		
		saveFolderBox.getChildren().addAll(saveFolderTextField, saveFolderButton, saveFolderLabel);
		
		// Second horizontal Box
		HBox shortcutBox = new HBox(10);
		shortcutBox.setAlignment(Pos.BASELINE_LEFT);
		
		shortcutCheckBox = new CheckBox();
		shortcutCheckBox.setScaleShape(true);
		Label shortcutLabel = new Label("Create a desktop shortcut");
		shortcutLabel.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 12));
		
		shortcutBox.getChildren().addAll(shortcutCheckBox, shortcutLabel);

		// Put everything together
		browseItems.setStyle("-fx-background-color: #ffffff;");
		browseItems.setMargin(saveFolderBox, new Insets(0, 0, 0, 130));
		browseItems.setMargin(shortcutBox, new Insets(30, 0, 0, 130));
		
		browseItems.getChildren().addAll(saveFolderBox, shortcutBox);
		this.setCenter(browseItems);
	}

	public Path getSaveFolderPath() {
		return saveFolderPath;
	}
	
	public boolean shortcutCheckBoxIfIsSelected() {
		return shortcutCheckBox.isSelected();
	}
}