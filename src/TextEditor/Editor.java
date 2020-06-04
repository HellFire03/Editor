package TextEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.sun.speech.freetts.*;


public class Editor extends JPanel implements ActionListener {
    File file;
    JButton save = new JButton("Save");
    JButton savec = new JButton("Save and Close");
    JButton audio = new JButton("Audio");
    JButton clear = new JButton("Clear");
    JTextArea text = new JTextArea(20, 40);
    JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    public Editor(String s) {
    	file = new File(s);
    	save.addActionListener(this);
    	savec.addActionListener(this);
    	audio.addActionListener(this);
    	clear.addActionListener(this);
        
    	if(file.exists()) {
    		try {
				BufferedReader input = new BufferedReader(new FileReader(file));
				String line = input.readLine();
				while(line != null) {
					text.append(line+"\n");
					line = input.readLine();
				}
				input.close();	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
    	}
    	add(save);
    	add(savec);
    	add(audio);
    	add(clear);
    	add(scroll);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== clear) {
			text.setText("");
		}
		if(e.getSource()== audio) {
			Voice voice;
			voice = VoiceManager.getInstance().getVoice("kevin");
			if (voice != null) {
				voice.allocate();
			}
			try {
				
				voice.setRate(130);
				voice.setPitch(80);
				voice.setVolume(3);
				voice.speak(text.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			FileWriter out = new FileWriter(file);
			out.write(text.getText());
			out.close();
			if(e.getSource() == savec) {
				Login login = (Login) getParent();
				login.cl.show(login, "fb");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}