var express = require('express');
var router = express.Router();
var informationModel = require('./../model/information.js');
var informationData = require('./../utility/informationDB');




router.get('/getAll', function (req, res) {
    informationData.allInformation().then(function (result) {
        var informationResults = result;
        var response = {};
        var info = [];

        Object.keys(informationResults).forEach(function (key) {
            info.push(informationModel.information(informationResults[key]['_id'], informationResults[key]['name'], informationResults[key]['detail'], informationResults[key]['link']));
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



module.exports = router