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

        res.render('information',{data:response.data});

    });
});


router.post('/search', function (req, res) {

    var informationName = req.body.infoName;
    informationData.searchInformation(informationName).then(function (result) {

        var informationResults = result;
        console.log('information result', informationResults);
        var response = {};
        var info = [];

        Object.keys(informationResults).forEach(function (key) {
            info.push(informationModel.information(informationResults[key]['_id'], informationResults[key]['name'], informationResults[key]['detail'], informationResults[key]['link']));
        });
        console.log("info", info);

        if (info.length > 0) {
            response.status = "success";
            response.data = info;
        } else {
            response.status = "success";
            response.data = "No matching search results found";
        }

        res.send(response);
    });
});

router.post('/create', function (req, res) {
    var informationReq = informationModel.information("", req.body.infoName, req.body.infoDetail, req.body.infoLink);
    var updateInformation;
    informationData.createNewInformationDB(informationReq).then(function (result) {
        updateInformation = result;
        return updateInformation;
    }).then(function (updateInformation) {
        informationData.allInformation().then(function (inforesult) {
            var informationResults = inforesult;
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
});

router.post('/edit', function (req, res) {
    var informationReq = informationModel.information(req.body.infoId, req.body.infoName, req.body.infoDetail, req.body.infoLink);
    var updateInformation;
    informationData.updateInformationDB(informationReq).then(function (result) {
        updateInformation = result;
        return updateInformation;
    }).then(function (updateInformation) {
        informationData.allInformation().then(function (inforesult) {
            var informationResults = inforesult;
            console.log("infor result",informationResults);
            var response = {};
            var info = [];
            Object.keys(informationResults).forEach(function (key) {
                info.push(informationModel.information(informationResults[key]['_id'], informationResults[key]['name'], informationResults[key]['detail'], informationResults[key]['link']));
            });
   console.log("info", info);

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

router.post('/delete', function (req, res) {

    var information_id = req.body.infoId;
    var infoSearch;
    informationData.deleteInformation(information_id).then(function (result) {
        infoSearch = result;

        return infoSearch;
    }).then(function (infoSearch) {

        informationData.allInformation().then(function (inforesult) {
            var informationResults = inforesult;
            var response = {};
            var info = [];
            Object.keys(informationResults).forEach(function (key) {
                info.push(informationModel.information(informationResults[key]['_id'], informationResults[key]['name'], informationResults[key]['detail'], informationResults[key]['link']));
            });

            if (informationResults.length > 0) {
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



module.exports = router;