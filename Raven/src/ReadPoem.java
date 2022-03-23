
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReadPoem extends Application {
	
	Button button;
	TextArea text;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		button = new Button();
		button.setText("Analyze Poem");
		text = new TextArea();
		
		

		VBox layout = new VBox(5);
		layout.setAlignment(Pos.TOP_CENTER);
		layout.setPadding(new Insets(5,0,5,0));
		layout.getChildren().add(button);
		layout.getChildren().add(text);
		
		
		Scene scene = new Scene(layout, 300, 300);
		primaryStage.setTitle("Title");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		button.setOnAction(event -> {
			button.setText("Complete");
			Document raven = null;
			try {
				raven = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//collecting just the pieces of the web page that we need using HTML tags
			Elements header1 = raven.getElementsByTag("h1");
			Elements header2 = raven.getElementsByTag("h2");
			Elements chapter = raven.getElementsByClass("chapter");
			
		//taking the scraped elements and writing to strings
		String sHeader1 = header1.toString();
		String sHeader2 = header2.toString();
		String sChapter = chapter.toString();
		
		//Concatenating the strings into one string
		String poem = sHeader1 + sHeader2 + sChapter;
		//System.out.println(poem);
			
		//removing all HTML tags, all characters beyond a-z and 0-9, converting all to lowercase,
		//and stripping beginning and ending blank spaces
		poem = poem.replaceAll("<[^>]*>", " ");
		poem = poem.replaceAll("\\W",  " ");
		poem = poem.toLowerCase();
		poem = poem.strip();
		//System.out.println(poem);
			
		//converting the full string to an array of strings
		String[] words = poem.split("\\s+");
		//System.out.println(Arrays.toString(words));
			
		//converting the array of strings to an arraylist so that it can be passed to countOccurrences
		ArrayList<String> strList = new ArrayList<String>(Arrays.asList(words));
			
		//pass the arraylist to the occurrence counter and get a returned unsorted map with occurrence
		Map<String,Integer> unSorted = countOccurrences(strList);
			
		//creating an empty linked hashmap in order to insert the entries after they are sorted
		LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
			
		//running through the unsorted entries and sorting them by value and inserting into the sorted hashmap
		unSorted.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
			//System.out.println(sorted);
			
		//printing every entry in the sorted hashmap on a new line
		for(Map.Entry<String,Integer> entry : sorted.entrySet()) {
			text.setText(text.getText()+entry+"\n");
			//System.out.println(entry);
		}
		});
	}
	
	//method to loop through the arraylist and count occurrences
			public static Map<String, Integer> countOccurrences(ArrayList<String> list) {
				Map<String, Integer> tmap = new TreeMap<String, Integer>();
					for (String st : list) {
						Integer n = tmap.get(st);
						tmap.put(st,  (n == null) ? 1 : n + 1);
					}
					
				return tmap;
			}
	
	
	
	public static void main(String[] args)  throws IOException {
		launch(args);	
	}
	
	

}