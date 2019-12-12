var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var informationSchema = new Schema({

    _id: {
        type: String
    },
    name: {
        type: String
    },
    detail: {
        type: String
    },
    link: {
        type: String
    }

}, {
    collection: 'informations'
});



var informationModel = mongoose.model('informationModel', informationSchema);


// returns the delete information from db
var deleteInformation = async function deleteInformationfromDb(informationId) {
    await informationModel.remove({
        _id: informationId
    }, function (err, informationd) {
        if (err)
            console.log(err);
        console.log('informationd', informationd);
    });
}


//creates new information in db
var createNewInformationDB = async function createNewInformationDB(information) {
    var id = information.infoName + "" + Math.floor(Math.random() * 10);

    new informationModel({
        _id: id,
        name: information.infoName,
        detail: information.infoDetail,
        link: information.infoLink
    }).save();
    return true;
}


//update information in DB
var updateInformationDB = async function updateInformationDB(information) {

    var userInformationList = {};
    await informationModel.findOneAndUpdate({
        _id: information.infoId
    }, {
        $set: {
            name: information.infoName,
            detail: information.infoDetail,
            link: information.infoLink
        }
    }, {
        new: true
    }, function (err, items) {
        userInformationList = items;
    });
    return userInformationList;
};



//returns all information from db
var allInformation = async function getAllInformation() {
    var infoData = {};

    await informationModel.find({}, function (err, information) {
        if (err)
            console.log("error", err);
        if (information.length > 0) {
            infoData = information;
        }
    });
    console.log("Information data in the db", infoData);
    return infoData;
}

//returns the information data for a particular search
var searchInformation = async function getsearchedInformation(informationName) {
    var infoData = {};

    await informationModel.find({name:informationName}, function (err, information) {
        if (err)
            console.log("error", err);
        if (information.length > 0) {
            infoData = information;
        }
    });
    console.log("Search information data in the db", infoData);
    return infoData;
}


module.exports.allInformation = allInformation;
module.exports.searchInformation = searchInformation;
module.exports.deleteInformation = deleteInformation;
module.exports.createNewInformationDB = createNewInformationDB;
module.exports.updateInformationDB = updateInformationDB;
