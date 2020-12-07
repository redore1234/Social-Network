<%-- 
    Document   : post
    Created on : Sep 22, 2020, 1:08:42 PM
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
        <title>View Post</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
        <!-- Nav bar -->
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        
        <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="home.jsp">Home</a>
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
            <div class="row h-100 justify-content-center align-items-center">
                <div class="col-12">
                    <div class="card my-3">
                        <form action="DispatchController" method="POST">
                            <c:set var="article" value="${requestScope.ARTICLE}"/>
                            <input type="hidden" name="txtArticleId" value="${article.articleId}"/> 
                            
                            <div class="card-header">
                                <span style="font-weight: bold">
                                     ${article.email}
                                </span>
                                at <f:formatDate value="${article.date}" type="both"/>
                                <br/>
                                
                                <input type="hidden" name="txtEmailId" value="${account.email}" />
                                <c:if test="${article.email eq account.email}">
                                    <c:url var="urlRewritingArticle" value="DispatchController">
                                        <c:param name="btnAction" value="Delete"/>
                                        <c:param name="txtArticleId" value="${article.articleId}"/>
                                    </c:url>
                                    <a href="${urlRewritingArticle}" class="badge badge-warning" 
                                       onclick="return confirm('Do you want to delete this post?');">
                                        Delete 
                                    </a>
                                </c:if>     
                            </div>
                            
                            <div class="card-body"> 
                                <c:if test="${not empty article.image}">
                                    <img src="data:image/png;base64,${article.image}" height="60%" width="40%" class="card-img-top mb-2"/>
                                </c:if>
                                    
                                <div class="card-title"> 
                                    ${article.title}
                                </div>
                                ${article.description}
                            </div>
                        
                            <div class="card-footer"> 
                                <!-- Emotion -->
                                <c:set var="likeNumber" value="${requestScope.COUNT_LIKE}"/>
                                <c:set var="dislikeNumber" value="${requestScope.COUNT_DISLIKE}"/>
                                
                                <span class="d-inline-block">
                                    <span class="text-primary">${likeNumber} Likes</span>
                                    <c:url var="urlLike" value="DispatchController">
                                        <c:param name="btnAction" value="Like"/>
                                        <c:param name="txtArticleId" value="${article.articleId}"/>
                                        <c:param name="txtEmailId" value="${account.email}"/>
                                    </c:url>
                                    <a href="${urlLike}"><img src="assets/images/like.png" style="width:40px;height:40px"></a>
                                </span>
                                
                                <span class="d-inline-block">
                                    <span class="text-danger">${dislikeNumber} Dislikes</span>
                                    <c:url var="urlDislike" value="DispatchController">
                                        <c:param name="btnAction" value="Dislike"/>
                                        <c:param name="txtArticleId" value="${article.articleId}"/>
                                        <c:param name="txtEmailId" value="${account.email}"/>
                                    </c:url>
                                    <a href="${urlDislike}"><img src="assets/images/dislike.png" style="width:40px;height:40px"></a>
                                </span>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
                    <!-- COMMENT AREA-->

        <!-- Comment form -->
        <c:set var="commentErr" value="${requestScope.COMMENT_ERROR}"/>
        <div class="form-group border-bottom">
            <form action="DispatchController" method="POST" >
                <textarea class="form-control" name="txtComment" cols="40" rows="6"></textarea>  
                <input type="hidden" name="txtEmail" value="${account.email}" class="form-control"/>
                <input type="hidden" name="txtArticleId" value="${article.articleId}" />
                <br/>
                <c:if test="${not empty commentErr}">
                    <p class="text-danger">
                        ${commentErr}
                    </p>
                </c:if>
                <br/>

                <input type="submit" value="Comment" name="btnAction" class="btn btn-success"/> <br/>
                <br/>
            </form>
        </div>
        
        <!-- Comments List -->
        <c:set var="listComment" value="${requestScope.LIST_COMMENT}"/>
        <c:if test="${not empty listComment}">
            <h3>Comments</h3>
            <div class="row justify-content-center align-items-center">
                <c:forEach var="list" items="${listComment}">
                    <input type="hidden" name="txtCommentId" value="${list.commentId}" />
                    <div class="col-12">
                        <div class="card my-2">
                            <div class="card-header">
                                <span style="font-weight: bold">
                                    ${list.email}
                                </span>
                                at <f:formatDate value="${list.curDate}" type="both"/>
                            </div>
                            
                            <div class="card-body">
                                ${list.comment} <br/>
                                
                                <c:if test="${list.email eq account.email}">
                                <c:url var="urlRewringComment" value="DispatchController">
                                    <c:param name="btnAction" value="Delete Comment"/>
                                    <c:param name="txtCommentId" value="${list.commentId}"/>
                                    <c:param name="txtArticleId" value="${list.articleId}"/>
                                </c:url>
                                    <a href="${urlRewringComment}" class="badge badge-warning"
                                   onclick="return confirm('Do you want to delete this comment?');">
                                    Delete comment
                                </a> <br/>
                            </c:if>
                            </div>
                        </div>
                    </div>
                
                </c:forEach>
            </div>
        </c:if>
        
        <%--<c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:choose>
            <c:when test="${not empty account}">
                <h3>
                    <font color="red"> Welcome ${account.name}</font>  
                </h3>
                <a href="home.jsp">   Back to Home Page</a>
            </c:when>
            
            <c:otherwise>
                <c:redirect url="login.html"/>
            </c:otherwise>
        </c:choose>
        
        <form action="DispatchController">
            <input type="submit" value="Logout" name="btnAction" />
        </form>

        <h1>View Post Page</h1>

        <form action="DispatchController" method="POST">
        <c:set var="article" value="${requestScope.ARTICLE}"/>
            <input type="hidden" name="txtArticleId" value="${article.articleId}"/> 

            <table border="0">
                <tr>
                    <th>
                        ${article.title}&ensp;
                        ${article.email}&ensp;
                        <input type="hidden" name="txtEmailId" value="${account.email}" />
                        <c:if test="${article.email eq account.email}">
                            <c:url var="urlRewritingArticle" value="DispatchController">
                                <c:param name="btnAction" value="Delete"/>
                                <c:param name="txtArticleId" value="${article.articleId}"/>
                            </c:url>
                            <a href="${urlRewritingArticle}" 
                               onclick="return confirm('Do you want to delete this post?');">
                                Delete 
                            </a>
                        </c:if>            
                    </th>
                </tr>
                
                <tr>
                    <td>${article.date}</td>
                </tr>
                <tr>
                    <td>${article.description}</td>
                </tr>
            </table>
                
                <c:if test="${not empty article.image}">
                    <img src="data:image/png;base64,${article.image}" height="60%" width="40%"/>
                </c:if>   <br/>

                <input type="submit" value="Like" name="btnAction" /> &ensp;${requestScope.COUNT_LIKE}&ensp;
                <input type="submit" value="Dislike" name="btnAction" />&ensp;${requestScope.COUNT_DISLIKE}
                    
        </form>
            <br/>
            
                    <!-- COMMENT AREA-->
            <br/>
            
                <c:set var="commentErr" value="${requestScope.COMMENT_ERROR}"/>
                <c:set var="listComment" value="${requestScope.LIST_COMMENT}"/>
                
        <form action="DispatchController">    
            <textarea class="description" name="txtComment" cols="40" rows="6"></textarea>  
            <input type="hidden" name="txtEmail" value="${account.email}"/>
            <input type="hidden" name="txtArticleId" value="${article.articleId}" />
            <br/>
            <c:if test="${not empty commentErr}">
                <font color="red">
                    ${commentErr}
                </font>
            </c:if>
            <br/>

            <input type="submit" value="Comment" name="btnAction" /> <br/>
            <br/>
        </form>

        <c:forEach var="list" items="${listComment}">
            <input type="hidden" name="txtCommentId" value="${list.commentId}" />
            <table border="0">
                <thead>
                    <tr>
                        <th>${list.email}</th>
                        <th>${list.curDate}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${list.comment}</td>
                    </tr>
                    <tr>
                        <td>                
                        <c:if test="${list.email eq account.email}">
                            <c:url var="urlRewringComment" value="DispatchController">
                                <c:param name="btnAction" value="Delete Comment"/>
                                <c:param name="txtCommentId" value="${list.commentId}"/>
                                <c:param name="txtArticleId" value="${list.articleId}"/>
                            </c:url>
                            <a href="${urlRewringComment}" 
                               onclick="return confirm('Do you want to delete this comment?');">
                                Delete comment
                            </a> <br/>
                        </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </c:forEach> --%>
    </body>
</html>
