package Data;

public class Word {
	
	private String word;
	private String definition;

	public Word() {
		// TODO Auto-generated constructor stub
		this.word = "";
		this.definition = "";
	}
	
	public Word(String w, String d) {
		this.word = w;
		this.definition = d;
	}
	
	public static void setWord(Word obj, String w, String d) {
		obj.word = w;
		obj.definition = d;
	}
	
	public static String getDef(Word obj) {
		return obj.definition;
	}
	
	public static String getWord(Word obj) {
		return obj.word;
	}

	public String getDef() {
		return this.definition;
	}
	

}
