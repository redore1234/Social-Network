<%-- 
    Document   : register
    Created on : Sep 15, 2020, 1:53:46 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">-->
        <title>Register</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
        <div class="container-fluid h-100 bg-custom">
            <div class="row h-100 justify-content-center align-items-center">
                <div class="card col-10 col-md-8 col-lg-6 p-5">
                    <div class="text-center pb-3">
                        <h1>Register Page</h1>
                    </div>
                    
                    <form action="DispatchController" method="POST">
                        <c:set var="errors" value="${requestScope.ERRORS}"/>
                        
                        <div class="form-group">
                            <label>Email</label>
                            <input type="text" class="form-control" placeholder="abc@gmail.com" name="txtEmail" 
                                   value="${param.txtEmail}" />
                            <small  class="text-muted">Must be 12-50 chars</small>
                            <p class="text-danger"> ${errors.emailIsEmpty} </p>
                        </div>
                        
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" placeholder="Your name" name="txtName" 
                                   value="${param.txtName}" />
                            <small  class="text-muted">Must be 8-50 chars</small>
                            <p class="text-danger"> ${errors.nameIsEmpty} </p>
                        </div>
                        
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" name="txtPassword" 
                                   value="" />
                            <small  class="text-muted">Must be 8-50 chars</small>
                            <p class="text-danger"> ${errors.passwordIsEmpty} </p>
                        </div>
                        
                        <div class="form-group">
                            <label>Confirm Password</label>
                            <input type="password" class="form-control" name="txtConfirmPassword" 
                                   value="" />
                            <small  class="text-muted">Match to Password</small>
                            <p class="text-danger">  ${errors.passwordIsNotMatch} </p>
                        </div>
                        
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary" value="Register" name="btnAction" />
                            <input type="reset" class="btn btn-primary" value="Reset" />
                            <p class="text-danger"> ${errors.emailIsExisted} </p>
                        </div>
                    </form>
                        
                    <div class="text-center">
                        Want to login again?<a href="login.html"> Try again</a> <br/>
                    </div>
                </div>
            </div>
        </div>
        
<%--        <h1>Register Page</h1>
    
        <form action="DispatchController" method="POST">
            <c:set var="errors" value="${requestScope.ERRORS}"/>
            <table border="0">
                <tr>
                    <th align="right">Email</th>
                    <td>
                        <input type="text" name="txtEmail" value="${param.txtEmail}" /> (e.g 12-50 chars)
                    </td>
                
                    <td>
                        <font color="red">
                            <c:if test="${not empty errors.emailIsEmpty}">
                            <br/>    ${errors.emailIsEmpty} <br/>
                            </c:if>
                        </font>
                    </td>
                    
                    <td>
                        <font color="red">
                            <c:if test="${not empty errors.emailIsExisted}">
                            <br/>    ${errors.emailIsExisted} <br/>
                            </c:if>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th align="right">Name</th>
                    <td>
                        <input type="text" name="txtName" value="${param.txtName}" /> (e.g 8-50 chars)
                    </td>
                    <td>
                        <font color="red">
                            <c:if test="${not empty errors.nameIsEmpty}">
                            <br/>    ${errors.nameIsEmpty} <br/>
                            </c:if>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th>Password</th>
                    <td>
                        <input type="password" name="txtPassword" value="" /> (e.g 8-50 chars)
                    </td>
                
                    <td>
                        <font color="red">
                            <c:if test="${not empty errors.passwordIsEmpty}">
                            <br/>    ${errors.passwordIsEmpty} <br/>
                            </c:if>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th>Confirm Password</th>
                    <td>
                        <input type="password" name="txtConfirmPassword" value="" />
                    </td>
                
                    <td>
                        <font color="red">
                            <c:if test="${not empty errors.passwordIsNotMatch}">
                            <br/>    ${errors.passwordIsNotMatch} <br/>
                            </c:if>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <td colspan="1" align="right">
                        <input type="submit" value="Register" name="btnAction"/> <br/>
                    </td>
                </tr>
            </table>            
            
            <div>
                Want to login again?<a href="login.html"> Try again</a> <br/>
            </div>
        </form>--%>
    </body>
</html>
