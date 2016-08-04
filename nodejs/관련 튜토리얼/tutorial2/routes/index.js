var express = require('express');
var router = express.Router();
/* GET home page. */
var app = express();
app.locals.vd = require('../db.json');
app.set('view engin','ejs');
var vd = require('../db.json');
router.get('/', function(req, res, next) {
  res.render('index', {
    title: '화재감지시스템',
    name : 'Burcky',
    vd : vd
  });
});

module.exports = router;
