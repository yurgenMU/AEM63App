<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="jqueryrss" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="content-language" content="en" />
    <title>FeedEk jQuery RSS/ATOM Feed Plugin Demo | jQuery RSS/ATOM Parser Plugin FeedEk Demo</title>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#divRss').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 3
            });

            $('#divRss2').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 4,
                ShowDesc: true,
                ShowPubDate: false,
                DescCharacterLimit: 100
            });

            $('#divRss3').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 5,
                ShowDesc: false
            });

            $('#divRss4').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'L',
                DateFormatLang: 'en'
            });

            $('#divRss5').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'L',
                DateFormatLang: 'tr'
            });

            $('#divRss6').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'L',
                DateFormatLang: 'en-gb'
            });

            $('#divRss7').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'LL',
                DateFormatLang: 'en'
            });

            $('#divRss8').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'LLL',
                DateFormatLang: 'en'
            });

            $('#divRss9').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'll',
                DateFormatLang: 'en'
            });
            $('#divRss10').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'lll',
                DateFormatLang: 'en'
            });

            $('#divRss11').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'DD MMMM YYYY',
                DateFormatLang: 'en'
            });
            $('#divRss12').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'MM/DD/YYYY'
            });

            $('#divRss13').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'MM/DD/YYYY HH:mm'
            });

            $('#divRss14').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'MM-DD-YYYY'
            });

            $('#divRss15').FeedEk({
                FeedUrl: 'http://jquery-plugins.net/rss',
                MaxCount: 2,
                DateFormat: 'MM-DD-YYYY HH:mm'
            });
        });

    </script>
    <style>
        body{font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;background-color: #efefef;font-size: 13px;line-height: 17px !important;}
        .rssDiv{float: left;padding-right: 35px;}
        ul{width: 300px !important;}
    </style>
</head>
<body>
<div style="padding:10px;">
    <h1>AEM RSS Example</h1>
    <div>
        <div class="rssDiv">
            <p>
                <strong>RSS Data</strong>
            <p />
            <div id="divRss"></div>

        </div>

</body>

</html>