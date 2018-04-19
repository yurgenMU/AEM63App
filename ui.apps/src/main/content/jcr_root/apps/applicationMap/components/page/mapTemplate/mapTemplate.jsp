<%@include file="/apps/applicationMap/globalLib/global.jsp" %>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>
<%@ page import="com.day.cq.wcm.workflow.api.WcmWorkflowService" %>
<cq:includeClientLib categories="myLibs"/>
<html>
<head>
    <style>
        #map {
            height: 400px;
            width: 80%;
            padding: 30px;
        }
    </style>
</head>
<body>
<%--<c:set var="${properties.name}" value="${2000*2}"/>--%>

<sling:adaptTo adaptable="${resource}" adaptTo="ru.macsyom.models.RSSModel" var="model"/>
<%--<c:set target="${model}" property="name" value="value"/>--%>
<h3>My Google Maps Demo</h3>
<div id="map"></div>

<script>
    function initMap() {
        var uluru = {lat: 51.195690, lng: 30.999396};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 4,
            center: uluru

        });
        // var marker = new google.maps.Marker({
        //     position: uluru,
        //     map: map
        // });
        // var marker = addMarker(uluru, map);
        <%
            if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
        %>
        google.maps.event.addListener(map, "click", function (event) {
            var latitude = event.latLng.lat();
            var longitude = event.latLng.lng();
            var position = {lat: latitude, lng: longitude};
            var newMarker = addMarker(position, map);

        });

        getAllMarkers(map);

        <%
        }
        %>
    }
</script>


<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD6q5a9LbNg3n5kxvBr5PzFLaul17rnfuQ&callback=initMap">
</script>


<script type="text/javascript">


    function getMap(lat, lng) {

        var myOptions = {
            center: new google.maps.LatLng(lat, lng),
            zoom: 8,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map"),
            myOptions);
        return map;
    }


    function getAllMarkers(map) {
        // $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "/bin/allMarkersServlet?parent=" + '${currentNode.path}',
            success: function (str) {
                var json = JSON.parse(str);
                for (var i in json) {
                    var position = {lat: parseFloat(json[i].latitude), lng: parseFloat(json[i].longitude)};
                    console.log(position);

                    paintMarker(position, map);

                }

            }
        });
        // });
    }


    function paintMarker(position, map) {
        var marker = new google.maps.Marker({
            position: position,
            map: map
        });
        google.maps.event.addListener(marker, "dblclick", function (event) {
            var lat = marker.getPosition().lat();
            var lng = marker.getPosition().lng();
            deleteMarker(lat,lng, map);
            console.log("HUI");
            marker.setMap(null);
        });
        return marker;
    }


    function addMarker(position, map) {
        $.ajax({
            type: "POST",
            url: "/bin/markerServlet",
            data: {
                lat: position.lat,
                lng: position.lng,
                parent: '${currentNode.path}'
            },

            success: function (str) {
                // var json = JSON.parse(str);
                // for (var i in json) {
                //     position
                // }
            }
        });
        paintMarker(position, map)


    }

    function deleteMarker(lat, lng, map) {
        console.log(lat);
        $.ajax({
            type: "DELETE",
            url: "/bin/markerServlet?lat=" + lat + "&lng=" + lng + "&parent=" + '${currentNode.path}',

            success: function (str) {
                // var json = JSON.parse(str);
                // for (var i in json) {
                //     position
                // }
            }
        });
        // paintMarker(position, map)


    }


    <%--$("body").bind("ajaxSend", function(elm, xhr, s){--%>
    <%--if (s.type == "POST") {--%>
    <%--xhr.setRequestHeader('X-CSRF-Token', getCSRFTokenValue());--%>
    <%--}--%>
    <%--});--%>


</script>


<form>
    Latitude: <input type="text" name="lat" value="37.3041"/>
    Longitude: <input type="text" name="lng" value="-121.8727"/>
    <button type="button" onclick="getMap(lat.value,lng.value)">Click Me!</button>

    <h3>${model.name}</h3>
</form>
</body>


<%--<cq:include path="par" resourceType="foundation/components/parsys"/>--%>
</html>