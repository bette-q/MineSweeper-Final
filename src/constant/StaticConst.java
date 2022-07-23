package constant;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;

public class StaticConst {
    //board size
    public static int rows;
    public static int cols;

    //game play
    public static int mineCount;
    public static int mineLeft;
    public static int timeCount = 0;
    public static boolean inGame;

    //board cell images
    public static Image[] img = new Image[14];
    public static Icon bombIcon = new ImageIcon("resources/image2/Bomb.gif");
    public static Icon minesIcon = new ImageIcon("resources/image2/9.png");

    //face icons for restart button
    public static Icon smileIcon = new ImageIcon("resources/image2/face0.png");
    public static Icon winSmileIcon = new ImageIcon("resources/image2/face4.gif");
    public static Icon downSmileIcon = new ImageIcon("resources/image2/face2.gif");
    public static Icon SadSmileIcon = new ImageIcon("resources/image2/face3.gif");

    //display
    public static Icon returnIcon = new ImageIcon("resources/image2/back.png");
    public static Icon pauseButton = new ImageIcon("resources/image2/pause.png");
    public static Icon resetTime = new ImageIcon("resources/image2/d0.gif");
    public static Icon aboutIcon = new ImageIcon("resources/image2/about.gif");
    public static ImageIcon LogoIcon = new ImageIcon("resources/image2/9.png");
    public static ImageIcon ruleIcon = new ImageIcon("resources/image2/Rule.png");

    //when mousePause is true, you can't press the board.
    public static boolean mousePause;
    //when timeStart is true, then you click the board, the timer will start.
    public static boolean timeStart;
    //0 = board panel, 1 = level panel, 2 = stat level panel
    public static int inGameMenu = 0;
    
    //font
    public static Font TitleFont = new Font("Comic Sans MS", Font.BOLD, 20);
    public static Font font = new Font("Comic Sans MS", Font.PLAIN, 14);

    //sound
    public static boolean UnmuteSound; 
    public static boolean willClick;
    public static Clip clipOver;
    public static Clip clipWin;
    public static Clip clipClick;
    public static Clip clipTimer;

    //stores player info for leaderboard
    public static int level;
    public static List<Stats> statEasy = new ArrayList<>();
    public static List<Stats> statMed = new ArrayList<>();
    public static List<Stats> statHard = new ArrayList<>();

    //local save file
    private static String writeFile = "";

    public static void init() {
        img = new Image[14];
        /**assign the No. of image based on the path*/
        for (int i = 0; i < 14; i++){
            var path = "resources/image2/" + i + ".png";
            if(i == 9) {
                path = "resources/image2/Bomb.gif";
            }
            if(i == 13) {
                path = "resources/image2/13.gif";
            }
            img[i] = (new ImageIcon(path)).getImage();
        }
        loadSound();
        playTimer();
    }

    //save player stats to external file
    public static void saveData() throws IOException {
        List<Stats> list = new ArrayList<>();
        if(level == 1) {
            writeFile = "resources/statEasy.txt";
            list = statEasy;
        }
        else if (level == 2) {
            writeFile = "resources/statMed.txt";
            list = statMed;
        }
        else if (level == 3) {
            writeFile = "resources/statHard.txt";
            list = statHard;
        }
        Collections.sort(list);
        BufferedWriter write = new BufferedWriter(new FileWriter(writeFile));

        //only take at most top 10 elements
        int size = list.size() > 10? 10 : list.size();
        for(int i = 0; i < size; ++i) {
            Stats s = list.get(i);
            String str = s.getName() + " " + s.getTime() + "\n";
            write.append(str);
        }
        write.close();

    }
    //load player stat from external file
    public static void loadData(List<Stats> list, String filename) throws FileNotFoundException, IOException {
        BufferedReader load = new BufferedReader(new FileReader(filename));
        String line = load.readLine();
        
        //parses each line in the file
        while(line != null) {
            String[] info = line.split(" ");
            Stats s = new Stats(info[0], Integer.parseInt(info[1]));
            list.add(s);
            line = load.readLine();
        }
        load.close();

    }
    //loads gameOver and gameWin audio
    public static void loadSound() {
        try {
            // Open an audio input stream.
            File fOver = new File("resources/GameOver.wav");
            AudioInputStream audioInOver = AudioSystem.getAudioInputStream(fOver.toURI().toURL()); 
            // Get a sound clip resource.
            clipOver = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clipOver.open(audioInOver);

            File fWin = new File("resources/GameWin.wav");
            AudioInputStream audioInWin = AudioSystem.getAudioInputStream(fWin.toURI().toURL()); 
            // Get a sound clip resource.
            clipWin = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clipWin.open(audioInWin);
            
            File fClick = new File("resources/Click.wav");
            AudioInputStream audioInClick = AudioSystem.getAudioInputStream(fClick.toURI().toURL()); 
            // Get a sound clip resource.
            clipClick = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clipClick.open(audioInClick); 
            
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
    }
    //rewinds audio
    public static void clipRestart() {
        clipOver.stop();
        clipWin.stop();
        clipTimer.stop();
        clipOver.setFramePosition(0);
        clipWin.setFramePosition(0);
        clipTimer.setFramePosition(0);
    }
    
    public static void playClick(){
        try{
            File fClick = new File("resources/Click.wav");
            AudioInputStream audioInClick = AudioSystem.getAudioInputStream(fClick.toURI().toURL()); 
            // Get a sound clip resource.
            clipClick = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clipClick.open(audioInClick); 
        } catch (LineUnavailableException e) {
        } catch (UnsupportedAudioFileException | IOException e) {
        }
        clipClick.start();
    }
    
    public static void playTimer(){
        try{
            File fTimer= new File("resources/TimeCount.wav");
            AudioInputStream audioInClick = AudioSystem.getAudioInputStream(fTimer.toURI().toURL()); 
            // Get a sound clip resource.
            clipTimer = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clipTimer.open(audioInClick); 
        } catch (LineUnavailableException e) {
        } catch (UnsupportedAudioFileException | IOException e) {
        }
    }
}