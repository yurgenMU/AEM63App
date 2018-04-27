/**
 * Script with functions being used by all the users
 */


/**
 *Function adding marker(event) to JCR
 * @param flag - boolean variable defining WCM mode
 * @param position - marker's position
 * @param name
 * @param description
 * @param map
 * @param parent
 */

function addMarker(flag, position, name, description, map, parent) {
    var marker;
    $.ajax({
        type: "POST",
        url: "/bin/eventServlet",
        data: {
            lat: position.lat,
            lng: position.lng,
            name: name,
            description: description,
            parent: parent
        },
        success: function (str) {
            var json = JSON.parse(str);
            var props = [json.name, json.name];
            marker = paintMarker(flag, position, props, map, json.path);
            paintBlock(name, description, json.path);
        }

    });
}

/**
 *Function editing event's state in JCR and repainting it on the page
 * @param lat - new latitude of the event
 * @param lng - new longitude of the event
 * @param name - new name of the event
 * @param description - new name of the event
 * @param id - event id
 */
function editMarker(lat, lng, name, description, id) {
    $.ajax({
        type: "POST",
        url: "/bin/editEventServlet",
        data: {
            lat: lat,
            lng: lng,
            path: id,
            name: name,
            description: description
        },
        success: function (str) {
            var json = JSON.parse(str);
            var id = createId(json.path);
            $('#' + 'heading_' + id).html("<div class=\"panel panel-default\">\n" +
                "<div id=\"heading_" + id + "\" class=\"panel panel-heading\">\n" +
                "<h3 class=\"panel panel-title\">\n" +
                "<a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#" + id + "\">\n<img src=\"/etc/designs/AEM63App/logo.jpg\">"

                + " " + json.name + "</a>\n" +
                "</h3>\n" +
                "</div>\n");
            $('#' + id).html(json.name);

        }
    });


}

/**
 * function deleting event from JCR
 * @param path event's path
 */
function deleteMarker(path) {
    $.ajax({
        type: "DELETE",
        url: "/bin/eventServlet?path=" + path,
        success: function (str) {
            deleteBlock(str);
        }
    });


}

/**
 * function for creating modal window for new event
 * @param flag - WCM mode
 * @param map - Google MAp instance
 * @param parent - path of the parent Node in JCR
 */
function addClickListener(flag, map, parent, messages) {
    google.maps.event.addListener(map, "click", function (event) {
        $('#myModal').modal('show');
        $('#myModal').on('hidden.bs.modal', function () {
            $("#name").val("");
            $("#eventText").val("");
        });
        document.getElementById('saveButton').onclick = function (ev) {
            var lat = event.latLng.lat();
            var lon = event.latLng.lng();
            var position = {lat: lat, lng: lon};
            var name = $("#name").val();
            var description = $("#eventText").val();
            if (name.trim() == "") {
                alert(messages[0]);
            } else if (description.trim() == "") {
                alert(messages[1]);
            } else {
                var marker = addMarker(flag, position, name, description, map, parent);
                document.getElementById('name').innerHTML = "";
                document.getElementById('eventText').innerHTML = "";
            }

        }
    });

}


/**
 * function deleting block connected with an event from the page
 * @param path - event path
 */
function deleteBlock(path) {
    var id = createId(path);
    $('#' + 'heading_' + id).remove();
    $('#' + id).remove();


}

