package UnitTests;

import Response.RegisterResponse;
import Services.RegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnitTests {
    @Test
    public void RegisterBasicTest(){
        RegisterService survy = new RegisterService();
        String user = "myUsername";
        String pass = "sillyPass";
        String email = "myEmail";
        RegisterResponse response = survy.Register(user,pass,email);
        String newUser = response.getUsername();
        Assertions.assertEquals(user,newUser);

    }
    @Test
    public void RegisterNullTest() {
        RegisterService survy = new RegisterService();
        String user = "myUsername";
        String pass = "sillyPass";
        String email = "myEmail";
        RegisterResponse response1 = survy.Register(user, pass, email);
        String nameOne = response1.getUsername();
        RegisterResponse response2 = survy.Register(user, pass, email);
        String nameTwo = response2.getUsername();
        Assertions.assertNotEquals(nameOne, nameTwo);
    }
}
