var express = require("express");
var app = express();
var path = require('path');

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));
app.use(express.static(__dirname + '/public'));

// route routing is very easy with express, this will handle the request for root directory contents.
// :id is used here to pattern match with the first value after the forward slash.
app.get("/",function (req,res)
{

  // now we use the templating capabilities of express and call our template to render the view, and pass a few parameters to it
  res.render("index.ejs", {
    map_location_x : 37.390845,
    map_location_y : 126.651871,
    mark_location_x: 37.370077,
    mark_location_y: 126.655474
  });
});

app.listen(9999);