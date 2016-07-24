
var firebase = require("firebase");
var http = require('http');
var querystring = require('querystring');

firebase.initializeApp({
  serviceAccount: "path/to/serviceAccountCredentials.json",
  databaseURL: "https://lora-847a2.firebaseio.com"
});

function defaultHandler(request, response) {
  try{
    if ( request.method == "POST" ) {
      var strBody = "";
      request.on("data", function(chunk) {
        strBody += chunk;
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
app.listen(8080);