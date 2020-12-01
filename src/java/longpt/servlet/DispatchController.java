/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author phamt
 */
@MultipartConfig
public class DispatchController extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String SEARCH_ARTICLE_CONTROLLER = "SearchArticleServlet";
    private final String POST_ARTICLE_CONTROLLER = "PostArticleServlet";
    private final String EMOTION_ARTICLE_CONTROLLER = "EmotionArticleServlet";
    private final String POST_COMMENT_CONTROLLER = "PostCommentServlet";
    private final String DELETE_POST_ARTICLE_CONTROLLER = "DeletePostArticleServlet";
    private final String DELETE_POST_COMMENT_CONTROLLER = "DeletePostCommentServlet";
    private final String VIEW_POST_DETAIL_CONTROLLER = "ViewPostDetailServlet";
    private final String REGISTER_NEW_ACCOUNT_CONTROLLER = "RegisterNewAccountServlet";
    private final String VERIFY_ACCOUNT_CONTROLLER = "VerifyAccountServlet";

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

        String button = request.getParameter("btnAction");
        String url = LOGIN_PAGE;
        HttpSession session = request.getSession(false);
        try {
            if (button == null) {
                url = LOGIN_PAGE;
            } else if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Logout") && session != null) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("Search") && session != null) {
                url = SEARCH_ARTICLE_CONTROLLER;
            } else if (button.equals("Post") && session != null) {
                url = POST_ARTICLE_CONTROLLER;
            } else if (button.equals("Delete") && session != null) {
                url = DELETE_POST_ARTICLE_CONTROLLER;
            } else if (button.equals("Delete Comment") && session != null) {
                url = DELETE_POST_COMMENT_CONTROLLER;
            } else if (button.equals("View Details") && session != null) {
                url = VIEW_POST_DETAIL_CONTROLLER;
            } else if (button.equals("Comment") && session != null) {
                url = POST_COMMENT_CONTROLLER;
            } else if ((button.equals("Like") || button.equals("Dislike")) && session != null) {
                url = EMOTION_ARTICLE_CONTROLLER;
            } else if (button.equals("Register")) {
                url = REGISTER_NEW_ACCOUNT_CONTROLLER;
            } else if (button.equals("VerifyEmail")) {
                url = VERIFY_ACCOUNT_CONTROLLER;
            }
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
