/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tbluser;

import java.io.Serializable;

/**
 *
 * @author phamt
 */
public class RegisterNewAccountError implements Serializable {

    private String emailIsEmpty;
    private String nameIsEmpty;
    private String passwordIsEmpty;
    private String emailIsExisted;
    private String passwordIsNotMatch;

    public RegisterNewAccountError() {
    }

    public RegisterNewAccountError(String emailIsEmpty, String nameIsEmpty, String passwordIsEmpty, String emailIsExisted, String passwordIsNotMatch) {
        this.emailIsEmpty = emailIsEmpty;
        this.nameIsEmpty = nameIsEmpty;
        this.passwordIsEmpty = passwordIsEmpty;
        this.emailIsExisted = emailIsExisted;
        this.passwordIsNotMatch = passwordIsNotMatch;
    }

    public String getEmailIsEmpty() {
        return emailIsEmpty;
    }

    public void setEmailIsEmpty(String emailIsEmpty) {
        this.emailIsEmpty = emailIsEmpty;
    }

    public String getNameIsEmpty() {
        return nameIsEmpty;
    }

    public void setNameIsEmpty(String nameIsEmpty) {
        this.nameIsEmpty = nameIsEmpty;
    }

    public String getPasswordIsEmpty() {
        return passwordIsEmpty;
    }

    public void setPasswordIsEmpty(String passwordIsEmpty) {
        this.passwordIsEmpty = passwordIsEmpty;
    }

    public String getEmailIsExisted() {
        return emailIsExisted;
    }

    public void setEmailIsExisted(String emailIsExisted) {
        this.emailIsExisted = emailIsExisted;
    }

    public String getPasswordIsNotMatch() {
        return passwordIsNotMatch;
    }

    public void setPasswordIsNotMatch(String passwordIsNotMatch) {
        this.passwordIsNotMatch = passwordIsNotMatch;
    }

}
