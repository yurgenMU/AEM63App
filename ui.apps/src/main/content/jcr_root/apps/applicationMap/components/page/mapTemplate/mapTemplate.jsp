<%@include file="/apps/applicationMap/globalLib/global.jsp" %>
<cq:include script="/libs/wcm/core/components/init/init.jsp"/>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>
<%@ page import="com.day.cq.wcm.workflow.api.WcmWorkflowService" %>
<cq:includeClientLib categories="mapTaskLibs"/>
<cq:includeClientLib categories="cq.widgets"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Locale,java.util.ResourceBundle,com.day.cq.i18n.I18n" %>
<% final Locale pageLocale = currentPage.getLanguage(false);
    final ResourceBundle resourceBundle = slingRequest.getResourceBundle(pageLocale);
    I18n i18n = new I18n(resourceBundle); %>
<html>
<head>
    <style>
        /*body {*/
        /*background: #29AB87;*/
        /*background-image: url("");*/
        /*}*/
        #map {
            /*margin-left:auto; margin-right:0;*/
            height: 500px;
            width: 80%;
            margin: 50px;
        }

        #accordion {
            padding: 50px;
        }

        #welcome {
            padding: 50px;
        }

        #accordion_search_bar {
            background-image: url('/etc/designs/AEM63App/search.png');
            background-position: 10px 12px;
            background-repeat: no-repeat;
            width: 80%;
            font-size: 16px;
            padding: 12px 20px 12px 40px;
            border: 1px solid #ddd;
            margin-bottom: 12px;
            margin-left: 50px;
        }

    </style>
</head>
<body>


<div id="welcome">
    <h1><%= i18n.get("welcome") %>
    </h1>
</div>

<div id="map"></div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD6q5a9LbNg3n5kxvBr5PzFLaul17rnfuQ">
</script>
<script>
    var path = '${currentNode.path}';
    var map = initMap(path);


    <%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
    %>
    addClickListener(true, map, path);
    getAllMarkers(true, map, path);
    <%} else {
        %>getAllMarkers(false, map, path)
    <%
        }%>

</script>
<%--<input type="hidden" id="currentLatitude">--%>
<%--<input type="hidden" id="currentLongitude">--%>
<%--<input type="hidden" value="${currentNode.path}" id="path">--%>

<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">
                    <%= i18n.get("parameters") %>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <%= i18n.get("description") %>
                    <input type="textfield" id="description" style="min-width: 100%" id="descr"></form>
                    <br>
                    <%= i18n.get("text") %>
                    <textarea class="form-control" style="min-height: 50%" id="eventText"></textarea>
                </div>

                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true"><%= i18n.get("close") %>
                    </button>
                    <button class="btn btn-primary" data-dismiss="modal" type="submit"
                            id="saveButton"><%= i18n.get("save") %>
                    </button>
                    <button class="btn btn-danger" data-dismiss="modal" type="submit"
                            id="deleteButton"><%= i18n.get("delete") %>
                    </button>
                </div>

            </div>

        </div>
    </div>
</div>
<input type="search" id="accordion_search_bar" class="form-control" placeholder="" onkeyup="myFunction()">
<div class="panel-group" id="accordion">
</div>



<script>
    $('.collapse').not(':first').collapse();
    function myFunction () {
        var input, filter, ul, li, a, i;
        input = document.getElementById("accordion_search_bar");
        filter = input.value.toUpperCase();
        var name = document.getElementsByClassName("panel panel-title");
        console.log($(".panel").html());
        for (i = 0; i < name.length; i++) {
            a = name[i].getElementsByTagName("a")[0];
            if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                name[i].style.display = "";
            } else {
                name[i].style.display = "none";

            }
        }
    }
     // Collapse all but the first row on the page.

    // This section makes the search work.

</script>

<script>
    funtion(){

    }
</script>

</body>
<footer class="container-fluid text-center">
    <p>Footer Text</p>
</footer>



</html>