<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>JSP Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="resources/start.js"></script>
        <link rel="stylesheet" type="text/css" href="resources/mycss.css">
        
    </head>
    <body>
        <div class="world">
            
            <div class="sun"></div>
            <div class="cloud1" style="top:0;width: 1500px"></div>
            <div class="cloud" style="width: 500px"></div>
            <div class="cloud" style="left:700px;width: 500px"></div>
            <div class="panel">
                <p id="test" class="test" style="position: absolute;left:250px;top:222px;font-size: 1.5em">check</p>
                <p>масса груза
                <input id="mas_gruz"type="range" min="0" max="100" step="1" value="50"></p> 
                <p>масса топлива
                    <input id="mas_full"type="range" min="0" max="100" step="1" value="50"></p>
                <p>масса ракеты
                    <input id="mas_rocet" type="range" min="0" max="100" step="1" value="50"></p>
                <br> <button  id="btn">start</button>
            </div>           
            <div class ="hause"></div>
            <div id="d1"class="brevno"style="left:650px"></div>
            <div id="d2"class="brevno"style="left:900px"></div>
            <div id="d3"class="brevno"style="left:1150px"></div>
            <div id="d4"class="raceta"></div>
            <div class="trava"></div>
            
        </div>
    </body>
</html>
