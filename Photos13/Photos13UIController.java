package Photos13;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.geometry.Pos;
import java.util.Calendar;

public class Photos13UIController {
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
    @FXML Button UAPb6;
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
    @FXML Button TB1;
    @FXML Button TB2;
    @FXML Button TargetAlbumButton;
    @FXML Button DIButton;
    @FXML Button SPb1;
    @FXML Button SPb2;
    @FXML Button SPb3;
    @FXML Button TIButton1;
    @FXML Pane MainPage;
    @FXML Pane ErrorMessageWindow;
    @FXML Pane AdminPage;
    @FXML Pane UserListPage;
    @FXML Pane UserAlbumPage;
    @FXML Pane ReadInputWindow;
    @FXML Pane AlbumPhotoPage;
    @FXML Pane TwoButtons;
    @FXML Pane TargetAlbumChooser;
    @FXML Pane DateInputer;
    @FXML Pane SearchResultPage;
    @FXML Pane TagInputer1;
    @FXML GridPane AlbumGridPane;
    @FXML GridPane AlbumPhotoGridPane;
    @FXML GridPane GoThrough;
    @FXML GridPane TargetAlbumGridPane;
    @FXML GridPane SearchResultGridPane;
    @FXML TextArea ErrorMessageText;
    @FXML TextArea UserList;
    @FXML TextArea ReadInputMessage;
    @FXML TextArea AlbumName;
    @FXML TextArea TBTA;
    @FXML TextArea SearchType;
    @FXML TextField tf1;
    @FXML TextField Admintf1;
    @FXML TextField ReadInputText;
    @FXML TextField DILeftTF;
    @FXML TextField DIRightTF;
    @FXML TextField TILeftTF1;
    @FXML TextField TIRightTF1;
    
    Pane Current;   //A global Pane variable to store what the current background pane is, used for scene transferings
    User LoggedUser;    //A global User variable to store what the current logged user is
    Pane selectedCell = null;   //A global Pane varaible used in User-Album Page, to store which pane is selected
    Pane selectedPhoto = null;  //A global Pane variable used in Album-Photo Page, to store which pane is selected
    ArrayList<String> ReadInput = new ArrayList<String>(); //A global String ArrayList to store inputs from GUI to manage operations
    Album CurrentAlbum; //A global Album variable to store what the current opened album is
    
