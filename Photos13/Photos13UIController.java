package Photos13;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.geometry.Pos;

public class Photos13UIController {
    public static boolean ErrorWindowAppear = false;
    private enum Operations{
        NewWindow,  //Open a new window, while the user can operate on both the current page and the new window
        CloseWindow,
        NewScene,   //Open a new scene to replace the old page(current page before), the old page can not be operated while users can only operate on the new scene(current page now)
        OpenError,  //Open an error window, different from NewScene that it won't invisible the old page
        CloseError;
    }
    
    @FXML Button b1;
    @FXML Button b2;
    @FXML Button b3;
    @FXML Button b4;
    @FXML Button adminb1;
    @FXML Button adminb2;
    @FXML Button adminb3;
    @FXML Button adminb4;
    @FXML Button adminb5;
    @FXML Button UAPb1;
    @FXML Button UAPb2;
    @FXML Button UAPb3;
    @FXML Button UAPb4;
    @FXML Button UAPb5;
    @FXML Button APb1;
    @FXML Button APb2;
    @FXML Button APb3;
    @FXML Button APb4;
    @FXML Button APb5;
    @FXML Button APb6;
    @FXML Button APb7;
    @FXML Button APb8;
    @FXML Button APb9;
    @FXML Button APb10;
    @FXML Button APb11;
    @FXML Pane MainPage;
    @FXML Pane ErrorMessageWindow;
    @FXML Pane AdminPage;
    @FXML Pane UserListPage;
    @FXML Pane UserAlbumPage;
    @FXML Pane ReadInputWindow;
    @FXML Pane AlbumPhotoPage;
    @FXML GridPane AlbumGridPane;
    @FXML GridPane AlbumPhotoGridPane;
    @FXML TextArea ErrorMessageText;
    @FXML TextArea UserList;
    @FXML TextArea ReadInputMessage;
    @FXML TextArea AlbumName;
    @FXML TextField tf1;
    @FXML TextField Admintf1;
    @FXML TextField ReadInputText;
    
    Pane Current;
    User LoggedUser;
    Pane selectedCell = null;
    Pane selectedPhoto = null;
    String ReadInput;
    Album CurrentAlbum;
    
    int selectedCellindex = -1; //A global variable for storing the current selected cell index
    int selectedPhotoindex = -1; //A global variable for storing the current selected photo index, use to separate from cell index
    int waitedOperation = -1; //A global variable for storing the current action required to be proceed after receiving input
    
