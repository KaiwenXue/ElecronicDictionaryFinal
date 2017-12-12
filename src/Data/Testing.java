package Data;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Testing {

	public static void main(String[] args){
		ArrayList<String> temp = new ArrayList<String>(Arrays.asList("angel"));

		Set<String> hs = new HashSet<>();
		hs.addAll(temp);
		temp.clear();
		temp.addAll(hs);
		
		HashMap<String, HashMap<String, String>> Dict = WordDictionary.buildDict(temp);
		
		for (String key : Dict.keySet()) {
			System.out.println(key + " : " + Dict.get(key));
		}
		
	     String fileName = "test.mp3"; 
	     try {
	     URL link = new URL("http://audio.oxforddictionaries.com/en/mp3/angel_gb_1.mp3"); 

	     InputStream in = new BufferedInputStream(link.openStream());
	     ByteArrayOutputStream out = new ByteArrayOutputStream();
	     byte[] buf = new byte[1024];
	     int n = 0;
	     while (-1!=(n=in.read(buf)))
	     {
	        out.write(buf, 0, n);
	     }
	     out.close();
	     in.close();
	     byte[] response = out.toByteArray();

	     FileOutputStream fos = new FileOutputStream(fileName);
	     fos.write(response);
	     fos.close();

	     // Create a Player object that realizes the audio
	     FileInputStream fileInputStream = new FileInputStream("test.mp3");
			Player player = new Player(fileInputStream);
			System.out.println("Song is playing...");
			player.play();


		} catch ( IOException | JavaLayerException e) {
				// TODO Auto-generated catch block
		}

	     System.out.println("Finished");
	}

	
}
