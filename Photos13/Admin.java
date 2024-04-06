package Photos13;

import java.util.ArrayList;
import java.io.*;

/*
 * The Main Class for Operation methods, note that this is not for user-admin
*/
public class Admin {
    static final long serialVersionID = 1L;
    public static ArrayList<User> UserList = new ArrayList<>();
    private static final String storeDir = "./";  //src/Photos13/data
    private static final String storeFile = "UserLists.tt2";
    
    public static void initializeList() throws Exception{
        UserList = readData(storeDir,storeFile);
    }
    
    public static ArrayList<User> printList(){
        return UserList;
    }
    
    public static boolean addUser(String name){     //requires improvement while adding more features to user
        User user = new User(name);
        for(int i=0;i<UserList.size();i++){
            if(UserList.get(i).toString().equals(name)) return false;
        }
        UserList.add(user);
        return true;
    }
    
    public static boolean deleteUser(String name){
        for(int i=0;i<UserList.size();i++){
            if(UserList.get(i).toString().equals(name)){
                UserList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static void writeUser() throws Exception{
        writeData(storeDir,storeFile,UserList);
    }
    
    private static void writeData(String storeDir, String storeFile, ArrayList<User> al) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(al);
    }
    
    private static ArrayList<User> readData(String storeDir, String storeFile){
        ArrayList<User> users = new ArrayList<User>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile))){
            users = (ArrayList<User>)ois.readObject();
        } catch (EOFException e){
            initializeSaveFile(users);
            try{
                writeData(storeDir,storeFile,users);
            } catch (Exception e1){
                e1.printStackTrace();
            }
        } catch (FileNotFoundException e){
            initializeSaveFile(users);
            try {
                new FileOutputStream(storeDir + File.separator + storeFile).close();
            } catch(IOException i){
                i.printStackTrace();
            }
            try{
                writeData(storeDir,storeFile,users);
            } catch (Exception e1){
                e1.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
    
    // A method for converting arraylist of objects into a total string with all tostring values of each element of the arraylist, separated by "\n", for FXML TextArea using
    // ArrayList<T> -> String: "String1\nString2\n..."
    public static <T> String ConvertArrayListtoString(ArrayList<T> as){
        String temp = "";
        T something = as.get(0);
        if(something instanceof String){
            for(int i=0;i<as.size();i++){
                temp+=as.get(i);
                temp+="\n";
            }
        }
        else if(something instanceof User){
            for(int i=0;i<as.size();i++){
                temp+=as.get(i).toString();
                temp+="\n";
            }
        }
        return temp;
    }
    
    // The key method for dealing killing the application bypassing safely quit
    public static void SafelyQuit(){
        try{
            writeUser();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // Initialize the savefile
    private static void initializeSaveFile(ArrayList<User> users){
        users.add(new User("admin"));
        User stock = new User("stock");
        ArrayList<Album> albums = new ArrayList<Album>();
        
        Album album = new Album(null, "stock"); //change null to pic
        albums.add(album);
        stock.setAlbums(albums);
        users.add(stock);
    }
    
    /*public static void main(String[] args) throws Exception{      // Test only part, remove before final submission
        initializeList();
        if(UserList.size() != 0) System.out.println(UserList.get(0).getName());
        UserList.add(new User("Admin"));
        writeUser();
    }*/
}
