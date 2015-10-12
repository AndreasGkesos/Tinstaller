import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import static java.nio.file.StandardCopyOption.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InstallerMainWindow extends Application {
	
	public static final int PRIMARYSTAGEWIDTH = 650;
	public static final int PRIMARYSTAGEHEIGHT = 400;
	
	private Path saveFolderPath;
	private String jarPath, jarName, faviconName;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("T installer...");
		
		
		BorderPane bp = new BorderPane();
		BaseBorderPane base = new BaseBorderPane();
		bp.setCenter(new WelcomeLayoutI());
		bp.setBottom(base);
		Scene mainScene = new Scene(bp, PRIMARYSTAGEWIDTH, PRIMARYSTAGEHEIGHT);
		
		base.hideBackButton();
		base.hideFinishButton();
		
		// Navigation with buttons
		base.getNextButton().setOnAction( e -> {
			if (bp.getCenter() instanceof WelcomeLayoutI) {
				bp.setCenter(new Save());
				base.unHideBackButton();
				base.hideExitButton();
				base.hideFinishButton();
			}
			else if (bp.getCenter() instanceof Save) {
				saveFolderPath = ((Save) bp.getCenter()).getSaveFolderPath();
				if ( saveFolderPath == null) {
					new Alert(primaryStage, "You must choose where you want to save the files").showAndWait();
					bp.setCenter(new Save());
				} else {
					bp.setCenter(new End());
					
					readTXTFile();
					((End) bp.getCenter()).appendText("Read data.txt file...\t\t\t OK\n");;
					moveFiles();
					((End) bp.getCenter()).appendText("Create direcrory...\t\t\t OK\n");;
					((End) bp.getCenter()).appendText("Move files...\t\t\t OK\n");;
					createShortcut();
					((End) bp.getCenter()).appendText("Create shortcut...\t\t\t OK\n");;
					
					((End) bp.getCenter()).appendText("Finish...\t\t\t OK\n");;
					
					base.hideNextButton();
					base.hideBackButton();
					base.hideExitButton();
					base.unHideFinishButton();
				}
			}
		});
		
		base.getExitButton().setOnAction( e -> {
			if (bp.getCenter() instanceof WelcomeLayoutI) {
				Platform.exit();
			}
		});
		
		base.getBackButton().setOnAction( e -> {
			if (bp.getCenter() instanceof Save) {
				bp.setCenter(new WelcomeLayoutI());
				base.hideBackButton();
				base.hideFinishButton();
				base.unHideExitButton();
			}
		});
		
		base.getFinishButton().setOnAction(e -> {
			if (bp.getCenter() instanceof End) {
				Platform.exit(); // prepei na balw check gia to an exei teleiwsei swsta i diadikasia
			}
		});
		
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private void moveFiles() {
     	final File installationFolderDirectory = new File(saveFolderPath + File.separator + jarName);
     	installationFolderDirectory.mkdir();
		
     	final File installationShortcutDirectory = new File(saveFolderPath + File.separator + jarName + File.separator + "icon");
     	installationShortcutDirectory.mkdir();
     	
		try {
			Files.copy(Paths.get(jarPath + jarName + ".jar"), Paths.get(installationFolderDirectory + File.separator + jarName + ".jar"), REPLACE_EXISTING);
			Files.copy(Paths.get(jarPath + faviconName), Paths.get(installationShortcutDirectory + File.separator + faviconName), REPLACE_EXISTING);
		} catch (IOException e) {
			
		}
	}
	
	private void createShortcut() {
	    String desktopPath = null;
    	desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
		    
	    File shortcut = new File(desktopPath + File.separator + jarName + ".url");
	    BufferedWriter writer;
	    String appPath = saveFolderPath + File.separator + jarName + File.separator + jarName + ".jar";
		try {
			writer = new BufferedWriter(new FileWriter(shortcut));
			writer.write("[InternetShortcut]");
			writer.newLine();
			writer.write("URL=file:\\\\" + appPath);
			writer.newLine();
			writer.write("IconIndex=0");
			writer.newLine();
			writer.write("IconFile=\\\\" + saveFolderPath + File.separator + jarName + File.separator + "icon" + File.separator + faviconName);
			writer.close();
		}
		catch (IOException e) {
		}
	}
	
	private void readTXTFile() {
		BufferedReader in = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("data.txt")));
		StringTokenizer st = null;
		try {
			st = new StringTokenizer(in.readLine(), "*");
		} catch (IOException e) {
		}
		
		if (st.countTokens() == 4) { // favicon has been chosen  by the user
			jarName = st.nextToken();
			st.nextToken(); // skip author name
			faviconName = st.nextToken();
			jarPath = st.nextToken();
		}
		else { // use default favicon
			jarName = st.nextToken();
			st.nextToken(); // skip author name
			faviconName = "defaultfavicon.ico";
			jarPath = st.nextToken();
		}
	}
}