package photos13;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import photos13.Admin;

public class Photos13UIController {
    public static boolean ErrorWindowAppear = false;
    
    @FXML Button b1;
    @FXML Button b2;
    @FXML TextField tf1;
    @FXML Pane ErrorMessageWindow;
    @FXML TextArea ErrorMessageText;
    
    public void convert(ActionEvent e) throws Exception{
        Button temp = (Button)e.getSource();
        if(temp == b1 && !ErrorWindowAppear){
            String inputname = tf1.getText();
            boolean UserFound = false;
            for(int i=0;i<Admin.UserList.size();i++){
                if(Admin.UserList.get(i).getName().equals(inputname)){
                        //do something to login, load save
                    UserFound = true;
                    break;
                }
            }
            if(!UserFound){
                ErrorMessageText.setText("Error01:Cannot find user, or it is not available");
                ErrorMessageWindow.setVisible(true);
                ErrorWindowAppear = true;
            }
        }
        else if(temp == b2 && ErrorWindowAppear){
            ErrorMessageWindow.setVisible(false);
            ErrorWindowAppear = false;
        }
    }
}
