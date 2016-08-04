var connect = require('connect');
var http = require('http');

var app = connect();

function profile(request, response){
    console.log('User requested profile');
}

function forum(request, response){
    console.log('User requested forum');
}

app.use('/profile',profile);
app.use('/forum',forum);
http.createServer(app).listen(8888);
console.log("server is running ");

///// connect 를 사용하는 이유는 다른 path에 대해서 다른 리스폰스를 해주기 위해서이다