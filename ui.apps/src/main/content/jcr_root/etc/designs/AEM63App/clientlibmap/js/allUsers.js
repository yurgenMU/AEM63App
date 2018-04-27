/**
 * Script with functions being used by all the users independently from WCM mode
 */

/**
 * function initializing the Google Map with given coordinates
 * @returns {google.maps.Map}
 */
function initMap() {
    var spb = {lat: 59.937350395733205, lng: 30.33682215546594};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: spb
    });
    return map;

}

/**
 * function getting all events from JCR and painting it on the Map and under the Map
 * @param flag - boolean variable defining functionality of the marker depending on WCM mode (author/publish)
 * @param map - Google Map instance
 * @param path - page path in JCR
 */
function getAllMarkers(flag, map, path) {
    $.ajax({
        type: "GET",
        url: "/bin/allEventsServlet?parent=" + path,
        success: function (str) {
            var json = JSON.parse(str);
            for (var i in json) {
                if (!(typeof json[i] === "function")) {
                    var position = {lat: parseFloat(json[i].latitude), lng: parseFloat(json[i].longitude)};
                    var props = [json[i].name, json[i].name];
                    paintMarker(flag, position, props, map, json[i].path);
                    paintBlock(json[i].name, json[i].name, json[i].path);
                }
            }
        }
    });
}


/**
 *Function creating identifiers for blocks in accordion
 * @param param - path of the current Event in JCR
 * @returns {string} last substring after '/' character
 */
function createId(param) {
    var splitParam = param.split(/[\s/]+/);
    var postf = splitParam[splitParam.length - 1];
    return postf;
}

/**
 * Function painting blocks with name and name of the event
 * @param name - Event name
 * @param description - event's name
 * @param path - event path in JCR
 */
function paintBlock(name, description, path) {
    var id = createId(path);
    $('#accordion').append("<div class=\"panel panel-default\">\n" +
        "<div id=\"heading_" + id + "\" class=\"panel panel-heading\">\n" +
        "<h3 id=\"title" + id + "\" class=\"panel panel-title\">\n" +
        " <a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#" + id + "\">\n<img src=\"/etc/designs/AEM63App/logo.jpg\">"
        + " " + name + "</a>\n" +
        "</h3>" +
        "</div>" +
        "<div id=\"" + id + "\" class=\"panel-collapse collapse in\">\n" +
        "<div class=\"panel panel-body\">" + description + "</div>\n" +
        "</div>\n" +
        "</div>");
}


/**
 * Function painting markers with functionality depending on WCM mode
 * @param flag - boolean variable describing VCM mode
 * @param position - marker's position
 * @param props - name and name of the event
 * @param map - Google Maps instance
 * @param id - event's id
 * @returns {google.maps.Marker}
 */
function paintMarker(flag, position, props, map, id) {
    var marker = new google.maps.Marker({
        position: position,
        draggable: flag,
        animation: google.maps.Animation.DROP,
        map: map
    });

    marker.setValues({id: id});
    marker.setValues({name: props[0]});
    marker.setValues({description: props[1]});
    if (flag == true) {
        google.maps.event.addListener(marker, "click", function (event) {
            var lat = marker.getPosition().lat();
            var lng = marker.getPosition().lng();
            $('#myModal').modal('show');
            $('#name').val(marker.get("name"));
            $("#eventText").val(marker.get("description"));
            $("#deleteButton").on('click', function () {
                deleteMarker(marker.get("id"));
                marker.setMap(null);
            });
            document.getElementById('saveButton').onclick = function (ev) {
                var name = $("#name").val();
                var description = $("#eventText").val();
                if (name.trim() == "") {
                    alert(messages[0]);
                } else if (description.trim() == "") {
                    alert(messages[1]);
                } else {
                    marker.setValues({name: name});
                    marker.setValues({description: description});
                    infowindow.setContent(marker.get("name"));
                    editMarker(lat, lng, name, description, marker.get("id"));
                    document.getElementById('name').innerHTML = "";
                    document.getElementById('eventText').innerHTML = "";
                }

            }
        });

        google.maps.event.addListener(marker, "dragend", function (event) {
            var lat = marker.getPosition().lat();
            var lng = marker.getPosition().lng();
            var text = null;
            var descr = null;
            editMarker(lat, lng, descr, text, marker.get("id"));
            document.getElementById('name').innerHTML = "";
            document.getElementById('eventText').innerHTML = "";
        });
    } else {
        google.maps.event.addListener(marker, "click", function (event) {
            $('#accordion_search_bar').val(marker.get("name"));
            filterEvents();
            $('html, body').animate({
                scrollTop: $('#accordion').offset().top
            }, 2000);
        });
    }
    var infowindow = new google.maps.InfoWindow({
        content: '<div contentEditable="true"></div>'
    });
    google.maps.event.addListener(marker, 'mouseover', function () {
        infowindow.setContent(marker.get("name"));
        infowindow.open(map, marker);
    });
    google.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close(map, marker);
    });

    return marker;
}

/**
 * Function filtering events on the page
 */
function filterEvents() {
    var input, filter, a, i;
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

