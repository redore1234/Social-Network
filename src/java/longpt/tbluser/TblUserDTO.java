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
public class TblUserDTO implements Serializable {

    private String email;
    private String name;
    private String password;
    private int statusId;

    public TblUserDTO() {
    }

    public TblUserDTO(String email, String name, String password, int statusId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.statusId = statusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return statusId;
    }

    public void setStatus(int statusId) {
        this.statusId = statusId;
    }

}
