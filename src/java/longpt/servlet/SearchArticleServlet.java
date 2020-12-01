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
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@MultipartConfig
@WebServlet(name = "SearchArticleServlet", urlPatterns = {"/SearchArticleServlet"})
public class SearchArticleServlet extends HttpServlet {

    private final String HOME_PAGE = "home.jsp";
    private final static Logger logger = Logger.getLogger(SearchArticleServlet.class);
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

        String search = request.getParameter("txtContent");
        
        String page = request.getParameter("txtPaging");
        int searchPage = 0;
        if (page != null) {
            searchPage = Integer.parseInt(page);
        }
         
        String url = HOME_PAGE;

        try {
            if (search.trim().length() > 0) {
                TblArticleDAO dao = new TblArticleDAO();
                dao.searchContent(search, searchPage);
                List<TblArticleDTO> listAllArticle = dao.getListArticle();
                
                request.setAttribute("RESULT", listAllArticle);
                
                //Count the number of article is found and calculate the page
                int pageSize = dao.countPage(search);
                // 20  means 20 records in page 
                if (pageSize % 20 == 0) {
                    pageSize /= 20;
                } else {
                    pageSize = (pageSize / 20) + 1;
                }
                request.setAttribute("PAGING_SIZE", pageSize);
            }
        } catch (SQLException ex) {
            logger.error("SearchServlet SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("SearchServlet NamingException: " + ex.getMessage());
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
