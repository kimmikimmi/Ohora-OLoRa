/**
 * Created by Yohan on 2016-07-21.
 */
/**
 * Created by Yohan on 2016-07-18.
 */

var firebase = require("firebase");
var http = require('http');
var querystring = require('querystring');
var promises = [];

firebase.initializeApp({
    serviceAccount: "./jimin.json",
    databaseURL:"https://graduateproto.firebaseio.com"
});

var ref = firebase.database().ref('address');
var messagesRef = ref.child('부산시동래구온천3동아림베르빌/101/fire');


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
                console.log("Received posted data: " + strBody);

                promises.push(messagesRef.push({
                    text: strBody
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