    int selectedCellindex = -1; //A global variable for storing the current selected cell index
    int selectedPhotoindex = -1; //A global variable for storing the current selected photo index, use to separate from cell index
    int waitedOperation = -1; //A global variable for storing the current action required to be proceed after receiving input
    int twoButtonsOperation = -1; //A global variable for storing the current action required to be proceed after closing TwoButtons Window
    int currentGoThroughindex = -1; //A global variable for storing the index of current GoThrough photo
    boolean ifTACOperationisCopy = false; //A global variable for storing if the move photo process is copy
    
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
            if(!Admin.addSome(Admin.UserList,new User(name)) || name.equals("")){    // Q, is a username with white spaces allowed?  //need to figure if needed add more features to intialize user
                ErrorMessageText.setText("Error02:This User already exists, or input is invalid");
                windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
                UserListPage.setDisable(true);
            }
            else{
                UserList.setText(Admin.ConvertArrayListtoString(Admin.UserList));
                UserListPage.setDisable(true);
                ErrorMessageText.setText("The new user has been successfully added.");
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
                if(Admin.deleteSome(Admin.UserList,new User(name))){
                    UserList.setText(Admin.ConvertArrayListtoString(Admin.UserList));
                    UserListPage.setDisable(true);
                    ErrorMessageText.setText("The specified user has been successfully removed.");
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
            ReadInputMessage.setText("Type in Name of the Album you want to create");
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
            ErrorMessageText.setText("Selected Album has been successfully removed.");
            windowTransfer(UserAlbumPage,ErrorMessageWindow,Operations.OpenError);
        }
        else if(temp == UAPb3){     //Rename Selected Album
            waitedOperation = 2;
            ReadInputMessage.setText("Type in the New Name you want to give to the selected Album");
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
            ReadInput.add(ReadInputText.getText());
            windowTransfer(ReadInputWindow,Current,Operations.CloseError);
            ReadInputOperations();
        }
        else if(temp == APb1){      //Add New Photo to Album
            
        }
        else if(temp == APb2){      //Delete Selected Photo from Album
            ArrayList<EachPhoto> eachphotos = CurrentAlbum.getContains();
            eachphotos.remove(selectedPhotoindex);
            CurrentAlbum.setContains(eachphotos);
            selectedPhotoindex = -1;
            selectedPhoto = null;
            setAPButton(1);
            InitializeAlbumPhotoPage(CurrentAlbum);
            ErrorMessageText.setText("Selected Photo has been successfully removed from the album");
            windowTransfer(AlbumPhotoPage,ErrorMessageWindow,Operations.OpenError);
        }
        else if(temp == APb3){      //Caption/Recaption the Selected Photo
            waitedOperation = 4;
            ReadInputMessage.setText("Type in the new Caption of this photo.");
            windowTransfer(AlbumPhotoPage,ReadInputWindow,Operations.OpenError);
        }
        else if(temp == APb4 || temp == SPb2){      //Display the Selected Photo
            EachPhoto ep = CurrentAlbum.getContains().get(selectedPhotoindex);
            Image image = new Image(ep.getPath());
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(1004);
            imageView.setFitHeight(668);
            String TextAreaWords = "Caption: "+ep.getCaption()+", Date: "+Admin.ConvertCalendartoString(ep.getDate())+"\nTags: [";
            for(int i=0;i<ep.getTags().size();i++){
                Tags tag = ep.getTags().get(i);
                TextAreaWords = TextAreaWords + tag.getTagName()+":"+tag.getTagValue()+";";
            }
            TextAreaWords += "]";
            TextArea ta = new TextArea(TextAreaWords);
            ta.setStyle("-fx-background-color: white;");
            ta.setPrefSize(imageView.getFitWidth()+20,80);
            ta.setEditable(false);
            StackPane rootPane = new StackPane(imageView);
            rootPane.setPrefSize(imageView.getFitWidth()+20,imageView.getFitHeight()+20);
            rootPane.setStyle("-fx-background-color: white;");
            VBox vbox = new VBox();
            vbox.getChildren().addAll(rootPane,ta);
            vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
            Scene scene = new Scene(vbox,imageView.getFitWidth()+20,imageView.getFitHeight()+100);
            Stage newWindow = new Stage();
            newWindow.setTitle("Photo Display");
            newWindow.setScene(scene);
            newWindow.setWidth(imageView.getFitWidth()+20);
            newWindow.setHeight(imageView.getFitHeight()+100);
            newWindow.show();
        }
        else if(temp == APb5){      //Add Tag to the Selected Photo
            waitedOperation = 5;
            ReadInputMessage.setText("Type in the Tag Name you want to add.");
            windowTransfer(AlbumPhotoPage,ReadInputWindow,Operations.OpenError);
        }
        else if(temp == APb6){      //Delete Tag to the Selected Photo
            waitedOperation = 7;
            ReadInputMessage.setText("Type in Name of the Tag you want to delete.");
            windowTransfer(AlbumPhotoPage,ReadInputWindow,Operations.OpenError);
        }
        else if(temp == APb7){      //Copy the Selected Photo to another Album
            InitializeAlbumChooserPage(LoggedUser);
            windowTransfer(AlbumPhotoPage,TargetAlbumChooser,Operations.OpenError);
            ifTACOperationisCopy = true;
        }
        else if(temp == APb8){      //Move the Selected Photo to another Album
            InitializeAlbumChooserPage(LoggedUser);
            windowTransfer(AlbumPhotoPage,TargetAlbumChooser,Operations.OpenError);
            ifTACOperationisCopy = false;
        }
        else if(temp == APb9){      //Go Through the Album with Different Modes
            twoButtonsOperation = 1;
            TBTA.setText("On Which Sequence do you want to Go Through the Album?");
            TB1.setText("Forward");
            TB2.setText("Backward");
            windowTransfer(AlbumPhotoPage,TwoButtons,Operations.OpenError);
        }
        else if(temp == APb10){     //Return to the Album Page
            selectedPhoto = null;
            selectedPhotoindex = -1;
            selectedCell = null;
            selectedCellindex = -1;
            setAPButton(1);
            setUAPButton(true);
            windowTransfer(AlbumPhotoPage,UserAlbumPage,Operations.NewScene);
            Current = UserAlbumPage;
            Admin.writeUser();
            InitializeAlbumPage(LoggedUser);
        }
        else if(temp == TB1){       //TwoButton Page's Left Button
            windowTransfer(TwoButtons,AlbumPhotoPage,Operations.CloseError);
            TwoButtonsOperation(true);
        }
        else if(temp == TB2){       //TwoButton Page's Right Button
            windowTransfer(TwoButtons,AlbumPhotoPage,Operations.CloseError);
            TwoButtonsOperation(false);
        }
        else if(temp == TargetAlbumButton){     //TargetAlbumChooser Page's Ok Button
            windowTransfer(TargetAlbumChooser,AlbumPhotoPage,Operations.CloseError);
            ChooseAlbumOperation(ifTACOperationisCopy);
            selectedPhoto = null;
            selectedPhotoindex = -1;
            selectedCell = null;
            selectedCellindex = -1;
            setAPButton(1);
            Admin.writeUser();
            InitializeAlbumPhotoPage(CurrentAlbum);
        }
        else if(temp == UAPb6){     //Activates Search Mode
            twoButtonsOperation = 2;
            TBTA.setText("On which mode do you prefer to search by?");
            TB1.setText("Date");
            TB2.setText("Tags");
            windowTransfer(UserAlbumPage,TwoButtons,Operations.OpenError);
        }
        else if(temp == DIButton){
            windowTransfer(DateInputer,UserAlbumPage,Operations.CloseError);
            Calendar FirstDate = Admin.ifTimeFormatCorrect(DILeftTF.getText());
            Calendar SecondDate = Admin.ifTimeFormatCorrect(DIRightTF.getText());
            if(FirstDate == null || SecondDate == null){
                ErrorMessageText.setText("Error10:One or both or your inputs is not in correct format, follow the format in prompted text, including right slashes");
                windowTransfer(UserAlbumPage,ErrorMessageWindow,Operations.OpenError);
            }
            else if(FirstDate.after(SecondDate)){
                ErrorMessageText.setText("Error11:The first date you provided is later than the second one");
                windowTransfer(UserAlbumPage,ErrorMessageWindow,Operations.OpenError);
            }
            else{
                Album album = new Album("Search Type: "+DILeftTF.getText()+" - "+DIRightTF.getText());
                ArrayList<EachPhoto> eachphotos = new ArrayList<EachPhoto>();
                for(int i=0;i<LoggedUser.getAlbums().size();i++){
                    Album a = LoggedUser.getAlbums().get(i);
                    for(int j=0;j<a.getContains().size();j++){
                        EachPhoto ep = a.getContains().get(j);
                        if(ep.getDate().after(FirstDate) && ep.getDate().before(SecondDate)) Admin.addSome(eachphotos,ep);
                    }
                }
                album.setContains(eachphotos);
                InitializeSearchResultPage(album);
                windowTransfer(UserAlbumPage,SearchResultPage,Operations.NewScene);
                Current = SearchResultPage;
            }
           DILeftTF.setText("");
           DIRightTF.setText("");
        }
        else if(temp == SPb1){
            waitedOperation = 9;
            ReadInputMessage.setText("Type in the Name of the New Album you want to Copy the Search Results to");
            windowTransfer(SearchResultPage,ReadInputWindow,Operations.OpenError);
        }
        else if(temp == SPb3){
            selectedPhoto = null;
            selectedPhotoindex = -1;
            selectedCell = null;
            selectedCellindex = -1;
            setUAPButton(true);
            setSearchButton(true);
            Current = UserAlbumPage;
            windowTransfer(SearchResultPage,UserAlbumPage,Operations.NewScene);
            InitializeAlbumPage(LoggedUser);
            Admin.writeUser();
        }
        else if(temp == TIButton1){
            windowTransfer(TagInputer1,UserAlbumPage,Operations.CloseError);
            Tags tag = new Tags(TILeftTF1.getText(),TIRightTF1.getText());
            Album album = new Album("Search Type: "+TILeftTF1.getText()+":"+TIRightTF1.getText());
            ArrayList<EachPhoto> eachphotos = new ArrayList<EachPhoto>();
            for(int i=0;i<LoggedUser.getAlbums().size();i++){
                Album a = LoggedUser.getAlbums().get(i);
                for(int j=0;j<a.getContains().size();j++){
                    EachPhoto ep = a.getContains().get(j);
                    for(int k=0;k<ep.getTags().size();k++){
                        if(ep.getTags().get(k).equals(tag)) Admin.addSome(eachphotos,ep);
                    }
                }
            }
            album.setContains(eachphotos);
            InitializeSearchResultPage(album);
            windowTransfer(UserAlbumPage,SearchResultPage,Operations.NewScene);
            Current = SearchResultPage;
            TILeftTF1.setText("");
            TIRightTF1.setText("");
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
            setCellClickEvent(cellContainer,i);
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
            iv.setFitWidth(100);
            iv.setFitHeight(100);
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
            vbox.setOnMouseClicked(event -> {
                if(selectedPhotoindex == currentindex){
                    selectedPhoto.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    selectedPhotoindex = -1;
                    selectedPhoto = null;
                    setAPButton(1);
                }
                else{
                    if(selectedPhoto != null) selectedPhoto.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    vbox.setStyle("-fx-border-color: red; -fx-border-width: 1; -fx-background-color: lightgray;");
                    selectedPhotoindex = currentindex;
                    selectedPhoto = vbox;
                    setAPButton(2);
                }
            });
            AlbumPhotoGridPane.add(vbox,i%4,i/4);
        }
    }
    
