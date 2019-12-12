var profile = function (_id, studentID, rewards, name, notification, studentImg, pastTask, pastWorkshop, step,studentNotify) {
    var profileModel = {
        _id: _id,
        studentID: studentID,
        rewards: rewards,
        name: name,
        notification: notification,
        studentImg: studentImg,
        pastTask: pastTask,
        pastWorkshop: pastWorkshop,
        step: step,
        studentNotify:studentNotify

    };
    return profileModel;
};

module.exports.profile = profile;
