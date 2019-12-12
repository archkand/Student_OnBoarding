var taskDataDB = require('./taskDB');
var profileData = require('./profileDB');
var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var verifySchema = new Schema({

    _id: {
        type: String
    },
    studentId: {
        type: String
    },
    studentName: {
        type: String
    },
    taskId: {
        type: String
    },
    taskName: {
        type: String
    }

}, {
    collection: 'verify'
});




var verifyStudentTaskSchema = new Schema({

    _id: {
        type: String
    },
    studentId: {
        type: String
    },
    tasks: {
        type: [{
            taskId: {
                type: String
            },
            taskName: {
                type: String
            },
            status: {
                type: String
            },
            notify: {
                type: String
            }
  }]
    }

}, {
    collection: 'studentTasks'
});



var verifyModel = mongoose.model('verifyModel', verifySchema);
var verifyTaskModel = mongoose.model('verifyTaskModel', verifyStudentTaskSchema);

//returns all task to verify from db
var verifyallTask = async function getVerifyAllTask() {
    var infoData = {};

    await verifyModel.find({}, function (err, verify) {
        if (err)
            console.log("error", err);
        if (verify.length > 0) {
            infoData = verify;
        }
    });
    console.log("Information data in the db", infoData);
    return infoData;
}



//get student task list
var getStudentTask = async function getStudentTaskDB(studentId) {
    var studentTaskList = {};
    var tasksList = [];
    var tlist = [];
    await verifyTaskModel.find({
        studentId: studentId,
    }, function (err, items) {
        if (err)
            console.log(err);
        console.log("error", items);
        if (items.length > 0) {
            studentTaskList = items[0].tasks;
            Object.keys(studentTaskList).forEach(function (key) {

                console.log(studentTaskList[key]['taskName']);
                tasksList.push(studentTaskList[key]);

            });

        }
    });
    return tasksList;
}


//notification
var getstudentsForNotification = async function getstudentsForNotification() {
    var tasklist = [];
    await verifyTaskModel.find({}, function (err, items) {
        if (err)
            console.log(err);
        if (items.length > 0) {
            console.log("items", items);

            Object.keys(items).forEach(function (key) {
                var tasks = items[key]['tasks'];
                Object.keys(tasks).forEach(function (x) {
                    if(tasks[x]['notify']=="ON"){
                        tasklist.push(tasks[x]);
                    }
                    
                });
            });
        }
    });
    return tasklist;

}


//change notification
var notifyStudentTask = async function notifyStudentTaskDB(studentId, taskId, notify) {
    
    await profileData.updatestudentNotify(studentId, taskId, notify).then(function(resx){
       return resx; 
    });
    
    
    
    console.log("req", studentId, taskId, notify);
    await verifyTaskModel.find({
        studentId: studentId
    }, function (err, items) {
        if (err)
            console.log(err);
        if (items.length > 0) {
            taskDataDB.searchTaskById(taskId).then(function (result) {
                var taskName = result[0].taskName;
                var status = result[0].status;
                if (status == null) {
                    status == "START";
                }
                verifyTaskModel.find({
                    studentId: studentId,
                    "tasks.taskId": taskId
                }, function (err, items) {
                    if (err)
                        console.log(err);
                    console.log("items", items);
                    if (items.length > 0) {
                        //update just notify

                        verifyTaskModel.findOneAndUpdate({
                            studentId: studentId,
                            "tasks.taskId": taskId
                        }, {
                            $set: {
                                "tasks.$.notify": notify
                            }
                        }, {
                            new: true
                        }, function (err, items) {
                            if (err)
                                console.log(err);
                            
                            
                            
                        });


                    } else {
                        //push the task
                        verifyTaskModel.findOneAndUpdate({
                            studentId: studentId,

                        }, {
                            $push: {
                                tasks: {
                                    taskId: taskId,
                                    taskName: taskName,
                                    status: "START",
                                    notify: notify
                                }

                            }
                        }, {
                            new: true
                        }, function (err, items) {
                            if (err)
                                console.log(err);
                        });


                    }
                });
            });
            //find task and update
        } else {
            var id = taskId + "" + Math.floor(Math.random() * 10);
            taskDataDB.searchTaskById(taskId).then(function (result) {
                var taskName = result[0].taskName;
                new verifyTaskModel({
                    _id: id,
                    studentId: studentId,
                    tasks: [{
                        taskId: taskId,
                        taskName: taskName,
                        status: "START",
                        notify: notify
                    }]

                }).save();
            });
        }
    });
    return true;
}


