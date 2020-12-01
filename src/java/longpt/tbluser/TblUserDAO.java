/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tbluser;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import longpt.dbutil.DBHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
public class TblUserDAO implements Serializable {

    private final static Logger logger = Logger.getLogger(TblUserDAO.class);

    public TblUserDTO checkLogin(String email, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblUserDTO dto = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email, name, statusId"
                        + " FROM tblUser"
                        + " WHERE email=? AND password=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    int statusId = rs.getInt("statusId");
                    dto = new TblUserDTO(email, name, password, statusId);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return dto;
    }

    public boolean createNewAccount(String email, String name, String password, int statusId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblUser(email, name, password, statusId)"
                        + " VALUES(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, name);
                stm.setString(3, password);
                stm.setInt(4, statusId);

                if (stm.executeUpdate() > 0) {
                    try {
                        VerifyUserAccount verify = new VerifyUserAccount(email, password);
                        verify.verifyEmail();
                    } catch (MessagingException ex) {
                        System.out.println(ex.getMessage());
                        logger.error("TblUserDAO: " + ex.getMessage());
                    }
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] encode = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encode.length; i++) {
            String hex = Integer.toHexString(0xff & encode[i]); //0xFF tuong duong voi 255 = 3 byte 
            if (hexString.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public boolean activateAccount(String email, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblUser"
                        + " SET statusId=?"
                        + " WHERE email=? AND password=?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, 2); //2 in DB means Activate
                stm.setString(2, email);
                stm.setString(3, password);

                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
