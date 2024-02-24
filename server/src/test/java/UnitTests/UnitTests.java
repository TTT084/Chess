package UnitTests;

import Response.RegisterResponse;
import Services.RegisterService;
import Response.*;
import Services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitTests {
    private String user;
    private String pass;
    private String email;
    private String auth;
    @BeforeEach
    public void setUp(){
        user = "myUsername";
        pass = "sillyPass";
        email = "myEmail";
    }
    @Test
    public void RegisterBasicTest(){
        RegisterService survy = new RegisterService();
        RegisterResponse response = survy.Register(user,pass,email);
        String newUser = response.getUsername();
        Assertions.assertEquals(user,newUser);

    }
    @Test
    public void RegisterNullTest() {
        RegisterService survy = new RegisterService();

        RegisterResponse response1 = survy.Register(user, pass, email);
        String nameOne = response1.getUsername();
        RegisterResponse response2 = survy.Register(user, pass, email);
        String nameTwo = response2.getUsername();
        Assertions.assertNotEquals(nameOne, nameTwo);
    }
    @Test
    public void LoginUserNotFound(){
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,"");
    }
    @Test
    public void LoginWrongPassword(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, "wrong");
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,"");
    }
}
