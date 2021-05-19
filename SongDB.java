/*
 * This project involves implementing a Java program that builds and manages a 
 * database of songs using
a GUI-based user interface implemented with JavaFX.
The application will consist of a single frame that allows a user to add, 
edit, or delete songs from the
database. Here’s a rough prototype for what the window might look like. 
Yours doesn’t have to look
exactly like this…the prototype is provided just to help you envision the 
ultimate application. However
a neat and reasonable-looking GUI is expected.

 */

/*
 * Author: Emanuel Calderon
 */

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.io.*;
import java.util.*;

public class SongDB extends Application 
{
	//main label
	Label labelMainTitle = new Label("Song Database");
	
	// Create the buttons.
	private Button btnAdd = new Button("Add");
	private Button btnEdit = new Button("Edit");
	private Button btnDelete = new Button("Delete");
	private Button btnAccept = new Button("Accept");
	private Button btnCancel = new Button("Cancel");
	private Button btnExit = new Button("Exit");
	
	// Create the text fields.
	private TextField textItemName = new TextField();
	private TextField textItemCode = new TextField();
	private TextField textDescription = new TextField();
	private TextField textArtist = new TextField();
	private TextField textAlbum = new TextField();
	private TextField textPrice = new TextField();
	             
	// Declare an ArrayList.
	private ArrayList<Song> songs = new ArrayList<Song>();
	private static ArrayList<Song> list = new ArrayList<Song>();
	
	// Create a Combobox.
	ObservableList<String> comboboxOL =
			FXCollections.observableArrayList();
	private ComboBox<Song> combobox = new ComboBox<Song>();
	private ComboBox<String> cDefault = new ComboBox<String>();

	// Create labels using JLabel class.
	private Label labelSelect = new Label("Select Song:");
	private Label labelTitle= new Label("Title:");
	private Label labelItemCode = new Label("Item Code:");
	private Label labelDescription = new Label("Description:");
	private Label labelArtist = new Label("Artist:");
	private Label labelAlbum = new Label("Album: ");
	private Label labelPrice = new Label("Price:");
	private Label labelMode = new Label("MODE:");
	private Label userMode = new Label();
	
	//declaring Hbox or horizontal alignment
	private HBox selectSongCBHbox, itemCodeHBox, descriptionHBox, artistHBox, albumHBox,
	priceHBox, buttonsHbox, buttonExitHbox, modeHBox,titleHbox; 
	
	//declaring vbox or vertical stack up
	private VBox mainVbox; 
	
	private Song currentSong;
	
	private static String path;
		
