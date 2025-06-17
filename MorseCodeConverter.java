import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public class MorseCodeConverter{
    public static void main(String[] args) throws Exception {
        BiMap<String,String> morseMap = codeLoaderB();//pass the filepath of the csv table in the codeLoaderB method
        
        JFrame frame = new JFrame("Morse Code Converter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300,400));
        frame.setLayout(new FlowLayout());
        JLabel eTMLabel = new JLabel("Type in a sentence and convert it to Morse Code");
        JTextField eTMInput = new JTextField(20);
        JButton eTMButton = new JButton("Convert to Morse Code");
        JLabel eTMOutput = new JLabel("");

        eTMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = eTMInput.getText();
                String answer = morseCodeConverter(morseMap,input);
                System.out.println(answer);
                eTMOutput.setText(answer);
            }
        });

        JLabel mTELabel = new JLabel("Type in Morse Code and convert it to English");
        JTextField mTEInput = new JTextField(20);
        JButton mTEButton = new JButton("Convert to English");
        JLabel mTEOutput = new JLabel("");

        mTEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = mTEInput.getText();
                String answer = englishToMorse(morseMap,input);
                System.out.println(answer);
                mTEOutput.setText(answer);
            }
        });

        frame.getContentPane().add(eTMLabel);
        frame.getContentPane().add(eTMInput);
        frame.getContentPane().add(eTMButton);
        frame.getContentPane().add(eTMOutput);

        frame.getContentPane().add(mTELabel);
        frame.getContentPane().add(mTEInput);
        frame.getContentPane().add(mTEButton);
        frame.getContentPane().add(mTEOutput);


        frame.pack();
        frame.setVisible(true);

    }

    public static String morseCodeConverter(BiMap<String,String> map,String userInput){
        String output = "";

        String[] words = userInput.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                output += map.get(String.valueOf(Character.toUpperCase(word.charAt(j)))) + " ";
            }
            if (i < words.length - 1){
                output += "/ ";
            }
        }
        return output.trim();
    }

    public static BiMap<String,String> codeLoaderB(String filePath) throws Exception{
        BiMap<String,String> output = HashBiMap.create();
        String line;
        try {
            BufferedReader br =new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");
                output.put(values[0],values[1]);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Sorry file not found!");
        }
        return output;
    }

    public static String englishToMorse(BiMap<String,String>  map,String userInput){
        String output = "";
        String[] words = userInput.split("/");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].trim();
            String[] letters = word.split(" ");
            for (int j = 0; j < letters.length; j++) {
                output += map.inverse().get(letters[j]);
            }
            output +=" ";
        }
        return output.trim();
    }
}