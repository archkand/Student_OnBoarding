var express = require('express');
var router = express.Router();
var taskModel = require('./../model/task.js');
var taskData = require('./../utility/taskDB.js');
var verifyData = require('./../utility/verifyDB.js');

const PushNotifications = require('pusher-push-notifications-node');

let pushNotifications = new PushNotifications({
    instanceId: '',
    secretKey: ''
});



router.post('/sendPush', async function (req, res) {
    var taskArray = [];

    verifyData.getstudentsForNotification().then(function (result) {
        taskArray = result;
    }).then(function (xresult) {
        for (var i = 0; i < taskArray.length; i++) {
            console.log("this is called", taskArray[i]);
            var notify = {
                title: 'Notification',
                body: 'Admin has notified you for task '+taskArray[i]['taskName']+' as the due date is approaching.'
            }
            console.log("notify check ccalled", notify);
            pushNotifications.publish(
      [taskArray[i]['taskId']], {
                    fcm: {
                        notification: notify
                    }
                }
            ).then((publishResponse) => {
                console.log('Just published:', publishResponse.publishId);
                res.json({
                    success: "Successfully",
                    status: 200
                });
            }).catch((error) => {
                console.log('Error:', error);
            });
        }
    });
});




router.get('/getAll', function (req, res) {
    console.log("Hit task in taskStudent");
    taskData.allTask().then(function (result) {
        var taskResults = result;
        var response = {};
        var info = [];

        Object.keys(taskResults).forEach(function (key) {
            info.push(taskModel.task(taskResults[key]['_id'], taskResults[key]['taskName'], taskResults[key]['description'], taskResults[key]['dueDate'], taskResults[key]['rewards']));
        });

        if (info.length > 0) {
            response.status = "success";
            response.data = info;
        } else {
            response.status = "success";
            response.data = "No data found";
        }
        console.log(response);
        res.render('task', {
            data: response.data
        });

    });
});



router.post('/search', function (req, res) {

    var taskName = req.body.taskName;
    taskData.searchTask(taskName).then(function (result) {

        var taskResults = result;
        var response = {};
        var info = [];

        Object.keys(taskResults).forEach(function (key) {
            info.push(taskModel.task(taskResults[key]['_id'], taskResults[key]['taskName'], taskResults[key]['description'], taskResults[key]['dueDate'], taskResults[key]['rewards']));
        });

        if (info.length > 0) {
            response.status = "success";
            response.data = info;
        } else {
            response.status = "success";
            response.data = "No matching search results found";
        }

        res.render('task', {
            data: response.data
        });
    });
});

router.post('/create', function (req, res) {
    var taskReq = taskModel.task("", req.body.taskName, req.body.taskDescription, req.body.taskDueDate, req.body.taskReward);
    var updateInformation;
    console.log('task req', taskReq);
    taskData.createNewTaskDB(taskReq).then(function (result) {
        updateInformation = result;
        return updateInformation;
    }).then(function (updateInformation) {
        taskData.allTask().then(function (result) {
            var taskResults = result;
            var response = {};
            var info = [];

            Object.keys(taskResults).forEach(function (key) {
                info.push(taskModel.task(taskResults[key]['_id'], taskResults[key]['taskName'], taskResults[key]['description'], taskResults[key]['dueDate'], taskResults[key]['rewards']));
            });

            if (info.length > 0) {
                response.status = "success";
                response.data = info;
            } else {
                response.status = "success";
                response.data = "No data found";
            }

            res.json({
                success: "Updated Successfully",
                status: 200,
                data: response.data
            });
        });
    });
});

router.post('/edit', function (req, res) {
    var taskReq = taskModel.task(req.body.taskId, req.body.taskName, req.body.taskDescription, req.body.taskDueDate, req.body.taskReward);
    console.log('task req', taskReq);
    var updateInformation;
    taskData.updateTaskDB(taskReq).then(function (result) {
        updateInformation = result;
        return updateInformation;
    }).then(function (updateInformation) {
        taskData.allTask().then(function (result) {
            var taskResults = result;
            var response = {};
            var info = [];

            Object.keys(taskResults).forEach(function (key) {
                info.push(taskModel.task(taskResults[key]['_id'], taskResults[key]['taskName'], taskResults[key]['description'], taskResults[key]['dueDate'], taskResults[key]['rewards']));
            });

            if (info.length > 0) {
                response.status = "success";
                response.data = info;
            } else {
                response.status = "success";
                response.data = "No data found";
            }

            res.json({
                success: "Updated Successfully",
                status: 200,
                data: response.data
            });

            /* res.render('task', {
                 data: response.data
             });*/

        });
    });
});

router.post('/delete', function (req, res) {

    var task_id = req.body.taskId;
    var infoSearch;
    taskData.deleteTask(task_id).then(function (result) {
        infoSearch = result;

        return infoSearch;
    }).then(function (infoSearch) {

        taskData.allTask().then(function (result) {
            var taskResults = result;
            var response = {};
            var info = [];

            Object.keys(taskResults).forEach(function (key) {
                info.push(taskModel.task(taskResults[key]['_id'], taskResults[key]['taskName'], taskResults[key]['description'], taskResults[key]['dueDate'], taskResults[key]['rewards']));
            });

            if (info.length > 0) {
                response.status = "success";
                response.data = info;
            } else {
                response.status = "success";
                response.data = "No data found";
            }

            res.json({
                success: "Updated Successfully",
                status: 200,
                data: response.data
            });

        });

    });

});



module.exports = router;