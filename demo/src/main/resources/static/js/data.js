var genderData = /*[[${genderData}]]*/[];
var ageDataList = /*[[${ageDataList}]]*/[];

// Gender distribution data
var genderLabels = [];
var genderDataValues = [];
var totalEmployees = 0;

genderData.forEach(function (item) {
    genderLabels.push(item.sex);
    genderDataValues.push(item.count);
    totalEmployees += item.count;
});

// Age distribution data
var ageLabels = ageDataList.map(function(item) {
    return item['age_group'];
});

var ageDataValues = ageDataList.map(function(item) {
    return item['count'];
});

// Render doughnut chart for gender distribution
var genderCtx = document.getElementById('genderChart').getContext('2d');
var genderChart = new Chart(genderCtx, {
    type: 'doughnut',
    data: {
        labels: genderLabels,
        datasets: [{
            label: 'Gender Distribution',
            data: genderDataValues,
            backgroundColor: [
                'rgba(54, 162, 235, 0.7)',
                'rgba(255, 99, 132, 0.7)'
            ]
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
});

// Render bar chart for age distribution
var ageCtx = document.getElementById('ageChart').getContext('2d');
var ageChart = new Chart(ageCtx, {
    type: 'bar',
    data: {
        labels: ageLabels,
        datasets: [{
            label: '人数',
            data: ageDataValues,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

// Display total employees count
document.getElementById("totalEmployees").innerText = totalEmployees;

// Display current date and time
var now = new Date();
var year = now.getFullYear();
var month = now.getMonth() + 1;
var day = now.getDate();
var hour = now.getHours();
var minute = now.getMinutes();
var second = now.getSeconds();
document.getElementById("currentDateTime").innerText = year + " 年" + month + "月" + day + "日 " ;