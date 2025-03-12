import dataObjects.User;
import org.testng.annotations.DataProvider;

public class UserData {

    @DataProvider(name = "valid-user-data2")
    public static Object[][] getUserData2() {
        return new Object[][]{
                {new User("standard_user", "secret_sauce")},
                {new User("standard_user", "secret_sauce")}
        };
    }
}
