package Photos13;

import java.io.Serializable;

public class Tags implements Serializable{
    private String TagName;
    private String TagValue;
    
    public Tags(String TagName, String TagValue){
        this.TagName = TagName;
        this.TagValue = TagValue;
    }
    
    public String getTagName(){
        return TagName;
    }
    
    public void setTagName(String newName){
        TagName = newName;
    }
    
    public String getTagValue(){
        return TagValue;
    }
    
    public void setTagValue(String newValue){
        TagValue = newValue;
    }
    
    public String toString(){
        return TagName + " " + TagValue;
    }
}
