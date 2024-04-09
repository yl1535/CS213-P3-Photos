package Photos13;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.Serializable;

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
    public EachPhoto(String PhotoPath, String Caption, ArrayList<Tags> tags){   //ToDO: Add Date of Photo
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
     * Gets the path of a photo
     * @return
     */
    public String getPath(){
        return PhotoPath;
    }
    
    /**
     * Sets the new path of a photo
     * @param newPath
     */
    public void setPath(String newPath){
        PhotoPath = newPath;
    }
    
    /**
     * Gets the caption of a photo
     * @return
     */
    public String getCaption(){
        return Caption;
    }
    
    /**
     * Sets the new caption of a photo
     * @param newCaption
     */
    public void setCaption(String newCaption){
        Caption = newCaption;
    }
    
    /**
     * Get the tags of a photo
     * @return tags
     */
    public ArrayList<Tags> getTags(){
        return tags;
    }
    
    /**
     * Set the new tags of a photo
     * @param newTags
     */
    public void setTags(ArrayList<Tags> newTags){
        tags = newTags;
    }
    
    /**
     * Get the width of a photo
     * @return width
     */
    public int getWidth(){
        return width;
    }
  
    /**
     * Get the height of a photo
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
     * Get date of photo
     * @return
     */
    public Calendar getDate(){
        return Date;
    }
    
    /**
     * Set the data of photo
     * @param c
     */
    public void setDate(Calendar c){
        this.Date = c;
    }
    
   /**
    * Gives the photo's path
    * @return path of photo
    */
    public String toString(){
        return PhotoPath;
    }

}
