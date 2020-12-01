/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblcomment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longpt.dbutil.DBHelper;

/**
 *
 * @author phamt
 */
public class TblCommentDAO implements Serializable {

    List<TblCommentDTO> listComment;

    public List<TblCommentDTO> getListComment() {
        return listComment;
    }

    public boolean insertComment(int articleId, String email, Date date, String comment, int status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblComment(articleId, email, commentDate, comment, statusId)"
                        + "VALUES(?,?,?,?,?)";

                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);
                stm.setString(2, email);
                stm.setDate(3, date);
                stm.setString(4, comment);
                stm.setInt(5, status);

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
    
    public void browseContent(int articleId, int statusId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT commentId, articleId, email, commentDate, comment, statusId"
                        + " FROM tblComment"
                        + " WHERE articleId=? AND statusId=?"
                        + " ORDER BY commentDate DESC";
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);
                stm.setInt(2, statusId);
                
                rs = stm.executeQuery();
                while (rs.next()) {
                    int commentId = rs.getInt("commentId");
                    int id = rs.getInt("articleId");
                    String email = rs.getString("email");
                    Date date = rs.getDate("commentDate");
                    String comment = rs.getString("comment");
                    int status = rs.getInt("statusId");
                    
                    TblCommentDTO dto = new TblCommentDTO(commentId, id, email, date, comment, status);
                    if (listComment == null) {
                        listComment = new ArrayList<>();
                    }
                    if (status == 3) {  //3 in DB means active
                        listComment.add(dto);
                    }
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
    }
    
    public boolean deleteComment(int commentId, int status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblComment"
                        + " SET statusId=?"
                        + " WHERE commentId=?";
                
                stm = con.prepareStatement(sql);
                
                stm.setInt(1, status);
                stm.setInt(2, commentId);
                
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
}