    public void convert(ActionEvent e) throws Exception{
        Button temp = (Button)e.getSource();
        if(temp == b3){ //Safely quit Button
            Stage s = (Stage)temp.getScene().getWindow();
            s.close();
        }
        else if(temp == b1){ //Login Button
            Current = MainPage;
            String inputname = tf1.getText();
            if(inputname.equals("admin")){
                windowTransfer(MainPage,AdminPage,Operations.NewScene);
                Current = AdminPage;
            }
            else{
                boolean UserFound = false;
                for(int i=0;i<Admin.UserList.size();i++){
                    if(Admin.UserList.get(i).toString().equals(inputname)){ //Initialize specfic user, read data
                        InitializeAlbumPage(Admin.UserList.get(i));
                        windowTransfer(MainPage,UserAlbumPage,Operations.NewScene);
                        Current = UserAlbumPage;
                        UserFound = true;
                        break;
                    }
                }
                if(!UserFound){
                    ErrorMessageText.setText("Error01:Cannot find user, or it is not available");
                    windowTransfer(MainPage,ErrorMessageWindow,Operations.OpenError);
                }
            }
        }
        else if(temp == b2){    //ErrorWindow OK Button
            windowTransfer(ErrorMessageWindow,Current,Operations.CloseError);
            if(Current == AdminPage && UserListPage.isVisible()) UserListPage.setDisable(false);
        }
        else if(temp == adminb1){   //AdminPage Add Button
            String name = Admintf1.getText();
            if(!Admin.addUser(name) || name.equals("")){    // Q, is a username with white spaces allowed?
                ErrorMessageText.setText("Error02:This User already exists, or input is invalid.");
                windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
                UserListPage.setDisable(true);
            }
            else{
                UserList.setText(Admin.ConvertArrayListtoString(Admin.UserList));
                UserListPage.setDisable(true);
                ErrorMessageText.setText("User Add Success.");
                windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
            }
        }
        else if(temp == adminb2){   //AdminPage Delete Button
            String name = Admintf1.getText();
            if(name.equals("admin") || name.equals("stock")){
                ErrorMessageText.setText("Error03:Default User cannot be Deleted");
                windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
                UserListPage.setDisable(true);
            }
            else{
                if(Admin.deleteUser(name)){
                    UserList.setText(Admin.ConvertArrayListtoString(Admin.UserList));
                    UserListPage.setDisable(true);
                    ErrorMessageText.setText("User Remove Success.");
                    windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
                }
                else{
                    ErrorMessageText.setText("Error04:Target User Not Found");
                    windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
                    UserListPage.setDisable(true);
                }
            }
        }
        else if(temp == adminb3){   //AdminPage PrintList Button
            windowTransfer(null,UserListPage,Operations.NewWindow);
            UserList.setText(Admin.ConvertArrayListtoString(Admin.UserList));
        }
        else if(temp == adminb4){   //AdminPage Logout Button
            windowTransfer(AdminPage,MainPage,Operations.NewScene);
            Admin.writeUser();
        }
        else if(temp == adminb5){   //Window UsersList Close Button
            windowTransfer(UserListPage,null,Operations.CloseWindow);
        }
        else if(temp == UAPb1){     //Create new Album
            waitedOperation = 1;
            ReadInputMessage.setText("Type in the new album name");
            windowTransfer(UserAlbumPage,ReadInputWindow,Operations.OpenError);
        }
        else if(temp == UAPb2){     //Delete Selected Album
            ArrayList<Album> albums = LoggedUser.getAlbums();
            albums.remove(selectedCellindex);
            LoggedUser.setAlbums(albums);
            selectedCellindex = -1;
            selectedCell = null;
            setUAPButton(true);
            InitializeAlbumPage(LoggedUser);
        }
        else if(temp == UAPb3){     //Rename Selected Album
            waitedOperation = 2;
            ReadInputMessage.setText("Type in the new album name");
            windowTransfer(UserAlbumPage,ReadInputWindow,Operations.OpenError);
        }
        else if(temp == UAPb4){     //Open Selected Album
            CurrentAlbum = LoggedUser.getAlbums().get(selectedCellindex);
            InitializeAlbumPhotoPage(CurrentAlbum);
            windowTransfer(UserAlbumPage,AlbumPhotoPage,Operations.NewScene);
            Current = AlbumPhotoPage;
        }
        else if(temp == UAPb5){     //Logout from User Page
            selectedCell = null;
            selectedCellindex = -1;
            setUAPButton(true);
            windowTransfer(UserAlbumPage,MainPage,Operations.NewScene);
            Current = MainPage;
            Admin.writeUser();
        }
        else if(temp == b4){        //Close ReadInput Window
            ReadInput = ReadInputText.getText();
            windowTransfer(ReadInputWindow,Current,Operations.CloseError);
            ReadInputOperations();
        }
        
    }
    
    public void windowTransfer(Pane p1, Pane p2, Operations o){
        switch(o){
            case NewWindow:
                p2.setDisable(false);
                p2.setVisible(true);
                break;
            case CloseWindow:
                p1.setDisable(true);
                p1.setVisible(false);
                break;
            case NewScene:
                p1.setDisable(true);
                p1.setVisible(false);
                p2.setDisable(false);
                p2.setVisible(true);
                break;
            case OpenError:
                p1.setDisable(true);
                p2.setDisable(false);
                p2.setVisible(true);
                break;
            case CloseError:
                p1.setDisable(true);
                p1.setVisible(false);
                p2.setDisable(false);
                break;
        }
    }
    
    public void InitializeAlbumPage(User u){
        AlbumGridPane.getChildren().clear();
        LoggedUser = u;
        ArrayList<Album> albums = u.getAlbums();
        int Albumsize= albums.size();
        AlbumGridPane.setPrefSize(542,96*Albumsize);
        for(int i=0;i<Albumsize;i++){
            Label cellContent = new Label("\n\n\n   Album:"+albums.get(i).getName()+", contains "+albums.get(i).getContains().size()
                    +" photos, filmed days: "+albums.get(i).getEarliest()+" - "+albums.get(i).getLatest());
            cellContent.setAlignment(Pos.CENTER_LEFT);
            Pane cellContainer = new Pane(cellContent);
            cellContainer.setPrefSize(542,96);
            final int currentindex = i;
            cellContainer.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
            cellContainer.setOnMouseClicked(event -> {
                if(selectedCellindex == currentindex){
                    selectedCell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    selectedCellindex = -1;
                    selectedCell = null;
                    setUAPButton(true);
                }
                else{
                    if(selectedCell != null) selectedCell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    cellContainer.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
                    selectedCellindex = currentindex;
                    selectedCell = cellContainer;
                    setUAPButton(false);
                }
            });
            AlbumGridPane.add(cellContainer,0,i);
        }
    }
    
