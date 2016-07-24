/**
 * Created by Yohan on 2016-07-18.
 */
var firebase = require('firebase');

firebase.initializeApp({
    serviceAccount: "./qwiver-two-service-account.json",
    databaseURL:"https://lora-847a2.firebaseio.com/"
});

var ref = firebase.database().ref('node-client');

ref.once('value')
   .then(function(snap){
    console.log('snap.val()',snap.val());
  });