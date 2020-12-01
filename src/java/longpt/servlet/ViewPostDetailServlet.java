/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longpt.tblarticle.TblArticleDAO;
import longpt.tblarticle.TblArticleDTO;
import longpt.tblcomment.TblCommentDAO;
import longpt.tblcomment.TblCommentDTO;
import longpt.tblemotion.TblEmotionDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@MultipartConfig
@WebServlet(name = "ViewPostDetailServlet", urlPatterns = {"/ViewPostDetailServlet"})
public class ViewPostDetailServlet extends HttpServlet {
    
    private final String VIEW_POST_PAGE = "viewpost.jsp";
    private final static Logger logger = Logger.getLogger(ViewPostDetailServlet.class);
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
        
        String url = VIEW_POST_PAGE;
        String id = request.getParameter("txtArticleId");
        int articleId = 0;
        if (id != null) {
            articleId = Integer.parseInt(id);
        }
        
        try {
                //View Details Article
            TblArticleDAO articleDAO = new TblArticleDAO();
            TblArticleDTO articleDTO = articleDAO.viewPostArticle(articleId);
            request.setAttribute("ARTICLE", articleDTO);
            
                //Count like or dislike of the Article
            TblEmotionDAO emotionDAO = new TblEmotionDAO();
            
            int like = emotionDAO.countEmotionLike(articleId);
            request.setAttribute("COUNT_LIKE", like);
            
            int dislike = emotionDAO.countEmotionDislike(articleId);
            request.setAttribute("COUNT_DISLIKE", dislike);
            
                //Load comment in Article
            TblCommentDAO commentDAO = new TblCommentDAO();
            commentDAO.browseContent(articleId, 3);
            List<TblCommentDTO> listComment = commentDAO.getListComment();

            request.setAttribute("LIST_COMMENT", listComment);
        } catch (SQLException ex) {
            logger.error("ViewPostDetail SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("ViewPostDetail NamingException: " + ex.getMessage());
        } finally {
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
