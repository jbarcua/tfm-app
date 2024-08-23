import { Component, OnInit } from '@angular/core';
import { Chart } from 'angular-highcharts';
import * as Highcharts from 'highcharts';
import { Options } from 'highcharts';
import { of, Subscription } from 'rxjs';
import { concatMap, delay } from 'rxjs/operators';
import { webSocket } from 'rxjs/webSocket';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  pieChart: Chart;
  barChart: Chart;
  wsChart: Chart;
  barChartOptions: Options;
  pieChartOptions: Options;
  title = 'Angular 9 HighCharts';
  rate: any;
  rate$: Subscription;
  Highcharts: typeof Highcharts = Highcharts;
  chardata: any[] = [];
  chartOptions: any;
  subject = webSocket('wss://ws.coincap.io/prices?assets=bitcoin')
  url = 'http://localhost:8080/ws'
  client: any;
  constructor() { }

  ngOnInit() {
    this.barChartPopulation();
    this.pieChartBrowser();
    this.otherChartPopulation();
    this.connection();
  }

  buttonclick() {
    this.client.send("/app/statistics/123");
  }

  connection(){
    let ws = new SockJS(this.url);
    this.client = Stomp.over(ws);
    let that = this;
	
    this.client.connect({}, (frame) => {
      that.client.subscribe("/topic/statistics/123", (message) => {
        if(message.body) {
          console.log(JSON.parse(message.body).value);
          this.wsChart.addPoint(Number(JSON.parse(message.body).value));
        }
      });
    });
  }
  

  otherChartPopulation () {
    this.chartOptions = {
      series: [{
        data: this.chardata,
      }, ],
      chart: {
        type: "line",
        zoomType: 'x'
      },
      title: {
        text: "linechart",
      },
    };
    this.wsChart = new Chart(this.chartOptions);    
  }
  

  barChartPopulation() {

    this.barChartOptions = {
      chart: {
        type: 'bar'
      },
      title: {
        text: 'Historic World Population by Region'
      },
      xAxis: {
        categories: ['Africa', 'America', 'Asia', 'Europe', 'Oceania'],
      },
      yAxis: {
        min: 0,
        title: {
          text: 'Population (millions)',
          align: 'high'
        },
      },
      tooltip: {
        valueSuffix: ' millions'
      },
      plotOptions: {
        bar: {
          dataLabels: {
            enabled: true
          }
        }
      },
      series: [{
        type: 'line',
        name: 'Year 1800',
        data: [107, 31, 635, 203, 2]
      }, {
        type: 'line',
        name: 'Year 1900',
        data: [133, 156, 947, 408, 6]
      }, {
        type: 'line',
        name: 'Year 2000',
        data: [814, 841, 3714, 727, 31]
      }, {
        type: 'line',
        name: 'Year 2016',
        data: [1216, 1001, 4436, 738, 40]
      }]
    };
    this.barChart = new Chart(this.barChartOptions);
  }

  pieChartBrowser() {
    
    this.pieChartOptions = {
      chart: {
        plotShadow: false,
        type: 'pie'
      },
      title: {
        text: 'Browser market shares in October, 2019'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
          }
        }
      },
      series: [{
        name: 'Brands',
        colorByPoint: true,
        type: 'line',
        data: [{
          name: 'Chrome',
          y: 61.41,
          selected: true
        }, {
          name: 'Internet Explorer',
          y: 11.84
        }, {
          name: 'Firefox',
          y: 10.85
        }, {
          name: 'Edge',
          y: 4.67
        }, {
          name: 'Safari',
          y: 4.18
        }, {
          name: 'Sogou Explorer',
          y: 1.64
        }, {
          name: 'Opera',
          y: 1.6
        }, {
          name: 'QQ',
          y: 1.2
        }, {
          name: 'Other',
          y: 2.61
        }]
      }]
    };
    this.pieChart = new Chart(this.pieChartOptions);
    this.pieChart.ref$.subscribe(c => console.log(c.options.chart));
  }
}

