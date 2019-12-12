var express = require('express');
var router = express.Router();
var workshopModel = require('./../model/workshop.js');
var workshopData = require('./../utility/workshopDB');



router.get('/getAll', function (req, res) {
    workshopData.allWorkshop().then(function (result) {
        var workshopResults = result;
        var response = {};
        var info = [];

        Object.keys(workshopResults).forEach(function (key) {
            info.push(workshopModel.workshop(workshopResults[key]['_id'], workshopResults[key]['workshopName'], workshopResults[key]['description'], workshopResults[key]['date']));
        });

        if (info.length > 0) {
            response.status = "success";
            response.data = info;
        } else {
            response.status = "success";
            response.data = "No data found";
        }

        res.render('workshop',{data:response.data});

    });
});


router.post('/search', function (req, res) {

    var workshopName = req.body.workshopName;
    var work = [];

    workshopData.searchWorkshop(workshopName).then(function (result) {
        var workshopResults = result;
        var response = {};

        Object.keys(workshopResults).forEach(function (key) {
            work.push(workshopModel.workshop(workshopResults[key]['_id'], workshopResults[key]['workshopName'], workshopResults[key]['description'], workshopResults[key]['date']));
        });


        if (work.length > 0) {
            response.status = "success";
            response.data = work;
        } else {
            response.status = "success";
            response.data = "No matching search results found";
        }

        res.send(response);
    });
});

router.post('/create', function (req, res) {
    var workshopReq = workshopModel.workshop("", req.body.workshopName, req.body.workshopDescription, req.body.workshopDate);
    var updateInformation;
    workshopData.createNewWorkshopDB(workshopReq).then(function (result) {
        updateInformation = result;
        return updateInformation;
    }).then(function (updateInformation) {
        workshopData.allWorkshop().then(function (result) {
            var workshopResults = result;
            var response = {};
            var info = [];

            Object.keys(workshopResults).forEach(function (key) {
                info.push(workshopModel.workshop(workshopResults[key]['_id'], workshopResults[key]['workshopName'], workshopResults[key]['description'], workshopResults[key]['date']));
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
    var workshopReq = workshopModel.workshop(req.body.workshopId, req.body.workshopName, req.body.workshopDescription, req.body.workshopDate);
    var updateInformation;
    workshopData.updateWorkshopDB(workshopReq).then(function (result) {
        updateInformation = result;
        return updateInformation;
    }).then(function (updateInformation) {
        workshopData.allWorkshop().then(function (result) {
            var workshopResults = result;
            var response = {};
            var info = [];

            Object.keys(workshopResults).forEach(function (key) {
                info.push(workshopModel.workshop(workshopResults[key]['_id'], workshopResults[key]['workshopName'], workshopResults[key]['description'], workshopResults[key]['date']));
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

router.post('/delete', function (req, res) {

    var workshop_id = req.body.workshopId;
    var infoSearch;
    workshopData.deleteWorkshop(workshop_id).then(function (result) {
        infoSearch = result;

        return infoSearch;
    }).then(function (infoSearch) {

        workshopData.allWorkshop().then(function (result) {
            var workshopResults = result;
            var response = {};
            var info = [];

            Object.keys(workshopResults).forEach(function (key) {
                info.push(workshopModel.workshop(workshopResults[key]['_id'], workshopResults[key]['workshopName'], workshopResults[key]['description'], workshopResults[key]['date']));
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



module.exports = router;