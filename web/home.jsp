<%-- 
    Document   : search
    Created on : Sep 16, 2020, 8:37:26 PM
    Author     : phamt
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
        <title>Search</title>
    </head>
    <body>
        <!-- Nav bar -->
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        
        <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Home</a>
                </li>
                
                <li class="nav-item">
                    <a class="nav-link" href="#">Notifications</a>
                </li>
            </ul>
            
            <ul class="navbar-nav ml-auto text-center">
                <!-- Log out form -->
                <li class="nav-item">
                    <c:choose>
                        <c:when test="${not empty account}">
                            <a class="welcome-user"> Welcome ${account.name}</a>
                        </c:when>

                        <c:otherwise>
                            <c:redirect url="login.html"/>
                        </c:otherwise>
                    </c:choose>
                    
                </li>
                
                <li class="nav-item">
                    <form action="DispatchController">
                        <input type="submit" value="Logout" name="btnAction"  class="btn btn-danger btn-sm my-2 my-sm-0 mx-3"/>
                    </form>
                </li>
            </ul>
        </nav>
        
        <div class="container">
            <!-- Post article form -->
            <c:set var="error" value="${requestScope.ERROR}"/>
            <div class="card">
                <p class="card-header" style="font-weight: bold">Want to post something?</p>
                <div class="m-3">
                    <form action="DispatchController" method="POST" enctype="multipart/form-data">
                        <c:set var="error" value="${requestScope.ERROR}"/>
                        <c:set var="imageError" value="${requestScope.IMAGE_ERROR}"/>
                        
                        <div class="form-group"> 
                            <label>Title</label>
                            <input type="text" name="txtTitle" value="" class="form-control"/>
                            <p class="text-danger"> ${error} </p>
                        </div>
                        
                        <div class="form-group"> 
                            <label>Image</label>
                            <input type="file" name="txtImage" accept="image/*" class="form-control-file"/>
                            <p class="text-danger"> ${imageError} </p>
                        </div>
                        
                        <div class="form-group"> 
                            <label>Description</label> <br/>
                            <textarea class="description" name="txtDescription" cols="50" rows="5" class="form-control"></textarea>
                            <p class="text-danger"> ${error} </p>
                        </div>
                        
                        <div class="text-right"> 
                            <input type="hidden" name="txtEmail" value="${account.email}" />
                            <input type="submit" value="Post" name="btnAction" class="btn btn-success" /> 
                        </div>
                            
                    </form>
                </div>
            </div>
            
            <!-- Search form -->
            <div class="my-4">
                <form action="DispatchController">
                    Search content <input type="text" name="txtContent" value="${param.txtContent}"
                                          class="form-control col-10"/> 
                    <input type="submit" value="Search" name="btnAction" class="btn btn-success col-2"/>
                    <input type="hidden" name="txtPaging" value="1" />
                    <!-- default page when search is 1 -->
                </form>
            </div>    
            <c:set var="searchValue" value="${param.txtContent}"/>           
            
            <!-- Display result -->
            <c:if test="${not empty searchValue}">
                <c:set var="resultArticle" value="${requestScope.RESULT}"/>
            
                <c:if test="${not empty resultArticle}">
                    <h3 class="text-center">POSTS</h3>
                    <!-- Display articles -->
                    
                    <div class="row justify-content-center align-items-center">
                        <c:forEach var="listArticle" items="${resultArticle}">
                            <form action="DispatchController">
                                <input type="hidden" name="txtArticleId" value="${listArticle.articleId}" />
                                
                                <div class="col-12">
                                    <div class="card my-3">
                                        <div class="card-header">
                                            <span style="font-weight: bold">
                                                ${listArticle.email}
                                            </span>
                                            at <f:formatDate value="${listArticle.date}" type="both"/>
                                        </div>
                                        
                                        <div class="card-body">
                                            <img src="data:image/png;base64,${listArticle.image}" height="60%" width="40%" class="card-img-top mb-2"/>
                                        
                                            <h4 class="card-title">
                                                ${listArticle.title}
                                            </h4>
                                            ${listArticle.description} <br/> <br/>
                                            
                                            <!-- View article detail -->
                                            <input type="submit" value="View Details" name="btnAction" class="btn btn-primary"/> 
                                        </div>    
                                    </div>
                                </div>
                            </form>   
                        </c:forEach>
                    </div>  
                </c:if>
                    
                <c:if test="${empty resultArticle}">
                    <h2 class="text-danger">You don't have any articles in here</h2>
                </c:if>
            </c:if>
                     
            <!-- Page indicator -->
            <ul class="pagination justify-content-center mt-4">
                <c:set var="endPage" value="${requestScope.PAGING_SIZE}"/>
                <c:forEach var="curPage" begin="1" end="${endPage}" varStatus="counter">
                    <c:if test="${param.txtPaging ne counter.count}">
                        <a href="DispatchController?txtContent=${param.txtContent}&txtPaging=${counter.count}&btnAction=Search">
                            ${counter.count}
                        </a>
                    </c:if>
                    <c:if test="${param.txtPaging eq counter.count}">
                        ${counter.count}
                    </c:if>
                </c:forEach>
            </ul>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
        <%--<c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:choose>
            <c:when test="${not empty account}">
                <h3>
                    <font color="red"> Welcome ${account.name} </font>
                </h3>
            </c:when>
            
            <c:otherwise>
                <c:redirect url="login.html"/>
            </c:otherwise>
        </c:choose>
        <form action="DispatchController">
            <input type="submit" value="Logout" name="btnAction" />
        </form>
        
        <h1>Search Article</h1>
        
        <form action="DispatchController">
            Search content <input type="text" name="txtContent" value="${param.txtContent}" /> 
            <input type="submit" value="Search" name="btnAction" />
            <input type="hidden" name="txtPaging" value="1" />
        </form>
            
        <c:set var="searchValue" value="${param.txtContent}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="resultArticle" value="${requestScope.RESULT}"/>
            
            <c:if test="${not empty resultArticle}">
                <c:forEach var="listArticle" items="${resultArticle}">
                    <form action="DispatchController">
                        <input type="hidden" name="txtArticleId" value="${listArticle.articleId}" />
                        <table border="0">
                            <thead>
                                <tr>
                                    <th>
                                        ${listArticle.title}&ensp;
                                        ${listArticle.email}
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${listArticle.date}</td>
                                </tr>
                                <tr>
                                    <td>${listArticle.description}</td>
                                </tr>
                            </tbody>
                        </table>
                        
                        <img src="data:image/png;base64,${listArticle.image}" height="60%" width="40%"/> <br/>
                        <input type="submit" value="View Details" name="btnAction" />              
                    </form>
                </c:forEach>
            </c:if>    
            
            <c:if test="${empty resultArticle}">
                <h2>You don't have any articles in here</h2>
            </c:if>
        </c:if>
                
                
            <!-- POST AREA -->
                <h3>Want to post something?</h3>
        <form action="DispatchController" method="POST" enctype="multipart/form-data">
            <c:set var="error" value="${requestScope.ERROR}"/>
            <c:set var="imageError" value="${requestScope.IMAGE_ERROR}"/>
            
            Title <input type="text" name="txtTitle" value="" /> <br/>
            <c:if test="${not empty error}">
                <font color="red"> 
                    ${error}
                </font> <br/>
            </c:if>
                                
            Image <input type="file" name="txtImage" accept="image"/> <br/>
            <c:if test="${not empty imageError}">
                <font color="red"> 
                    ${imageError}
                </font> <br/>
            </c:if>
                
            Description <br/> <textarea class="description" name="txtDescription" cols="20" rows="4"></textarea> <br/>
            <c:if test="${not empty error}">
                <font color="red"> 
                    ${error}
                </font>
            </c:if> <br/>
            
            <input type="hidden" name="txtEmail" value="${account.email}" />
            
            <input type="submit" value="Post" name="btnAction" />            
        </form>        
        
            
            <!-- PAGING AREA -->
        <c:set var="endPage" value="${requestScope.PAGING_SIZE}"/>
        <c:forEach var="curPage" begin="1" end="${endPage}" varStatus="counter">
            <c:if test="${param.txtPaging ne counter.count}">
                <a href="DispatchController?txtContent=${param.txtContent}&txtPaging=${counter.count}&btnAction=Search">
                    ${counter.count}
                </a>
            </c:if>
            <c:if test="${param.txtPaging eq counter.count}">
                ${counter.count}
            </c:if>
        </c:forEach>
    </body> --%>
</html>
