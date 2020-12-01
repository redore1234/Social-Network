<%-- 
    Document   : post
    Created on : Sep 22, 2020, 1:08:42 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <title>View Post</title>
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
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
                            <c:url var="urlRewringArticle" value="DispatchController">
                                <c:param name="btnAction" value="Delete"/>
                                <c:param name="txtArticleId" value="${article.articleId}"/>
                            </c:url>
                            <a href="${urlRewringArticle}" 
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
        </c:forEach>
    </body>
</html>
