package Photos13;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.Serializable;

public class EachPhoto implements Serializable{
    static final long serialVersionID = 1L;
    private String PhotoPath;
    private String Caption;
    private ArrayList<Tags> tags;
        //ToDO: Set Date of Photo by Calendar
    
    public EachPhoto(String PhotoPath, String Caption, ArrayList<Tags> tags){   //ToDO: Add Date of Photo
        this.PhotoPath = PhotoPath;
        this.Caption = Caption;
        this.tags = tags;
    }
    
    public String getPath(){
        return PhotoPath;
    }
    
    public void setPath(String newPath){
        PhotoPath = newPath;
    }
    
    public String getCaption(){
        return Caption;
    }
    
    public void setCaption(String newCaption){
        Caption = newCaption;
    }
    
    public ArrayList<Tags> getTags(){
        return tags;
    }
    
    public void setTags(ArrayList<Tags> newTags){
        tags = newTags;
    }
    
    //ToDO: Add Date of Photo operations
}
