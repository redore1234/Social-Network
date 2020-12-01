/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblemotion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import longpt.dbutil.DBHelper;

/**
 *
 * @author phamt
 */
public class TblEmotionDAO implements Serializable {

    public boolean getEmotionByArticleIdAndEmail(int articleId, String emotionUser) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT emotionId, articleId, email, emotionLike, emotionDislike, emotionDate, statusId"
                        + " FROM tblEmotion"
                        + " WHERE articleId=? AND email=?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);
                stm.setString(2, emotionUser);

                rs = stm.executeQuery();
                while (rs.next()) {
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

    public void updateEmotionByArticleId(int articleId, String emotionUser, boolean like, boolean dislike) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblEmotion"
                        + " SET emotionLike=?, emotionDislike=?"
                        + " WHERE articleId=? AND email=?";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, like);
                stm.setBoolean(2, dislike);
                stm.setInt(3, articleId);
                stm.setString(4, emotionUser);

                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean insertEmotionByArticleId(int articleId, String emotionUser, boolean like, boolean dislike, Date date, int status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblEmotion(articleId, email, emotionLike, emotionDislike, emotionDate, statusId)"
                        + " VALUES(?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);

                stm.setInt(1, articleId);
                stm.setString(2, emotionUser);
                stm.setBoolean(3, like);
                stm.setBoolean(4, dislike);
                stm.setDate(5, date);
                stm.setInt(6, status);

                if (stm.executeUpdate() > 0) {
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

    public int countEmotionLike(int articleId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(emotionId) AS numberLike"
                        + " FROM tblEmotion"
                        + " WHERE articleId=? AND emotionLike=1"; //1 means true in DB
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    int like = rs.getInt("numberLike");
                    return like;
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
        return 0;
    }

    public int countEmotionDislike(int articleId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(emotionId) AS numberDislike"
                        + " FROM tblEmotion"
                        + " WHERE articleId=? AND emotionDislike=1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    int dislike = rs.getInt("numberDislike");
                    return dislike;
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
        return 0;
    }
}
