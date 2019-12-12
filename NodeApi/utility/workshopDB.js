var profileData = require('./profileDB');
var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var workshopSchema = new Schema({

    _id: {
        type: String
    },
    workshopName: {
        type: String
    },
    description: {
        type: String
    },
    date: {
        type: String
    }

}, {
    collection: 'workshops'
});


var workshopStudentSchema = new Schema({

    _id: {
        type: String
    },
    studentId: {
        type: String
    },
    workshops: {
        type: [{
            workshopId: {
                type: String
            },
            workshopName: {
                type: String
            }
  }]
    }

}, {
    collection: 'studentWorkshops'
});






var workshopModel = mongoose.model('workshopModel', workshopSchema);
var workshopStudentModel = mongoose.model('workshopStudentModel', workshopStudentSchema);

// returns the delete workshop from db
var deleteWorkshop = async function deleteworkshopfromDb(workshopId) {
    await workshopModel.remove({
        _id: workshopId
    }, function (err, workshopd) {
        if (err)
            console.log(err);
    });
}


//creates new workshop in db
var createNewWorkshopDB = async function createNewWorkshopDB(workshop) {
    var id = workshop.workshopName + "" + Math.floor(Math.random() * 10);

    new workshopModel({
        _id: id,
        workshopName: workshop.workshopName,
        description: workshop.workshopDescription,
        date: workshop.workshopDate
    }).save();
    return true;
}


//update workshop in DB
var updateWorkshopDB = async function updateWorkshopDB(workshop) {

    var workshopList = {};
    await workshopModel.findOneAndUpdate({
        _id: workshop.workshopId
    }, {
        $set: {
            workshopName: workshop.workshopName,
            description: workshop.workshopDescription,
            date: workshop.workshopDate
        }
    }, {
        new: true
    }, function (err, items) {
        workshopList = items;
    });
    return workshopList;
};



//returns all Workshop from db
var allWorkshop = async function getAllWorkshop() {
    var infoData = {};

    await workshopModel.find({}, function (err, workshop) {
        if (err)
            console.log("error", err);
        if (workshop.length > 0) {
            infoData = workshop;
        }
    });
    return infoData;
}

//returns the workshop data for a particular search
var searchWorkshop = async function getsearchedWorkshop(workshopName) {
    var infoData = {};

    await workshopModel.find({
        workshopName: workshopName
    }, function (err, workshop) {
        if (err)
            console.log("error", err);
        if (workshop.length > 0) {
            infoData = workshop;
        }
    });
    return infoData;
}


//returns the workshop data for a particular search
var searchWorkshopById = async function getsearchedWorkshopById(workshopId) {
    var infoData = {};

    await workshopModel.find({
        _id: workshopId
    }, function (err, workshop) {
        if (err)
            console.log("error", err);
        if (workshop.length > 0) {
            infoData = workshop;
        }
    });
    return infoData;
}


//add in student workshop for registeration

var registerStudentWorkshop = async function getregisterStudentWorkshop(studentId, workshopId) {

    await workshopStudentModel.find({
        studentId: studentId
    }, function (err, workshop) {
        if (err)
            console.log("error", err);
        if (workshop.length > 0) {
            searchWorkshopById(workshopId).then(function (result) {
                //push the workshop
                var workshopName = result[0].workshopName;
                workshopStudentModel.findOneAndUpdate({
                    studentId: studentId,
                }, {
                    $push: {
                        workshops: {
                            workshopId: workshopId,
                            workshopName: workshopName
                        }

                    }
                }, {
                    new: true
                }, function (err, items) {
                    if (err)
                        console.log(err);
                });

                profileData.insertPastWorkshop(studentId, workshopId, workshopName, result[0].date).then(function (iresult) {
                    return result;
                });
            });


        } else {
            var id = workshopId + "" + Math.floor(Math.random() * 10);
            searchWorkshopById(workshopId).then(function (result) {

                var workshopName = result[0].workshopName;
                new workshopStudentModel({
                    _id: id,
                    studentId: studentId,
                    workshops: [{
                        workshopId: workshopId,
                        workshopName: workshopName
                    }]
                }).save();

                profileData.insertPastWorkshop(studentId, workshopId, workshopName, result[0].date).then(function (iresult) {
                    return result;
                });

            });
        }
    });



    return true;
}



//return number of students in particular task 
var studentNumberWorkshop = async function getstudentNumberWorkshop() {
    var count = [];
    var name = [];
    var datasend = {};
    var workshops;
    await allWorkshop().then(function (result) {
        workshops = result;
    });

    console.log("result here", workshops);

    for (var i = 0; i < workshops.length; i++) {
        var workshopId = workshops[i]._id;
        var workshopLabel = workshops[i].workshopName;
        console.log("task id", workshopId);
        await workshopStudentModel.find({
            "workshops.workshopId": workshopId
        }, function (err, items) {
            if (err)
                console.log(err);
            console.log("this items are task", items);
            count.push(items.length);
            name.push(workshopLabel);
        });
    }

    datasend.dataArray = count;
    datasend.dataLabel = name;
    console.log("returning count", datasend);
    return datasend;
}



// return email id of student registered for workshop
var studentWorkshopEmailId = async function getstudentWorkshopEmailId(workshopName) {

    var id = [];
    var datasend = [];


    await workshopStudentModel.find({
        "workshops.workshopName": workshopName
    }, function (err, items) {
        if (err)
            console.log(err);
        console.log("this items are task", items);
        Object.keys(items).forEach(function (key) {
            id.push(items[key]['studentId']);
        });
    });




    console.log("result here", id);

    for (var i = 0; i < id.length; i++) {
        await profileData.searchProfileBySid(id[i]).then(function (items) {
            datasend.push(items[0]._id);
            console.log(datasend);
        })
    }

    console.log("returning count", datasend);
    return datasend;
}


var getStudentWorkshops = async function getStudentWorkshops(studentId) {
    var workshops = [];
    await workshopStudentModel.find({
        studentId: studentId
    }, function (err, items) {
        if (err)
            console.log(err);
        if (items.length > 0) {
            workshops = items[0].workshops;
        }

    });

    return workshops;
}




module.exports.deleteWorkshop = deleteWorkshop;
module.exports.createNewWorkshopDB = createNewWorkshopDB;
module.exports.updateWorkshopDB = updateWorkshopDB;
module.exports.allWorkshop = allWorkshop;
module.exports.searchWorkshop = searchWorkshop;
module.exports.registerStudentWorkshop = registerStudentWorkshop;
module.exports.studentNumberWorkshop = studentNumberWorkshop;
module.exports.studentWorkshopEmailId = studentWorkshopEmailId;
module.exports.getStudentWorkshops = getStudentWorkshops;