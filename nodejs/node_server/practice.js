/**
 * Created by Yohan on 2016-08-03.
 */
var express = require('express');
var app = express();

app.get('/',function (req,res){
   res.render('index');
});

app.listen(1337, function(){
   console.log('ready on port 1337');
});