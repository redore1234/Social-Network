/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblcomment;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phamt
 */
public class TblCommentDTO implements Serializable {

    private int commentId;
    private int articleId;
    private String email;
    private Date curDate;
    private String comment;
    private int status;

    public TblCommentDTO() {
    }

    public TblCommentDTO(int commentId, int articleId, String email, Date curDate, String comment, int status) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.email = email;
        this.curDate = curDate;
        this.comment = comment;
        this.status = status;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
