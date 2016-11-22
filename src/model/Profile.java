package model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * representation of a profile
 */

public class Profile {

    //private static int totalNumberOfAccounts = 1;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private final ObjectProperty<AccountType> _accountType =
            new SimpleObjectProperty<>();
    private final IntegerProperty thisInstanceAccountNumber =
            new SimpleIntegerProperty();

    /**
     * makes a new profile
     * @param username profile's username
     * @param password profile's password
     * @param accountType profile's account type
     * @param firstName the first name of profile
     * @param lastName the last name of profile
     *
     */
    public Profile(String username, String password, AccountType accountType,
                   String firstName, String lastName) {
        this.username = username;
        this.password = password;
        _accountType.set(accountType);
        this.firstName = firstName;
        this.lastName = lastName;
        //this.thisInstanceAccountNumber.setValue(totalNumberOfAccounts);
        //System.out.println(toString());
        //totalNumberOfAccounts++;
    }

    /**
     * makes a profile
     * @param username username being passed in
     * @param password password being passed in
     * @param accountType accountType being passed in
     */
    public Profile(String username, String password, AccountType accountType) {
        this.username = username;
        this.password = password;
        _accountType.setValue(accountType);
    }

    /**
     * constructor for profile
     */

    public Profile() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AccountType getAccountType() {
        return _accountType.get();
    }

    public String toString() {
        return username + " " + password + " " + _accountType.get();
    }

    public IntegerProperty thisInstanceAccountNumberProperty() {
        return thisInstanceAccountNumber;
    }

}
