var express = require('express');
var router = express.Router();
var verifyModel = require('./../model/verify.js');
var verifyData = require('./../utility/verifyDB.js');




router.get('/getAll', function (req, res) {
    verifyData.verifyallTask().then(function (result) {
        var verifyResults = result;
        var response = {};
        var info = [];

        Object.keys(verifyResults).forEach(function (key) {
            info.push(verifyModel.verify(verifyResults[key]['_id'], verifyResults[key]['studentId'], verifyResults[key]['studentName'], verifyResults[key]['taskId'], verifyResults[key]['taskName']));
        });

        if (info.length > 0) {
            response.status = "success";
            response.data = info;
        } else {
            response.status = "success";
            response.data = "No data found";
        }

        res.render('verification', {
            data: response.data
        });

    });
});



router.post('/takeAction', async function (req, res) {
    var verifyStudentId = req.body.studentId;
    var verifyTaskId = req.body.taskId;
    var verifyAction = req.body.action;
    var update;
    await verifyData.verifyStudentTask(verifyStudentId, verifyTaskId, verifyAction).then(function (result) {
        update = result;
        return update;
    }).then(function (update) {

        verifyData.verifyallTask().then(function (result) {
            var verifyResults = result;
            var response = {};
            var info = [];

            Object.keys(verifyResults).forEach(function (key) {
                info.push(verifyModel.verify(verifyResults[key]['_id'], verifyResults[key]['studentId'], verifyResults[key]['studentName'], verifyResults[key]['taskId'], verifyResults[key]['taskName']));
            });

            console.log("update from admin", info);
            res.json({
                success: "Updated Successfully",
                status: 200,
                data: info
            });

        });

    });
});


module.exports = router