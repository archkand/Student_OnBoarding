var express = require('express');
var router = express.Router();
var workshopModel = require('./../model/workshop.js');
var workshopData = require('./../utility/workshopDB');
var profileData = require('./../utility/profileDB.js');


router.get('/getAll', function (req, res) {
    var emailId = req.query.emailId;
    var studentId;
    profileData.searchProfile(emailId).then(function (presult) {
        studentId = presult[0].studentID;
        return presult;
    }).then(function (presult) {
        console.log(studentId);


        workshopData.getStudentWorkshops(studentId).then(function (wresults) {
            var dumyarr = wresults;
            console.log("wresults", dumyarr);

            workshopData.allWorkshop().then(function (result) {
                var workshopResults = result;
                var response = {};
                var info = [];

                const remainingTList = workshopResults.filter(({workshopName}) => !dumyarr.some(x => x.workshopName == workshopName));

                console.log("all workshoplist after filter", remainingTList);

                Object.keys(remainingTList).forEach(function (key) {
                    info.push(workshopModel.workshop(remainingTList[key]['_id'], remainingTList[key]['workshopName'], remainingTList[key]['description'], remainingTList[key]['date']));
                });


                if (info.length > 0) {
                    response.status = "success";
                    response.data = info;
                } else {
                    response.status = "success";
                    response.data = "No data found";
                }

                res.send(response);

            });


        });

    });
});



router.post('/register', function (req, res) {
    console.log("req.body", req.body);
    var emailId = req.body.emailId;
    var workshopId = req.body.workshopId;
    console.log("hit register", emailId, workshopId);
    var studentId;
    profileData.searchProfile(emailId).then(function (presult) {
        studentId = presult[0].studentID;
        return presult;
    }).then(function (presult) {
        console.log(studentId);
        workshopData.registerStudentWorkshop(studentId, workshopId).then(function (result) {
            if (result == true) {
                res.send("success");
            }
        });
    });

});




module.exports = router