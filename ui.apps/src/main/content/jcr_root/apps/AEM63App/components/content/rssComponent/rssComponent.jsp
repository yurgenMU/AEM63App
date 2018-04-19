<%@include file="/apps/applicationMap/globalLib/global.jsp"%>
<%--<%@page session="false" %>--%>
<%--<%@page import="ru.macsyom.models.RSSModel" %>--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%--<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling" %>--%>
<cq:includeClientLib categories="myLibs" />




<c:choose>
    <c:when test="${empty properties.name && empty properties.address}">
        Please enter name and url
    </c:when>

    <c:when test="${empty properties.name}">
        Please enter name
    </c:when>

    <c:when test="${empty properties.address}">
        Please enter address
    </c:when>



</c:choose>


<sling:adaptTo adaptable="${resource}" adaptTo="ru.macsyom.models.RSSModel" var="model"/>

<%--<c:set var="model" value="<%= resource.adaptTo(RSSModel.class)%>"/>--%>
<div class="result" id="${currentNode.path}">
    <%@include file="data.jsp" %>
    <h3>${model.name}</h3>
    <div><img src='${properties.fileReference}' /></div>



</div>
