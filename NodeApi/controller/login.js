var express = require('express');
var router = express.Router();
var profileModel = require('./../model/profile.js');
var profileData = require('./../utility/profileDB');



router.get('/', function (req, res) {
    res.render('login');
});


router.post('/student', function (req, res) {
    console.log("req body", req.body);
    var profile = req.body;
    console.log("email", profile.id);
    console.log("password", profile.studentID);
    var username = profile.id;
    var password = profile.studentID;
    profileData.loginProfile(username, password).then(function (result) {

        var response = "";
        console.log("Status", result);
        if (result == "success") {
            response = "success";
            console.log(response);
        } else {
            response = "Login Unsuccessful"
            console.log(response);
        }
        res.send(response);
    });

});




router.post('/loginAdmin', function (req, res) {
    console.log("req body", req.body);
    var username = req.body.username;
    var password = req.body.password;
    if (username == "admin" && password == "admin") {
        console.log("inside");
        res.json({
            success: "Updated Successfully",
            status: 200
        });
    } else {
        console.log("eror");
          res.json({
            error: "not found",
            status: 404
        });
    }

});

module.exports = router;