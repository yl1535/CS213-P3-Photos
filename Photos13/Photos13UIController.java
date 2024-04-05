package Photos13;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    @FXML Button adminb1;
    @FXML Button adminb2;
    @FXML Button adminb3;
    @FXML Button adminb4;
    @FXML Button adminb5;
    @FXML TextField tf1;
    @FXML TextField Admintf1;
    @FXML Pane MainPage;
    @FXML Pane ErrorMessageWindow;
    @FXML Pane AdminPage;
    @FXML Pane UserListPage;
    @FXML TextArea ErrorMessageText;
    @FXML TextArea UserList;
    
    Pane Current;
    
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
                    if(Admin.UserList.get(i).toString().equals(inputname)){
                        //do something to login, load save
                            //Q: if admin? if stock?
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
            boolean repeat = false;
            for(int i=0;i<Admin.UserList.size();i++){
                if(Admin.UserList.get(i).toString().equals(name)){
                    ErrorMessageText.setText("Error02:This User already exists");
                    windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
                    UserListPage.setDisable(true);
                    repeat = true;
                    break;
                }
            }
            if(!repeat){
                Admin.UserList.add(new User(name));
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
                boolean ifexist = false;
                for(int i=0;i<Admin.UserList.size();i++){
                    if(Admin.UserList.get(i).toString().equals(name)){
                        Admin.UserList.remove(i);
                        UserList.setText(Admin.ConvertArrayListtoString(Admin.UserList));
                        UserListPage.setDisable(true);
                        ErrorMessageText.setText("User Remove Success.");
                        windowTransfer(AdminPage,ErrorMessageWindow,Operations.OpenError);
                        ifexist = true;
                        break;
                    }
                }
                if(!ifexist){
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
}