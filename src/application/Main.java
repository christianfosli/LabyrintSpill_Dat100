package application;
	
import controller.KeyListener;
import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Spillet;
import view.Labview;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	private Stage owner;
	private MenuBar menuBar;
	private BorderPane root;
	private KeyListener keyListener;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			
			Scene scene = new Scene(root,600,400);
			
			root.prefWidthProperty().bind(scene.widthProperty());
			root.prefHeightProperty().bind(scene.heightProperty());
			
			this.owner = primaryStage;
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			//Last inn menuBar
			FXMLLoader menuLoader = new FXMLLoader();
			MenuController menuController = new MenuController(owner,this);
			menuLoader.setController(menuController);
			menuLoader.setLocation(getClass().getResource("/view/MenuBar.fxml"));
			menuBar = menuLoader.load();
			root.setTop(menuBar);
			
			//Sett opp lytter til aa navigere spill (ArrowKeys eller hjkl):
			keyListener = new KeyListener(menuController);
			scene.setOnKeyPressed(keyListener);
			
			//Klar til aa vise vindu!:
			primaryStage.setTitle("Labyrint Spill - DAT100 Ov9 - av Christian Fosli");
			primaryStage.show();
			
			//Start spill(model) & view til aa vise laborynt:
			Spillet model = new Spillet(menuBar, this);
			Labview view = new Labview(model, root);
			model.initialize(view);
			GridPane viewPane = view.get();
			root.setCenter(viewPane);
			
			menuController.setModel(model);
			keyListener.setModel(model);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getOwner() {
		return owner;
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	public MenuBar getMenuBAR() {
		return menuBar;
	}
	
	public KeyListener getKeyListener() {
		return keyListener;
	}
}