function initMap(path) {
    var spb = {lat: 59.937350395733205, lng: 30.33682215546594};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: spb

    });

    return map;

}


function getAllMarkers(flag, map, path) {
    $.ajax({
        type: "GET",
        url: "/bin/allEventsServlet?parent=" + path,
        success: function (str) {
            var json = JSON.parse(str);
            for (var i in json) {
                var position = {lat: parseFloat(json[i].latitude), lng: parseFloat(json[i].longitude)};
                var props = [json[i].description, json[i].text];
                paintMarker(flag, position, props, map, json[i].path);

            }


        }
    });
}

function paintMarker(flag, position, props, map, id) {
    var marker;
    if (flag == true) {
        marker = paintAuthorMarker(position, props,map, id);
    } else {
        marker = paintUserMarker(position, props, map, id);
    }
    return marker;
}




function paintUserMarker(position, props, map, id) {
    var marker = new google.maps.Marker({
        position: position,
        animation: google.maps.Animation.DROP,
        map: map

    });
    infowindow = new google.maps.InfoWindow({
        content: '<div contentEditable="true" id="asdasd"></div>'
    });
    marker.setValues({id: id});
    marker.setValues({description: props[0]});
    marker.setValues({text: props[1]});
    google.maps.event.addListener(marker, 'mouseover', function () {
        infowindow.setContent(marker.get("description"));
        infowindow.open(map, marker);
    });
    google.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close(map, marker);
    });

    return marker;
}
