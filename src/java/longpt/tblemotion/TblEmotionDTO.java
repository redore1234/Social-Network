/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblemotion;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phamt
 */
public class TblEmotionDTO implements Serializable {

    private int emotionId;
    private int articleId;
    private String email;
    private boolean like;
    private boolean dislike;
    private Date date;
    private int status;

    public TblEmotionDTO() {
    }

    public TblEmotionDTO(int articleId, String email, boolean like, boolean dislike, Date date, int status) {
        this.articleId = articleId;
        this.email = email;
        this.like = like;
        this.dislike = dislike;
        this.date = date;
        this.status = status;
    }

    public TblEmotionDTO(int emotionId, int articleId, String email, boolean like, boolean dislike, int status) {
        this.emotionId = emotionId;
        this.articleId = articleId;
        this.email = email;
        this.like = like;
        this.dislike = dislike;
        this.status = status;
    }

    public int getEmotionId() {
        return emotionId;
    }

    public void setEmotionId(int emotionId) {
        this.emotionId = emotionId;
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

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
