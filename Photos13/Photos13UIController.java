package Photos13;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Photos13UIController {
    public static boolean ErrorWindowAppear = false;
    private enum Operations{
        NewWindow,  //Open a new window, while the user can operate on both the current page and the new window
        CloseWindow,
        NewScene,   //Open a new scene to replace the old page(current page before), the old page can not be operated while users can only operate on the new scene(current page now)
        CloseScene,
        OpenError,  //Open an error window, different from NewScene that it won't invisible the old page
        CloseError;
    }
    
    @FXML Button b1;
    @FXML Button b2;
    @FXML TextField tf1;
    @FXML Pane MainPage;
    @FXML Pane ErrorMessageWindow;
    @FXML TextArea ErrorMessageText;
    
    public void convert(ActionEvent e) throws Exception{
        Button temp = (Button)e.getSource();
        if(temp == b1){
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
                windowTransfer(MainPage,ErrorMessageWindow,Operations.OpenError);
            }
        }
        else if(temp == b2){
            windowTransfer(ErrorMessageWindow,MainPage,Operations.CloseError);
        }
    }
    
    public void windowTransfer(Pane p1, Pane p2, Operations o){
        switch(o){
            case NewWindow:
                
            case CloseWindow:
            
            case NewScene:
            
            case CloseScene:
                
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