    public void InitializeSearchResultPage(Album a){
        SearchResultGridPane.getChildren().clear();
        CurrentAlbum = a;
        SearchType.setText(a.getName());
        int PhotoSize = a.getContains().size();
        int Photoheight = (PhotoSize-1)/4 + 1;
        SearchResultGridPane.setPrefSize(480,160*Photoheight);
        for(int i=0;i<PhotoSize;i++){
            EachPhoto p = a.getContains().get(i);
            Image image = new Image(p.getPath());
            ImageView iv = new ImageView(image);
            iv.setPreserveRatio(true);
            iv.setFitWidth(100);
            iv.setFitHeight(100);
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
            vbox.setOnMouseClicked(event -> {
                if(selectedPhotoindex == currentindex){
                    selectedPhoto.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    selectedPhotoindex = -1;
                    selectedPhoto = null;
                    setSearchButton(true);
                }
                else{
                    if(selectedPhoto != null) selectedPhoto.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                    vbox.setStyle("-fx-border-color: red; -fx-border-width: 1; -fx-background-color: lightgray;");
                    selectedPhotoindex = currentindex;
                    selectedPhoto = vbox;
                    setSearchButton(false);
                }
            });
            SearchResultGridPane.add(vbox,i%4,i/4);
        }
    }
    
