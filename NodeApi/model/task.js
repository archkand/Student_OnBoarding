var task = function (taskId, taskName, taskDescription, taskDueDate, taskReward) {
    var taskModel = {
        taskId: taskId,
        taskName: taskName,
        taskDescription: taskDescription,
        taskDueDate: taskDueDate,
        taskReward:taskReward        
    };
    return taskModel;
};

module.exports.task = task;