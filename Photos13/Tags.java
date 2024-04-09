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
     * Gets the name of the tag
     * @return TagName
     */
    public String getTagName(){
        return TagName;
    }
    
    /**
     * Sets the name of the tag
     * @param newName
     */
    public void setTagName(String newName){
        TagName = newName;
    }
    
    /**
     * Gets the value of the tag
     * @return TagValue
     */
    public String getTagValue(){
        return TagValue;
    }
    
    /**
     * Sets the value of the tag
     * @param newValue
     */
    public void setTagValue(String newValue){
        TagValue = newValue;
    }
    
    /**
     * Return toString value with a String made of TagName and TagValue
     * @return TagName + " " + TagValue
     */
    public String toString(){
        return TagName + " " + TagValue;
    }
    
    /**
     * Check if tags identical in name AND value
     * @param tag
     * @return If Tags Are Equal in boolean format
     */
    public boolean equals(Tags tag){
        return this.TagName.equals(tag.getTagName()) && this.TagValue.equals(tag.getTagValue());
    }
}
