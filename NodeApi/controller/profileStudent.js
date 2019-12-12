var express = require('express');
var router = express.Router();
var profileModel = require('./../model/profile.js');
var profileData = require('./../utility/profileDB');

router.post('/', function (req, res) {
    console.log("req body", req.body);
    var profile = req.body;
    console.log("req body", profile.id);
    var emailId = profile.id;
    profileData.searchProfile(emailId).then(function (result) {

        var profileResult = result;
        console.log('profile', profileResult);
        var response = {};
        var profile = [];

        Object.keys(profileResult).forEach(function (key) {
            profile.push(profileModel.profile(profileResult[key]['_id'], profileResult[key]['studentID'], profileResult[key]['rewards'], profileResult[key]['name'], profileResult[key]['notification'], profileResult[key]['studentImg'], profileResult[key]['pastTask'], profileResult[key]['pastWorkshop'], profileResult[key]['step'],profileResult[key]['studentNotify']));
        });
        console.log("profile", profile);

        if (profile.length > 0) {
            response.status = "success";
            response.data = profile;
        } else {
            response.status = "success";
            response.data = "No profile found";
        }

        res.send(response);
    });    
    
});

module.exports = router;
