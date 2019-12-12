var totalNumber;
var taskDataC;
var taskLabelC;
var workshopDataC;
var workshopLabelC;



function getTotalNumber(number) {
    totalNumber = number;
    return totalNumber;
}

function drawTaskChart(taskData, tasklabels) {
    taskDataC = taskData;
    taskLabelC = tasklabels;

    var data1 = {
        datasets: [{
            data: taskData,
            backgroundColor: ['#008080', '#469594', '#6ea9a8', '#93bebd', '#b7d4d3', '#dbe9e9', '#00cdcd', '#00b3b3', '#009a9a', '#004d4d']
    }],

        labels: tasklabels
    };

    var ctx = document.getElementById("taskChart").getContext('2d');
    var myChart = new Chart(ctx, {

        type: 'doughnut',
        data: data1
    });




}

function drawWorkshopChart(workshopData, workshoplabels) {
    workshopDataC = workshopData;
    workshopLabelC = workshoplabels;

    var data = {
        datasets: [{
            data: workshopData,
            backgroundColor: ['#008080', '#469594', '#6ea9a8', '#93bebd', '#b7d4d3', '#dbe9e9', '#00cdcd', '#00b3b3', '#009a9a', '#004d4d']
    }],

        labels: workshoplabels
    };

    var ctx1 = document.getElementById("workshopChart").getContext('2d');
    var myDoughnutChart = new Chart(ctx1, {
        type: 'doughnut',
        data: data
    });



}


function sendEmailTask(taskName, type) {
    $("#headingMessage").html("");

    //alert("send email to " + taskName);
    //ajax call to node to send email

    
    $.ajax({
        type: 'POST',
        dataType: 'JSON',
        url: '/dashboardAdmin/sendEmail',
        data: {
            name: taskName,
            type: type
        },
        success: function (data) {
            console.log("success", data);
            var message;
            if (type == "task") {
                message = "Reminder email has sent successfully to students";
            } else {
                message = "Email has sent successfully to students for feedback";
            }
            $("#headingMessage").html(message);
            $("#sendemailModal").hide();
            $("#emailSent").show();
        },
        error:function(data){
            $("#headingMessage").html("Some error ocurred");
            $("#sendemailModal").hide();
            $("#emailSent").show();
        }
    });

}



function tableTaskData() {
    $("#tableData").html("");

    var td = "";
    var type = "task";
    for (var i = 0; i < taskLabelC.length; i++) {
        var notdone = totalNumber - taskDataC[i];
        td += '<tr><td>' + taskLabelC[i] + '</td><td>' + taskDataC[i] + '</td><td>' + notdone + '</td><td><i class="fa fa-envelope" onclick="sendEmailTask(\'' + taskLabelC[i] + '\'' + ',\'' + type + '\')" style="cursor:pointer;" aria-hidden="true"></i></td></tr>';
    }

    var th = '<table style="font-size:18px;"><tr style="background-color:#009688;color: white;"><th>Task Name</th><th>Students Done Task</th><th>Students Not Done Task</th><th>Action</th></tr><tbody>' + td + '</tbody></table>';

    $("#tableData").html(th);
    $("#sendemailModal").show();
}




function tableWorkshopData() {
    $("#tableData").html("");

    var td = "";
    var type = "workshop";
    for (var i = 0; i < workshopLabelC.length; i++) {
        td += '<tr><td>' + workshopLabelC[i] + '</td><td>' + workshopDataC[i] + '</td><td><i class="fa fa-envelope" onclick="sendEmailTask(\'' + workshopLabelC[i] + '\'' + ',\'' + type + '\')" style="cursor:pointer;" aria-hidden="true"></i></td></tr>';
    }

    var th = '<table style="font-size:18px;"><tr style="background-color:#009688;color: white;"><th>workshop Name</th><th>Students Registered</th><th>Action</th></tr><tbody>' + td + '</tbody></table>';

    $("#tableData").html(th);
    $("#sendemailModal").show();
}