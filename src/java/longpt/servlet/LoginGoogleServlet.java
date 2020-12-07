/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.google.GooglePojo;
import longpt.google.GoogleUtils;
import longpt.tbluser.TblUserDAO;
import longpt.tbluser.TblUserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/LoginGoogleServlet"})
public class LoginGoogleServlet extends HttpServlet {

    private final String INVALID_PAGE = "invalid.html";
    private final String HOME_PAGE = "home.jsp";
    private final Logger logger = Logger.getLogger(LoginGoogleServlet.class.getName());

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

        String url = INVALID_PAGE;

        try {
            //code return by google after user choose account
            String code = request.getParameter("code");
            if (code != null && !code.isEmpty()) {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

                TblUserDAO dao = new TblUserDAO();
                String encrypted = dao.encryptPassword("012345678");

                HttpSession session = request.getSession();
                if (session != null) {
                    String email = googlePojo.getEmail();

                    if (dao.checkLogin(email, encrypted) == null) {
                        dao.createNewAccount(googlePojo.getEmail(), googlePojo.getEmail(), encrypted, 2);
                    } else {
                        TblUserDTO dto = new TblUserDTO();
                        dto.setName(googlePojo.getEmail());
                        dto.setEmail(googlePojo.getEmail());

                        session.setAttribute("ACCOUNT", dto);

                        url = HOME_PAGE;
                    }
                }
            } else {
                url = INVALID_PAGE;
            }
        } catch (SQLException ex) {
            logger.error("LoginGoogle Servlet SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("LoginGoogle Servlet NamingException: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            logger.error("LoginGoogle Servlet NoSuchAlgorithmException: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
