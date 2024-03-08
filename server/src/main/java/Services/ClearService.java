package Services;

import dataAccess.*;

public class ClearService {
    public void Clear(){
        UserDAO userAccess = new SQLUserDAO();
        AuthDAO authAccess = new SQLAuthDAO();
        GameDAO gameAccess = new SQLGameDAO();
        userAccess.clear();
        authAccess.clear();
        gameAccess.clear();
    }
}
