package Photos13;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    private String name;
    private ArrayList<Album> albums;
    
    public User(String myname){
        name = myname;
    }
    
    public String toString(){
        return name;
    }
}
