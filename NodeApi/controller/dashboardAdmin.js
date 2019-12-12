var express = require('express');
var router = express.Router();
var chartData = require('./../utility/chartData');
var nodemailer = require('nodemailer');

router.get('/', function (req, res) {

    var taskData = [];
    var workshopData = [];
    var tasklabels = [];
    var workshoplabels = [];
    var totalNumber;
    chartData.taskData().then(function (result) {
        taskData = result.dataArray;
        tasklabels = result.dataLabel;
        return result;
    }).then(function (xrest) {
        chartData.workshopData().then(function (result) {
            workshopData = result.dataArray;
            workshoplabels = result.dataLabel;
            chartData.numberS().then(function (xds) {
                totalNumber = xds;
                console.log("admin count", totalNumber);
                res.render('dashboard', {
                    taskData: taskData,
                    tasklabels: tasklabels,
                    workshopData: workshopData,
                    workshoplabels: workshoplabels,
                    totalNumber: totalNumber
                });
            });

        });
    });
});

router.post('/sendEmail', function (req, res) {
    console.log("in send email", req.body);
    var type = req.body.type;
    var name = req.body.name;
    console.log("in send email type", type);
    if (type == "task") {
        chartData.taskDataEmail(name).then(function (result) {
            var email_ids = result.toString();
            console.log("email_ids", email_ids);

            var transporter = nodemailer.createTransport({
                service: 'gmail',
                auth: {
                    user: 'onboardingnet@gmail.com',
                    pass: ''
                }
            });

            var message = "Hi, It shows in onboarding system that you have not completed task " + name + ". Please Complete the task.";
            var mailOptions = {
                from: 'onboardingnet@gmail.com',
                to: email_ids,
                subject: 'Reminder from onboarding System for task',
                text: message
            };
            transporter.sendMail(mailOptions, function (error, info) {
                if (error) {
                    res.send('error occured');
                } else {
                     res.json({
                        success: "Updated Successfully",
                        status: 200
                    });
                }
            });

        });
    } else {
        chartData.workshopDataEmail(name).then(function (result) {
            var email_ids = result.toString();
            console.log("email_ids", email_ids);

            var transporter = nodemailer.createTransport({
                service: 'gmail',
                auth: {
                    user: 'onboardingnet@gmail.com',
                    pass: ''
                }
            });

            var message = "Hi, Thank you for attending the workshop" + name + " can go below link for feedback.";
            var mailOptions = {
                from: 'onboardingnet@gmail.com',
                to: '' + email_ids + '',
                subject: 'Feedback for workshop',
                text: message
            };
            transporter.sendMail(mailOptions, function (error, info) {
                if (error) {
                    console.log(error);
                    res.send('error occured', error);
                } else {
                    res.json({
                        success: "Updated Successfully",
                        status: 200
                    });
                }
            });
        });
    }

});




module.exports = router;