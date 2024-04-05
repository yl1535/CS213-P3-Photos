package Photos13;

import java.util.ArrayList;

public class Album {
    private ArrayList<EachPhoto> Contains;
    private String name;
    
    public Album(ArrayList<EachPhoto> Contains, String name){
        this.Contains = Contains;
        this.name = name;
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
}
