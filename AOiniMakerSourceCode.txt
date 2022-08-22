//This code is meant to automate the process of ini-making on Attorney Online 2.
//Credit to Jouto#6149 on Discord.
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.*;
import java.util.Scanner;

public class Inimakerv2 {
    //File "fileMaker is used access files in File Explorer and access the character.
    static  File fileMaker;
    //FileOutputStream "fos" is used to write data down to the ini for the character.
    static  FileOutputStream fos;
    //BufferedWritter "bw" is what allows the program to type in stuff into the ini.
    static BufferedWriter bw;
    //Scanner variable "key" to receive input from the user on number of emotions, sub-folders, etc..
    static Scanner key = new Scanner(System.in);
    //Integer "a" acts a stopper for the method "Subfolder" once "a" is no longer less than b.
    static int a = 0;
    //String array "o" is used to store the inputs of the options section of an ini.
    static String[] o = new String[7];
    //Integer "x" as a general purpose variable.
    static int x;
    //Integer "b" used to in tandem with variable "a" for the loop containing the method "Subfolder".
    static int b;
    //Integer "check" to see if character as sub-folders.
    static int check;
    //Integer "retry" as a stopper for any loops that require user's input.
    static int retry;
    //String Array "name" defines the names of each subfolder.
    static String[] name;
    //Integer Array "endpoint" acts as where each set of emotions will stop if character has sub-folders.
    static int[] endpoint;
    //Integer "emotions" to record the number of emotions each character has.
    static int emotions;
    //Integer array "preSounds" is used to record what emotion will have a sound play.
    static int[] preSounds;
    //Integer "soundVerify" is used to check if the character has sounds.
    static int soundVerify;
    //Integer array "preAnim" is used to record if the emotion will have the preanim play by default.
    static int[] preAnim;
    //String array "soundName" is used to record the names of a sound at certain emotions.
    static String [] soundName;
    //Integer array "soundTime" is used to record if certain emotions have a sound to play.
    static int [] soundTime;
    //Integer array "soundTimePlay" is used to record when the sound will play.
    static int[] soundTimePlay;
    //Integer "soundEndpoint" is used  to help the "Sounds" method.
    static int[] soundEndpoint;
    //Integer "i" is used as a stopper for various while loops.
    static int i = 1;
    //Integer "k" is used as a stopper for printing out lines with sub-folders.
    static int k = 0;
    //Integer "q" is used to determine if program uses the method "singleSubfolder" or "multipleSubfolders".
    static int q = 0;
    // Variable "t" acts as a set of new emotions for each subfolder.
    static int t = 1;
    //Variable "sn" acts as the stopper for when to stop the "Sounds" method.
    static int sn;
    //Integer "loopCheck" is used to check if the sound will loop or not.
    static int loopCheck;
    //Integer "preAnimCheck" is used to check if the preanim will play by default.
    static int preAnimCheck;
    public static void main(String[] args) throws IOException, InterruptedException{
        System.out.println("Welcome to the Attorney Online 2 .ini Maker!");
        Thread.sleep(1000);
        System.out.println("Let's start making an ini for your character.");
        Thread.sleep(1000);
        System.out.println("Find the location of the folder where you'll put the ini.");
        System.out.println("Click on a sprite in the folder and the program will record the path to the character's folder.");
        Thread.sleep(1000);
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        File[] f = fd.getFiles();
        if(f.length > 0){
            fileMaker = new File(fd.getFiles()[0].getParent() + "\\\\char.ini");
            fos = new FileOutputStream(fileMaker);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            //Method "Options" lets user input the values for the options selection of an ini.
            Options();
            retry = 0;
            //Method "numberOfEmotions" determines how many emotions are in the character.
            numberOfEmotions();
            retry = 0;
            for(int pa = 0; pa < emotions; pa++){ preAnim[pa] = 0; }
            //Method "subfolderCheck" determines if there's any subfolder in the character.
            subfolderCheck();
            retry = 0;
            //If check equals 1, then the program will print out lines based on the subfolders of the character.
            //Otherwise, it just prints out standard lines for the ini.
            if (check == 1) {
                //Method "numberOfSubfolders" checks how many subfolders are in the character.
                numberOfSubfolders();
                b = q;
                retry = 0;
                //If b is greater than 1, then program will use the "ManySubfolders" method.
                //Otherwise, it will resort to the "Subfolder" method.
                if(b > 1){ ManySubfolders(); }
                else{ Subfolder(); }
                retry = 0;
                check = 0;
            }
            //Method "soundsCheck" is used to check if character has sounds tied to emotions.
            soundsCheck();
            retry = 0;
            soundVerify = check;
            //Method "Sounds" is used for user to input which emotions have sound effects.
            if(soundVerify == 1){
                numberOfSounds();
                retry = 0;
                Sounds();
            }
            retry = 0;
            key.close();
            //If q is greater than 1, program will use the "multipleSubfolders" method.
            //If not, it will use the "singleSubfolder" method.
            if(q > 1){ multipleSubfolders();}
            else if(q == 1){ singleSubfolder(); }
            else{ noSubfolders(); }

            //Final printed line to give user instructions on where to put the printed lines for their AO Character.
            System.out.println(" ");
            System.out.println("ini successfully created.");
            Thread.sleep(1000);
            System.out.println("If your emotes have names instead of numbers, go to the ini");
            System.out.println("and replace the numbers with the names of the emotes.");
            Thread.sleep(5000);
            System.out.println("Exiting out of program...");

        }
        else{
            System.out.println("Character not found, exiting out of program...");
        }
        Thread.sleep(1000);
        System.exit(0);
    }
    public static void Options(){
        String g;
        System.out.println("What is the character's name?");
        g = key.next();
        o[0] = g;
        System.out.println("What is the character's show name? (What the client will show as the name.)");
        g = key.next();
        o[1] = g;
        System.out.println("What is the default position of the character?");
        System.out.println("Valid positions are def, pro, jud, wit, and jur.");
        if (key.hasNextLine()) {
            g = key.next();
            if(g.contains("def") || g.contains("pro") || g.contains("jud") || g.contains("wit") || g.contains("jur")){
                o[2] = g;
                retry = 1;
            }

        }
        else{
            key.next();
            retry = 0;
        }

        while (retry < 1) {
            System.out.println("Try again. Input a valid position.");
            System.out.println("Valid positions are def, pro, jud, wit, and jur.");
            if (key.hasNextLine()) {
                g = key.next();
                if (g.contains("def") || g.contains("pro") || g.contains("jud") || g.contains("wit") || g.contains("jur")) {
                    o[2] = g;
                    retry = 1;
                }

            } else {
                key.next();
                retry = 0;
            }
        }
        retry = 0;
        System.out.println("What is the gender of the character? If you are unsure, input \"male\" or \"female\".");
        g = key.next();
        o[3] = g;
        System.out.println("What is the chat of the character? If you are unsure, input \"default\".");
        g = key.next();
        o[4] = g;
        System.out.println("What are the shouts of the character? If you are unsure, input \"default\".");
        g = key.next();
        o[5] = g;
        System.out.println("How will the character be scaled in-client?");
        System.out.println("Valid scaling options are pixel, fast, or smooth.");
        if (key.hasNextLine()) {
            g = key.next();
            if(g.contains("pixel") || g.contains("fast") || g.contains("smooth")){
                o[6] = g;
                retry = 1;
            }

        }
        else{
            key.next();
            retry = 0;
        }

        while (retry < 1) {
            System.out.println("Try again. Input a valid scaling option.");
            System.out.println("Valid scaling options are pixel, fast, or smooth.");
            if (key.hasNextLine()) {
                g = key.next();
                if(g.contains("pixel") || g.contains("fast") || g.contains("smooth")){
                    o[6] = g;
                    retry = 1;
                }

            } else {
                key.next();
                retry = 0;
            }
        }
    }

