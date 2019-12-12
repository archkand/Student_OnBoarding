var profileData = require('./profileDB');
var taskDataDB = require('./taskDB');
var verifyData = require('./verifyDB');
var workshopDataDB = require('./workshopDB');

var numberS = async function getTotalNumber() {
    var count;
   await profileData.totalStudentsNumber().then(
        function (result) {
            count= result;
        });
    return count;
}

var taskData = async function getTaskData() {
    var dataArray = [];
    var dataLabel = [];
    var retr = {};


    await verifyData.studentNumberTask().then(function (result) {
        retr.dataArray = result.dataArray;
        retr.dataLabel = result.dataLabel;
    });

    console.log("return from task", retr);
    return retr;
}

var workshopData = async function getWorkshopData() {
    var dataArray = [];
    var dataLabel = [];
    var retr = {};


    await workshopDataDB.studentNumberWorkshop().then(function (result) {
        retr.dataArray = result.dataArray;
        retr.dataLabel = result.dataLabel;
    });

    console.log("return from workshop", retr);
    return retr;


}


var taskDataEmail = async function getTaskDataEmail(taskName) {
    var emailIds = [];
  
    await verifyData.studentTaskEmailId(taskName).then(function (result) {
       emailIds =result;
    });

    console.log("return from task", emailIds);
    return emailIds;
}


var workshopDataEmail = async function getWorkshopDataEmail(workshopName) {
    var emailIds = [];
  
    await workshopDataDB.studentWorkshopEmailId(workshopName).then(function (result) {
       emailIds =result;
    });

    console.log("return from task", emailIds);
    return emailIds;
}



module.exports.numberS = numberS;
module.exports.taskData = taskData;
module.exports.workshopData = workshopData;
module.exports.taskDataEmail = taskDataEmail;
module.exports.workshopDataEmail = workshopDataEmail;