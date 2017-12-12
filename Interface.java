package Data;

import java.awt.Composite;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;


public class Interface {
	private static JTextField WordBank;    // Your class name
	private static JTextField WordField;
	private static JTextField DefinitionLabel;
	private static JTextField SearchLabel;
	private static JTextField SearchField;
	private static JButton btnSearch;
	private static JButton btnReset;
	private static JButton btnAdd;
	private static JTextField oxfordAddField;

   public static void main(String[] args) {
	  HashMap<String, HashMap<String, String>> Dict = WordDictionary.readDictfromFile("data.ser");
	  ArrayList<String> keys = new ArrayList<>(Dict.keySet());
      Collections.sort(keys);
      JFrame f = new JFrame("A JFrame");
      f.setSize(500, 530);
      f.getContentPane().setLayout(null);
      DefaultListModel listModel=new DefaultListModel();
      for (int i=0; i<keys.size(); i++) {
        listModel.addElement(keys.get(i));
      }

      WordBank = new JTextField();
      WordBank.setEditable(false);
      WordBank.setText("Word Bank");
      WordBank.setBounds(21, 11, 173, 20);
      f.getContentPane().add(WordBank);
      WordBank.setColumns(10);

      
      WordField = new JTextField();
      WordField.setEditable(false);
      WordField.setBounds(204, 35, 86, 20);
      f.getContentPane().add(WordField);
      WordField.setColumns(10);
      
      DefinitionLabel = new JTextField();
      DefinitionLabel.setEditable(false);
      DefinitionLabel.setText("Definition:");
      DefinitionLabel.setBounds(204, 67, 86, 20);
      f.getContentPane().add(DefinitionLabel);
      DefinitionLabel.setColumns(10);
      
      JButton btnPlay = new JButton("Play");
      btnPlay.setBounds(397, 35, 64, 52);
      f.getContentPane().add(btnPlay);
      
      SearchLabel = new JTextField();
      SearchLabel.setEditable(false);
      SearchLabel.setText("Search For:");
      SearchLabel.setBounds(21, 351, 86, 20);
      f.getContentPane().add(SearchLabel);
      SearchLabel.setColumns(10);
      
      SearchField = new JTextField();
      SearchField.setBounds(20, 382, 174, 20);
      f.getContentPane().add(SearchField);
      SearchField.setColumns(10);
      
      JTextArea Definition = new JTextArea();
      Definition.setLineWrap(true);
      Definition.setWrapStyleWord(true);
      Definition.setEditable(false);
      Definition.setBounds(204, 98, 257, 107);
      f.getContentPane().add(Definition);
      
      btnSearch = new JButton("SEARCH!");
      btnSearch.setBounds(21, 414, 77, 23);
      f.getContentPane().add(btnSearch);
      
      btnReset = new JButton("RESET");
      btnReset.setBounds(117, 413, 77, 23);
      f.getContentPane().add(btnReset);
      
      JScrollPane VocabScroll = new JScrollPane();
      VocabScroll.setBounds(21, 38, 173, 302);
      f.getContentPane().add(VocabScroll);
      
      JList list = new JList(listModel);
      VocabScroll.setViewportView(list);
      
      JLabel lblAddWordTo = new JLabel("Oxford Dictonary Search");
      lblAddWordTo.setBounds(331, 350, 130, 23);
      f.getContentPane().add(lblAddWordTo);
      
      oxfordAddField = new JTextField();
      oxfordAddField.setBounds(277, 382, 184, 20);
      f.getContentPane().add(oxfordAddField);
      oxfordAddField.setColumns(10);
      
      btnAdd = new JButton("Add");

      btnAdd.setBounds(372, 414, 89, 23);
      f.getContentPane().add(btnAdd);
      
      JMenuBar menuBar = new JMenuBar();
      f.setJMenuBar(menuBar);
      
      JMenu mnOptions = new JMenu("Options");
      menuBar.add(mnOptions);
      
      JMenuItem mntmAddWord = new JMenuItem("Add Word");
      mnOptions.add(mntmAddWord);
      
      JMenuItem mntmSaveToFile = new JMenuItem("Save to File");
      mnOptions.add(mntmSaveToFile);
      f.setVisible(true);
      
      list.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
        	  Object tr = list.getSelectedValue();
        	  if(tr!=null) {
            	  String choose = list.getSelectedValue().toString();
                  HashMap<String, String> Word = Dict.get(choose);
                  Definition.setText(Word.get("definition"));
                  WordField.setText(choose);
        	  }

          }
        });

      btnSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String searchfor = SearchField.getText();
			ArrayList<String> searchList = new ArrayList<String>();
			for(int i = 0; i< keys.size();i++) {
				if(keys.get(i).toLowerCase().contains(searchfor.toLowerCase())!=true) {
					listModel.removeElement(keys.get(i));
				}
				
			}
			
		}
      });
      
      btnReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		listModel.removeAllElements();
        	      for (int i=0; i<keys.size(); i++) {
        	        listModel.addElement(keys.get(i));
        	      }
        	}
        });
      
      btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String word_id = oxfordAddField.getText().toLowerCase();
        		if(keys.contains(word_id)) {
        			JOptionPane.showMessageDialog(f,
        				    "Word already exists in dictionary!",
        				    "Duplicate error",
        				    JOptionPane.ERROR_MESSAGE);
        			}
        		else {
        			String test = WordDictionary.HttpRequest("https://od-api.oxforddictionaries.com:443/api/v1/entries/en/"+word_id);
            		try {
        				JSONObject obj = new JSONObject(test);
        				String def = obj.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("entries")
        						.getJSONObject(0).getJSONArray("senses").getJSONObject(0).getJSONArray("definitions").toString();
        				String definition = def.substring(2,def.length()-2);

        				HashMap<String, String> hmap = new HashMap<String, String>();
        				hmap.put("definition", definition);
        				Dict.put(word_id, hmap);
        				keys.add(word_id);
        				Collections.sort(keys);
        				listModel.clear(); // remove all elements
        				for(Object o:keys){ listModel.addElement(o); } // add elements
        				

        			} catch (JSONException e) {
        				// TODO Auto-generated catch block
        			}
        			
        		}
        		
        	}
        });
      
      
    }
}