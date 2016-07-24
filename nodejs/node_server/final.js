
var firebase = require("firebase");
var http = require('http');
var querystring = require('querystring');


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
};

var app = http.createServer(defaultHandler);
app.listen(8080);/**
 * Created by Yohan on 2016-07-21.
 */
