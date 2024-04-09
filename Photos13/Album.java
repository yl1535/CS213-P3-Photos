package Photos13;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class is to display the albums of photos.
 * @author Yue Luo
 * @author Nicole Le
 */

public class Album implements Serializable{
    static final long serialVersionID = 1L;
    private ArrayList<EachPhoto> Contains;
    private String name;
    
   /**
    * Construct the album
    * @param Contains
    * @param name
    */
    public Album(ArrayList<EachPhoto> Contains, String name){
        this.Contains = Contains;
        this.name = name;
    }
    
    /**
     * Construct the album
     * @param name
     */
    public Album(String name){
        this.name = name;
        Contains = new ArrayList<EachPhoto>();
    }
    
    /**
     * Getter method
     * @return
     */
    public ArrayList<EachPhoto> getContains(){
        return Contains;
    }
    
    /**
     * Setter method
     * @param newContains
     */
    public void setContains(ArrayList<EachPhoto> newContains){
        Contains = newContains;
    }
    
    /**
     * Getter method for name
     * @return
     */
    public String getName(){
        return name;
    }
    
    /**
     * Setter method for name
     * @param newName
     */
    public void setName(String newName){
        name = newName;
    }
    
    /**
     * Getter method for the earliest
     * @return
     */
    public String getEarliest(){
        if(Contains.size() == 0) return null;
        else{
            Calendar earliest = Contains.get(0).getDate();
            for(int i=1;i<Contains.size();i++){
                if(earliest.after(Contains.get(i).getDate())) earliest = Contains.get(i).getDate();
            }
            return Admin.ConvertCalendartoString(earliest);
        }
    }
    
    /**
     * Getter method for the latest
     * @return
     */
    public String getLatest(){
        if(Contains.size() == 0) return null;
        else{
            Calendar latest = Contains.get(0).getDate();
            for(int i=1;i<Contains.size();i++){
                if(latest.before(Contains.get(i).getDate())) latest = Contains.get(i).getDate();
            }
            return Admin.ConvertCalendartoString(latest);
        }
    }
    
    /**
     * Return name
     */
    public String toString(){
        return name;
    }
}
