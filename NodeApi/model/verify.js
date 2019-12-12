var verify = function (verifyId, studentId, studentName, taskId, taskName) {
    var verifyModel = {
        verifyId: verifyId,
        studentId: studentId,
        studentName: studentName,
        taskId: taskId,
        taskName:taskName        
    };
    return verifyModel;
};


var verifyStudentTask = function (verifyTaskId, studentId, taskId, taskName,status,notify) {
    var verifyModel = {
        verifyTaskId: verifyTaskId,
        studentId: studentId,
        tasks:[{
            taskId:taskId,
            taskName:taskName,
            status:status,
            notify:notify
        }]     
    };
    return verifyModel;
};




module.exports.verify = verify;
module.exports.verifyStudentTask = verifyStudentTask;