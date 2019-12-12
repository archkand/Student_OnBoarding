var express = require('express');
var router = express.Router();
var taskModel = require('./../model/task.js');
var taskData = require('./../utility/taskDB.js');
var verifyData = require('./../utility/verifyDB.js');
var profileData = require('./../utility/profileDB.js');









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
        res.send(response);

    });
});




router.get('/getStudentTask', function (req, res) {
    console.log("hit student task for my emaill");
    var emailId = req.query.emailId;
    var studentTaskList;
    var studentId;
    var taskList = [];
     var response = {};
    var tslist = [];
    profileData.searchProfile(emailId).then(function (presult) {
        studentId = presult[0].studentID;
        return presult;
    }).then(function (presult) {
        console.log(studentId);
        verifyData.getStudentTask(studentId).then(function (result) {
            //console.log("result", result);
            studentTaskList = result;
            return studentTaskList;
        }).then(function (xtaskList) {

            taskData.allTask().then(function (result) {
                tlist = result;
                //    console.log("tresult", tlist);


                Object.keys(studentTaskList).forEach(function (key) {
                    Object.keys(tlist).forEach(function (v) {
                        if (studentTaskList[key]['taskName'] == tlist[v]['taskName']) {
                            task = {};
                            task.id = tlist[v]['_id'];
                            task.taskName = tlist[v]['taskName'];
                            task.dueDate = tlist[v]['dueDate'];
                            task.description = tlist[v]['description'];
                            task.rewards = tlist[v]['rewards'];
                            task.status = studentTaskList[key]['status'];
                            task.notify = studentTaskList[key]['notify'];
                            taskList.push(task);
                        }

                    });
                });

             //   console.log("tasklist includejust my task", taskList);

                const remainingTList = tlist.filter(({taskName}) => !studentTaskList.some(x => x.taskName == taskName));
               
                    Object.keys(remainingTList).forEach(function (v) {
                            task = {};
                            task.id = remainingTList[v]['_id'];
                            task.taskName = remainingTList[v]['taskName'];
                            task.dueDate = remainingTList[v]['dueDate'];
                            task.description = remainingTList[v]['description'];
                            task.rewards = remainingTList[v]['rewards'];
                            task.status = "Not_Started";
                            task.notify = "OFF";
                            taskList.push(task);
                    });
                
                  Object.keys(taskList).forEach(function (vx) {
                      if (taskList[vx]['status'] != "verified" && taskList[vx]['status'] != "declined") {
                          tslist.push(taskList[vx]);
                          
                      }
                  });
                
                
                
             response.status = "success";
            response.data = tslist;
                    
            //   console.log("tresult  ttt", taskList);
                res.send(response);
            });
        });
    });
});


router.post('/notify', function (req, res) {
     console.log("notify is hit");
    console.log("re body",req.body);
    var emailId = req.body.emailId;
    var taskId = req.body.taskId;
    var notify = req.body.notify;
    var studentId;
    var studentTaskList;
    var taskList = [];
    profileData.searchProfile(emailId).then(function (presult) {
        studentId = presult[0].studentID;
        return presult;
    }).then(function (presult) {
        console.log(studentId);
        verifyData.notifyStudentTask(studentId, taskId, notify).then(function (result) {
            if (result == true) {
                res.send("success");
            }
        });
    });
});


router.post('/sendVerify', function (req, res) {
    var emailId = req.body.emailId;
    var taskId = req.body.taskId;
    var studentId;
    var studentTaskList;
    var taskList = [];
    profileData.searchProfile(emailId).then(function (presult) {
        studentId = presult[0].studentID;
        return presult;
    }).then(function (presult) {
        console.log(studentId);
        verifyData.statusStudentTask(studentId, taskId).then(function (result) {
            if (result == true) {
                res.send("success");
            }
        });
    });
});





module.exports = router;