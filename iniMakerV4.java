import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class iniMakerV4 {
    //"fileMaker" is used access files in File Explorer and access the character.
    static File fileMaker;
    //"fileFinder" is used to find file names, which is used for various variables.
    static FileDialog fileFinder = new FileDialog(new JFrame(), "Choose a directory", FileDialog.LOAD);
    //FileOutputStream "fos" is used to write data down to the ini for the character.
    static FileOutputStream iniOutput;
    //BufferedWriter "bw" is what allows the program to type in stuff into the ini.
    static BufferedWriter iniWriter;
    //Scanner variable "key" to receive input from the user on number of emotions, sub-folders, etc..
    static Scanner key = new Scanner(System.in);
    //Integer "x" is used as a variable to take in Scanner inputs.
    static int x;
    //"input" records what x has stored in, and gives that input to various other variables of the program.
    static int input;
    //Integer Array "subfolderEndpoint" acts as where each set of emotions will stop if character has sub-folders.
    static int[] subfolderEndpoint;
    //Boolean "retry" is used to ensure the loops like "setInput" are looping until certain conditions are met.
    static boolean retry = false;
    // "checkPointEnd", "checkpoint", and "checkInput" work for parts of the ini with certain items like sub-folders.
    static int checkpointEnd = 0, checkpoint = 0, checkInput = 0;
    //String array "optionsLabels" prints an array with the options needed to build an ini.
    //"options" will store hte inputs for the options part of the ini.
    static String[] optionsLabels = {"name", "showname", "side", "blips", "chat", "shouts", "scaling"}, options = new String[7];
    //"emotions" stores how many emotions a character has.
    static int emotions;
    //Integer array "preAnim" is used to record if the emotion will have the preanim play by default.
    static int[] autoPreAnim;
    /*"preAnimName" is used to give names to pre-animations (default one is "-").
    "emoteName" gives names to emotions of the character (default names are simply numbers) */
    static String[] preAnimName, emoteName, subfolderName;
    //"preAnimationVerify" is used to check if the character has any pre-animations.
    //"preNestedVerify" is used to check if the pre-animation is in a subfolder.
    static int preAnimationVerify, preAnimNestedVerify;
    //"preAnimEndpoint" acts as a stopping point if character has pre-animation.
    static int[] preAnimEndpoint;
    //Integer array "preSounds" is used to record what emotion will have a sound play.
    //"soundEndpoint" acts as a stopping point if character has sounds.
    static int [] soundEndpoint, preSounds;
    //String array "soundName" is used to record the names of a sound at certain emotions.
    static String [] soundName;
    //Integer "loopCheck" is used to check if the sound will loop or not.
    //Integer "autoPreAnimCheck" is used to check if the preanim will play by default.
    static int loopCheck, autoPreAnimCheck;
    //Integer array "soundTime" is used to record if certain emotions have a sound to play.
    //Integer array "soundTimePlay" is used to record when the sound will play.
    static int [] soundTime, soundTimePlay;
    //"soundVerify" is used check if there are sounds in teh character.
    static boolean soundVerify = false;

    public static void main(String[] args) throws InterruptedException, IOException {
        //Basic introduction to the program.
        System.out.println("Welcome to the Attorney Online 2 .ini Maker!"); Thread.sleep(1000);
        System.out.println("Let's start making an ini for your character."); Thread.sleep(1000);

        //Program directs the user to find the directory of where the character is.
        System.out.println("Locate the directory your character is.");
        System.out.println("Click on a file in the character folder to select the directory."); Thread.sleep(2000);
        //If there is a directory, the program will show the directory the character folder is.
        //If there is no directory chosen, then the program will quit.
        fileFinder.setAlwaysOnTop(true);
        fileFinder.setLocation(0, 0);
        fileFinder.setVisible(true);
        fileFinder.isActive();
        File[] directory = fileFinder.getFiles();
        if(directory.length > 0){
            fileMaker = new File(fileFinder.getFiles()[0].getParent() + "\\\\char.ini");
            System.out.println("File path chosen: " + fileMaker);
            System.out.println();
            iniOutput = new FileOutputStream(fileMaker);
            iniWriter = new BufferedWriter(new OutputStreamWriter(iniOutput));
        }
        else{
         System.out.println("Character not found. Exiting out of program...");
         System.exit(0);
        }
        //Options will ask the user to input the options selection of the ini.
        Options();
        //numberOfEmotions will record how many emotions are in the character according to the user.
        numberOfEmotions();
        //After the number of emotions are recorded, various variables of the program will be fulfilled.
        preAnimName = new String[input];
        for (int apa = 0; apa < emotions; apa++) { autoPreAnim[apa] = 0; }
        for (int pan = 0; pan < emotions; pan++) { preAnimName[pan] = "-"; }
        for (int en = 0; en < emotions; en++) { emoteName[en] = (en + 1) + ""; }
        //subfolderCheck will check if the character has sub-folders, which can contain emotions.
        subfolderCheck();
        //namesOrNumbers will check if the user wants names for the emotions part of the ini or numbers.
        namesOrNumbers();
        //preAnimCheck checks if the character has pre-animations.
        preAnimCheck();
        //soundsCheck checks if the character has sounds.
        soundCheck();
        //Final step of the program, which is to create the ini.
        createIni();

        System.out.println("ini successfully created. Remember to edit the ini if you need something to your liking.");
        Thread.sleep(3000);
        System.out.println("Exiting out of program..."); Thread.sleep(1000);
        System.exit(0);
    }
    public static void Options(){
        String q;

        System.out.println("What is the character's name?");
        q = key.nextLine(); options[0] = q;

        System.out.println("What is the character's show name? (What the client will show as the name.)");
        q = key.nextLine(); options[1] = q;

        System.out.println("What is the default position of the character? ");
        System.out.println("Valid positions are def, pro, jud, wit, and jur.");
        do{
            if (key.hasNextLine()) {
                q = key.nextLine();
                if(q.contains("def") || q.contains("pro") || q.contains("jud") || q.contains("wit") || q.contains("jur")){
                    options[2] = q; retry = true;
                }
                else{
                    System.out.println("Try again. Input a valid position.");
                    System.out.println("Valid positions are def, pro, jud, wit, and jur.");
                    System.out.println();
                }
            }
        } while(!retry);
        retry = false;

        System.out.println("What blips does the character use? If you are unsure, input \"male\" or \"female\".");
        q = key.nextLine(); options[3] = q;

        System.out.println("What is the chat of the character? If you are unsure, input \"default\".");
        q = key.nextLine(); options[4] = q;

        System.out.println("What are the shouts of the character? If you are unsure, input \"default\".");
        q = key.nextLine(); options[5] = q;

        System.out.println("How will the character be scaled in-client?");
        System.out.println("Valid scaling options are pixel, fast, or smooth.");
        do{
            if (key.hasNextLine()) {
                q = key.nextLine();
                if(q.contains("pixel") || q.contains("fast") || q.contains("smooth")){
                    options[6] = q; retry = true;
                }
                else{
                    System.out.println("Try again. Input a valid scaling option.");
                    System.out.println("Valid scaling options are pixel, fast, or smooth.");
                    System.out.println();
                }
            }
        } while(!retry);
        retry = false;
    }
    public static void numberOfEmotions() {
        System.out.println("How many emotions are in your AO Character? Input a valid number");
        setInput();
        if(input > 0){
            emotions = input;
            autoPreAnim = new int[input];
            emoteName = new String[input];
            retry = false;
        }
        else{
            System.out.println("No emotes found in character. Exiting out of program...");
            System.exit(0);
        }
    }
    public static void subfolderCheck() {
        System.out.println("Are there any subfolders in your AO character? Type 1 for yes");
        setInput();

        if (input == 1) {
            numberOfSubfolders();
            Subfolder();
            reset();
        } else {
            subfolderEndpoint = new int[1];
            subfolderEndpoint[0] = emotions;

        }
    }
    public static void numberOfSubfolders() {
        int q, h;
        System.out.println("How many subfolders are on your AO character? Input a valid number (not a string.)");
        System.out.println("(Number also has to be less than number of emotions.)");
        setNumberOf();
        q = input;
        h = q + 1;
        subfolderEndpoint = new int[h];
        checkpointEnd = (subfolderEndpoint.length - 1);
        subfolderName = new String[q];
    }
    public static void Subfolder() {
        String setName;
        while (checkpoint < checkpointEnd) {
            System.out.println("Name the subfolder:");
            key.nextLine();
            setName = key.nextLine();
            subfolderName[checkpoint] = setName;
            System.out.println("Where does the previous folder end?");
            if (checkpoint < 1) {
                System.out.println("(Input a value less than number of emotes for the whole AO Character.)");
            } else {
                System.out.println("(Input a value less than number of emotes character has and less than the previous folder.)");
            }
            setSubfolderTarget();
            subfolderEndpoint[checkpoint] = input;

            checkpoint++;

            if (subfolderEndpoint[(checkInput - 1)] == (emotions - 1) && checkpoint != checkpointEnd) {
                System.out.println("Error: Endpoint has reached number of emotions, but some subfolders are still not set.");
                System.out.println("Exiting out of program...");
                System.exit(0);
            }
        }
        subfolderEndpoint[subfolderEndpoint.length - 1] = emotions;
    }
    public static void namesOrNumbers() {
        System.out.println("Do you want your ini to print names for the emotes? Type 1 for yes");
        setInput();
        if(input == 1){
            setName();
        }
    }
    public static void setName(){
        int p = 1;
        while(p <= emotions) {
            do {
                System.out.println("Find an emote name in the character folder.");
                fileFinder.setVisible(true);
                File[] nameGetter = fileFinder.getFiles();
                if (nameGetter.length > 0) {
                    String x = fileFinder.getFiles()[0].getName();
                    String x2 = x.substring(0, x.lastIndexOf("."));
                    emoteName[(p - 1)] = x2;
                    retry = true;
                } else {
                    System.out.println("Name not found. Try again.");
                }
            } while (!retry);
            p++;
            retry = false;
        }
    }
    public static void preAnimCheck(){
        System.out.println("Are there any pre-animations in your AO character? Type 1 for yes");
        setInput();
        preAnimationVerify = input;
        if(preAnimationVerify == 1){
            numberOfPreAnimations();
            PreAnimations();
            reset();
        }
    }
    public static void numberOfPreAnimations() {
        int k, g;
        System.out.println("How many pre-animations are in your AO character? Input a valid number (not a string.)");
        System.out.println("(Number also has to be less than number of emotions.)");
        setNumberOf();
        k = input;
        g = k + 1;
        preAnimEndpoint = new int[g];
        checkpointEnd = (preAnimEndpoint.length - 1);
    }
    public static void PreAnimations(){
        int preAnimationNumber;
        String y,  y2 = "", nestedSubfolder;

        while(checkpoint < checkpointEnd) {
            do {
                System.out.println("Find an emote name in the character folder.");
                fileFinder.setVisible(true);
                File[] preAnimationNameGetter = fileFinder.getFiles();
                if (preAnimationNameGetter.length > 0) {
                    y = fileFinder.getFiles()[0].getName();
                    y2 = y.substring(0, y.lastIndexOf("."));
                    retry = true;
                } else {
                    System.out.println("Name not found. Try again.");
                }
            } while (!retry);
            retry = false;

            System.out.println("Which emote has this pre-animation?");
            if (checkpoint < 1) {
                System.out.println("(Input a value less than number of emotes for the whole AO Character.)");
            } else {
                System.out.println("(Input a value less than number of emotes character has and less than the previous folder.)");
            }
            setPreAnimationTarget();
            preAnimEndpoint[checkpoint] = input;
            preAnimationNumber = input;

            System.out.println("Is this pre-animation within another subfolder? Type 1 for yes.");
            setInput();
            preAnimNestedVerify = input;

            if(preAnimNestedVerify == 1){
                System.out.println("What is the subfolder's name? Remember to check if the folder is capitalized or not.");
                System.out.println("Also check if the subfolder within the folder is in another subfolder.");
                key.nextLine();
                nestedSubfolder = key.nextLine();
                preAnimName[(preAnimationNumber - 1)] = "anim/" + nestedSubfolder + "/" + y2;
            }
            else{
                preAnimName[(preAnimationNumber - 1)] = "anim/" + y2;
            }
            preAnimNestedVerify = 0; checkpoint++;

            if (preAnimEndpoint[(checkInput - 1)] == (emotions) && checkpoint != checkpointEnd) {
                System.out.println("Error: Endpoint has reached number of emotions, but some pre-animations are still not set.");
                System.out.println("Exiting out of program...");
                System.exit(0);
            }

        }
    }
    public static void soundCheck(){
        System.out.println("Are there any sounds in your AO character? Type 1 for yes");
        setInput();

        if (input == 1) {
            soundVerify = true;
            numberOfSounds();
            Sounds();
            reset();
        }
    }
    public static void numberOfSounds(){
        int q2, h2;
        System.out.println("How many sounds are in your AO Character? Input a valid number");
        System.out.println("(Number also has to be less than number of emotions.)");
        setNumberOf();
        q2 = input;
        h2 = q2 + 1;
        soundEndpoint = new int[h2];
        preSounds = new int[q2];
        soundTime = new int[q2];
        soundTimePlay = new int[q2];
        checkpointEnd = (soundEndpoint.length -1);
        soundName = new String[q2];

    }
    public static void Sounds(){
        String v;
        while (checkpoint < checkpointEnd) {
            System.out.println("Which emotion has a sound effect?");
            if(checkpoint < 1){
                System.out.println("(Input a value less than number of emotes for the whole AO Character.)");
            }
            else{
                System.out.println("(Input a value less than number of emotes and less than the previous endpoint.)");
            }
            setSoundTarget();
            preSounds[checkpoint] = input; soundTime[checkpoint] = input; soundEndpoint[checkpoint] = input;

            System.out.println("What is the name of the sound?");
            key.nextLine();
            v = key.nextLine();
            soundName[checkpoint] = v;

            System.out.println("When does the sound play?");
            setInput();
            soundTimePlay[checkpoint] = input;

            System.out.println("Will the emote automatically play the preanim? (Type 1 for yes)");
            setInput();
            autoPreAnimCheck = input;

            if(autoPreAnimCheck == 1){ autoPreAnim[(preSounds[checkpoint] - 1)] = 1; }
            autoPreAnimCheck = 0; loopCheck = 0; checkpoint++;

            if (soundEndpoint[(checkInput - 1)] == (emotions) && checkpoint != checkpointEnd) {
                System.out.println("Error: Endpoint has reached number of emotions, but some sounds are still not set.");
                System.out.println("Exiting out of program...");
                System.exit(0);
            }
        }
        soundEndpoint[soundEndpoint.length - 1] = emotions;
    }
    public static void setInput() {
        do {
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                input = x; retry = true;
            }
            else {
                System.out.println("Try again. Input a valid number (not a string.)");
                System.out.println();
                key.next();
            }
        } while (!retry);
        retry = false;
    }
    public static void setNumberOf() {
        do {
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                if (x < emotions && x > 0) {
                    input = x; retry = true;
                }
                else{
                    System.out.println("Try again. Input a valid number that satisfies the above conditions");
                    System.out.println();
                }
            } else {
                System.out.println("Try again. Input a valid number that satisfies the above conditions");
                System.out.println();
                key.next();
            }
        } while (!retry);
        retry = false;
    }
    public static void setSubfolderTarget() {
        if(checkpoint < 1){
            do {
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if (checkpoint < 1) {
                        if (x < emotions && x > 0) {
                            input = x; checkInput++; retry = true;
                        }
                        else {
                            System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                            System.out.println();
                        }
                    }
                }
                else {
                    System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                    System.out.println();
                    key.next();
                }
            } while (!retry);
        }
        else{
            do {
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if (x < emotions && x > 0 && x > subfolderEndpoint[checkpoint - 1]) {
                        input = x; checkInput++; retry = true;
                    }
                    else {
                        System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                    System.out.println();
                    key.next();
                }
            } while (!retry);
        }
        retry = false;
    }
    public static void setPreAnimationTarget() {
        if(checkpoint < 1){
            do {
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if (checkpoint < 1) {
                        if (x < emotions && x > 0) {
                            input = x; checkInput++; retry = true;
                        }
                        else {
                            System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                            System.out.println();
                        }
                    }
                }
                else {
                    System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                    System.out.println();
                    key.next();
                }
            } while (!retry);
        }
        else{
            do {
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if (x < emotions && x > 0 && x > preAnimEndpoint[checkpoint - 1]) {
                        input = x; checkInput++; retry = true;
                    }
                    else {
                        System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                    System.out.println();
                    key.next();
                }
            } while (!retry);
        }
        retry = false;
    }
    public static void setSoundTarget() {
        if(checkpoint < 1){
            do {
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if (checkpoint < 1) {
                        if (x < emotions && x > 0) {
                            input = x; checkInput++; retry = true;
                        }
                        else {
                            System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                            System.out.println();
                        }
                    }
                }
                else {
                    System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                    System.out.println();
                    key.next();
                }
            } while (!retry);
        }
        else{
            do {
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if (x < emotions && x > 0 && x > soundEndpoint[checkpoint - 1]) {
                        input = x; checkInput++; retry = true;
                    }
                    else {
                        System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Try again. Input a valid number that satisfies the conditions above.");
                    System.out.println();
                    key.next();
                }
            } while (!retry);
        }
        retry = false;
    }
    public static void reset(){ checkpoint = 0; checkpointEnd = 0; checkInput = 0; }
    public static void createIni() throws IOException {
        int r = 0, u = 0, i = 1, t = 1, k = 0;

        iniWriter.write("[Options]");
        iniWriter.newLine();
        while (u < options.length) {
            iniWriter.write(optionsLabels[u] + " = " + options[u]);
            iniWriter.newLine();
            u++;
        }
        iniWriter.newLine(); u = 0;

        iniWriter.write("[Emotions]");
        iniWriter.newLine();
        iniWriter.write("emotions = " + emotions);
        iniWriter.newLine();
        iniWriter.newLine();
        while (r < (subfolderEndpoint.length)) {
            if (r < 1) {
                while (i <= subfolderEndpoint[r]) {
                    iniWriter.write(i + " = " + emoteName[u] + "#" + preAnimName[u] + "#" + emoteName[u] + "#" + autoPreAnim[u] + "#1#");
                    iniWriter.newLine();
                    u++; i++;
                }
                iniWriter.newLine();
            }
            else {
                while (i <= subfolderEndpoint[r]) {
                    iniWriter.write(i + " = " + emoteName[t] + "- " + subfolderName[k] + "#" + preAnimName[u] + "#" + subfolderName[k] + "/" + emoteName[t] + "#" + autoPreAnim[u] + "#1#");
                    iniWriter.newLine();
                    i++; u++; t++;
                }
                iniWriter.newLine(); k++;
            }
            r++;
        }
        iniWriter.newLine(); i = 1; u = 0;
        if(soundVerify){
            iniWriter.write("[SoundN]");
            iniWriter.newLine();
            while (i <= (soundEndpoint.length -1)) {
                iniWriter.write(preSounds[u] + " = " + soundName[u]);
                iniWriter.newLine();
                u++; i++;
            }

            iniWriter.newLine();
            i = 1; u = 0;

            iniWriter.write("[SoundT]");
            iniWriter.newLine();
            while (i <= (soundEndpoint.length -1)) {
                iniWriter.write(soundTime[u] + " = " + soundTimePlay[u]);
                iniWriter.newLine();
                u++; i++;
            }
        }
        iniWriter.close();
    }
}