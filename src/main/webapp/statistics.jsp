<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 18/12/2019
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Statistics</title>
    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/styleMain3.css">
    <link rel="icon" href="static/media/icon.png">

    <script>
        window.onload = function () {
            let data = ${users};
            //console.log(data);
            let formattedDataSend = [];
            let formattedDataReceived = [];
            Object.keys(data).forEach(function (key) { formattedDataSend.push({label: key, y: data[key]["stats"+key].sent});});
            Object.keys(data).forEach(function (key) { formattedDataReceived.push({label: key, y: data[key]["stats"+key].received});});
            // console.log(formattedDataSend);
            // console.log(formattedDataReceived);
            var chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                title:{
                    text: "Amount of stars send/received by users",
                    fontFamily:"arial,Helvetica"
                },
                axisY: {
                    title: "Amount of stars",
                    titleFontColor: "#4F81BC",
                    lineColor: "#4F81BC",
                    labelFontColor: "#4F81BC",
                    tickColor: "#4F81BC"
                },
                toolTip: {
                    shared: true
                },
                legend: {
                    cursor:"pointer",
                    itemclick: toggleDataSeries
                },
                data: [{
                    type: "column",
                    name: "Stars send",
                    legendText: "Stars send",
                    showInLegend: true,
                    dataPoints:formattedDataSend
                },
                    {
                        type: "column",
                        name: "Stars received",
                        legendText: "Stars received",
                        axisYType: "secondary",
                        showInLegend: true,
                        dataPoints:formattedDataReceived
                    }]
            });
            chart.render();

            function toggleDataSeries(e) {
                if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                    e.dataSeries.visible = false;
                }
                else {
                    e.dataSeries.visible = true;
                }
                chart.render();
            }

            var json = ${tags};
            var total =${totaltags};
            console.log(total);
            //console.log(json);
            var datapie = [];
            Object.keys(json).forEach(function (key) {
                console.log((json[key]/total)*100);
                datapie.push({y: ((json[key]/total)*100).toFixed(2), label: key});
            });
            console.log(datapie);
            var chart2 = new CanvasJS.Chart("chartContainer2", {
                animationEnabled: true,
                title: {
                    text: "Amount of tags",
                    fontFamily:"arial,Helvetica"
                },
                data: [{
                    type: "pie",
                    startAngle: 240,
                    yValueFormatString: "##0.00\"%\"",
                    indexLabel: "{label} {y}",
                    dataPoints: datapie
                }
                ]
            });
            chart2.render();
        }
    </script>
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</head>
<body>
<%@ include file="components/navigation.jspf" %>
<main id="statPage">
    <%@ include file="components/addStar.jspf" %>


    <div id="chartContainer"></div>
    <div id="chartContainer2"></div>

    <div id="chartContainer3">
        <h3>coming soon</h3>
    </div>

</main>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>