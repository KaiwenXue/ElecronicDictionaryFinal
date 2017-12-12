package Data;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;


public class WordDictionary {
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
	Map<String, Word> DictList;
	ArrayList<String> wordList;

	public WordDictionary() {
		Map<String, String> Dict = new HashMap<String, String>();
	}
	
	public static HashMap<String, HashMap<String, String>> buildDict(ArrayList<String> list) {
		HashMap<String, HashMap<String, String>> DictList = new HashMap<String, HashMap<String, String>>();
		int arraySize = list.size();
		for(int i=0; i< arraySize; i++) {
			
			String word_id = list.get(i).toLowerCase();
			String test = WordDictionary.HttpRequest("https://od-api.oxforddictionaries.com:443/api/v1/entries/en/"+word_id);
			try {
				JSONObject obj = new JSONObject(test);
				String def = obj.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("entries")
						.getJSONObject(0).getJSONArray("senses").getJSONObject(0).getJSONArray("definitions").toString();
				String pronun = obj.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("pronunciations")
						.getJSONObject(0).getString("audioFile");
				String definition = def.substring(2,def.length()-2);

				HashMap<String, String> hmap = new HashMap<String, String>();
				hmap.put("definition", definition);
				hmap.put("pronunciation", pronun);
				DictList.put(word_id, hmap);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}

			
		}
		return DictList;
	}
	
	public static void saveDictToFile(HashMap<String, HashMap<String, String>> Dict) {
		try {
	          FileOutputStream fileOut =
	          new FileOutputStream("data.ser");
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          out.writeObject(Dict);
	          out.close();
	          fileOut.close();
	       } catch (IOException i) {
	          i.printStackTrace();
	       }
	}
	
	public static HashMap<String, HashMap<String, String>> readDictfromFile(String path) {
		HashMap<String, HashMap<String, String>> Dict = null;
		try {
	         FileInputStream fileIn = new FileInputStream("data.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Dict = (HashMap<String, HashMap<String, String>>) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	      } catch (ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	      }
		return Dict;
		
	}
	
	public static ArrayList<String> createWords(String filepath){
		ArrayList<String> newList = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
        {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
            	Pattern p = Pattern.compile("[\\w']+");
            	Matcher m = p.matcher(sCurrentLine);

            	while ( m.find() ) {
            	    newList.add(sCurrentLine.substring(m.start(), m.end()));
            	}
            	
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 

		return newList;
		
	}
	
	public static boolean writeToFile(ArrayList<String> list){
		try {
		FileWriter writer = new FileWriter("output.txt"); 
		for(String str: list) {
		  writer.write(str+" ");
		}
		writer.close();
		
		}

		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static String HttpRequest(String wordurl){

	        int responseCode  = 0;
	        String inputLine;
	        String res = null;
	        try {
	        	URL url = new URL(wordurl);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setReadTimeout(READ_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id","27788346");
                urlConnection.setRequestProperty("app_key","9275509248e762685d89e108b24bbf97");
                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                res = stringBuilder.toString();
	        } catch (IOException e) {
	            e.printStackTrace();

			}
            return res;

	        }
	        
		
	
	      
}
