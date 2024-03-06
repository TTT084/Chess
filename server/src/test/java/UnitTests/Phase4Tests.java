package UnitTests;

import Responses.RegisterResponse;
import Services.RegisterService;
import Responses.*;
import Services.*;
import dataAccess.AuthDAO;
import dataAccess.SQLAuthDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Phase4Tests {
    private String user;
    private String pass;
    private String email;
    private String auth;
    @BeforeEach
    public void setUp(){
        ClearService survy = new ClearService();
        survy.Clear();
        user = "myUsername";
        pass = "sillyPass";
        email = "myEmail";
    }
    @Test
    public void InsertAuth(){
        AuthDAO authy = new SQLAuthDAO();
        auth = authy.createAuth("usey");
        Assertions.assertNotNull(auth);
    }
    @Test
    public void ClearAuth(){
        AuthDAO authy = new SQLAuthDAO();
        String auth1 = authy.createAuth("usey");
        Assertions.assertNotNull(auth1);
        authy.clear();

        //Assertions.assertEquals(newUser2,newUser);
    }
}
