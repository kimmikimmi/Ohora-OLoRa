var http = require('http');

function onRequest(request,response){
  console.log("A user made a request " + request.url);
  response.writeHead(200, {"Context-Type": "test/plain"});
  response.write("Here is some data ");
  response.end();
}

http.createServer(onRequest).listen(8888);
console.log("server is now running");

//모든 파일구조가 express에의해 관리된다.