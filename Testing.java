package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Testing {

	public static void main(String[] args){
		ArrayList<String> temp = WordDictionary.createWords("vocab.txt");
		Set<String> hs = new HashSet<>();
		hs.addAll(temp);
		temp.clear();
		temp.addAll(hs);
		
		HashMap<String, HashMap<String, String>> Dict1 = WordDictionary.buildDict(temp);
		
		WordDictionary.saveDictToFile("data.ser", Dict1);
		
		HashMap<String, HashMap<String, String>> Dict = WordDictionary.readDictfromFile("data.ser");
		
		for (String key : Dict.keySet()) {
			System.out.println(key + " : " + Dict.get(key));
		}
	}
}
