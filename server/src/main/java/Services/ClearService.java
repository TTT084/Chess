package Services;

import dataAccess.*;

public class ClearService {
    public void clear(){
        UserDAO userAccess = new SQLUserDAO();
        AuthDAO authAccess = new SQLAuthDAO();
        GameDAO gameAccess = new SQLGameDAO();
        userAccess.clear();
        authAccess.clear();
        gameAccess.clear();
    }
}
