/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblarticle;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phamt
 */
public class TblArticleDTO implements Serializable {

    private int articleId;
    private String title;
    private String description;
    private String image;
    private Date date;
    private String email;
    private int statusId;
    
    public TblArticleDTO() {
    }

    public TblArticleDTO(int articleId, String title, String description, Date date, String email, int statusId) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.email = email;
        this.statusId = statusId;
    }

    public TblArticleDTO(int articleId, String title, String description, String image, Date date, String email, int statusId) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.email = email;
        this.statusId = statusId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

}
