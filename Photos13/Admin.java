package Photos13;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * The Main Class for Operation methods, note that this is not for user-admin
 * @author Yue Luo
 * @author Nicole Le
*/
public class Admin {
    static final long serialVersionID = 1L;
    public static ArrayList<User> UserList = new ArrayList<>();
    private static final String storeDir = "./";
    private static final String storeFile = "UserLists.tt2";
    public static Photos13UIController AdminCopy;
    
    /**
     * The intialization method called by Main Class to load Stored Data
     */
    public static void initializeList(){
        UserList = readData(storeDir,storeFile);
    }
    
    /**
     * Gets the UserList for all
     * @return UserList
     */
    public static ArrayList<User> printList(){
        return UserList;
    }
    
    /**
      * A T method used to test if the given variable with type T exists in the TargetList, by using their toString() values to compare
      * If exist, then return false to this process, else add this T variable to the TargetList and return true
      * @param <T>
      * @param TargetList
      * @param some
      * @return If the given variable exists in the TargetList in boolean format
      */
    public static <T> boolean addSome(ArrayList<T> TargetList, T some){
        for(int i=0;i<TargetList.size();i++){
            if(TargetList.get(i).toString().equals(some.toString())) return false;
        }
        TargetList.add(some);
        return true;
    }
    
