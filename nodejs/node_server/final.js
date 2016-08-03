var url = require('url');
var firebase = require("firebase");
var http = require('http');
var fs = require('fs');
var querystring = require('querystring');
var ROOT_DIR = "html/hello.html";
var express = require('express');
var router = express.Router();




var wow ="";

firebase.initializeApp({
    serviceAccount: "./lora.json",
    databaseURL:"https://graduateproto.firebaseio.com"
});


var ref = firebase.database().ref('address');
var messagesRef = ref.child('/');



function defaultHandler(request, response) {
    try{



        if ( request.method == "POST" ) {
            var strBody = "";
            request.on("data", function(chunk) {
                strBody += chunk;
                chunk.toJSON();
                chunk.toString()
                strBody.toJSON
                chunk.size
                messagesRef.on('child_added', function(snap){
                    snap.ref.update(
                        { "101" : {"fire" : "yes", "image1" : "image1","image2" : "image2","member" : "7","smoke" : "20","temperature" : "30"},"102" : {"fire" : "no","image1" : "image1","image2" : "image2","member" : "4","smoke" : "20","temperature" : "27.10"}}
                    );
                });
            });
            request.on("end", function() {
                console.log("Received posted data: " + strBody);
            });
        } else {
            // console.dir(request);
        }
    } catch( ex ) {
        //console.dir(ex);
    }




   // ref.child("/").on("value", function(snapshot) {
    //    console.log(snapshot.val());  // Alerts "San Francisco"
   //     wow += snapshot.val();
  //  });


    ref.child("/").on('child_added', function(snap){
        console.log(snap.val());
        wow+= snap.val();
    });




    var urlObj = url.parse(request.url, true, false);
    fs.readFile(ROOT_DIR + urlObj.pathname, function (err,data) {
        if (err) {
            response.writeHead(404);
            response.end(JSON.stringify(err));
            return;
        }
        response.writeHead(200);
       // response.write(JSON.parse(wow));
        response.end(data);
    });
};
var app = http.createServer(defaultHandler);
var apps = express();
apps.get('/', function(req, res) { // route for '/'
   res.send(wow.valueOf());
   // res.render('index');
});
apps.listen(8000);
app.listen(8080);
/**
 * Created by Yohan on 2016-07-21.
 */
