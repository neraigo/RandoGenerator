/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randogenerator;

import java.util.LinkedList;
import java.util.Random;
import static randogenerator.RandoGenerator.ListGenerator;

/**
 *
 * @author Nathan
 */
public class RandomStatGenerator {
    int ap;
    int def;
    int mag;
    int str;
    Random rand;
    LinkedList list;
    
    public RandomStatGenerator(int ap, int def, int mag, int str, Random rand, LinkedList list){
        this.ap = ap;
        this.def = def;
        this.mag = mag;
        this.str = str;
        this.rand = rand;
        this.list = list;
    }
    
    public LinkedList RandomizeStats(Random rand) throws Exception{
/*
        Initial Stats are:
            AP = 0
            Defense  = 2
            Magic = 6
            Strenght = 2
            00020602
     
        */
        LinkedList stats = new LinkedList();
        
        //list = ListGenerator(file);
        
        for(int i = 0; i < list.size(); i++){
            
            stats.add(list.get(i).toString()+RandomStatString());
            //System.out.println(list.get(i).toString());
            //randomize
            //System.out.println(StatToString(def));
            //System.out.println(RandomStatString(ap,def,mag,str,rand));
            //System.out.println(RandomStatString(ap, def,mag,str, rand));
            //System.out.println(rand.nextInt((3-0) + 1) + 0);
            //System.out.println(Integer.toHexString(mag+6));
        }
        
        //System.out.println(Integer.toHexString(mag));
        return stats;
    }
    public String StatToString(int stat){
        //assumes hex value is passed
        //checks to see if the hex value is less than 16
        //if yes then need to add a leading zero to the string or format will be wrong.
        return (stat < 16) ? "0"+Integer.toHexString(stat) : Integer.toHexString(stat);
    }
    public String RandomStatString(){
        //roll between 0-3
        int range = ((3 - 0) + 1) + 0;        
        ap = ap+rand.nextInt(range);
        def = def+rand.nextInt(range);
        mag = mag+rand.nextInt(range);
        str = str+rand.nextInt(range);
        
        //return (StatToString(ap+rand.nextInt(range))+StatToString(def+rand.nextInt(range))+StatToString(mag+rand.nextInt(range))+StatToString(str+rand.nextInt(range)));
        
        return StatToString(ap)+StatToString(def)+StatToString(mag)+StatToString(str);
    }  
}
