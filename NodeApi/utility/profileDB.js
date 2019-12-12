var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var verifyDB = require('./verifyDB');

var profileSchema = new Schema({

    _id: {
        type: String
    },
    studentID: {
        type: String
    },
    rewards: {
        type: String
    },
    name: {
        type: String
    },
    notification: {
        type: String
    },
    studentImg: {
        type: String
    },
    pastTask: {
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
    },
    studentNotify: {
        type: Array
    },
    pastWorkshop: {
        type: [{
            workshopId: {
                type: String
            },
            workshopName: {
                type: String
            },
            workshopDate: {
                type: String
            }
  }]
    },
    step: {
        type: String
    }
}, {
    collection: 'students'
});

var profileModel = mongoose.model('profileModel', profileSchema);

var searchProfile = async function getCurrentProfile(emailId) {
    var profileData = {};

    await profileModel.find({
        _id: emailId
    }, function (err, profile) {
        if (err)
            console.log("error", err);
        if (profile.length > 0) {
            profileData = profile;
        }
    });
    console.log("Profile Information in the db", profileData);
    return profileData;
}



var totalStudentsNumber = async function getStudents() {
    var totalStudent;

    await profileModel.find({}, function (err, profile) {
        if (err)
            console.log("error", err);
        if (profile.length > 0) {
            totalStudent = profile.length;
        }
    });
    console.log("return profile data count", totalStudent);
    return totalStudent;
}

var loginProfile = async function login(emailId, password) {
    var response = "";
    await profileModel.find({
        _id: emailId,
        studentID: password
    }, function (err, profile) {
        if (err)
            console.log("error", err);
        if (profile.length > 0) {
            response = "success";
            console.log("login successful");
        } else {
            response = "unsuccessful";
            console.log("login unsuccessful");
        }
    });
    return response;
}


var searchProfileBySid = async function getsearchProfileBySid(studentid) {
    var profileData = {};

    await profileModel.find({
        studentID: studentid
    }, function (err, profile) {
        if (err)
            console.log("error", err);
        if (profile.length > 0) {
            profileData = profile;
        }
    });
    console.log("Profile Information in the db", profileData);
    return profileData;
}



//notify task array
var updatestudentNotify = async function getupdatestudentNotify(studentId, taskId, notify) {
   console.log("task id while pushing",taskId);
    if(notify=="ON"){
       
            await profileModel.findOneAndUpdate({
                studentID: studentId
            }, {
                $push: {
                    "studentNotify":taskId
                    
                }
            
        },
        function (err, profile) {
            if (err)
                console.log("error", err);
        });
        
       }else{
       
               await profileModel.findOneAndUpdate({
                studentID: studentId
            }, {
                $pull: {
                    "studentNotify":taskId
                    
                }
            
        },
        function (err, profile) {
            if (err)
                console.log("error", err);
        });
       
       }

return true;
}


//returns all student

var allStudents = async function getAllStudents() {
    var totalStudent;

    await profileModel.find({}, function (err, profile) {
        if (err)
            console.log("error", err);
        if (profile.length > 0) {
            totalStudent = profile;
        }
    });
    console.log("return profile data count", totalStudent);
    return totalStudent;
}

var setSteps = async function setStudentStep(studentId) {
    console.log("Inside setSteps");
    var count = 0;
    var studentStep = "";
    var pastTasks = {};
    await verifyDB.completedTasks(studentId).then(function (result) {
        pastTasks = result;
        console.log("Past Tasks in setSteps", pastTasks);
        count = result.length;
        console.log("count value ", count);
        if (parseInt(count) == 2) {
            studentStep = "1";
        } else if (parseInt(count) > 2 && parseInt(count) <= 4) {
            studentStep = "2";
        } else if (parseInt(count) >= 4) {
            studentStep = "3";
        } else {
            studentStep = "0";
        }
        console.log("Before model count value", studentStep);
        profileModel.findOneAndUpdate({
            studentID: studentId
        }, {
            $set: {
                "step": studentStep,
                "pastTask": pastTasks
            }
        }, function (err, items) {
            if (err)
                console.log(err);
        });
    });
    return true;
}


var insertPastWorkshop = async function (studentId, workshopId, workshopName, workshopDate) {
    console.log("here in insert workshop", studentId, workshopId, workshopName, workshopDate);
    profileModel.findOneAndUpdate({
        studentID: studentId
    }, {
        $push: {
            "pastWorkshop": {
                workshopId: workshopId,
                workshopName: workshopName,
                workshopDate: workshopDate
            },
        }
    }, function (err, items) {
        if (err)
            console.log(err);
    });

}

module.exports.totalStudentsNumber = totalStudentsNumber;
module.exports.searchProfile = searchProfile;
module.exports.loginProfile = loginProfile;
module.exports.searchProfileBySid = searchProfileBySid;
module.exports.allStudents = allStudents;
module.exports.setSteps = setSteps;
module.exports.insertPastWorkshop = insertPastWorkshop;
module.exports.updatestudentNotify = updatestudentNotify;