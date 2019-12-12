var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var taskSchema = new Schema({

    _id: {
        type: String
    },
    taskName: {
        type: String
    },
    description: {
        type: String
    },
    dueDate: {
        type: String
    },
    rewards: {
        type: String
    }

}, {
    collection: 'tasks'
});



var taskModel = mongoose.model('taskModel', taskSchema);


// returns the delete task from db
var deleteTask = async function deleteTaskfromDb(taskId) {
    await taskModel.remove({
        _id: taskId
    }, function (err, taskd) {
        if (err)
            console.log(err);
    });
}


//creates new task in db
var createNewTaskDB = async function createNewTaskDB(task) {
    var id = task.taskName + "" + Math.floor(Math.random() * 10);
    new taskModel({
        _id: id,
        taskName: task.taskName,
        description: task.taskDescription,
        dueDate: task.taskDueDate,
        rewards: task.taskReward
    }).save();
    return true;
}


//update task in DB
var updateTaskDB = async function updateTaskDB(task) {

    var userTaskList = {};
    await taskModel.findOneAndUpdate({
        _id: task.taskId
    }, {
        $set: {
            taskName: task.taskName,
            description: task.taskDescription,
            dueDate: task.taskDueDate,
            rewards: task.taskReward
        }
    }, {
        new: true
    }, function (err, items) {
        userTaskList = items;
    });
    return userTaskList;
};



//returns all task from db
var allTask = async function getAllTask() {
    var infoData = {};

    await taskModel.find({}, function (err, task) {
        if (err)
            console.log("error", err);
        if (task.length > 0) {
            infoData = task;
        }
    });
    return infoData;
}

//returns the task data for a particular search
var searchTask = async function getsearchedTask(taskName) {
    var infoData = {};

    await taskModel.find({
        taskName: taskName
    }, function (err, task) {
        if (err)
            console.log("error", err);
        if (task.length > 0) {
            infoData = task;
        }
    });
    return infoData;
}




//returns the task data for a particular search
var searchTaskById = async function getsearchTaskById(taskId) {
    var infoData = {};

    await taskModel.find({
        _id: taskId
    }, function (err, task) {
        if (err)
            console.log("error", err);
        if (task.length > 0) {
            infoData = task;
        }
    });
    return infoData;
}

module.exports.searchTask = searchTask;
module.exports.allTask = allTask;
module.exports.updateTaskDB = updateTaskDB;
module.exports.createNewTaskDB = createNewTaskDB;
module.exports.deleteTask = deleteTask;
module.exports.searchTaskById = searchTaskById;