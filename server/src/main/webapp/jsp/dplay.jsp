<%-- 
Document   : index
Created on : Jan 2, 2011, 7:37:30 AM
Author     : Jagadeesh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>dPlay</title>
        <!-- You have to include these two JavaScript files from DWR -->
        <script type='text/javascript' src='../dwr/engine.js'></script>
        <script type='text/javascript' src='../dwr/util.js'></script>
        <!-- This JavaScript file is generated specifically for your application -->
        <!-- in dwr.xml we have converted MyJavaClass to MyJavaScriptClass.js-->
        <!-- so we can call java class methods using this javascript class-->
        <script type='text/javascript' src='../dwr/interface/MyJavaScriptClass.js'>
        </script>

        <style>
            
            ::-webkit-scrollbar{width:9px;height:9px;}
            ::-webkit-scrollbar-button:start:decrement,::-webkit-scrollbar-button:end:increment{display:block;height:0;background-color:transparent;}
            ::-webkit-scrollbar-track-piece{background-color:#FAFAFA;-webkit-border-radius:0;-webkit-border-bottom-right-radius:8px;-webkit-border-bottom-left-radius:8px;}
            ::-webkit-scrollbar-thumb:vertical{height:50px;background-color:#999;-webkit-border-radius:8px;}
            ::-webkit-scrollbar-thumb:horizontal{width:50px;background-color:#999;-webkit-border-radius:8px;}

            
            table{
                border-width: 0 0 1px 0px;
                border-spacing: 0;
                border-color:lightgrey;
                font-size:14;

            }

            

            td{
                margin: 0;
                padding: 4px;
                border-width: 1px 1px 0 1px;
                background-color: white;
                text-align:center;
            }​

            body {
                margin:0;
                padding:0;
                height:100%;
            }
            #container {
                min-height:100%;
                position:relative;
            }
            #header {
                background:black;
                padding:10px;
            }
            #body {
                padding:10px;
                padding-bottom:60px;   /* Height of the footer */
            }
            #footer {
                float:left;
                bottom:0;
                width:100%;
                height:60px;   /* Height of the footer */
                background:black;
            }

            tr:nth-child(even) {
                background-color: #F8F8F8;
            }


            tr:nth-child(odd) {
                background-color: white;
            }

            .centrecl
            {
                width: 200px;
                display: block;
                margin-left: auto;
                margin-right: auto;
            }

            .maindiv{
                height:600px;
                font-family:"Times New Roman", Times, serif
            }

            .top{
                font-family:"Times New Roman", Times, serif;
                height:50px;
                width:100%;
                background-color:black;
                color:white;
                text-align:center;
                line-height: 50px;
                margin:0;
            }

            .footer{
                font-family:"Times New Roman", Times, serif;
                height:25px;
                width:100%;
                background-color:black;
                color:white;
                font-size:14px;
                text-align:center;
                line-height: 25px;
                margin:0;
            }

            .search{
                float:left;
                width: 300px;
                font-size:12px;
                margin: 10px;
                border-right: 1px solid #666;
            }

            .playlist{
                float: left;
                width: 350px;
                margin: 10px;
                font-size:12px;
            }

            tableclass{
                font-family:"Times New Roman", Times, serif;
                font-size:12px;
            }

            .tablediv{
                height:300px;
                overflow-x: hidden;
                overflow-y:auto;
            }



        </style>

        <script>

            function addRow(tableID, artist, track, votes, id, add) {



                var table = document.getElementById(tableID);



                var rowCount = table.rows.length;
                var row = table.insertRow(rowCount);



                var cell2 = row.insertCell(0);
                cell2.innerHTML = artist;

                var cell3 = row.insertCell(1);
                cell3.innerHTML = track;

                var cell4 = row.insertCell(2);
                cell4.innerHTML = votes;

                var cell5 = row.insertCell(3);

                var oImg = document.createElement("img");
                if (add == 1) {
                    oImg.setAttribute('src', '../img/plus.png');
                    oImg.setAttribute('onClick', 'addtrack(this)');
                    oImg.setAttribute('height', '20');
                    oImg.setAttribute('width', '20');
                } else {
                    oImg.setAttribute('src', '../img/vote_1.png');
                    oImg.setAttribute('onClick', 'vote(this)');
                }


                oImg.setAttribute('alt', id);
                cell5.appendChild(oImg);
                /*
                 var a = document.createElement('a');
                 a.href =  "#";
                 a.innerHTML = "Link"; 
                 a.setAttribute(setAttribute('onclick','vote()'));
                 cell4.appendChild(a);
                 */
            }

            function vote(cell)
            {
                var id = cell.getAttribute('alt');
                MyJavaScriptClass.vote(id, handlevote);
                //setTimeout(function(){reloadPlaylistsilent()},1000)
                
            }
             
             function handlevote()
            {
                reloadPlaylistsilent();
            }


            function addtrack(cell)
            {
                var id = cell.getAttribute('alt');
                MyJavaScriptClass.addTrack(id);
                reloadPlaylistsilent();
            }

            function getTrack()
            {
                //calling serverside method using our generated
                //javascript class.if you do my first example
                //then you can understand this example very easy
                MyJavaScriptClass.getTrack(handletrack);
            }
            function handletrack(obj) {
                DWRUtil.setValue("trackid", obj.id);
                DWRUtil.setValue("trackname", obj.title);
            }
            function getDetailsFromServer()
            {
        //calling serverside method using our generated
        //javascript class.if you do my first example
        //then you can understand this example very easy
                MyJavaScriptClass.getDetails(handleReceivedData);
            }
            function handleReceivedData(obj)
            {
        //DWRUtil.setValue and DWRUtil.getValue are the existing
        //properties in engine.js, setValue sets the value to that
        //particular id(including div,span,forms etc),DWRUtil.getValue
        //get the value of that particular id.
                DWRUtil.setValue("firstname", obj.firstName);
                DWRUtil.setValue("lastname", obj.lastName);
        //we got bean object and we converted that bean object to
        //javascript object. so we have to call those bean properties
        //using object.propertyname
            }

            function search()
            {
                var query = DWRUtil.getValue("searchfield");
                MyJavaScriptClass.search(query, handlesearch);
            }

            function getPlaylist(str)
            {

                MyJavaScriptClass.getPlaylist(handleplaylist);
            }

            function reloadPlaylist(str)
            {
                MyJavaScriptClass.getPlaylist(handleplaylist);
            }
            
            function reloadPlaylistsilent()
            {
                MyJavaScriptClass.getPlaylist(handleplaylist);
            }
            

            function handlesearch(obj) {
                handletracks(obj, 'searchtable', 1);
            }

            function handleplaylist(obj) {
                handletracks(obj, 'dataTable', 0);
            }

            function handletracks(obj, tableid, add) {
                playing = obj.playing;
                if (playing != null) {
                   document.getElementById('nowPlayingTitle').textContent = obj.playing.title;
                   document.getElementById('nowPlayingArtist').textContent = obj.playing.artist;
                }
                var myStringArray = obj.tracks;

                //Delete old rows except header
                var table = document.getElementById(tableid);

                for (var i = table.rows.length - 1; i > 0; i--)
                {
                    table.deleteRow(i);
                }

                // Add new rows
                for (var i = 0; i < myStringArray.length; i++) {
                   if (add) {
                    addRow(tableid, myStringArray[i].artist, myStringArray[i].title, null, myStringArray[i].id, add);
                   } else {
                    addRow(tableid, myStringArray[i].artist, myStringArray[i].title, myStringArray[i].votes, myStringArray[i].id, add);
                   }
                    //Do something
                }
            }

            function init() {
               getPlaylist();
               document.getElementById('searchfield').focus();
               window.setInterval(getPlaylist, 5000);
            }

        </script>
    </head>
    <body onload="init()">

        <div id="container">
            <div id="header">
                <div class="top"><h3>Search for songs to add to playlist or vote on your favorite already on the playlist.</h3></div>
            </div>
            <div id="body">

                <div class='maindiv'>

                    <div class='search'>

                        <h3>Search for songs to add to the playlist.</h3>

                        <input id="searchfield" type="text" name="search"/>
                        <button onclick="search()">submit</button>
                        <br/><br/>
                        <div class="tablediv">
                        <TABLE width="200px"  id="searchtable" border="0" style="font-family:Times New Roman, Times, serif;color:black;font-size:14px;">
                            <TR>
                                <TH> Artist </TH>
                                <TH> Song </TH>
                                <TH> Add </TH>
                            </TR>
                        </TABLE>
                        </div>    

                    </div>

                    <div class='playlist'>
                       <h3>Now playing <span id="nowPlayingTitle"></span> by <span id="nowPlayingArtist"></span></h3>
                        <h3>Songs currently on the playlist.</h3>
                        <div class="tablediv">
                        <TABLE class="tableclass" width="300px" id="dataTable" style="font-family:Times New Roman, Times, serif;color:black;font-size:14px;">
                            <TR>
                                <TH style="border-bottom:1px"> Artist </TH>
                                <TH> Song </TH>
                                <TH> Votes </TH>
                                <TH> Vote</TH>
                            </TR>
                        </TABLE>
                        </div>   
                        <img src="../img/reload.png" onClick="reloadPlaylist()" height="20" width="20">Reload<img/>
                    </div>

                </div>
            </div>

            <div id="footer">
                <div class="footer">&#169;dPlay&nbsp&nbsp&nbsp&nbspdPlay&#8482; </div>
            </div>

        </div>

    </body>
</html>