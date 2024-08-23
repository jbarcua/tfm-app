import { Component, OnInit } from '@angular/core';
import { Layer } from '../layer';
import { Sensor } from '../sensor';
import { SensorService } from '../_services/sensor.service';

@Component({
  selector: 'app-sensor',
  templateUrl: './sensor.component.html',
  styleUrls: ['./sensor.component.css']
})
export class SensorComponent implements OnInit {

  sensors: Sensor[]; 

  constructor(private sensorService: SensorService) { }

  ngOnInit(): void {

    this.sensorService.getSensors('').subscribe((res) => {
      this.sensors = res;
    });
  }

}
