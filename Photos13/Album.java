package Photos13;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
    static final long serialVersionID = 1L;
    private ArrayList<EachPhoto> Contains;
    private String name;
    
    public Album(ArrayList<EachPhoto> Contains, String name){
        this.Contains = Contains;
        this.name = name;
    }
    
    public Album(String name){
        this.name = name;
        Contains = new ArrayList<EachPhoto>();
    }
    
    public ArrayList<EachPhoto> getContains(){
        return Contains;
    }
    
    public void setContains(ArrayList<EachPhoto> newContains){
        Contains = newContains;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public String getEarliest(){
            //return the earliest filmed photo
        return null;
    }
    
    public String getLatest(){
            //return the latest filmed photo
        return null;
    }
    
    public String toString(){
        return name;
    }
}