//change status verification
var statusStudentTask = async function statusStudentTaskDB(studentId, taskId) {




    var taskName = "";
    await verifyTaskModel.find({
        studentId: studentId
    }, function (err, items) {
        if (err)
            console.log(err);
        if (items.length > 0) {
            taskDataDB.searchTaskById(taskId).then(function (result) {
                taskName = result[0].taskName;
                var status = result[0].status;
                verifyTaskModel.find({
                    studentId: studentId,
                    "tasks.taskId": taskId
                }, function (err, items) {
                    if (err)
                        console.log(err);
                    console.log("items", items);
                    if (items.length > 0) {
                        //update just notify

                        verifyTaskModel.findOneAndUpdate({
                            studentId: studentId,
                            "tasks.taskId": taskId
                        }, {
                            $set: {
                                "tasks.$.status": "InProgress"
                            }
                        }, {
                            new: true
                        }, function (err, items) {
                            if (err)
                                console.log(err);
                            var idverify = taskId + "" + Math.floor(Math.random() * 10);
                            console.log("here else");
                            new verifyModel({
                                _id: idverify,
                                studentId: studentId,
                                studentName: "sheetal",
                                taskId: taskId,
                                taskName: taskName
                            }).save();
                        });


                    } else {

                        //push the task
                        verifyTaskModel.findOneAndUpdate({
                            studentId: studentId,
                        }, {
                            $push: {
                                tasks: {
                                    taskId: taskId,
                                    taskName: taskName,
                                    status: "InProgress",
                                    notify: "OFF"
                                }

                            }
                        }, {
                            new: true
                        }, function (err, items) {
                            if (err)
                                console.log(err);

                            console.log("pushed new item in db,now updating the verify model for admin side");
                            var id = taskId + "" + Math.floor(Math.random() * 10);
                            console.log("here");
                            new verifyModel({
                                _id: id,
                                studentId: studentId,
                                studentName: "sheetal",
                                taskId: taskId,
                                taskName: taskName
                            }).save();
                        });


                    }
                });
            });
            //find task and update
        } else {
            var id = taskId + "" + Math.floor(Math.random() * 10);
            taskDataDB.searchTaskById(taskId).then(function (result) {
                var taskName = result[0].taskName;
                new verifyTaskModel({
                    _id: id,
                    studentId: studentId,
                    tasks: [{
                        taskId: taskId,
                        taskName: taskName,
                        status: "InProgress",
                        notify: "OFF"
                    }]

                }).save();

                console.log("pushed new item in db,now updating the verify model for admin side in the esle part");
                var idverify = taskId + "" + Math.floor(Math.random() * 10);
                console.log("here else");
                new verifyModel({
                    _id: idverify,
                    studentId: studentId,
                    studentName: "sheetal",
                    taskId: taskId,
                    taskName: taskName
                }).save();
            });
        }
    });



    return true;
}




//when admin verify or declines task
var verifyStudentTask = async function getverifyTask(studentId, taskId, action) {
    console.log("send for verify", studentId, taskId, action);
    await verifyModel.remove({
        studentId: studentId,
        taskId: taskId
    }, function (err, verifyd) {
        if (err)
            console.log("there is an error", err);
        console.log("in here", verifyd);
        //update status over here
        verifyTaskModel.findOneAndUpdate({
            studentId: studentId,
            "tasks.taskId": taskId
        }, {
            $set: {
                "tasks.$.status": action
            }
        }, {
            new: true
        }, function (err, items) {
            if (err)
                console.log(err);
        });

    });

    return true;
}



//return number of students in particular task 
var studentNumberTask = async function getNumberTask() {
    var count = [];
    var name = [];
    var datasend = {};
    var tasks;
    await taskDataDB.allTask().then(function (result) {
        tasks = result;
    });

    console.log("result here", tasks);

    for (var i = 0; i < tasks.length; i++) {
        var taskId = tasks[i]._id;
        var taskLabel = tasks[i].taskName;
        //   console.log("task id",taskId);
        await verifyTaskModel.find({
            "tasks.taskId": taskId
        }, function (err, items) {
            if (err)
                console.log(err);
            //console.log("this items are task",items);
            count.push(items.length);
            name.push(taskLabel);
        });
    }

    datasend.dataArray = count;
    datasend.dataLabel = name;
    // console.log("returning count",datasend);
    return datasend;
}



//return number of students in particular task Name
var studentNameTask = async function getNameTask(taskId) {
    var taskName;
    await verifyTaskModel.find({
        "tasks.taskId": taskId
    }, function (err, items) {
        if (err)
            console.log(err);
        console.log(items);
        taskName = items.tasks.taskName;
    });
    return taskName;
}


//return email id of students not done task
var studentTaskEmailId = async function getstudentTaskEmailId(taskName) {

    var id = [];
    var datasend = [];
    var arr = [];

    await verifyTaskModel.find({
        "tasks.taskName": taskName
    }, function (err, items) {
        if (err)
            console.log(err);
        Object.keys(items).forEach(function (key) {
            id.push(items[key]['studentId']);
        });

    });

    console.log("student in task model", id);
    await profileData.allStudents().then(function (items) {
        var dumyarr = [];
        console.log("all student data", dumyarr);
        Object.keys(items).forEach(function (key) {
            dumyarr.push(items[key]['studentID']);
        });
        arr = dumyarr.filter(item => !id.includes(item));
        console.log("arr", arr);

    });


    for (var i = 0; i < arr.length; i++) {
        await profileData.searchProfileBySid(arr[i]).then(function (items) {
            datasend.push(items[0]._id);
            console.log(datasend);
        })
    }

    console.log("returning count", datasend);
    return datasend;
}

var completedTasks = async function pastStudentTasks(studentid) {
    var taskData = {};
    var arr = [];
    console.log("Inside complete Tasks");
    await verifyTaskModel.find({
        "studentId": studentid
    }, function (err, items) {
        if (err)
            console.log("error", err);
        console.log("items length", items.lenth);
        if (items.length > 0) {
            var tasks = items[0].tasks;
            Object.keys(tasks).forEach(function (key) {
                if (tasks[key]['status'] == "verified") {
                    arr.push(tasks[key]);
                }
            });
        }

    });
    console.log("In array", arr);
    return arr;
}





module.exports.verifyallTask = verifyallTask;
module.exports.verifyStudentTask = verifyStudentTask;
module.exports.studentNumberTask = studentNumberTask;
module.exports.studentNameTask = studentNameTask;
module.exports.getStudentTask = getStudentTask;
module.exports.notifyStudentTask = notifyStudentTask;
module.exports.statusStudentTask = statusStudentTask;
module.exports.studentTaskEmailId = studentTaskEmailId;
module.exports.completedTasks = completedTasks;
module.exports.getstudentsForNotification = getstudentsForNotification;