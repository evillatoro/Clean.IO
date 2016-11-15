package tests;
import model.AccountType;
import model.Database;
import model.Profile;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

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
    public void testAddWaterSourceReport() {
        database.addProfile(profile);
        assertTrue("profile was found", database.getProfiles()
                .contains(profile));
    }





}
