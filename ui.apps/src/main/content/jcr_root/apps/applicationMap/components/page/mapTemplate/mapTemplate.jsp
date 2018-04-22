<%@include file="/apps/applicationMap/globalLib/global.jsp" %>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>
<%@ page import="com.day.cq.wcm.workflow.api.WcmWorkflowService" %>
<cq:includeClientLib categories="myLibs"/>
<html>
<head>
    <style>
        #map {
            margin-left:auto; margin-right:0;
            height: 400px;
            width: 40%;
            padding: 30px;
        }
    </style>
</head>
<body>
<%--<c:set var="${properties.name}" value="${2000*2}"/>--%>

<sling:adaptTo adaptable="${resource}" adaptTo="ru.macsyom.models.Marker" var="model"/>
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
                    paintMarker(position, map, json[i].path);

                }

            }
        });
        // });
    }


    var contentString = '<div id="content">' +
        '<div id="siteNotice">' +
        '</div>' +
        '<h1 id="firstHeading" class="firstHeading">Uluru</h1>' +
        '<div id="bodyContent">' +
        '<p><b>Uluru</b>, also referred to as <b>Ayers Rock</b>, is a large ' +
        'sandstone rock formation in the southern part of the ' +
        'Northern Territory, central Australia. It lies 335&#160;km (208&#160;mi) ' +
        'south west of the nearest large town, Alice Springs; 450&#160;km ' +
        '(280&#160;mi) by road. Kata Tjuta and Uluru are the two major ' +
        'features of the Uluru - Kata Tjuta National Park. Uluru is ' +
        'sacred to the Pitjantjatjara and Yankunytjatjara, the ' +
        'Aboriginal people of the area. It has many springs, waterholes, ' +
        'rock caves and ancient paintings. Uluru is listed as a World ' +
        'Heritage Site.</p>' +
        '<p>Attribution: Uluru, <a href="https://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">' +
        'https://en.wikipedia.org/w/index.php?title=Uluru</a> ' +
        '(last visited June 22, 2009).</p>' +
        '</div>' +
        '</div>';

    var infowindow;

    function paintMarker(position, map, id) {
        var marker = new google.maps.Marker({
            position: position,
            draggable: true,
            animation: google.maps.Animation.DROP,
            map: map
        });
        infowindow = new google.maps.InfoWindow({
            content: '<div contentEditable="true" id="asdasd">changeme...</div>'
        });
        marker.setValues({id: id});
        google.maps.event.addListener(marker, "dblclick", function (event) {
            var lat = marker.getPosition().lat();
            var lng = marker.getPosition().lng();
            deleteMarker(lat, lng, map);
            marker.setMap(null);
        });
        google.maps.event.addListener(marker, "dragend", function (event) {
            var lat = marker.getPosition().lat();
            var lng = marker.getPosition().lng();
            // marker.setPosition(new google.maps.LatLng(lat, lng));
            editMarker(lat, lng, map, marker.get("id"));
        });
        google.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, marker);
        });

        return marker;
    }


    function toggleBounce() {
        if (marker.getAnimation() !== null) {
            marker.setAnimation(null);
        } else {
            marker.setAnimation(google.maps.Animation.BOUNCE);
        }

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
                var json = JSON.parse(str);
                marker.setValues({id: json.id});
            }
        });
        paintMarker(position, map)


    }

    function editMarker(lat, lng, map, id) {
        $.ajax({
            type: "POST",
            url: "/bin/editMarkerServlet",
            data: {
                lat: lat,
                lng: lng,
                path: id,
            },

            success: function (str) {
                var json = JSON.parse(str);
                // marker.setValues({id: json.id});
            }
        });
        // paintMarker(position, map)


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