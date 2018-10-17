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
import org.apache.commons.lang3.RandomStringUtils;

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
        String spoiler;
        String cutsceneLocations;
        String critAbilities;
        String critAbilitiesLocations;
        String doNotTouch;
        String path;
        String levels;
        
        String seedName;
        String input;
        
        LinkedList abilitiesList;
        LinkedList locationsList;
        LinkedList cutsceneLocationsList;
        LinkedList itemsList;
        LinkedList critAbilitiesList;
        LinkedList critAbilitiesLocationsList;
        LinkedList levelsList;
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
        spoiler = dir.toString() + "/Spoiler.txt";
        path = dir.toString() + "/Files";
             
        //output = "F266B00B.pnach";
        items = "/Items.txt";
        abilities = "/Ability_Codes.txt";
        locations = "/Location_Codes.txt";
        cutsceneLocations = "/Cutscene_Codes.txt";
        critAbilities = "/CritAbilities.txt";
        critAbilitiesLocations = "/CritAbilitiesLocation.txt";
        levels = "/Levels.txt";
        doNotTouch = "/Do_Not_Touch.txt";
        
        RandomStatGenerator randStats;
    
        Scanner s = new Scanner(System.in);
        Random rand;// = new Random();
                
        //ask for seed input
        System.out.print("Seed Name(Use 'random' for random seed): ");
        input = s.nextLine();
        System.out.println();
        
        //generate seedName
        if(input.equals("random")){
            seedName = RandomStringUtils.randomAlphabetic(8);
            //seedName = "butts";
        } else {
            seedName = input;
        }
        //set rand with the seedName
        rand = new Random(seedName.hashCode());
        System.out.printf("Using \"%s\" as the seed name.\n\n", seedName);
/**************Choose crit or not***************/
        System.out.print("Enter 1 if this is a Critical Mode Playthrough: ");
        input = s.nextLine();
        System.out.println();
        
        if(input.equals("1")){
            System.out.println("Adding Crit Abilities.\n");
            critAbilitiesList = ListGenerator(path+critAbilities);
            critAbilitiesLocationsList = ListGenerator(path+critAbilitiesLocations);
        }else {
            System.out.println("Proceeding with a non Crit playthrough");
            critAbilitiesList = null;
            critAbilitiesLocationsList = null;
        }

  /**************Choose crit or not***************/      
       
        //generating lists 
        //looks for relative path (local files)
        abilitiesList = ListGenerator(path+abilities, critAbilitiesList);
        locationsList = ListGenerator(path+locations, critAbilitiesLocationsList);
        cutsceneLocationsList = ListGenerator(path+cutsceneLocations);
        itemsList = ListGenerator(path+items);
        //levelsList = 
        //RandomizeStats(path+levels, rand);
        randStats = new RandomStatGenerator(0x00, 0x02, 0x06, 0x02, rand, ListGenerator(path+levels));
        levelsList = randStats.RandomizeStats(rand);
        doNotTouchList = ListGenerator(path+doNotTouch);
        
        /* Looks for absolute path in jar file, edit line in ListGenerator if using this.
        abilitiesList = ListGenerator(abilities);
        locationsList = ListGenerator(locations);
        cutsceneLocationsList = ListGenerator(cutsceneLocations);
        itemsList = ListGenerator(items);
        doNotTouchList = ListGenerator(doNotTouch);
        
        */
               
        //shuffle items
        //System.out.println(rand.nextInt());
        //System.out.println(rand.nextInt());
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
        finalList.addAll(levelsList);
        finalList.addAll(doNotTouchList);
        
                      
        //fileoutput
        //File file = new File(System.getProperty("/"), output);
        try{
        
            PrintWriter writer = new PrintWriter(output, "UTF-8");
            for(int i = 0; i < finalList.size(); i++){
                writer.println(finalList.get(i));
            }
            
            //make spoiler file
            writer = new PrintWriter(spoiler, "UTF-8");
            writer.printf("The seed name was: \"%s\"", seedName);
            
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
