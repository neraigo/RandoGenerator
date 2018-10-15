/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randogenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Nathan
 */
public class RandoGenerator {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String abilities;
        String items;
        String locations;
        String output;
        String cutsceneLocations;
        String critAbilities;
        String critAbilitiesLocations;
        String doNotTouch;
        String path;
        
        String input;
        
        LinkedList abilitiesList;
        LinkedList locationsList;
        LinkedList cutsceneLocationsList;
        LinkedList itemsList;
        LinkedList critAbilitiesList;
        LinkedList critAbilitiesLocationsList;
        LinkedList doNotTouchList;
        LinkedList finalList = new LinkedList();
        //randomize
        //output = "FAF99301.pnach";
        
        //generating the folder path
        //path = B00BFileGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File temp = new File(System.getProperty("java.class.path"));
        File dir = temp.getAbsoluteFile().getParentFile();     
        
        //specific output for relative path location
        output = dir.toString() + "/F266B00B.pnach";
        path = dir.toString() + "/Files";
        
        
        //output = "F266B00B.pnach";
        items = "/Items.txt";
        abilities = "/Ability_Codes.txt";
        locations = "/Location_Codes.txt";
        cutsceneLocations = "/Cutscene_Codes.txt";
        critAbilities = "/CritAbilities.txt";
        critAbilitiesLocations = "/CritAbilitiesLocation.txt";
        doNotTouch = "/Level_Ups.txt";

        
        Scanner s = new Scanner(System.in);
        Random rand = new Random();
                
        //ask for seed input
        System.out.print("Seed Name(Use 'random' for random seed): ");
        input = s.nextLine();
        System.out.println();
        
 
        //generate random seed
        if(input.equals("random")){
            System.out.println("Using a random seed.");
            //System.out.println(rand.nextInt());
            //System.out.println(rand.nextInt());
        } else {
            System.out.printf("Using %s as the seed name.\n", input);
            rand.setSeed(input.hashCode());
            //System.out.println(rand.nextInt());
        }
        
        
/**REBEL DO YOUR WORK HERE**********
        String modeSelect;
        String keyblades = "/keyblades.txt";
        LinkedList keybladesMode;
    //prompt for normal or not (keyblade)
        System.out.println();
        System.out.println("Normalized Keyblade Mode takes all the Keyblades and preserves their abilities, but makes every Keyblade do the same damage as Kingdom Key.\n");
        System.out.print("Press 1 for Standard Keyblade Mode, 2 for Normalized Keyblade Mode: ");

    //read input
        //could read input as integer but thats not as safe. 
        //reading it as a string allows some flexibility to avoid a crash
        modeSelect = s.next();
        System.out.println();
        
    //assumes that 1 or invalid character will be default 
        if(modeSelect.equals("2") || modeSelect.toLowerCase().equals("two")){
            System.out.println("Activating Normalized Keyblade Mode.");
            keybladesMode = ListGenerator(path+keyblades);
            //adds the keyblade settings to the start of the file. this will not affect anything that follows but could be done at the end of the program as well.
            finalList.addAll(keybladesMode);
        } else {
            System.out.println("Proceeding with Standard Keyblade Mode.");
        }
***************************************/
        
/**************Choose crit or not***************/
        System.out.print("Enter 1 if this is a Critical Mode Playthrough: ");
        input = s.nextLine();
        
        if(input.equals("1")){
            critAbilitiesList = ListGenerator(path+critAbilities);
            critAbilitiesLocationsList = ListGenerator(path+critAbilitiesLocations);
        }else {
            critAbilitiesList = null;
            critAbilitiesLocationsList = null;
        }

  /**************Choose crit or not***************/      
        /**** TEST BLOCK****/
        //System.out.println(B00BFileGenerator.class.getResource("/Files/Items.txt"));
        //InputStream stream = B00BFileGenerator.class.getResourceAsStream("/Items.txt");
        //System.out.println(stream!=null);
        //stream = B00BFileGenerator.class.getClass().getResourceAsStream("Files/Items.txt");
                //System.out.println(stream!=null);
        //System.out.println(path);  

        //System.out.println(path+items);
                
        
        //generating lists 
        //looks for relative path (local files)
        abilitiesList = ListGenerator(path+abilities, critAbilitiesList);
        locationsList = ListGenerator(path+locations, critAbilitiesLocationsList);
        cutsceneLocationsList = ListGenerator(path+cutsceneLocations);
        itemsList = ListGenerator(path+items);
        doNotTouchList = ListGenerator(path+doNotTouch);
        
        /* Looks for absolute path in jar file, edit line in ListGenerator if using this.
        abilitiesList = ListGenerator(abilities);
        locationsList = ListGenerator(locations);
        cutsceneLocationsList = ListGenerator(cutsceneLocations);
        itemsList = ListGenerator(items);
        doNotTouchList = ListGenerator(doNotTouch);
        
        */
        
        
        //shuffle items
        Collections.shuffle(itemsList, rand);
        //Add Items to cutscene Locations
        //use of list.pollFirst() so that it removes items that are added
        //therefore remaining items will be added to abilities to be shuffled again
        for(int i = 0; i < cutsceneLocationsList.size(); i++){
           finalList.add(cutsceneLocationsList.get(i).toString() + itemsList.pollFirst().toString());          
        }
        //combine and shuffle remaining items to abilites
        abilitiesList.addAll(itemsList);
        Collections.shuffle(abilitiesList, rand);
                 
         //currently assume lists are same size
        for(int i = 0; i < abilitiesList.size(); i++){
            finalList.add(locationsList.get(i).toString() + abilitiesList.get(i).toString());
        }    
        //add donottouchme
        finalList.addAll(doNotTouchList);
        
                      
        //fileoutput
        //File file = new File(System.getProperty("/"), output);
        try{
        
            PrintWriter writer = new PrintWriter(output, "UTF-8");
            for(int i = 0; i < finalList.size(); i++){
                writer.println(finalList.get(i));
            }
            writer.close();        
        } catch (Exception e){
            System.err.println(e);
        }
    }
    public static LinkedList ListGenerator(String name)throws Exception {
        String line;
        LinkedList list = new LinkedList();
        
        try{
        //read relative path?
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(name)));
        
        //For reading directly from JAR file
        //BufferedReader br = new BufferedReader(new InputStreamReader(B00BFileGenerator.class.getResourceAsStream(name)));
        while((line = br.readLine()) != null) {
            list.add(line);    
        }        

        } catch (Exception e){
            System.err.println(e);
        }
        return list;
    }
    public static LinkedList ListGenerator(String name, LinkedList oldList) throws Exception {
        LinkedList list;
        
        list = ListGenerator(name);
        if (oldList != null){
            list.addAll(oldList);
        }
        return list;
    }
}