	@Override
    public void start(Stage myStage)
    {
	    
		//logic to get the read in files array
		readSongFromListArrayToSongArray(list);
		
		
		if(songs.isEmpty())
		{
			//if file is empty do this
			cDefault.setPrefWidth(200);
			cDefault.setValue("Database Empty" );
		}
		else
		{
			//if not set the combobox
			combobox.setPrefWidth(200);
		}
		selectSongCBHbox = new HBox(10);
		selectSongCBHbox.setSpacing(17);
		combobox.setOnAction( new ComboBoxHandler() ); //handler for combobox
        // label & text field
		if(songs.isEmpty())
		{
			//if file not read in, meaning 0 for empty do this 
			selectSongCBHbox.getChildren().addAll(labelSelect,cDefault);
		}
		else
		{
			//if file is not empty set combobox
			selectSongCBHbox.getChildren().addAll(labelSelect,combobox);
		}
		textItemCode.setPrefWidth(100);
		itemCodeHBox = new HBox(50);
		itemCodeHBox.setSpacing(23);
        // label & text field
		itemCodeHBox.getChildren().addAll(labelItemCode,textItemCode);
		
		textDescription.setPrefWidth(300);
		descriptionHBox = new HBox(50);
		descriptionHBox.setSpacing(17);
        // label & text field
		descriptionHBox.getChildren().addAll(labelDescription,textDescription);
		
		textArtist.setPrefWidth(300);
		artistHBox = new HBox(50);
		artistHBox.setSpacing(50);
        // label & text field
		artistHBox.getChildren().addAll(labelArtist,textArtist);
		
		textAlbum.setPrefWidth(300);
		albumHBox = new HBox(50);
		albumHBox.setSpacing(41);
        // label & text field
		albumHBox.getChildren().addAll(labelAlbum,textAlbum);
		
		priceHBox = new HBox(50);
		priceHBox.setSpacing(51);
        // label & text field
		priceHBox.getChildren().addAll(labelPrice,textPrice);
	
	    //buttons 
	    btnAdd.setPrefWidth(75);
	    btnEdit.setPrefWidth(75);
	    btnDelete.setPrefWidth(75);
	    btnAccept.setPrefWidth(75);
	    btnCancel.setPrefWidth(75);
	    btnExit.setPrefWidth(75);
	    
	    //another way to check if song is empty 
	    if(songs.isEmpty())
	    {
	    	//if array is empty disable these buttons 
	    	btnEdit.setDisable(true);
	    	btnDelete.setDisable(true);
	    	btnAccept.setDisable(true);
	    	btnCancel.setDisable(true);
	    }
	    else
	    {
	    	btnAccept.setDisable(true);
	    	btnCancel.setDisable(true);
	    }
        // Adding action event to button
	    btnAdd.setOnAction(e -> btnAddClick());
	    btnAccept.setOnAction(e -> btnAcceptClick());
	    btnExit.setOnAction(e -> btnExitClick());
	    btnCancel.setOnAction(e -> btnCancelClick());
	    btnEdit.setOnAction(e -> btnEditClick());
	    btnDelete.setOnAction(e -> btnDeleteClick());
	    
	    buttonsHbox = new HBox(10);
	    buttonsHbox.setPadding(new Insets(10, 10, 10, 10));
	    buttonsHbox.setSpacing(10);
        // Creating border to hbox
        // Adding 5 buttons 
	    buttonsHbox.getChildren().addAll(btnAdd, btnEdit,btnDelete,btnAccept, btnCancel);
	    
	    buttonExitHbox = new HBox(1);
	    buttonExitHbox.setPadding(new Insets(10, 10, 10, 180));
	    buttonExitHbox.setSpacing(10);
	    //adding exit buton in the end 
	    buttonExitHbox.getChildren().addAll(btnExit);
	    
	    modeHBox = new HBox(1);
	    modeHBox.setSpacing(10);
        // label & and changing mainframe mode 
	    modeHBox.getChildren().addAll(labelMode,userMode);
	    userMode.setText("Main Menu");
	    
	    titleHbox = new HBox(10);
	    titleHbox.setAlignment(Pos.CENTER);
		labelMainTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleHbox.getChildren().add(labelMainTitle);
        
        mainVbox = new VBox(10);
        mainVbox.setPadding(new Insets (50));
        //initial gui set up 
        mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,itemCodeHBox,descriptionHBox,
        		artistHBox,albumHBox,priceHBox,buttonsHbox,buttonExitHbox,modeHBox);
        
        //text fields disabled 
        textItemCode.setDisable(true);
		textDescription.setDisable(true);
		textArtist.setDisable(true);
		textAlbum.setDisable(true);
		textPrice.setDisable(true);
                
