package Photos13;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implements the non-admin user subsystem. The user has the functionalities of manipulating albums (e.g. create, delate, rename)
 * @author Yue Luo
 * @author Nicole Le
 */
public class User implements Serializable{
    static final long serialVersionID = 1L;
    private String name;
    private ArrayList<Album> albums;
    
    /**
     * Construct a user
     * @param myname
     */
    public User(String myname){
        name = myname;
        albums = new ArrayList<Album>();
    }
    
    /**
     * Return toString value with the name of the User
     * @return name
     */
    public String toString(){
        return name;
    }
    
    /**
     * Gets the name of user
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Sets the name of user
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Get the albums associated with a particular user
     * @return albums
     */
    public ArrayList<Album> getAlbums(){
        return albums;
    }
    
    /**
     * Set the albums of a particular user
     * @param albums
     */
    public void setAlbums(ArrayList<Album> albums){
        this.albums = albums;
    }
}
