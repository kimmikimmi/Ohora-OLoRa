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

messagesRef.on('child_added', function(snap){
    snap.ref.update({
       text:  "wow!!"
    });
});

