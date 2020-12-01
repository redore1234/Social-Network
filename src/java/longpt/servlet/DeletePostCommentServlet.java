/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longpt.tblcomment.TblCommentDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "DeletePostCommentServlet", urlPatterns = {"/DeletePostCommentServlet"})
public class DeletePostCommentServlet extends HttpServlet {

    private final String VIEW_POST_PAGE = "viewpost.jsp";
    private final static Logger logger = Logger.getLogger(DeletePostCommentServlet.class);

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

        String article = request.getParameter("txtArticleId");
        int articleId = 0;
        if (article != null) {
            articleId = Integer.parseInt(article);
        }

        String comment = request.getParameter("txtCommentId");
        int commentId = 0;
        if (comment != null) {
            commentId = Integer.parseInt(comment);
        }
        String url = VIEW_POST_PAGE;
        try {
            TblCommentDAO dao = new TblCommentDAO();
            boolean result = dao.deleteComment(commentId, 4);  // 4 in DB means Delete
            if (result) {
                url = "DispatchController?"
                        + "btnAction=View Details"
                        + "&txtArticleId=" + articleId;
            }
        } catch (SQLException ex) {
            logger.error("DeletePostCommentServlet SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("DeletePostCommentServlet NamingException: " + ex.getMessage());
        } finally {
            // không được dùng response.sendRedirect vì khi sendRedirect nó sẽ không lưu 
            // value của txtArticleId nên ở ViewPostDetailServlet nó sẽ báo null 
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
