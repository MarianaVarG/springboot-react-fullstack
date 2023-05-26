package com.solera.backendcursojava.models.requests;

import javax.validation.constraints.*;


public class UserDetailsRequestModel {

    public static final String LAST_NAME_IS_MANDATORY = "LastName is mandatory.";
    public static final String FIRSTNAME_IS_MANDATORY = "Firstname is mandatory.";
    public static final String EMAIL_IS_MANDATORY = "Email is mandatory.";
    public static final String PASSWORD_IS_MANDATORY = "Password is mandatory.";
    @NotBlank(message = FIRSTNAME_IS_MANDATORY)
    @NotNull(message = FIRSTNAME_IS_MANDATORY)
    @NotEmpty(message = FIRSTNAME_IS_MANDATORY)
    @Size(min = 1)
    private String firstName;

    @NotBlank(message = LAST_NAME_IS_MANDATORY)
    @NotNull(message = LAST_NAME_IS_MANDATORY)
    @NotEmpty(message = LAST_NAME_IS_MANDATORY)
    @Size(min = 1)
    private String lastName;

    @NotBlank(message = EMAIL_IS_MANDATORY)
    @NotNull(message = EMAIL_IS_MANDATORY)
    @NotEmpty(message = EMAIL_IS_MANDATORY)
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "Email must be valid")
    private String email;

    @NotBlank(message = PASSWORD_IS_MANDATORY)
    @NotNull(message = PASSWORD_IS_MANDATORY)
    @NotEmpty(message = PASSWORD_IS_MANDATORY)
    @Size(min = 8, max = 30, message = "Password must be at least 8 characters and no more than 30.")
    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
