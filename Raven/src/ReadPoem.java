
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * 
 * @author angelamcelwee
 *
 */
public class ReadPoem extends Application {
	
	/**
	 * declaring variables for the button, the labels, the text area results pane
	 */
	Button button;
	Label label1;
	Label label2;
	TextArea text;
	
	
	/**
	 * building the FX Stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/**
		 * instantiating the button
		 */
		button = new Button();
		button.setFont(Font.font("Impact",20));
		button.setText("Analyze Poem");
		
		/**
		 * instantiating the labels
		 */
		label1 = new Label();
		label1.setFont(Font.font("Verdana"));
		label1.setText("The Raven");
		label2 = new Label();
		label2.setFont(Font.font("Verdana"));
		label2.setText("By: Edgar Allen Poe");
		
		/**
		 * instantiating the text area
		 */
		text = new TextArea();
		text.setPrefHeight(400);
		text.setPrefWidth(300);
		
		/**
		 * creating the vertical box to stack the objects with appropriate padding
		 */
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.TOP_CENTER);
		layout.setPadding(new Insets(5,0,5,0));
		layout.getChildren().add(button);
		layout.getChildren().add(label1);
		layout.getChildren().add(label2);
		layout.getChildren().add(text);
		
		/**
		 * creating the scene and defining the size
		 */
		Scene scene = new Scene(layout, 300, 600);
		primaryStage.setTitle("Analyze The Raven");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		/**
		 * kicking off the code by setting the action on the button
		 */
		button.setOnAction(event -> {
			button.setText("Complete");
			Document raven = null;
			try {
				raven = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		/**
		 * collecting just the pieces of the web page that we need using HTML tags
		 */
			Elements header1 = raven.getElementsByTag("h1");
			Elements header2 = raven.getElementsByTag("h2");
			Elements chapter = raven.getElementsByClass("chapter");
			
		/**
		 * taking the scraped elements and writing to strings
		 */
		String sHeader1 = header1.toString();
		String sHeader2 = header2.toString();
		String sChapter = chapter.toString();
		
		/**
		 * Concatenating the strings into one string
		 */
		String poem = sHeader1 + sHeader2 + sChapter;
		//System.out.println(poem);
			
		/**
		 * removing all HTML tags, all characters beyond a-z and 0-9, 
		 * converting all to lowercase, 
		 * stripping beginning and ending blank spaces
		 */
		poem = poem.replaceAll("<[^>]*>", " ");
		poem = poem.replaceAll("\\W",  " ");
		poem = poem.toLowerCase();
		poem = poem.strip();
		//System.out.println(poem);
			
		/**
		 * converting the full string to an array of strings
		 */
		String[] words = poem.split("\\s+");
		System.out.println(Arrays.toString(words));
			
		/**
		 * converting the array of strings to an arraylist so that it can 
		 * be passed to countOccurrences
		 */
		ArrayList<String> strList = new ArrayList<String>(Arrays.asList(words));
		
			
		/**
		 * pass the arraylist to the occurrence counter and get a returned unsorted
		 *  map with occurrence
		 */
		Map<String,Integer> unSorted = countOccurrences(strList);
			
		/**
		 * creating an empty linked hashmap in order to insert the entries
		 *  after they are sorted
		 */
		LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
			
		/**
		 * running through the unsorted entries, 
		 * sorting them by value,
		 * inserting into the sorted hashmap
		 */
		unSorted.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
			//System.out.println(sorted);

		/**
		 * printing every entry in the sorted hashmap on a new line
		 */
		for(Map.Entry<String,Integer> entry : sorted.entrySet()) {
			
			
			/**
			 * printing to the text area instead of the console
			 */
			text.setText(text.getText()+entry+"\n");
			//System.out.println(entry);
		}
		});
	}
	
	/**
	 * countOccurrences - method to loop through the arraylist and count occurrences
	 * 
	 * @param list - a string arraylist with the words in the poem
	 * @return treemap of words and occurences
	 */
			public static Map<String, Integer> countOccurrences(ArrayList<String> list) {
				Map<String, Integer> tmap = new TreeMap<String, Integer>();
					for (String st : list) {
						Integer n = tmap.get(st);
						tmap.put(st,  (n == null) ? 1 : n + 1);
					}
				System.out.println(tmap);	
				return tmap;
			}
	
	
	/**
	 * putting it all together in the main
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args)  throws IOException {
		launch(args);
		
	}
	
	

}