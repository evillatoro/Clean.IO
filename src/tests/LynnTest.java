package tests;
import model.AccountType;
import model.Database;
import model.Profile;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Created by lynnyou on 11/14/16.
 */
public class LynnTest {
    private Database database;
    private Profile profile;
    public static final int TIMEOUT = 200;
    @Before
    public void setUp() {
        database = new Database();
        profile = new Profile("username", "password", AccountType.MANAGER);
    }
    @Test(timeout = TIMEOUT)
    public void testSearchProfile() {
        database.addProfile(profile);
        assertEquals("profile was not found", profile, database.searchForProfile
                ("username", "password"));
    }

    @Test(timeout = TIMEOUT)
    public void testSearchProfileNull() {
        assertEquals("profile with that username and password was found", null,
        database.searchForProfile("username", "password"));
    }

}
