package Photos13;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

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
        if(Contains.size() == 0) return null;
        else{
            Calendar earliest = Contains.get(0).getDate();
            for(int i=1;i<Contains.size();i++){
                if(earliest.after(Contains.get(i).getDate())) earliest = Contains.get(i).getDate();
            }
            return Admin.ConvertCalendartoString(earliest);
        }
    }
    
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
    
    public String toString(){
        return name;
    }
}