    public static void numberOfEmotions(){
        System.out.println("How many emotions are in your AO Character? Input a valid number");
        if (key.hasNextInt()) {
            x = Math.abs(key.nextInt());
            emotions = x;
            preAnim = new int[x];
            retry = 1;

        }
        else {
            key.next();
            retry = 0;
        }
        while (retry < 1) {
            System.out.println("Try again. Input a valid number (more than 0 and not a string.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                emotions = x;
                preAnim = new int[x];
                retry = 1;
            }
            else {
                key.next();
                retry = 0;
            }
        }
    }
    public static void subfolderCheck(){
        System.out.println("Are there any subfolders in your AO character? Type 1 for yes");
        if (key.hasNextInt()) {
            x = Math.abs(key.nextInt());
            check = x;
            retry = 1;
        }
        else {
            key.next();
            retry = 0;
        }
        while (retry < 1) {
            System.out.println("Try again. Input a valid number (not a string.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                check = x;
                retry = 1;
            }
            else {
                key.next();
                retry = 0;
            }
        }
    }

    public static void numberOfSubfolders(){
        System.out.println("How many subfolders are on your AO character? Input a valid number (not a string.)");
        System.out.println("(Number also has to be less than number of emotions.)");
        if (key.hasNextInt()) {
            x = Math.abs(key.nextInt());
            if(x < emotions){
                q = x;
                endpoint = new int[q];
                name = new String[q];
                retry = 1;
            }

        }
        else {
            key.next();
            retry = 0;
        }
        while (retry < 1) {
            System.out.println("Try again. Input a valid number (not a string.)");
            System.out.println("(Number also has to be less than number of emotions.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                if(x < emotions){
                    q = x;
                    endpoint = new int[q];
                    name = new String[q];
                    retry = 1;
                }
            }
            else {
                key.next();
                retry = 0;
            }
        }
    }

    public static void Subfolder() {
        System.out.println("Name the subfolder:");
        String m = key.next();
        name[a] = m;
        System.out.println("Where does the subfolder start?");
        System.out.println("(Input a value less than number of emotes for the whole AO Character.)");
        if (key.hasNextInt()) {
            x = Math.abs(key.nextInt());
            if(x < emotions && x > 0){
                endpoint[a] = x;
                retry = 1;
            }
        }
        else {
            key.next();
            retry = 0;
        }
        while (retry < 1) {
            System.out.println("Try again. Input a valid number (more than 0, less than number of emotions and not a string.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                if(x < emotions && x > 0){
                    endpoint[a] = x;
                    retry = 1;
                }
            }
            else {
                key.next();
                retry = 0;
            }
        }
        endpoint[a] = x;
    }
    public static void ManySubfolders() {
        int s= 0;
        String m;
        while(s < 1){
            System.out.println("Name the subfolder:");
            m = key.next();
            name[a] = m;
            System.out.println("Where does the subfolder start?");
            System.out.println("(Input a value less than number of emotes for the whole AO Character.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                if(x < emotions && x > 0){
                    endpoint[a] = x;
                    a++;
                    s++;
                    retry = 1;
                }
            }
            else {
                key.next();
                retry = 0;
            }
            while (retry < 1) {
                System.out.println("Try again. Input a valid number (more than 0, less than number of emotions and not a string.)");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if(x < emotions && x > 0){
                        endpoint[a] = x;
                        a++;
                        s++;
                        retry = 1;
                    }
                }
                else {
                    key.next();
                    retry = 0;
                }
            }
        }
        retry = 0;

        while(s < b){
            System.out.println("Name the subfolder:");
            m = key.next();
            name[a] = m;
            System.out.println("Where does the subfolder start?");
            System.out.println("(Input a value less than number of emotes for the Character and less than the previous endpoint.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                if(x > 0  && x > endpoint[a - 1] && x < emotions){
                    endpoint[a] = x;
                    retry = 1;
                    a++;
                    s++;
                }
            }
            else {
                key.next();
                retry = 0;
            }
            while (retry < 1) {
                System.out.println("Try again. Input a valid number (more than 0 and previous endpoint, less than number of emotions and not a string.)");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if(x > 0  && x > endpoint[a - 1] && x < emotions){
                        endpoint[a] = x;
                        retry = 1;
                        a++;
                        s++;
                    }
                }
                else {
                    key.next();
                    retry = 0;
                }
            }
            retry = 0;
        }

    }

    public static void soundsCheck(){
        System.out.println("Are there any sounds in your AO character? Type 1 for yes");
        if (key.hasNextInt()) {
            x = Math.abs(key.nextInt());
            check = x;
            retry = 1;
        }
        else {
            key.next();
            retry = 0;
        }
        while (retry < 1) {
            System.out.println("Try again. Input a valid number (not a string.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                check = x;
                retry = 1;
            }
            else {
                key.next();
                retry = 0;
            }
        }
    }

    public static void numberOfSounds(){
        System.out.println("How many sounds are in your AO Character? Input a valid number");
        if (key.hasNextInt()) {
            x = Math.abs(key.nextInt());
            preSounds = new int[x];
            soundName = new String[x];
            soundTime = new int[x];
            soundEndpoint = new int[x];
            soundTimePlay = new int[x];
            sn = x;
            retry = 1;

        }
        else {
            key.next();
            retry = 0;
        }
        while (retry < 1) {
            System.out.println("Try again. Input a valid number (more than 0 and not a string.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                preSounds = new int[x];
                soundName = new String[x];
                soundTime = new int[x];
                soundEndpoint = new int[x];
                soundTimePlay = new int[x];
                sn = x;
                retry = 1;
            }
            else {
                key.next();
                retry = 0;
            }
        }
    }
    public static void Sounds(){
        int h = 0;
        String q;
        int n = 0;
        while(h != 1){
            System.out.println("Which emotion has a sound effect?");
            System.out.println("(Input an number that less than the number of emotions, and more than 0.)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                if(x < emotions && x > 0) {
                    preSounds[h] = x;
                    soundTime[h] = x;
                    soundEndpoint[h] = x;
                    n = x;
                    retry = 1;
                }
            }
            else {
                key.next();
                retry = 0;
            }
            while (retry < 1) {
                System.out.println("Try again. Input a valid number (not a string.)");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if(x < emotions && x > 0) {
                        preSounds[h] = x;
                        soundTime[h] = x;
                        soundEndpoint[h] = x;
                        n = x;
                        retry = 1;
                    }
                }
                else {
                    key.next();
                    retry = 0;
                }
            }
            retry = 0;
            System.out.println("What is the name of the sound?");
                q = key.next();
                soundName[h] = q;
            System.out.println("When does the sound play?");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                    soundTimePlay[h] = x;
                    retry = 1;
            }
            else {
                key.next();
                retry = 0;
            }
            while (retry < 1) {
                System.out.println("Try again. Input a valid number (not a string.)");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                        soundTimePlay[h] = x;
                        retry = 1;
                }
                else {
                    key.next();
                    retry = 0;
                }
            }
            retry = 0;
            System.out.println("Will the emote automatically play the preanim? (Type 1 for yes)");
            if (key.hasNextInt()) {
                x = Math.abs(key.nextInt());
                    preAnimCheck = x;
                    retry = 1;

            }
            else {
                key.next();
                retry = 0;
            }
            while (retry < 1) {
                System.out.println("Try again. Input a valid number (not a string and greater than 0.)");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    preAnimCheck = x;
                    retry = 1;
                }
                else {
                    key.next();
                    retry = 0;
                }
            }
            if(preAnimCheck == 1){
                preAnim[n - 1] = 1;
            }
            loopCheck = 0;
            retry = 0;
            n = 0;
            h++;
            preAnimCheck = 0;
        }
        if(sn > 1){
            while(h < sn){
                System.out.println("Which emotion has a sound effect?");
                System.out.println("(Input an number that less than the number of emotions, and more than 0.)");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    if(x > 0  && x > soundEndpoint[h - 1] && x < emotions){
                        preSounds[h] = x;
                        soundTime[h] = x;
                        soundEndpoint[h] = x;
                        n = x;
                        retry = 1;
                    }
                }
                else {
                    key.next();
                    retry = 0;
                }
                while (retry < 1) {
                    System.out.println("Try again. Input a valid number (not a string.)");
                    if (key.hasNextInt()) {
                        x = Math.abs(key.nextInt());
                        if(x > 0  && x > soundEndpoint[h - 1] && x < emotions){
                            preSounds[h] = x;
                            soundTime[h] = x;
                            soundEndpoint[h] = x;
                            n = x;
                            retry = 1;
                        }
                    }
                    else {
                        key.next();
                        retry = 0;
                    }
                }
                retry = 0;
                System.out.println("What is the name of the sound?");
                q = key.next();
                soundName[h] = q;
                System.out.println("When does the sound play?");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    soundTimePlay[h] = x;
                    retry = 1;
                }
                else {
                    key.next();
                    retry = 0;
                }
                while (retry < 1) {
                    System.out.println("Try again. Input a valid number (not a string.)");
                    if (key.hasNextInt()) {
                        x = Math.abs(key.nextInt());
                        soundTimePlay[h] = x;
                        retry = 1;
                    }
                    else {
                        key.next();
                        retry = 0;
                    }
                }
                retry = 0;
                System.out.println("Will the emote automatically play the preanim? (Type 1 for yes)");
                if (key.hasNextInt()) {
                    x = Math.abs(key.nextInt());
                    preAnimCheck = x;
                    retry = 1;
                }
                else {
                    key.next();
                    retry = 0;
                }
                check = 0;
                while (retry < 1) {
                    System.out.println("Try again. Input a valid number (not a string.)");
                    if (key.hasNextInt()) {
                        x = Math.abs(key.nextInt());
                        preAnimCheck = x;
                        retry = 1;
                    }
                    else {
                        key.next();
                        retry = 0;
                    }
                }
                if(preAnimCheck == 1){
                    preAnim[n - 1] = 1;
                }
                retry = 0;
                n = 0;
                preAnimCheck = 0;
                h++;
            }
        }
        else{
            System.out.println(" ");
        }
    }

    public static void noSubfolders() throws IOException {
        int u = 0;
        bw.write("[Options]");
        bw.newLine();
        bw.write("name = " + o[0]);
        bw.newLine();
        bw.write("showname = " + o[1]);
        bw.newLine();
        bw.write("side = " + o[2]);
        bw.newLine();
        bw.write("gender = " + o[3]);
        bw.newLine();
        bw.write("chat = " + o[4]);
        bw.newLine();
        bw.write("shouts = " + o[5]);
        bw.newLine();
        bw.write("scaling = " + o[6]);
        bw.newLine();
        bw.write(" ");
        bw.newLine();

        bw.write("[Emotions]");
        bw.newLine();
        bw.write("emotions = " + emotions);
        bw.newLine();
        bw.write(" ");
        bw.newLine();
        while (i <= emotions) {
            bw.write(i + " = " + i + "#-#" + i + "#" + preAnim[u] + "#1#");
            u++;
            i++;
            bw.newLine();
        }
        u = 0;
        i = 1;

        bw.write(" ");
        bw.newLine();

        if(soundVerify == 1){
            bw.write("[SoundN]");
            bw.newLine();
            while (i <= sn) {
                bw.write(preSounds[u] + " = " + soundName[u]);
                u++;
                i++;
                bw.newLine();
            }

            bw.write(" ");
            bw.newLine();

            u = 0;
            i = 1;
            bw.write("[SoundT]");
            bw.newLine();
            while (i <= sn) {
                bw.write(soundTime[u] + " = " + soundTimePlay[u]);
                u++;
                i++;
                bw.newLine();
            }
        }
        bw.write(" ");
        bw.newLine();
        bw.close();
    }

    public static void singleSubfolder() throws IOException {
        int u = 0;
        bw.write("[Options]");
        bw.newLine();
        bw.write("name = " + o[0]);
        bw.newLine();
        bw.write("showname = " + o[1]);
        bw.newLine();
        bw.write("side = " + o[2]);
        bw.newLine();
        bw.write("gender = " + o[3]);
        bw.newLine();
        bw.write("chat = " + o[4]);
        bw.newLine();
        bw.write("shouts = " + o[5]);
        bw.newLine();
        bw.write("scaling = " + o[6]);
        bw.newLine();
        bw.write(" ");
        bw.newLine();

        int t = 1;
        bw.write("[Emotions]");
        bw.newLine();
        bw.write("emotions = " + emotions);
        bw.newLine();
        bw.write(" ");
        bw.newLine();

        while (i <= endpoint[0]) {
            bw.write(i + " = " + i + "#-#" + i + "#" + preAnim[u] + "#1#");
            bw.newLine();
            u++;
            i++;
        }
        bw.write(" ");
        bw.newLine();


        while (k < (endpoint.length)) {
            while (i <= emotions) {
                bw.write(i + " = " + (t) + "- " + name[k] + "#-#" + name[k] + "/" + (t) + "#" + preAnim[u] + "#1#");
                bw.newLine();
                u++;
                i++;
                t++;
            }
            k++;
        }
        bw.write(" ");
        bw.newLine();
        u = 0;
        i = 1;
        if(soundVerify == 1){
            bw.write("[SoundN]");
            bw.newLine();
            while (i <= sn) {
                bw.write(preSounds[u] + " = " + soundName[u]);
                u++;
                i++;
                bw.newLine();
            }

            bw.write(" ");
            bw.newLine();

            u = 0;
            i = 1;
            bw.write("[SoundT]");
            bw.newLine();
            while (i <= sn) {
                bw.write(soundTime[u] + " = " + soundTimePlay[u]);
                u++;
                i++;
                bw.newLine();
            }
        }
        bw.write(" ");
        bw.newLine();
        bw.close();
    }

    public static void multipleSubfolders() throws IOException {
        int u = 0;
        int d = 0;
        bw.write("[Options]");
        bw.newLine();
        bw.write("name = " + o[0]);
        bw.newLine();
        bw.write("showname = " + o[1]);
        bw.newLine();
        bw.write("side = " + o[2]);
        bw.newLine();
        bw.write("gender = " + o[3]);
        bw.newLine();
        bw.write("chat = " + o[4]);
        bw.newLine();
        bw.write("shouts = " + o[5]);
        bw.newLine();
        bw.write("scaling = " + o[6]);
        bw.newLine();
        bw.write(" ");
        bw.newLine();

        bw.write("[Emotions]");
        bw.newLine();
        bw.write("emotions = " + emotions);
        bw.newLine();
        bw.write(" ");
        bw.newLine();

        while (i <= endpoint[d]) {
            bw.write(i + " = " + i + "#-#" + i + "#" + preAnim[u] + "#1#");
            bw.newLine();
            u++;
            i++;
        }

        bw.write(" ");
        bw.newLine();

        d++;
        while (k < (endpoint.length - 1)) {
            while (i <= endpoint[d]) {
                bw.write(i + " = " + (t) + "- " + name[k] + "#-#" + name[k] + "/" + (t) + "#" + preAnim[u] + "#1#");
                bw.newLine();
                u++;
                i++;
                t++;
            }
            bw.write(" ");
            bw.newLine();
            d++;
            t = 1;
            k++;
        }

        bw.write(" ");
        bw.newLine();

        emotionReset();

        while (i <= emotions) {
            bw.write(i + " = " + (t) + "- " + name[k] + "#-#" + name[k] + "/" + (t) + "#" + preAnim[u] + "#1#");
            bw.newLine();
            u++;
            i++;
            t++;
        }
        bw.write(" ");
        bw.newLine();
        u = 0;
        i = 1;
        if(soundVerify == 1){
            bw.write("[SoundN]");
            bw.newLine();
            while (i <= sn) {
                bw.write(preSounds[u] + " = " + soundName[u]);
                u++;
                i++;
                bw.newLine();
            }

            bw.write(" ");
            bw.newLine();

            u = 0;
            i = 1;
            bw.write("[SoundT]");
            bw.newLine();
            while (i <= sn) {
                bw.write(soundTime[u] + " = " + soundTimePlay[u]);
                u++;
                i++;
                bw.newLine();
            }
        }
        bw.write(" ");
        bw.newLine();
        bw.close();
    }

    public static void emotionReset(){ t = 1;}
}