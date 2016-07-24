/**
 * Created by Yohan on 2016-07-18.
 */

var firebase = require("firebase");
var http = require('http');
var querystring = require('querystring');
var promises = [];

firebase.initializeApp({
    serviceAccount: "./qwiver-two-service-account.json",
    databaseURL:"https://lora-847a2.firebaseio.com/"
});

var ref = firebase.database().ref('node-client');
var messagesRef = ref.child('some/path/messages');


function defaultHandler(request, res) {
    try{
        res.writeHead(200, {'Content-Type': 'text/plain'});
        res.end("Hello World\n");
        console.log("wow");
        if ( request.method == "POST" ) {
            var strBody = "";
            request.on("data", function(chunk) {
                strBody += chunk;
            });
            request.on("end", function() {
                    console.log( strBody);

                promises.push(messagesRef.push({
                    text: chunk
                }));

                Promise.all(promises)
                    .then(function(){
                        ref.once('value')
                            .then(function(snap){
                                console.log(snap.val());
                            });
                    });
            });
        } else {
            // console.dir(request);
        }
    } catch( ex ) {
        //console.dir(ex);
    }
};

var app = http.createServer(defaultHandler);
app.listen(8080,'127.0.0.1');