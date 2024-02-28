package Services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.UserDAO;

public class ClearService {
    public void Clear(){
        UserDAO userAccess = new UserDAO();
        AuthDAO authAccess = new MemoryAuthDAO();
        GameDAO gameAccess = new GameDAO();
        userAccess.clear();
        authAccess.clear();
        gameAccess.clear();
    }
}
