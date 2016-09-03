var express = require('express');
var path = require('path');
var app = express();
var fs = require('fs');
var firebase = require("firebase");
var css = require('css');
var pjax    = require('express-pjax');



/* **
 * 사용할 모듈을 정의한다.
 * **/


firebase.initializeApp({
  serviceAccount: "./lora.json",
  databaseURL:"https://graduateproto.firebaseio.com"
});

/* **
 * firebase Database 게정과 연결한다.
 * **/


var ref = firebase.database().ref('address');
var messagesRef = ref.child('/');

/* **
 * firebase Database 부터 address값의 데이터를 가지고온다.
 * **/


app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));
app.use(express.static(__dirname + '/public'));
var ast = css.parse('h1 { font-size: 12px; }', { source: 'style.css' });
/* **
 * view engine 으로 html 대신 ejs 를 사용한다.
 * static path로 /public을 추가한다.
 * **/


//var vd = require('./total_db.json');
//JSON.stringify(vd);
//console.log(vd);

var col;
var fire = " ";
var img_url=" ";
var img_color=" ";
ref.child("/").on('child_changed', function(snap){
  console.log(snap.child('101/fire').val());
  fire = snap.child('101/fire').val();
  if(fire =='yes'){
    img_url = 'http://cyh1704.dothome.co.kr/image/firesmog.png';
    img_color = 'red';
  }else{

  }

  app.get('/',function(req, res){
    res.render('index',{
      title : 'url("ultari2.jpg")',
      img_color : img_color,
      img_url : img_url,
      temp1 :fire,
      blue : 'red'
    });


  });


});
//JSON.stringify(wow);

//JSON.parse(wow);
//for (var i=0; i<wow.length; i++){
  // console.log(i);
  //console.log(wow['101'][i] );
//}


app.get('/',function(req, res){
  res.render('index',{
    title : 'url("ultari2.jpg")',
    img_color : img_color,
    img_url : img_url,
    temp1 :fire,
    blue : 'red'
  });
});




/* **
 * root page를 호출했을때, index.ejs 를 rendering 한다.
 * **/

app.listen(8888, function(){
  console.log('ready on port 8888');
});
/* **
 * app을 포트 8888을 listen 하도록 한다.
 * **/
