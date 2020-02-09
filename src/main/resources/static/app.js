var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/arduino_data', function (arduino_data) {
            // The following line is for debugging
            // showGreeting(JSON.parse(arduino_data.body).content);
            var content = JSON.parse(arduino_data.body).content;
            var readings_str = content.split(",");
            var readings = [];
            readings_str.forEach(function(d) {
                readings.push(parseFloat(d));
            });
        var path = svg1.selectAll(".heatmap_path")
          .data(readings);

        path.enter()
          .append('path');

        path
        .transition()
        .duration(2)
          .attr('d', symbol)
          .attr('stroke', 'transparent')
          .style('opacity,', 1)
          .attr('fill', function(d){
            console.log(colorScale1(d));
            return colorScale1(d);
          })
          .attr("transform", function(d, i) {
            var y = 65 + (i % M) * 50 ;
            var x = 165 + (i / N) * 50;
            return "translate(" +x + "," + y + ")"});

        path.exit().remove();
        });

    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

// for Debug purpose
function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});