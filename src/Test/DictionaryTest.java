package Test;
import java.util.ArrayList;

import Data.*;


import junit.framework.TestCase;
public class DictionaryTest extends TestCase{
	
	public void testConstructor() {
		Word temp = new Word();
		assertEquals(Word.getWord(temp), "");
		assertEquals(Word.getDef(temp), "");

	}
	
	public void testConstructor1() {
		Word temp = new Word("example", "blah blah blah");
		assertNotNull(Word.getWord(temp));
		assertNotNull(Word.getDef(temp));
		assertEquals(Word.getWord(temp), "example");
		assertEquals(Word.getDef(temp), "blah blah blah");
	}
	
	public void testSetWord(){
		Word temp = new Word("example", "blah blah blah");
		assertEquals(Word.getWord(temp), "example");
		assertEquals(Word.getDef(temp), "blah blah blah");
		Word.setWord(temp, "new", "new");
		assertEquals(Word.getWord(temp), "new");
		assertEquals(Word.getDef(temp), "new");
	}
	
	public void testDictionaryConstructor() {
		WordDictionary temp = new WordDictionary();
	}
	
	public void testCreateWords() {
		ArrayList<String> temp = WordDictionary.createWords("C:\\Users\\Kaiwenxue\\eclipse-workspace\\ElectronicDictionary\\src\\Data\\test.txt");
		assertEquals(temp.get(0), "Alice");
		assertTrue(temp.size()>0);

	}
	
	public void testWriteToFile() {
		ArrayList<String> temp = WordDictionary.createWords("C:\\Users\\Kaiwenxue\\eclipse-workspace\\ElectronicDictionary\\src\\Data\\test.txt");
		assertEquals(temp.get(0), "Alice");
		assertTrue(temp.size()>0);
		boolean Test = WordDictionary.writeToFile(temp);
		assertTrue(Test);

	}
	
	public void testHttpRequest() {
		String test = WordDictionary.HttpRequest("https://od-api.oxforddictionaries.com:443/api/v1/entries/en/apple");
		assertNotNull(test);
	}
}
