// server.js
var express = require('express');
var path = require('path');
var serveStatic = require('serve-static');
app = express();
app.use(require('connect-history-api-fallback')())
app.use(serveStatic(__dirname + "/dist"));
var port = process.env.PORT || 5000;
app.listen(port);
console.log('server started '+ port);
