package Photos13;

import java.util.ArrayList;
import java.io.*;
import java.util.Calendar;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * The Main Class for Operation methods, note that this is not for user-admin
 * @author Yue Luo
 * @author Nicole Le
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
    
     /**
      * A T method used to test if the given variable with type T exists in the TargetList, by using their toString() values to compare
      * If exist, then return false to this process, else add this T variable to the TargetList and return true
      * @param <T>
      * @param TargetList
      * @param some
      * @return
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
     * @return
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
     * 
     * @throws Exception
     */
    public static void writeUser() throws Exception{
        writeData(storeDir,storeFile,UserList);
    }

    /**
     * 
     * @param storeDir
     * @param storeFile
     * @param al
     * @throws IOException
     */
    private static void writeData(String storeDir, String storeFile, ArrayList<User> al) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(al);
    }
    
    /**
     * 
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
    
    /**
     * A method for converting arraylist of objects into a total string with all tostring values of each element of the arraylist, separated by "\n", for FXML TextArea using
     * ArrayList<T> -> String: "String1\nString2\n..."
     * @param <T>
     * @param as
     * @return
     */
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
    
    /**
     * The key method for dealing killing the application bypassing safely quit
     */
    public static void SafelyQuit(){
        try{
            writeUser();
        } catch(Exception e){
            e.printStackTrace();
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
     * 
     * @param path
     * @param IfAbsolutePath
     * @return
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
            e.printStackTrace();    //TODO: GUI BUG REPORT
        }
        return eachphoto;
    }
    
    /**
     * 
     * @param c
     * @return
     */
    public static String ConvertCalendartoString(Calendar c){
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
    }

    /**
     * 
     * @param input
     * @return
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
     * 
     * @param Formatted
     * @return
     */
    public static Calendar createCalendarDate(ArrayList<Integer> Formatted){
        Calendar c = Calendar.getInstance();
        c.set(Formatted.get(0),Formatted.get(1),Formatted.get(2),Formatted.get(3),Formatted.get(4),Formatted.get(5));
        c.set(Calendar.MILLISECOND,0);
        return c;
    }
    
    /**
     * 
     * @param p
     */
    public static void UpdateToAll(EachPhoto p){
        for(int i=0;i<UserList.size();i++){
            for(int j=0;j<UserList.get(i).getAlbums().size();j++){
                for(int k=0;k<UserList.get(i).getAlbums().get(j).getContains().size();k++){
                    EachPhoto ep = UserList.get(i).getAlbums().get(j).getContains().get(k);
                    if(ep.toString().equals(p.toString())){
                        ep.setCaption(p.getCaption());
                        ep.setTags(p.getTags());
                    }
                }
            }
        }
    }
}
