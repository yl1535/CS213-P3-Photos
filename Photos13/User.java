package photos13;

import java.io.Serializable;

public class User implements Serializable{
    private String name;
    
    public User(String myname){
        name = myname;
    }
    
    public String getName(){
        return name;
    }
}
