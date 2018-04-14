<%@include file="/libs/foundation/global.jsp"%>
<%--<%@page session="false" %>--%>
<%@page import="ru.macsyom.models.RSSModel" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<cq:includeClientLib categories="rssLibs" />
<html>


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


<c:set var="model" value="<%= resource.adaptTo(RSSModel.class)%>"/>
<div class="result" id="${currentNode.identifier}">
    <%@include file="data.jsp" %>
    <h3>${model.name}</h3>



</div>
</html>