package Services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class ClearService {
    public void Clear(){
        UserDAO userAccess = new UserDAO();
        AuthDAO authAccess = new AuthDAO();
        GameDAO gameAccess = new GameDAO();
        userAccess.clear();
        authAccess.clear();
        gameAccess.clear();
    }
}
