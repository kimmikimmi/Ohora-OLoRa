/**
 * Created by Yohan on 2016-07-18.
 */
/**
 * Created by Yohan on 2016-07-18.
 */
var firebase = require('firebase');

firebase.initializeApp({
    serviceAccount: "./qwiver-two-service-account.json",
    databaseURL:"https://lora-847a2.firebaseio.com/"
});

var ref = firebase.database().ref('node-client');
var messagesRef = ref.child('some/path/messages');
var promises = [];

for(var i =0; i<10; i++){
    promises.push(messagesRef.push({
        text: 'wow',
        count: i
    }));
}

Promise.all(promises)
  .then(function(){
      ref.once('value')
       .then(function(snap){
           console.log(snap.val());
       });
 });

