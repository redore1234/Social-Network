/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longpt.tblemotion.TblEmotionDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "EmotionArticleServlet", urlPatterns = {"/EmotionArticleServlet"})
public class EmotionArticleServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(EmotionArticleServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String emotion = request.getParameter("btnAction");
        boolean flag = true; //mac dinh emotion la true
        
        if (emotion.equals("Dislike")) {
            flag = false;
        }
        
        String id = request.getParameter("txtArticleId");
        int articleId = 0;
        if (id != null) {
            articleId = Integer.parseInt(id);
        }
        
        String email = request.getParameter("txtEmailId");
        
        try {
            TblEmotionDAO dao = new TblEmotionDAO();
            if (dao.getEmotionByArticleIdAndEmail(articleId, email)) { // return false if articleId and email don't exist
                if (flag) { // flag = true => change like into true and dislike into false
                    dao.updateEmotionByArticleId(articleId, email, true, false);
                } else {
                    dao.updateEmotionByArticleId(articleId, email, false, true);
                }
            } else {
                long milis = System.currentTimeMillis();
                Date date = new Date(milis);
                if (flag) { 
                    dao.insertEmotionByArticleId(articleId, email, true, false, date, 3);  //3 means active in DB
                } else {
                    dao.insertEmotionByArticleId(articleId, email, false, true, date, 3);
                }
            }
        } catch (SQLException ex) {
            logger.error("EmotionArticleServlet SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("EmotionArticleServlet NamingException: " + ex.getMessage());
        } finally {
            String url = "DispatchController?"
                    + "btnAction=View Details"
                    + "&txtArticleId=" + articleId;
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
