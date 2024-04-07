package Photos13;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    static final long serialVersionID = 1L;
    private String name;
    private ArrayList<Album> albums;
    
    public User(String myname){
        name = myname;
        albums = new ArrayList<Album>();
    }
    
    public String toString(){
        return name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public ArrayList<Album> getAlbums(){
        return albums;
    }
    
    public void setAlbums(ArrayList<Album> albums){
        this.albums = albums;
    }
}
