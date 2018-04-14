<%@include file="/libs/foundation/global.jsp" %>


<script>
    $(document).ready(function () {
        var param = "${currentNode.identifier}";
        var div = document.getElementById(param);
        var splitParam = param.split(/[\s/]+/);
        var postf = splitParam[splitParam.length - 1]
        $.ajax({
            type: "GET",
            url: "/bin/getRSSData?address=" + param,
            success: function (str) {
                var json = JSON.parse(str);
                for (var i in json) {
                    var title = json[i].title;
                    var descr = json[i].description;
                    var link = json[i].link;
                    var author = json[i].author;
                    var guid = json[i].guid;
                    var pubDate = json[i].pubDate
                    $(div).append("<button type=\"button\" class=\"btn btn-info btn-sm\" id=\""+postf+i+"\" " +
                        "data-toggle=\"collapse\" data-target=\"#demo" + postf + i + "\">+</button>" +
                        "<a href=\"" + link + "\">" +
                        "<strong>" + title + "</strong><\a> " + pubDate + "<br>" +
                        "<div id=\"demo" + postf + i + "\" class=\"collapse\">" + descr +
                        "  </div>");
                    $("#" + postf + i).click(function(){
                        $(this).text(function(i,old){
                            return old=='+' ?  '-' : '+';
                        });
                    });

                }

            }
        });
    });


</script>


