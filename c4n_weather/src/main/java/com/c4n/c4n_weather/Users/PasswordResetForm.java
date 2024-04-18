package com.c4n.c4n_weather.Users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

public class PasswordResetForm {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String code;

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String confirmPassword;

    public PasswordResetForm(String email, String code, String newPassword, String confirmPassword) {
        this.email = email;
        this.code = code;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean passwordsMatch() {
        return newPassword.equals(confirmPassword);
    }

}
