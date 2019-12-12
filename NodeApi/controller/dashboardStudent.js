var express = require('express');
var router = express.Router();
var profileModel = require('./../model/profile.js');
var profileData = require('./../utility/profileDB');

router.get('/step', function (req, res) {
    console.log("req body", req.query.studentId);
    console.log("Inside the step function");
    var response = "";
    var studentId = req.query.studentId;
    profileData.setSteps(studentId).then(function (result) {
        if(result == true)
            response = "success";
        else
            response = "failure";
        res.send(response);
        
    });

});

module.exports = router;
