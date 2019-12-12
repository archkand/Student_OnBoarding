var information = function (infoId, infoName, infoDetail, infoLink) {
    var informationModel = {
        infoId: infoId,
        infoName: infoName,
        infoDetail: infoDetail,
        infoLink: infoLink
        
    };
    return informationModel;
};

module.exports.information = information;