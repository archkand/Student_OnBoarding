var express = require('express');
var bodyParser = require('body-parser');
var session = require('express-session');
var app = express();
const path = require('path');
var mongoose = require('mongoose');


app.set('view engine', 'ejs');
app.use(bodyParser.json({}));

app.use(bodyParser.urlencoded({
    extended: true
}));

app.use(session({
    secret: 'onboard'
}));

app.use('/assets', express.static('assets'));
app.use('/commonFunctions', express.static('commonFunctions'));

mongoose.connect('mongodb://localhost/onboarding', {
    useNewUrlParser: true
});

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function () {});

var informationStudent = require('./controller/informationStudent');
app.use('/informationStudent', informationStudent);

var informationAdmin = require('./controller/informationAdmin');
app.use('/informationAdmin', informationAdmin);


var workshopStudent = require('./controller/workshopStudent');
app.use('/workshopStudent', workshopStudent);

var workshopAdmin = require('./controller/workshopAdmin');
app.use('/workshopAdmin', workshopAdmin);

var profileStudent = require('./controller/profileStudent');
app.use('/profileStudent', profileStudent);


var taskAdmin = require('./controller/taskAdmin');
app.use('/taskAdmin', taskAdmin);


var taskStudent = require('./controller/taskStudent');
app.use('/taskStudent', taskStudent);


var verifyAdmin = require('./controller/verifyAdmin');
app.use('/verifyAdmin', verifyAdmin);


var dashboardAdmin = require('./controller/dashboardAdmin');
app.use('/dashboardAdmin', dashboardAdmin);

var login = require('./controller/login');
app.use('/login', login);

var dashboardStudent = require('./controller/dashboardStudent');
app.use('/dashboardStudent',dashboardStudent);


app.use('/',login);



app.listen(3000, function () {
    console.log('app started');
    console.log('listening on port 3000');
});
