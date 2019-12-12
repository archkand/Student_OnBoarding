var workshop = function (workshopId, workshopName, workshopDescription, workshopDate) {
    var workshopModel = {
        workshopId: workshopId,
        workshopName: workshopName,
        workshopDescription: workshopDescription,
        workshopDate: workshopDate
    };
    return workshopModel;
};

module.exports.workshop = workshop;