    public void InitializeAlbumChooserPage(User u){
        TargetAlbumGridPane.getChildren().clear();
        ArrayList<Album> albums = u.getAlbums();
        TargetAlbumGridPane.setPrefSize(227,60*albums.size());
        for(int i=0;i<albums.size();i++){
            Label cellContent = new Label("\n   "+albums.get(i).getName());
            cellContent.setAlignment(Pos.CENTER_LEFT);
            Pane cellContainer = new Pane(cellContent);
            cellContainer.setPrefSize(227,60);
            setCellClickEvent(cellContainer,i);
            TargetAlbumGridPane.add(cellContainer,0,i);
        }
    }
    
    public void setCellClickEvent(Pane cellContainer, int currentindex){
        cellContainer.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
        cellContainer.setOnMouseClicked(event -> {
            if(selectedCellindex == currentindex){
                selectedCell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                selectedCellindex = -1;
                selectedCell = null;
                if(Current == UserAlbumPage) setUAPButton(true);
                else if(Current == AlbumPhotoPage) TargetAlbumButton.setDisable(true);
            }
            else{
                if(selectedCell != null) selectedCell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                cellContainer.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
                selectedCellindex = currentindex;
                selectedCell = cellContainer;
                if(Current == UserAlbumPage) setUAPButton(false);
                else if(Current == AlbumPhotoPage) TargetAlbumButton.setDisable(false);
            }
        });
    }
    
