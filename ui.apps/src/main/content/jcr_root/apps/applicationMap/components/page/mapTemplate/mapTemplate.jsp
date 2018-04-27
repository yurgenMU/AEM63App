<%@include file="/apps/applicationMap/globalLib/global.jsp" %>
<%--<cq:include script="/libs/wcm/core/components/init/init.jsp"/>--%>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>
<%@ page import="com.day.cq.wcm.workflow.api.WcmWorkflowService" %>

<%--<cq:includeClientLib categories="cq.widgets"/>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>--%>
<%@page import="java.util.Locale,java.util.ResourceBundle,com.day.cq.i18n.I18n" %>
<% final Locale pageLocale = currentPage.getLanguage(false);
    final ResourceBundle resourceBundle = slingRequest.getResourceBundle(pageLocale);
    I18n i18n = new I18n(resourceBundle); %>
<html>
<head>
    <cq:includeClientLib categories="LibsMap"/>
    <title><%= i18n.get("titleMap") %></title>
</head>
<body>

<div class="welcome">
    <img src="/etc/designs/AEM63App/spb.svg.png">
</div>
<div class="welcome">
    <h1><%= i18n.get("welcome") %>
    </h1>
</div>
<div class="pageDescription">
    <%= i18n.get("pageDescription") %>
    <fmt:message key="pageDescription"/>
</div>
<div id="map"></div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD6q5a9LbNg3n5kxvBr5PzFLaul17rnfuQ">
</script>
<script>
    var path = '${currentNode.path}';
    var map = initMap();
    var messages = ["<%= i18n.get("message0") %>", "<%= i18n.get("message1") %>"];
    <%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
    %>
    addClickListener(true, map, path, messages);
    getAllMarkers(true, map, path);

    <%} else {
        %>getAllMarkers(false, map, path)
    <%
        }%>

</script>
<%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
%>
<%@include file="modalForm.jsp" %>
<%}%>

<input type="search" id="accordion_search_bar" class="form-control" placeholder="" onkeyup="filterEvents()">
<div class="panel-group" id="accordion">
</div>

</body>
<footer class="container-fluid description-center" id="footer">
    <p><%= i18n.get("footerText") %></p>
</footer>



</html>