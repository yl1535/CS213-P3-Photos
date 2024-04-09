package Photos13;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This is the class for each individual photo within an album 
 * @author Yue Luo
 * @author Nicole Le
 */

public class EachPhoto implements Serializable{
    static final long serialVersionID = 1L;
    private String PhotoPath;
    private String Caption;
    private ArrayList<Tags> tags = new ArrayList<Tags>();
    private int width;
    private int height;
    private Calendar Date;
    
    /**
     * Constructs each photo
     * @param PhotoPath
     * @param Caption
     * @param tags
     */
    public EachPhoto(String PhotoPath, String Caption, ArrayList<Tags> tags){
        this.PhotoPath = PhotoPath;
        this.Caption = Caption;
        this.tags = tags;
    }
    
    /**
     * Constructs each photo
     * @param PhotoPath
     */
    public EachPhoto(String PhotoPath){
        this.PhotoPath = PhotoPath;
        this.Caption = "";
        tags = new ArrayList<Tags>();
    }
    
    /**
     * Gets the path of the photo
     * @return PhotoPath
     */
    public String getPath(){
        return PhotoPath;
    }
    
    /**
     * Sets the new path of the photo
     * @param newPath
     */
    public void setPath(String newPath){
        PhotoPath = newPath;
    }
    
    /**
     * Gets the caption of the photo
     * @return Caption
     */
    public String getCaption(){
        return Caption;
    }
    
    /**
     * Sets the new caption of the photo
     * @param newCaption
     */
    public void setCaption(String newCaption){
        Caption = newCaption;
    }
    
    /**
     * Get the list of tags of the photo
     * @return tags
     */
    public ArrayList<Tags> getTags(){
        return tags;
    }
    
    /**
     * Set the new list of tags of the photo
     * @param newTags
     */
    public void setTags(ArrayList<Tags> newTags){
        tags = newTags;
    }
    
    /**
     * Get the width of the photo
     * @return width
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Get the height of the photo
     * @return height
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Set the size of the photo
     * @param width
     * @param height
     */
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    /**
     * Get date of the photo in Calendar datatype format
     * @return Date
     */
    public Calendar getDate(){
        return Date;
    }
    
    /**
     * Set the data of photo
     * @param calendar
     */
    public void setDate(Calendar calendar){
        this.Date = calendar;
    }
    
    /**
    * Return toString value with path of the photo
    * @return PhotoPath
    */
    public String toString(){
        return PhotoPath;
    }

}
