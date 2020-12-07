/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import longpt.tblarticle.TblArticleDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "PostArticleServlet", urlPatterns = {"/PostArticleServlet"}) 
@MultipartConfig
public class PostArticleServlet extends HttpServlet {

    private final String HOME_PAGE = "home.jsp";
    private final String VIEW_POST_PAGE = "viewpost.jsp";
    private final static Logger logger = Logger.getLogger(PostArticleServlet.class);
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
        
        String title = request.getParameter("txtTitle");
        String description = request.getParameter("txtDescription");
        Part fileImage = request.getPart("txtImage");
        
        long millis = System.currentTimeMillis();
        Date curDate = new Date(millis);
        
        InputStream fileInputStream = null;
        String email = request.getParameter("txtEmail");
        
        boolean foundErr = false;
        String url = VIEW_POST_PAGE;
        try {
            if (title.isEmpty() || description.isEmpty()) {
                foundErr = true;
                request.setAttribute("ERROR", "Title or Description is not empty!!!");
                url = HOME_PAGE;
            }
            if (fileImage != null) {
                String fileName = fileImage.getSubmittedFileName();
                if (fileName.contains(".jpg") || fileName.contains(".png")) {
                    fileInputStream = fileImage.getInputStream();
                    //fileInputStream.close();
                } else { 
                    foundErr = true;
                    request.setAttribute("IMAGE_ERROR", "Please choose image with *.jpg or *.png extension");
                    url = HOME_PAGE;
                }
            }
            
            if (foundErr == false) {
                TblArticleDAO dao = new TblArticleDAO();
                dao.postArticle(title, description, fileInputStream, curDate, 3, email); //3 in DB means active post
                url = HOME_PAGE;
            }
        } catch (SQLException ex) {
            logger.error("PostServlet SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("PostServlet NamingException: " + ex.getMessage());
        } finally {
            fileInputStream.close();
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
