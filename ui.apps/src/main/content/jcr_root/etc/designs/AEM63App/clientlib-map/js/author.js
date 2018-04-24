function addMarker(flag, position, descr, text, map, parent) {
    var marker;
    $.ajax({
        type: "POST",
        url: "/bin/eventServlet",
        data: {
            lat: position.lat,
            lng: position.lng,
            descr: descr,
            text: text,
            parent: parent
        },
        success: function (str) {
            var json = JSON.parse(str);
            var props = [json.description, json.text];
            marker = paintMarker(flag, position, props, map, json.path);
        }

    });
}


function editMarker(lat, lng, descr, text, map, id) {
    $.ajax({
        type: "POST",
        url: "/bin/editEventServlet",
        data: {
            lat: lat,
            lng: lng,
            path: id,
            descr: descr,
            text: text
        },
        success: function (str) {
            var json = JSON.parse(str);
        }
    });


}


function deleteMarker(lat, lng, path) {
    console.log(lat);
    $.ajax({
        type: "DELETE",
        url: "/bin/eventServlet?lat=" + lat + "&lng=" + lng + "&parent=" + path,
        success: function (str) {
        }
    });


}

function addClickListener(flag, map, parent) {
    google.maps.event.addListener(map, "click", function (event) {
        $('#myModal').modal('show');
        $('#myModal').on('hidden.bs.modal', function () {
            $("#description").val("");
            $("#eventText").val("");
        });
        document.getElementById('saveButton').onclick = function (ev) {
            var lat = event.latLng.lat();
            var lon = event.latLng.lng();
            var position = {lat: lat, lng: lon};
            var descr = $("#description").val();
            var text = $("#eventText").val();
            var marker = addMarker(flag, position, descr, text, map, parent);
            document.getElementById('description').innerHTML="";
            document.getElementById('eventText').innerHTML="";
        }
    });

}

function deleteButtonListener(path) {
    var lat = $("#currentLatitude").text();
    var lng = $("#currentLongitude").text();
    deleteMarker(lat, lng, path);


}


function paintAuthorMarker(position, props, map, id) {
    var marker = new google.maps.Marker({
        position: position,
        draggable: true,
        animation: google.maps.Animation.DROP,
        map: map
    });
    infowindow = new google.maps.InfoWindow({
        content: '<div contentEditable="true"></div>'
    });

    marker.setValues({id: id});
    marker.setValues({description: props[0]});
    marker.setValues({text: props[1]});
    var lat;
    var lng;
    google.maps.event.addListener(marker, "click", function (event) {
        lat = marker.getPosition().lat();
        lng = marker.getPosition().lng();
        var path = $('#path').val();
        $('#myModal').modal('show');
        $('#description').val(marker.get("description"));
        $("#eventText").val(marker.get("text"));
        $("#deleteButton").on('click', function () {
            deleteMarker(lat, lng, path);
            marker.setMap(null);
        });
        document.getElementById('saveButton').onclick = function (ev) {
            var position = {lat: lat, lng: lng};
            var descr = $("#description").val();
            var text = $("#eventText").val();
            marker.setValues({description: descr});
            marker.setValues({text: text});
            infowindow.setContent(marker.get("description"));
            editMarker(lat, lng, descr, text, map, marker.get("id"));
            document.getElementById('description').innerHTML="";
            document.getElementById('eventText').innerHTML="";

        }
    });
    google.maps.event.addListener(marker, "dragend", function (event) {
        lat = marker.getPosition().lat();
        lng = marker.getPosition().lng();
        var text = null;
        var descr = null;
        editMarker(lat, lng, descr, text, map, marker.get("id"));
        document.getElementById('description').innerHTML="";
        document.getElementById('eventText').innerHTML="";
    });
    google.maps.event.addListener(marker, 'mouseover', function () {
        infowindow.setContent(marker.get("description"));
        infowindow.open(map, marker);
    });
    google.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close(map, marker);
    });

    return marker;
}
