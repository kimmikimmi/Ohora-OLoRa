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
    serviceAccount: "./lora.json",
    databaseURL:"https://graduateproto.firebaseio.com"
});

var ref = firebase.database().ref('address');
var messagesRef = ref.child('/');


messagesRef.on('child_added', function(snap){
    snap.ref.update({
            "101" : {
                "fire" : "yes",
                "image1" : "image1",
                "image2" : "image2",
                "member" : "7",
                "smoke" : "20",
                "temperature" : "30"
            },
            "102" : {
                "fire" : "no",
                "image1" : "image1",
                "image2" : "image2",
                "member" : "4",
                "smoke" : "20",
                "temperature" : "30"
            }

    });
});