    public void InitializeAlbumPhotoPage(Album a){
        AlbumPhotoGridPane.getChildren().clear();
        CurrentAlbum = a;
        AlbumName.setText("Album: "+a.getName());
        int PhotoSize = a.getContains().size();
        int Photoheight = (PhotoSize-1)/4 + 1;
        AlbumPhotoGridPane.setPrefSize(480,160*Photoheight);
        for(int i=0;i<PhotoSize;i++){
            EachPhoto p = a.getContains().get(i);
            Image image = new Image(p.getPath());
            ImageView iv = new ImageView(image);
            iv.setPreserveRatio(true);
            if(p.getWidth() > p.getHeight()) iv.setFitWidth(100);
            else iv.setFitHeight(100);
            StackPane sp = new StackPane(iv);
            sp.setPrefSize(120,120);
            TextArea ta = new TextArea(p.getCaption());
            ta.setStyle("-fx-background-color: white;");
            ta.setPrefSize(120,40);
            ta.setEditable(false);
            VBox vbox = new VBox();
            vbox.getChildren().addAll(sp,ta);
            vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
            final int currentindex = i;
            iv.setOnMouseClicked(event -> {
                if(selectedPhotoindex == currentindex){
                    selectedPhoto.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    selectedPhotoindex = -1;
                    selectedPhoto = null;
                    setAPButton(1);
                }
                else{
                    if(selectedPhoto != null) selectedPhoto.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
                    selectedPhotoindex = currentindex;
                    selectedPhoto = vbox;
                    setAPButton(2);
                }
            });
            AlbumPhotoGridPane.add(vbox,i%4,i/4);
        }
    }
    
    /*  A method used to control the button availables in album page
    *   true -> Add button becomes available, other buttons become not available
    *   false -> Add button becomes not available, other buttons become available
    */
    public void setUAPButton(boolean mode){
        if(mode){
            UAPb1.setDisable(false);
            UAPb2.setDisable(true);
            UAPb3.setDisable(true);
            UAPb4.setDisable(true);
        }
        else{
            UAPb1.setDisable(true);
            UAPb2.setDisable(false);
            UAPb3.setDisable(false);
            UAPb4.setDisable(false);
        }
    }
    
    /** A method used to control the button availables in photo page
     *  1 -> Not selected mode, Add, Go Through and Search buttons become available, other buttons become not available
     *  2 -> Selected mode, Add, Go Through and Search buttons become not available, other buttons become available
     *  3 -> Special: Search mode, all buttons except Return become unavailable
     */
    public void setAPButton(int mode){
        switch(mode){
            case 1:
                APb1.setDisable(false);
                APb2.setDisable(true);
                APb3.setDisable(true);
                APb4.setDisable(true);
                APb5.setDisable(true);
                APb6.setDisable(true);
                APb7.setDisable(true);
                APb8.setDisable(true);
                APb9.setDisable(false);
                APb10.setDisable(false);
                break;
            case 2:
                APb1.setDisable(true);
                APb2.setDisable(false);
                APb3.setDisable(false);
                APb4.setDisable(false);
                APb5.setDisable(false);
                APb6.setDisable(false);
                APb7.setDisable(false);
                APb8.setDisable(false);
                APb9.setDisable(true);
                APb10.setDisable(true);
                break;
            case 3:
                APb1.setDisable(true);
                APb2.setDisable(true);
                APb3.setDisable(true);
                APb4.setDisable(true);
                APb5.setDisable(true);
                APb6.setDisable(true);
                APb7.setDisable(true);
                APb8.setDisable(true);
                APb9.setDisable(true);
                APb10.setDisable(true);
                break;
        }
    }
    
    /**  A universal method used to operate set functions after receiving input
     *   1 -> Add new Album
     *   2 -> Rename selected Album
     */
    public void ReadInputOperations(){
        ArrayList<Album> albums;
        switch(waitedOperation){
            case 1:
                boolean ifexist = false;
                albums = LoggedUser.getAlbums();
                for(int i=0;i<albums.size();i++){
                    if(albums.get(i).getName().equals(ReadInput)){
                        ifexist = true;
                        break;
                    }
                }
                if(ifexist){
                    ErrorMessageText.setText("Error05:Album with this name already exist");
                    windowTransfer(UserAlbumPage,ErrorMessageWindow,Operations.OpenError);
                }
                else{
                    Album a = new Album(ReadInput);
                    albums.add(a);
                    LoggedUser.setAlbums(albums);
                    InitializeAlbumPage(LoggedUser);
                }
                break;
            case 2:
                albums = LoggedUser.getAlbums();
                Album b = albums.get(selectedCellindex);
                b.setName(ReadInput);
                LoggedUser.setAlbums(albums);
                selectedCellindex = -1;
                selectedCell = null;
                setUAPButton(true);
                InitializeAlbumPage(LoggedUser);
                break;
            case 3:
                
        }
        ReadInputText.setText("");
        ReadInput = "";
        waitedOperation = -1;
    }
}