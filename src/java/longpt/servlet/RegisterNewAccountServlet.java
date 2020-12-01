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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longpt.tbluser.TblUserDAO;
import longpt.tbluser.RegisterNewAccountError;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "RegisterNewAccountServlet", urlPatterns = {"/RegisterNewAccountServlet"})
public class RegisterNewAccountServlet extends HttpServlet {
    
    private final String REGISTER_PAGE = "register.jsp";
    private final String VERIFY_PAGE = "verify.html";
    private final static Logger logger = Logger.getLogger(RegisterNewAccountServlet.class);
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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("txtEmail");
        String name = request.getParameter("txtName");
        String password = request.getParameter("txtPassword");
        String confirmPassword = request.getParameter("txtConfirmPassword");
        
        RegisterNewAccountError errors = new RegisterNewAccountError();
        boolean foundErr = false;
        
        String url = REGISTER_PAGE;
        try { 
            //* 0 or more times, + 1 or more times
            if (!email.trim().matches("((\\w*)(\\d*))+@((\\w+)\\.(\\w+)){1}(\\.(\\w+))*") || email.trim().length() < 12 || email.trim().length() > 50) {
                foundErr = true;
                errors.setEmailIsEmpty("Email must be range 12-50 characters! and match the pattern 'xxx@xxx.xxx'");
            }            
            if (name.trim().length() < 8 || name.trim().length() > 50) {
                foundErr = true;
                errors.setNameIsEmpty("Name must be range 8-50 characters!");
            }
            if (password.trim().length() < 8 || password.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordIsEmpty("Password must be range 8-30 characters!");
            } else if (!confirmPassword.equals(password)) {
                foundErr = true;
                errors.setPasswordIsNotMatch("Confirm must match password!");
            }
            
            if (foundErr) {
                request.setAttribute("ERRORS", errors);
            } else {
                TblUserDAO dao = new TblUserDAO();
                String encrypted = dao.encryptPassword(password);
                boolean result = dao.createNewAccount(email, name, encrypted, 1);  // 1 in DB means New in tblStatus
                if (result) {
                    url = VERIFY_PAGE;
                }
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            logger.error("RegisterNewAccountServlet SQLException: " + msg);
            if (msg.contains("duplicate")) {
                request.setAttribute("ERRORS", errors);
                errors.setEmailIsExisted("Email is existed. Please input another email!");
            }
        } catch (NoSuchAlgorithmException ex) {
            logger.error("RegisterNewAccountServlet NoSuchAlgorithmException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("RegisterNewAccountServlet NamingException: " + ex.getMessage());
        }  finally {            
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