    /**
     * A T method used to test if the given variable with type T exists in the TargetList, by using their toString() values to compare
     * If exist, then remove the specified T variable from the TargetList and return true, else return false
     * @param <T>
     * @param TargetList
     * @param some
     * @return If the given variable exists in the TargetList in boolean format
     */
    public static <T> boolean deleteSome(ArrayList<T> TargetList, T some){
        for(int i=0;i<TargetList.size();i++){
            if(TargetList.get(i).toString().equals(some.toString())){
                TargetList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * The transfer method used to let other classes call the process of storing data to savefile
     */
    public static void writeUser(){
        writeData(storeDir,storeFile,UserList);
    }
    
    /**
     * The method used to store data to savefile
     * @param storeDir
     * @param storeFile
     * @param userlist
     */
    private static void writeData(String storeDir, String storeFile, ArrayList<User> userlist){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
            oos.writeObject(userlist);
        } catch (Exception e){
            PrintErrorMessage(e);
        }
    }
    
    /**
     * The method used to read data from savefile
     * @param storeDir
     * @param storeFile
     * @return
     */
    private static ArrayList<User> readData(String storeDir, String storeFile){
        ArrayList<User> users = new ArrayList<User>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile))){
            users = (ArrayList<User>)ois.readObject();
        } catch (EOFException e){
            initializeSaveFile(users);
            try{
                writeData(storeDir,storeFile,users);
            } catch (Exception e1){
                PrintErrorMessage(e1);
            }
        } catch (FileNotFoundException e){
            initializeSaveFile(users);
            try {
                new FileOutputStream(storeDir + File.separator + storeFile).close();
            } catch(IOException i){
                PrintErrorMessage(i);
            }
            try{
                writeData(storeDir,storeFile,users);
            } catch (Exception e1){
                PrintErrorMessage(e1);
            }
        } catch (Exception e){
            PrintErrorMessage(e);
        }
        return users;
    }
    
    /**
     * A method for converting arraylist of objects into a total string with all tostring values of each element of the arraylist, separated by "\n", for FXML TextArea using
     * ArrayList<T> -> String: "String1\nString2\n..."
     * @param <T>
     * @param arraylist
     * @return A string containing specialized converted version of provided ArrayList
     */
    public static <T> String ConvertArrayListtoString(ArrayList<T> arraylist){
        String temp = "";
        T something = arraylist.get(0);
        if(something instanceof String){
            for(int i=0;i<arraylist.size();i++){
                temp+=arraylist.get(i);
                temp+="\n";
            }
        }
        else if(something instanceof User){
            for(int i=0;i<arraylist.size();i++){
                temp+=arraylist.get(i).toString();
                temp+="\n";
            }
        }
        return temp;
    }
    
    /**
     * The key method for dealing killing the application bypassing safely quit
     */
    public static void SafelyQuit(){
        try{
            writeUser();
        } catch(Exception e){
            PrintErrorMessage(e);
        }
    }
    
    /**
     * Initialize the savefile
     * @param users
     */
    private static void initializeSaveFile(ArrayList<User> users){
        users.add(new User("admin"));
        User stock = new User("stock");
        ArrayList<Album> albums = new ArrayList<Album>();
        ArrayList<EachPhoto> stockphotos = new ArrayList<EachPhoto>();
        stockphotos.add(createNewPhoto("/Photos13/data/angry-cat-meme.gif",false));
        stockphotos.add(createNewPhoto("/Photos13/data/FCP.jpeg",false));
        stockphotos.add(createNewPhoto("/Photos13/data/huh_cat.jpeg",false));
        stockphotos.add(createNewPhoto("/Photos13/data/KNOWYOURMEME-sad-cat-crying-1120.jpeg",false));
        stockphotos.add(createNewPhoto("/Photos13/data/Melvin.jpeg",false));
        Album album = new Album(stockphotos, "stock");
        albums.add(album);
        stock.setAlbums(albums);
        users.add(stock);
    }
    
    /**
     * The general method used to create a fully captured new Photo
     * @param path
     * @param IfAbsolutePath
     * @return EachPhoto
     */
    public static EachPhoto createNewPhoto(String path, boolean IfAbsolutePath){
        EachPhoto eachphoto = new EachPhoto(path);
        try{
            URL completepath;
            if(!IfAbsolutePath){
                path = path.substring(10);
                completepath = Admin.class.getResource(path);
            }
            else completepath = new File(path).toURI().toURL();
            eachphoto.setPath(completepath.toString());
            File file = new File(completepath.toURI());
            long LastModifiedTime = file.lastModified();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(LastModifiedTime);
            calendar.set(Calendar.MILLISECOND, 0);
            eachphoto.setDate(calendar);
            BufferedImage image = ImageIO.read(completepath);
            eachphoto.setSize(image.getWidth(),image.getHeight());
        } catch(Exception e){
            PrintErrorMessage(e);
        }
        return eachphoto;
    }
    
    /**
     * A method used to convert the Calendar data type into a specialized String format
     * @param calendar
     * @return Specialized Calendar Value in String format
     */
    public static String ConvertCalendartoString(Calendar calendar){
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
    }
    
    /**
     * A method used to test if an input fits correct time format
     * If fits, then return its transformed Calendar format
     * If doesn't fit, then return null
     * @param input
     * @return Transformed String Value in Calendar format
     */
    public static Calendar ifTimeFormatCorrect(String input){
        ArrayList<Integer> Formatted = new ArrayList<Integer>();
        try{
            String part = input.substring(0,4);
            input = input.substring(4);
            Formatted.add(Integer.parseInt(part));
            for(int i=0;i<5;i++){
                String slash = input.substring(0,1);
                if(!slash.equals("/")) return null;
                part = input.substring(1,3);
                input = input.substring(3);
                Formatted.add(Integer.parseInt(part));
            }
            if(!input.equals("")) return null;
            return createCalendarDate(Formatted);
        } catch(Exception e){
            return null;
        }
    }
    
    /**
     * A method used to turn an Integer ArrayList to a Calendar data type
     * @param Formatted
     * @return Transformed Integer ArrayList in Calendar type
     */
    public static Calendar createCalendarDate(ArrayList<Integer> Formatted){
        Calendar c = Calendar.getInstance();
        c.set(Formatted.get(0),Formatted.get(1),Formatted.get(2),Formatted.get(3),Formatted.get(4),Formatted.get(5));
        c.set(Calendar.MILLISECOND,0);
        return c;
    }
    
    /**
     * A method called after changes have done to a photo, to let all its copies accept those changes
     * @param eachphoto
     */
    public static void UpdateToAll(EachPhoto eachphoto){
        for(int i=0;i<UserList.size();i++){
            for(int j=0;j<UserList.get(i).getAlbums().size();j++){
                for(int k=0;k<UserList.get(i).getAlbums().get(j).getContains().size();k++){
                    EachPhoto ep = UserList.get(i).getAlbums().get(j).getContains().get(k);
                    if(ep.toString().equals(eachphoto.toString())){
                        ep.setCaption(eachphoto.getCaption());
                        ep.setTags(eachphoto.getTags());
                    }
                }
            }
        }
    }
    
    /**
     * A method used to create an ErrorMessage Window in Stage and print the Exception Message
     * @param exception
     */
    public static void PrintErrorMessage(Exception exception){
        AdminCopy.ErrorMessageText.setText("Exception: "+exception.toString());
        AdminCopy.windowTransfer(AdminCopy.Current, AdminCopy.ErrorMessageWindow,AdminCopy.getOperation(1));
    }
}
