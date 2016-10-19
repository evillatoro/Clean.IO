package model;

import javafx.beans.property.*;

public class Profile {

    private static int accountNumber = 1;

    private final StringProperty _firstName = new SimpleStringProperty();
    private final StringProperty _username = new SimpleStringProperty();
    private final StringProperty _lastName = new SimpleStringProperty();
    private final StringProperty _password = new SimpleStringProperty();
    private final ObjectProperty<AccountType> _accountType = new SimpleObjectProperty<>();
    private final IntegerProperty thisInstanceAccountNumber = new SimpleIntegerProperty();

    public String getUsername() {
        return _username.get();
    }

    public void setUsername(String username) {
        _username.set(username);
    }

    public String getFirstName() {
        return _firstName.get();
    }

    public void setFirstName(String name) {
        _firstName.set(name);
    }

    public String getLastName() {
        return _lastName.get();
    }

    public void setLastName(String lastName) {
        _lastName.set(lastName);
    }

    public String getPassword() {
        return _password.get();
    }

    public void setPassword(String password) {
        _password.set(password);
    }

    public AccountType getAccountType() {
        return _accountType.get();
    }

    public void setAccountType(AccountType accountType) {
        _accountType.set(accountType);
    }

    /**
     * makes a new profile
     * @param username profile's username
     * @param password profile's password
     * @param accountType profile's account type
     */
    public Profile (String username, String password, AccountType accountType) {
        _username.set(username);
        _password.set(password);
        _accountType.set(accountType);
        this.thisInstanceAccountNumber.setValue(accountNumber);
        System.out.println("class account number " + accountNumber);
        accountNumber++;
    }

    public String toString() {
        return _username.get() + " " + _password.get() + " " + _accountType.get();
    }

    public static int getAccountNumber() {
        return accountNumber;
    }

    public static void setAccountNumber(int accountNumber) {
        Profile.accountNumber = accountNumber;
    }

    public int getThisInstanceAccountNumber() {
        return thisInstanceAccountNumber.get();
    }

    public IntegerProperty thisInstanceAccountNumberProperty() {
        return thisInstanceAccountNumber;
    }

    public void setThisInstanceAccountNumber(int thisInstanceAccountNumber) {
        this.thisInstanceAccountNumber.set(thisInstanceAccountNumber);
    }
}
