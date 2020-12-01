<%-- 
    Document   : search
    Created on : Sep 16, 2020, 8:37:26 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <title>Search</title>
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
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
    </body>
</html>