    /**  A method used to control the button availables in album page
     *   true -> Add & Search button becomes available, other buttons become not available
     *   false -> Add & Search button becomes not available, other buttons become available
     */
    public void setUAPButton(boolean mode){
        UAPb1.setDisable(!mode);
        UAPb2.setDisable(mode);
        UAPb3.setDisable(mode);
        UAPb4.setDisable(mode);
        UAPb6.setDisable(!mode);
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
    
    public void setSearchButton(boolean mode){
        SPb1.setDisable(!mode);
        SPb2.setDisable(mode);
        SPb3.setDisable(!mode);
    }
    
    /**  A universal method used to operate set functions after receiving input
     *   1 -> Add new Album
     *   2 -> Rename selected Album
     * 
     *   4 -> Caption/Recaption selected Photo
     *   5 -> Transition to operation 6
     *   6 -> Add new tag(TagType, TagName) to selected Photo
     *   7 -> Transition to operation 8
     *   8 -> Delete specific tag(TagType, TagName) from selected Photo
     *   9 -> Create new Album for search results
     */
    public void ReadInputOperations(){
        ArrayList<Album> albums;
        switch(waitedOperation){
            case 1:
                boolean ifexist = false;
                albums = LoggedUser.getAlbums();
                Album a = new Album(ReadInput.get(0));
                if(Admin.addSome(albums,a)){
                    LoggedUser.setAlbums(albums);
                    InitializeAlbumPage(LoggedUser);
                    ErrorMessageText.setText("New Album has been successfully added.");
                }
                else ErrorMessageText.setText("Error05:Album with this name already exist");
                ReadInput.remove(0);
                waitedOperation = -1;
                break;
            case 2:
                albums = LoggedUser.getAlbums();
                Album b = albums.get(selectedCellindex);
                b.setName(ReadInput.get(0));
                LoggedUser.setAlbums(albums);
                selectedCellindex = -1;
                selectedCell = null;
                setUAPButton(true);
                InitializeAlbumPage(LoggedUser);
                ReadInput.remove(0);
                ErrorMessageText.setText("Selected Album has been successfully renamed.");
                waitedOperation = -1;
                break;
            case 3:
                
            case 4:
                ArrayList<EachPhoto> eachphotos4 = CurrentAlbum.getContains();
                EachPhoto ep4 = eachphotos4.get(selectedPhotoindex);
                ep4.setCaption(ReadInput.get(0));
                CurrentAlbum.setContains(eachphotos4);
                Admin.UpdateToAll(ep4);
                selectedPhotoindex = -1;
                selectedPhoto = null;
                setAPButton(1);
                InitializeAlbumPhotoPage(CurrentAlbum);
                ReadInput.remove(0);
                ErrorMessageText.setText("Selected Photo has successfully received a new caption.");
                waitedOperation = -1;
                break;
            case 5:
                waitedOperation = 6;
                ReadInputMessage.setText("Type in the Tag Value you want to add.");
                windowTransfer(AlbumPhotoPage,ReadInputWindow,Operations.OpenError);
                break;
            case 6:
                ArrayList<EachPhoto> eachphotos6 = CurrentAlbum.getContains();
                EachPhoto ep6 = eachphotos6.get(selectedPhotoindex);
                ArrayList<Tags> tags6 = ep6.getTags();
                Tags newTag = new Tags(ReadInput.get(0),ReadInput.get(1));
                if(Admin.addSome(tags6,newTag)){
                    ep6.setTags(tags6);
                    CurrentAlbum.setContains(eachphotos6);
                    ErrorMessageText.setText("New Tag has been successfully added to the selected photo.");
                    Admin.UpdateToAll(ep6);
                }
                else ErrorMessageText.setText("Error05:Specified Tag has already been added to the selected Photo.");
                selectedPhotoindex = -1;
                selectedPhoto = null;
                setAPButton(1);
                InitializeAlbumPhotoPage(CurrentAlbum);
                ReadInput.remove(1);
                ReadInput.remove(0);
                waitedOperation = -1;
                break;
            case 7:
                waitedOperation = 8;
                ReadInputMessage.setText("Type in the Value of the Tag you want to delete.");
                windowTransfer(AlbumPhotoPage,ReadInputWindow,Operations.OpenError);
                break;
            case 8:
                ArrayList<EachPhoto> eachphotos8 = CurrentAlbum.getContains();
                EachPhoto ep8 = eachphotos8.get(selectedPhotoindex);
                ArrayList<Tags> tags8 = ep8.getTags();
                Tags t = new Tags(ReadInput.get(0),ReadInput.get(1));
                if(Admin.deleteSome(tags8, t)){
                    ep8.setTags(tags8);
                    CurrentAlbum.setContains(eachphotos8);
                    ErrorMessageText.setText("The specified Tag has been successfully removed from the selected photo.");
                }
                else ErrorMessageText.setText("Error06:Specified Tag Not Found.");
                selectedPhotoindex = -1;
                selectedPhoto = null;
                setAPButton(1);
                InitializeAlbumPhotoPage(CurrentAlbum);
                ReadInput.remove(1);
                ReadInput.remove(0);
                waitedOperation = -1;
                break;
            case 9:
                Album a9 = new Album(ReadInput.get(0));
                a9.setContains(CurrentAlbum.getContains());
                ArrayList<Album> albums9 = LoggedUser.getAlbums();
                if(Admin.addSome(albums9,a9)){
                    LoggedUser.setAlbums(albums9);
                    ErrorMessageText.setText("New Album has been successfully added.");
                }
                else ErrorMessageText.setText("Error05:Album with this name already exist");
                ReadInput.remove(0);
                waitedOperation = -1;
                break;
            case 10:
                
        }
        ReadInputText.setText("");
        if(waitedOperation == -1) windowTransfer(Current,ErrorMessageWindow,Operations.OpenError);
    }
    
    /** A universal method used to operate set functions after closing the TargetAlbumChooser window
     * 
     * @param ifCopy 
     */
    public void ChooseAlbumOperation(boolean ifCopy){
        Album Target = LoggedUser.getAlbums().get(selectedCellindex);
        if(Target.getName().equals(CurrentAlbum.getName())){
            ErrorMessageText.setText("Error08:The Selected Album is not a Different Album from Current.");
        }
        else{
            ArrayList<EachPhoto> eachphotos = Target.getContains();
            if(Admin.addSome(eachphotos,CurrentAlbum.getContains().get(selectedPhotoindex))){
                if(!ifCopy){
                    ArrayList<EachPhoto> currentphotos = CurrentAlbum.getContains();
                    currentphotos.remove(selectedPhotoindex);
                    CurrentAlbum.setContains(currentphotos);
                }
                ErrorMessageText.setText("Selected Photo has been successfully "+(ifCopy?"copied":"moved")+" to the Selected Album.");
            }
            else{
                ErrorMessageText.setText("Error09:The Selected Photo already has a copy in the Target Album");
            }
        }
        windowTransfer(AlbumPhotoPage,ErrorMessageWindow,Operations.OpenError);
    }
    
    /** A universal method used to operate set functions after closing the TwoButton window
     *  1 -> Initialize GoThrough window with direction
     *  2 -> Search by Date or Search by Tags
     *  3 -> Search by Single Tag or Search by Two Tags
     */
    public void TwoButtonsOperation(boolean leftbutton){
        switch(twoButtonsOperation){
            case 1:
                if(leftbutton) currentGoThroughindex = 0;
                else currentGoThroughindex = CurrentAlbum.getContains().size()-1;
                UpdateGoThroughPhoto();
                GoThrough.setOnMouseClicked(event -> {
                    if(leftbutton) currentGoThroughindex++;
                    else currentGoThroughindex--;
                    if(currentGoThroughindex == CurrentAlbum.getContains().size() || currentGoThroughindex == -1){
                        currentGoThroughindex = -1;
                        windowTransfer(GoThrough,AlbumPhotoPage,Operations.NewScene);
                        Current = AlbumPhotoPage;
                    }
                    else UpdateGoThroughPhoto();
                });
                GoThrough.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    switch(event.getCode()){
                        case ESCAPE:
                            currentGoThroughindex = -1;
                            windowTransfer(GoThrough,AlbumPhotoPage,Operations.NewScene);
                            Current = AlbumPhotoPage;
                            break;
                        default:
                            break;
                    }
                });
                windowTransfer(AlbumPhotoPage,GoThrough,Operations.NewScene);
                Current = GoThrough;
                GoThrough.requestFocus();
                break;
            case 2:
                if(leftbutton){
                    windowTransfer(TwoButtons,UserAlbumPage,Operations.CloseError);
                    windowTransfer(UserAlbumPage,DateInputer,Operations.OpenError);
                }
                else{
                    twoButtonsOperation = 3;
                    TBTA.setText("How many tags do you prefer to search by?");
                    TB1.setText("One Pair");
                    TB2.setText("Two Pairs");
                    windowTransfer(UserAlbumPage,TwoButtons,Operations.OpenError);
                }
                break;
            case 3:
                if(leftbutton){
                    windowTransfer(TwoButtons,UserAlbumPage,Operations.CloseError);
                    windowTransfer(UserAlbumPage,TagInputer1,Operations.OpenError);
                }
                else{
                    
                }
                break;
        }
    }
    
    /** A method called after a change to a photo's caption/tags has been made
     *  The method goes through every album in each user and replace copies of Photos with same path with the provided Photo data
     */
    public void UpdatePhotoData(EachPhoto p){
        for(int i=0;i<Admin.UserList.size();i++){
            ArrayList<Album> albums = Admin.UserList.get(i).getAlbums();
            for(int j=0;j<albums.size();j++){
                ArrayList<EachPhoto> eachphotos = albums.get(j).getContains();
                for(int k=0;k<eachphotos.size();k++){
                    EachPhoto ep = eachphotos.get(k);
                    if(ep.getPath() == p.getPath()){
                        ep.setCaption(p.getCaption());
                        ep.setTags(p.getTags());
                            // TODO: Changes to time?
                    }
                }
            }
        }
    }
    
    /** A specific method used to update the GoThrough Photo imageView
     * 
     */
    
    public void UpdateGoThroughPhoto(){
        GoThrough.getChildren().clear();
        EachPhoto ep = CurrentAlbum.getContains().get(currentGoThroughindex);
        Image image = new Image(ep.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        StackPane GoThroughCurrent = new StackPane(imageView);
        GoThroughCurrent.setPrefSize(600,400);
        GoThroughCurrent.setStyle("-fx-background-color: white;");
        GoThrough.add(GoThroughCurrent,0,0);
    }
}