/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblarticle;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author phamt
 */
public class TblArticleDAO implements Serializable {

    private List<TblArticleDTO> listArticle;
    private final static Logger logger = Logger.getLogger(TblArticleDAO.class);
    
    public List<TblArticleDTO> getListArticle() {
        return listArticle;
    }

    public int countPage(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(articleId) as number"
                        + " FROM tblArticle"
                        + " WHERE description LIKE ? AND statusId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setInt(2, 3); // 3 in DB means Active

                rs = stm.executeQuery();
                if (rs.next()) {
                    int number = rs.getInt("number");
                    return number;
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

    public void searchContent(String searchValue, int searchPage) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT articleId, title, description, image, date, email, statusId"
                        + " FROM tblArticle"
                        + " WHERE description LIKE ? AND statusId=?"
                        + " ORDER BY date DESC"
                        + " OFFSET ? ROWS"
                        + " FETCH NEXT ? ROWS ONLY";

                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setInt(2, 3);  // 3 in DB means Active
                stm.setInt(3, 20 * (searchPage - 1)); //offset, 20 means 20 records in page
                stm.setInt(4, 20); //fetch

                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("articleId");
                    String title = rs.getString("title");
                    String description = rs.getString("description");

                    byte[] imageByte = rs.getBytes("image");
                    String imageEncode = null;
                    if (imageByte != null) {
                        try {
                            imageEncode = new String(Base64.encodeBase64(imageByte), "UTF-8");
                        } catch (IOException ex) {
                            logger.error("TblArticleDAO IOException: " + ex.getMessage());
                        }
                    }
                    Date date = rs.getDate("date");
                    String email = rs.getString("email");
                    int status = rs.getInt("statusId");

                    TblArticleDTO dto = new TblArticleDTO(articleId, title, description, imageEncode, date, email, status);
                    if (this.listArticle == null) {
                        listArticle = new ArrayList<>();
                    }
                    if (status == 3) {
                        listArticle.add(dto);
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

    public boolean postArticle(String title, String description, InputStream image, Date date, int status, String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblArticle(title, description, image, date, email, statusId)"
                        + " VALUES(?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, title);
                stm.setString(2, description);

                if (image != null) {
                    stm.setBinaryStream(3, image);
                } else {
                    stm.setBinaryStream(3, null);
                }

                stm.setDate(4, date);
                stm.setString(5, email);
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

    public TblArticleDTO viewPostArticle(int postId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblArticleDTO dto = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT articleId, title, description, image, date, email, statusId"
                        + " FROM tblArticle"
                        + " WHERE articleId=? AND statusId=?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setInt(2, 3);  //3 means active in DB
                rs = stm.executeQuery();
                if (rs.next()) {
                    int articleId = rs.getInt("articleId");
                    String title = rs.getString("title");
                    String description = rs.getString("description");

                    byte[] imageByte = rs.getBytes("image");
                    String imageEncode = null;
                    if (imageByte != null) {
                        try {
                            imageEncode = new String(Base64.encodeBase64(imageByte), "UTF-8");
                        } catch (IOException ex) {
                            logger.error("TblArticleDAO IOException: " + ex.getMessage());
                        }
                    }
                    Date date = rs.getDate("date");
                    String email = rs.getString("email");
                    int status = rs.getInt("statusId");
                    dto = new TblArticleDTO(articleId, title, description, imageEncode, date, email, status);
                    if (this.listArticle == null) {
                        listArticle = new ArrayList<>();
                    }
                    listArticle.add(dto);
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

    public boolean deletePost(int articleId, int status) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblArticle"
                        + " SET statusId=?"
                        + " WHERE articleId=?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, status);
                stm.setInt(2, articleId);

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