        //adding main object with lscene
        //main object with lscene
        Scene lscene = new Scene(mainVbox);
        myStage.setTitle("Song Database");
        myStage.setScene(lscene);
        myStage.show();
		
    }
	    
	// You only need main when running from an IDE with limited
	// JavaFX support. Not needed when running from command line
	    public static void main( String [] args ) 
	    {
	    	// setup variables for file logic.
	    	String choice;
	    //	String fileName;
	    	
	    	//input for new file creation
	    	Scanner input = new Scanner(System.in);
	    	
	    	//enters database file name 
	    	System.out.print("Enter the database file name:");
	  //  	fileName = input.nextLine();
			
	    	//should be able to just type "SongDB mySongDB.txt"
	    	String fileName = args[0]; 
	    	
	    	File file = new File(fileName);
	    	
	    	path = file.getAbsolutePath();
	    	
	    	
	    	try(Scanner infile = new Scanner(new File(path)))
	    	{
	    		//statement on file location 	
	    		System.out.println();
	    		System.out.println("Database located:");
	    		System.out.println(path);
	    		// Read the contents of the File.
	    		while (infile.hasNextLine())
	    		{	    			
	    			String line = infile.nextLine();
	    			String[] item = line.split(",");
	    		
	    			// Add the songs in the file to the
	    			// songs list.
	    			Song readSong = new Song(item[0], item[1], item[2],
	    					item[3],item[4], Double.parseDouble(item[5]));
	    		    list.add(readSong);
	    			
	    			
	    		}

	    		launch( args ); // launch the application 
	    		infile.close(); //closes file
	    	
	    			
	    	}// closes try
	    	
	    	// If the file is not found, then prompt
	    	// the user to create a new database.
	    	catch (FileNotFoundException e)
	    	{
	    		System.out.println("The database file does not exist.");
	    		System.out.println("Do you want to create a new one? (Enter Y/N):");
	    		choice = input.nextLine();
	    		
	    		if("Y".equals(choice) || "y".equals(choice))
	    		{
	    			launch( args ); // launch the application 	
	    		}
	    		else
	    		{
	    			System.out.println("Exiting...");
	    			System.exit(0);
	    		}
	    		input.close();
	    		
	    	}// closes catch
	 	    
	    }//closes main
	    
	    private ArrayList<Song> readSongFromListArrayToSongArray(ArrayList<Song> list)
	    {
	    	// Create an ArrayList.
	    	songs = new ArrayList<Song>();
	    	// Find the size of the ArrayList.
	    	
	    	if (list.size() > 0)
	    	{
	    		// If the size is greater than 0, then add
	    		// the song in the combobox and sons List.
	    		for (Song song : list)
	    		{
	    			//adding to song array
	    			songs.add(song);
	    			//adding to combobox 
	    			combobox.getItems().add(song);
	    		}
	    	}
	    	// If condition fails
	    	else
	    	{
	    		//does nothing
	    	}
	    return songs;
	    }// closes method for list array to song array
	    
	    //add button 
	    private void btnAddClick()
	    {
	    	//sets mode to add
	    	userMode.setText("Add");
	    	
	    	//inittially places blanks in text
	    	textItemName.setText("");
	    	textItemCode.setText("");
	    	textDescription.setText("");
	    	textArtist.setText("");
	    	textAlbum.setText("");
	    	textPrice.setText("");
	    	
	    	//clears the gui
	    	mainVbox.getChildren().clear();
	    	
	    	//adds title ability to add text 
	    	selectSongCBHbox = new HBox(10);
			selectSongCBHbox.setSpacing(56);
			selectSongCBHbox.getChildren().addAll(labelTitle,textItemName);
	    	
			//adds in new gui
	    	mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,
	    			itemCodeHBox,descriptionHBox,artistHBox,albumHBox,
	    			priceHBox,buttonsHbox,buttonExitHbox,modeHBox);
	    	
	    	//buttons to appear
	    	btnAdd.setDisable(true);
	    	btnEdit.setDisable(true);
	    	btnDelete.setDisable(true);
	    	btnAccept.setDisable(false);
	    	btnCancel.setDisable(false);
	    	
	    	//allow text entry
	    	textItemName.setDisable(false);
	    	textItemCode.setDisable(false);
    		textDescription.setDisable(false);
    		textArtist.setDisable(false);
    		textAlbum.setDisable(false);
    		textPrice.setDisable(false);
	    	
	    	
	    }
	    private void btnDeleteClick()
	    {	
	    	//mode to delete
	    	userMode.setText("Delete");
	    	
	    	//buttons to disable 
	    	btnAdd.setDisable(true);
	    	btnEdit.setDisable(true);
	    	btnDelete.setDisable(true);
	    	btnAccept.setDisable(false);
	    	btnCancel.setDisable(false);
	    	
	    	//no entry allowed 
	    	combobox.setDisable(true);
	    	textItemName.setDisable(true);
	    	textItemCode.setDisable(true);
	    	textDescription.setDisable(true);
	    	textArtist.setDisable(true);
	    	textAlbum.setDisable(true);
	    	textPrice.setDisable(true);
	    	
	    }
	    private void btnAcceptClick()
	    {	    	
	    	if(userMode.getText() == "Add")
	    	{
	    		//logic for capturing errors
	    		int error = 0;
	    		
	    		//get text fields 
    			String itemName = textItemName.getText();
    			String itemCode = textItemCode.getText();
    			String description = textDescription.getText();
   		    	String artist = textArtist.getText();
   		    	String album = textAlbum.getText();    		    	
   		    	String priceText = textPrice.getText();
   		    	double price = 0;
   		    	
   		    	//logic for if user puts string to double
   		    	try
   		    	{
   		    		price = Double.parseDouble(priceText);
   		    	}
   		    	catch (NumberFormatException e) 
   		    	{
   		    		error = 1;
					userMode.setText("ALERT! ENTER NUMBER FOR PRICE, "
							+ "SONG NOT ADDED, TRY AGAIN");
                }
   		    	//if single song 
   		    	if(" ".equals(album) || "".equals(album))
   		    	{
   		    		album = "None";
   		    	}
   		    	
   		    	//sift through array to find identical item code
	    		for(int i = 0; i < songs.size(); i++)
	    		{
	    			Song holder = songs.get(i);
    				if(holder.getSongItemCode().equals(itemCode))
    				{
    					//error logic
    					error = 1;
    					userMode.setText("ALERT! ITEM CODE IN USE, "
    							+ "SONG NOT ADDED, TRY AGAIN");
    				}
	    		}	
	    		
	    		if("".equals(itemName) || "".equals(itemCode) || "".equals(description) ||
						"".equals(artist) || "".equals(priceText))
				{
					//error logic
					error = 1;
					userMode.setText("ALERT! FIELDS LEFT BLANK, SONG NOT ADDED,"
							+ " TRY AGAIN" );
				}
	    		
	    		
	    		
	    		//why i used error as 0 or 1 
	    		if(error == 1)
	    		{
	    			combobox.setVisibleRowCount(7);
	    			mainVbox.getChildren().clear();
		    		
		    		selectSongCBHbox = new HBox(10);
		    		selectSongCBHbox.setSpacing(17);
		    		combobox.setPrefWidth(200);
			
		    		selectSongCBHbox.getChildren().addAll(labelSelect,combobox);
		    		mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,
		    				itemCodeHBox,descriptionHBox,artistHBox,
		    				albumHBox,priceHBox,buttonsHbox,
		    				buttonExitHbox,modeHBox);
	    		
		    		Song songNameDefault = null;
	    		
		    		combobox.getSelectionModel().select(songNameDefault);
	    		
		    		btnAdd.setDisable(false);
		    		btnEdit.setDisable(false);
		    		btnDelete.setDisable(false);
		    		btnAccept.setDisable(true);
		    		btnCancel.setDisable(true);
	    		
		    		textItemName.setText("");
		    		textItemCode.setText("");
		    		textDescription.setText("");
		    		textArtist.setText("");
		    		textAlbum.setText("");
		    		textPrice.setText("");
	    		    		
		    		textItemCode.setDisable(true);
		    		textDescription.setDisable(true);
		    		textArtist.setDisable(true);
		    		textAlbum.setDisable(true);
		    		textPrice.setDisable(true);
		    		
		    		error = 0;//change back error to 0
	    		}
	    		else //add the song 
				{
					userMode.setText("Main Menu");
					
					//add the atributes 
					Song oneSong = new Song(itemName,itemCode, description,
	   		    			artist, album, price);	   		    	
					
	   		    	songs.add(songs.size(), oneSong);	//add to next location in list
	   		    	combobox.getItems().add(oneSong);	//add to combo
	   		    	combobox.setVisibleRowCount(7);		// in case things get big
		    		
	   		    	//below resets the gui
		    		mainVbox.getChildren().clear();
		    		selectSongCBHbox = new HBox(10);
		    		selectSongCBHbox.setSpacing(17);
		    		combobox.setPrefWidth(200);
		    		selectSongCBHbox.getChildren().addAll(labelSelect,combobox);
		    		mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,
		    				itemCodeHBox,descriptionHBox,artistHBox,
		    				albumHBox,priceHBox,buttonsHbox,
		    				buttonExitHbox,modeHBox);
		    		//resets to blanck space
		    		Song songNameDefault = null; 
		    		combobox.getSelectionModel().select(songNameDefault);
		    		
		    		//disables what i dont need 
		    		btnAdd.setDisable(false);
		    		btnEdit.setDisable(false);
		    		btnDelete.setDisable(false);
		    		btnAccept.setDisable(true);
		    		btnCancel.setDisable(true);
	    		
		    		//puts to empty space 
		    		textItemName.setText("");
		    		textItemCode.setText("");
		    		textDescription.setText("");
		    		textArtist.setText("");
		    		textAlbum.setText("");
		    		textPrice.setText("");
	    		    		
		    		//disables what i dont need 
		    		textItemCode.setDisable(true);
		    		textDescription.setDisable(true);
		    		textArtist.setDisable(true);
		    		textAlbum.setDisable(true);
		    		textPrice.setDisable(true);
				}
	    	}
	    	else if(userMode.getText() == "Edit")
	    	{	    		
	    		//logic for capturing errors
	    		int error = 0;
	    		
	    		for(int i = 0; i < songs.size(); i++)
	    		{
	    			//sifts through value 
	    			if(songs.get(i) == combobox.getValue())
	    			{
	    				Song holderSong = songs.get(i);
	    				String holderSong2 = holderSong.toString();
	    				
	    				Song holderIC = songs.get(i);
	    				String holderIC2 = holderIC.getSongItemCode();
	    							
	    				String description = textDescription.getText();
	    		    	String artist = textArtist.getText();
	    		    	String album = textAlbum.getText();
	    		    	String priceText = textPrice.getText();
	       		    	double price = 0;
	       		    	
	       		    	//logic for if user puts string to double
	       		    	try
	       		    	{
	       		    		price = Double.parseDouble(priceText);
	       		    	}
	       		    	catch (NumberFormatException e) 
	       		    	{
	       		    		error = 1;
	    					userMode.setText("ALERT! ENTER NUMBER FOR PRICE, "
	    							+ "SONG NOT ADDED, TRY AGAIN");
	                    }
	       		    	
	       		    	if( "".equals(description) ||"".equals(artist) ||
	       		    			"".equals(priceText))
	    				{
	    					//error logic
	    					error = 1;
	    					userMode.setText("ALERT! FIELDS LEFT BLANK, SONG NOT ADDED,"
	    							+ " TRY AGAIN" );
	    				}
	    		    	
	       		    	if(error == 1)
	    	    		{
	       		    		combobox.setVisibleRowCount(7);
		    	    		
		    	    		mainVbox.getChildren().clear();
		    	    		combobox.getItems();
		    	    		selectSongCBHbox = new HBox(10);
		    	    		selectSongCBHbox.setSpacing(17);
		    	    		combobox.setPrefWidth(200);
		    			
		    	    		selectSongCBHbox.getChildren().addAll(labelSelect,combobox);
		    	    		mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,
		    	    				itemCodeHBox,descriptionHBox,artistHBox,
		    	    				albumHBox,priceHBox,buttonsHbox,
		    	    				buttonExitHbox,modeHBox);
		    	    		
	       		    		Song songNameDefault = null;
	       		    		
	       		    		combobox.getSelectionModel().select(songNameDefault);
	       		    		
	       		    		textItemCode.setText("");
	       			    	textDescription.setText("");
	       			    	textArtist.setText("");
	       			    	textAlbum.setText("");
	       			    	textPrice.setText("");
	       			    	
	       		    		btnAdd.setDisable(false);
	       		    		btnEdit.setDisable(false);
	       		    		btnDelete.setDisable(false);
	       		    		btnAccept.setDisable(true);
	       		    		btnCancel.setDisable(true);
	       		    	
	       		    		textItemCode.setDisable(true);
	       		    		textDescription.setDisable(true);
	       		    		textArtist.setDisable(true);
	       		    		textAlbum.setDisable(true);
	       		    		textPrice.setDisable(true);
	    	    		}
	       		    	else
	       		    	{
	       		    		//songs added
	       		    		Song oneSong = new Song(holderSong2,holderIC2, 
	       		    				description,artist, album, price);
		    	    		
	       		    		//replacing song and placing in combobox 
		    		    	songs.set(i, oneSong);
		    		    	combobox.getItems().set(i,oneSong);
		    		    		    	    	
		    		    	combobox.setVisibleRowCount(7);			//setting how much to view
		    	    		
		    		    	mainVbox.getChildren().clear();
		    	    		combobox.getItems();
		    	    		selectSongCBHbox = new HBox(10);
		    	    		selectSongCBHbox.setSpacing(17);
		    	    		combobox.setPrefWidth(200);
		    	    		
		    	    		//reinitializing gui
		    	    		selectSongCBHbox.getChildren().addAll(labelSelect,combobox);
		    	    		mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,
		    	    				itemCodeHBox,descriptionHBox,artistHBox,
		    	    				albumHBox,priceHBox,buttonsHbox,
		    	    				buttonExitHbox,modeHBox);
		    	    		
		    	    		Song songNameDefault = null;
	       		    		
	       		    		combobox.getSelectionModel().select(songNameDefault);
	       		    		
	       		    		//set texts
	       		    		textItemCode.setText("");
	       			    	textDescription.setText("");
	       			    	textArtist.setText("");
	       			    	textAlbum.setText("");
	       			    	textPrice.setText("");
	       			    	
	       			    	//set buttons
	       		    		btnAdd.setDisable(false);
	       		    		btnEdit.setDisable(false);
	       		    		btnDelete.setDisable(false);
	       		    		btnAccept.setDisable(true);
	       		    		btnCancel.setDisable(true);
	       		    		
	       		    		//set text fields
	       		    		textItemCode.setDisable(true);
	       		    		textDescription.setDisable(true);
	       		    		textArtist.setDisable(true);
	       		    		textAlbum.setDisable(true);
	       		    		textPrice.setDisable(true);
	       		    		
	       		    		userMode.setText("Main Menu"); 			//set mode
	       		    	}
	    			}
	    		}
	    		
		    	
	    	}
	    	else if(userMode.getText() == "Delete")
	    	{
	    		for(int i = 0; i < songs.size(); i++)
	    		{
	    			if(songs.get(i) == combobox.getValue())
	    			{    				
	/*    				System.out.println(songs.get(i));
	    				
	    				System.out.println(songs.size());*/
	    				
	    				combobox.getItems().remove(combobox.getValue());    				
	    				songs.remove(i);
	    				
	    				combobox.setVisibleRowCount(7);
	    	    		
	    	    		mainVbox.getChildren().clear();
	    	    		
	    	    		selectSongCBHbox = new HBox(10);
	    	    		selectSongCBHbox.setSpacing(17);
	    	    		combobox.setPrefWidth(200);
	    			
	    	    		selectSongCBHbox.getChildren().addAll(labelSelect,combobox);
	    	    		mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,itemCodeHBox,
	    	    				descriptionHBox,artistHBox,albumHBox,priceHBox,
	    	    				buttonsHbox,buttonExitHbox,modeHBox);
	    			}
	    		}
	    		userMode.setText("Main Menu");
	    		
	    		Song songNameDefault = null;
	    		
	    		combobox.getSelectionModel().select(songNameDefault);
	    		
	    		textItemName.setText("");
	    		textItemCode.setText("");
		    	textDescription.setText("");
		    	textArtist.setText("");
		    	textAlbum.setText("");
		    	textPrice.setText("");
		    	
	    		btnAdd.setDisable(false);
	    		btnEdit.setDisable(false);
	    		btnDelete.setDisable(false);
	    		btnAccept.setDisable(true);
	    		btnCancel.setDisable(true);
	    			    	
	    		combobox.setDisable(false);
	    		textItemCode.setDisable(true);
	    		textDescription.setDisable(true);
	    		textArtist.setDisable(true);
	    		textAlbum.setDisable(true);
	    		textPrice.setDisable(true);
	    	}
	    
	    }
	    private void btnCancelClick()
	    {
	    	mainVbox.getChildren().clear();
	    	
	    	selectSongCBHbox = new HBox(10);
			selectSongCBHbox.setSpacing(17);
			combobox.setPrefWidth(200);			
			selectSongCBHbox.getChildren().addAll(labelSelect,combobox);
			
	    	mainVbox.getChildren().addAll(titleHbox,selectSongCBHbox,
	    			itemCodeHBox,descriptionHBox,artistHBox,
	    			albumHBox,priceHBox,buttonsHbox,
	    			buttonExitHbox,modeHBox);
	    	
	    	btnAdd.setDisable(false);
	    	btnEdit.setDisable(false);
	    	btnDelete.setDisable(false);
	    	btnAccept.setDisable(true);
	    	btnCancel.setDisable(true);
	    	
	    	userMode.setText("Main Menu");
	    	
	    	Song songNameDefault = null;
    		
    		combobox.getSelectionModel().select(songNameDefault);
	    	
	    	textItemCode.setText("");
	    	textDescription.setText("");
	    	textArtist.setText("");
	    	textAlbum.setText("");
	    	textPrice.setText("");
	    	
	    	combobox.setDisable(false);
	    	textItemCode.setDisable(true);
	    	textDescription.setDisable(true);
	    	textArtist.setDisable(true);
	    	textAlbum.setDisable(true);
	    	textPrice.setDisable(true);
	    }
	    
	    private void btnExitClick()
	    {
	    	String fileName = "SongDB.txt";
	    	
	    	File file = new File(fileName);
	    	
	    	String path = file.getAbsolutePath();
    		
    		try(PrintWriter out = new PrintWriter(path))
    		{
    			for(int i = 0; i < songs.size(); i++)
	    		{
    				Song holder = songs.get(i);
    				out.print(String.valueOf(holder.getSongName()) + ",");
    				out.print(String.valueOf(holder.getSongItemCode()) + ",");
    				out.print(String.valueOf(holder.getSongDescription()) + ",");
    				out.print(String.valueOf(holder.getSongArtist()) + ",");
    				out.print(String.valueOf(holder.getSongAlbum()) + ",");
    				out.print(String.valueOf(holder.getSongPrice())+ "\n");
	    		}
    		} 
    		catch (FileNotFoundException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		System.out.println();
    		System.out.println("Database saved:");
    		System.out.println(path);
    		
	    	Stage stage = (Stage) btnExit.getScene().getWindow();
	        stage.close();
	    }
	    
	    //combobox handler for when it is picked in gui 
	    class ComboBoxHandler implements EventHandler<ActionEvent>
	    {
	    	public void handle( ActionEvent e ) 
	    	{
	    		
	    		currentSong = (Song) combobox.getValue();
	    		
	    		
	    		for(int i = 0; i < songs.size(); i++)
	    		{
	    			if(songs.get(i) == combobox.getValue())
	    			{
	    				
	    				textItemCode.setText(currentSong.getSongItemCode());
	    		    	textDescription.setText(currentSong.getSongDescription());
	    		    	textArtist.setText(currentSong.getSongArtist());
	    		    	textAlbum.setText(currentSong.getSongAlbum());
	    		    	textPrice.setText("" + currentSong.getSongPrice());
	    			}
	    		}
	    	} 
	    }
	    //sets up edit gui 
    	private void btnEditClick()
	    {	
    		userMode.setText("Edit");
    		
    		textItemCode.setDisable(true);
	    	textDescription.setDisable(false);
	    	textArtist.setDisable(false);
	    	textAlbum.setDisable(false);
	    	textPrice.setDisable(false);
    		
    		currentSong = (Song) combobox.getValue();
    		
	    	//finds the values selected 
    		for(int i = 0; i < songs.size(); i++)
    		{
    			if(songs.get(i) == combobox.getValue())
    			{
    				
    				textItemCode.setText(currentSong.getSongItemCode());
    		    	textDescription.setText(currentSong.getSongDescription());
    		    	textArtist.setText(currentSong.getSongArtist());
    		    	textAlbum.setText(currentSong.getSongAlbum());
    		    	textPrice.setText("" + currentSong.getSongPrice());
    		    	textDescription.setEditable(true);
    		    	textArtist.setEditable(true);
    		    	textAlbum.setEditable(true);
    		    	textPrice.setEditable(true);
    			}
    		}
	    	
	    	btnAdd.setDisable(true);
	    	btnEdit.setDisable(true);
	    	btnDelete.setDisable(true);
	    	btnAccept.setDisable(false);
	    	btnCancel.setDisable(false);
	    
	    }
}
	    	

	    


