package photos13;

import java.util.ArrayList;
import java.io.*;

public class Admin {
    public static ArrayList<User> UserList = new ArrayList<>();
    public static final String storeDir = "src/photos13/data";
    public static final String storeFile = "UserLists.tt2";
    static final long serialVersionID = 1L;
    
    public static void initializeList() throws Exception{
        UserList = readData(storeDir,storeFile);
    }
    
    public static ArrayList<User> printList(){
        return UserList;
    }
    
    public static boolean addUser(String name){
        User user = new User(name);
        for(int i=0;i<UserList.size();i++){
            if(UserList.get(i).getName().equals(name)) return false;
        }
        UserList.add(user);
        return true;
    }
    
    public static boolean deleteUser(String name){
        for(int i=0;i<UserList.size();i++){
            if(UserList.get(i).getName().equals(name)){
                UserList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static void writeUser() throws Exception{    // Not sure if doing so, etc. just updating list during admin's acitivation and update real save on disk leaving, is best
        writeData(storeDir,storeFile,UserList);
    }
    
    public static void writeData(String storeDir, String storeFile, ArrayList<User> al) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(al);
    }
    
    public static ArrayList<User> readData(String storeDir, String storeFile) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        ArrayList<User> users = (ArrayList<User>)ois.readObject();
        return users;
    }
    
    /*public static void main(String[] args) throws Exception{      // Test only part, remove before final submission
        initializeList();
        if(UserList.size() != 0) System.out.println(UserList.get(0).getName());
        UserList.add(new User("Admin"));
        writeUser();
    }*/
}
