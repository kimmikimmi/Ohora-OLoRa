
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
            var jsonString = "";
            request.on("data", function(chunk) {
                jsonString += chunk ;
               // console.log("data posted data: " + chunk.toString());

            }).on("end", function() {
                console.log("posted" + jsonString);
                messagesRef.on('child_added', function(snap){
                    snap.ref.update(
                    	
                        JSON.parse(jsonString)

                    );
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
app.listen(80);