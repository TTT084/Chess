package Services;

import dataAccess.*;

public class ClearService {
    public void Clear(){
        UserDAO userAccess = new MemoryUserDAO();
        AuthDAO authAccess = new MemoryAuthDAO();
        GameDAO gameAccess = new MemoryGameDAO();
        userAccess.clear();
        authAccess.clear();
        gameAccess.clear();
    }
}
