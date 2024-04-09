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
    * @param Contains
    * @param name
    */
    public Album(String name){
        this.name = name;
        Contains = new ArrayList<EachPhoto>();
    }
    
    /**
     * Gets the list of photos in the album
     * @return Contains
     */
    public ArrayList<EachPhoto> getContains(){
        return Contains;
    }
    
    /**
     * Set the new list of photos in the album
     * @param newContains
     */
    public void setContains(ArrayList<EachPhoto> newContains){
        Contains = newContains;
    }
    
    /**
     * Gets the name of the Album
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Sets the name of the Album
     * @param newName
     */
    public void setName(String newName){
        name = newName;
    }
    
    /**
     * Gets the earliest modification time of the photos stored
     * @return Earliest Modification Time in String format
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
     * Gets the latest modification time of the photos stored
     * @return Latest Modification Time in String format
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
     * Return toString value with name of the album
     * @return name
     */
    public String toString(){
        return name;
    }
}
