package Photos13;

import java.io.Serializable;

/**
 * This class implements the tags functionality for photos
 * @author Yue Luo
 * @author Nicole Le
 */
public class Tags implements Serializable{
    private String TagName;
    private String TagValue;
    
    /**
     * Constructs a photo tag
     * @param TagName
     * @param TagValue
     */
    public Tags(String TagName, String TagValue){
        this.TagName = TagName;
        this.TagValue = TagValue;
    }
    
    /**
     * Gets a tag name
     * @return
     */
    public String getTagName(){
        return TagName;
    }
    
    /**
     * Sets a tag name
     * @param newName
     */
    public void setTagName(String newName){
        TagName = newName;
    }
    
    /**
     * Gets a tag value
     * @return
     */
    public String getTagValue(){
        return TagValue;
    }
    
    /**
     * Sets a tag value
     * @param newValue
     */
    public void setTagValue(String newValue){
        TagValue = newValue;
    }
    
    /**
     * @return TagName and TagValue as String
     */
    public String toString(){
        return TagName + " " + TagValue;
    }
    
    /**
     * Check if tags identical in name AND value
     * @param tag
     * @return
     */
    public boolean equals(Tags tag){
        return this.TagName.equals(tag.getTagName()) && this.TagValue.equals(tag.getTagValue());
    }
}
