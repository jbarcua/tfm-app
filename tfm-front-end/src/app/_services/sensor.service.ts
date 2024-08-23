import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { from, Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Sensor } from '../sensor';
import { SensorRequest } from '../sensor-request';
import {environment} from "../../environments/environment";
import * as process from "process";

@Injectable({
  providedIn: 'root'

})
export class SensorService {

  constructor(private httpClient: HttpClient) { }

  getSensor(sensorId: String): Observable<Sensor[]> {
    // @ts-ignore
    // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
    // de forma dinamica al lanzar el contenedor Docker.
    return this.httpClient.get<Sensor[]>(`http://` + window.env.APP_SENSOR_SERVICE + `:8081/sensor/${sensorId}`).
        pipe(
           map((data: Sensor[]) => {
             console.log(data);
             return data;
           }), catchError( error => {
             return throwError( 'Something went wrong!' );
           })
        )
    }

    deleteSensor(sensorId: String) {
      // @ts-ignore
      // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
      // de forma dinamica al lanzar el contenedor Docker.
      return this.httpClient.delete<any>(`http://` + window.env.APP_SENSOR_SERVICE + `:8081/sensor/${sensorId}`).subscribe(
        (res) => console.log(res),
        (err) => console.log(err)
      );
    }

    getSensors(catastralReference: String): Observable<Sensor[]> {
      // @ts-ignore
      // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
      // de forma dinamica al lanzar el contenedor Docker.
      return this.httpClient.get<Sensor[]>(`http://` + window.env.APP_SENSOR_SERVICE + `:8081/sensor`, {
        params: {
          owner: 'pepe'
        }}).
          pipe(
             map((data: Sensor[]) => {
               console.log(data);
               return data;
             }), catchError( error => {
               return throwError( 'Something went wrong!' );
             })
          )
      }

  addSensor(sensorData: SensorRequest) {
    // @ts-ignore
    // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
    // de forma dinamica al lanzar el contenedor Docker.
    this.httpClient.post<any>(`http://` + window.env.APP_SENSOR_SERVICE + `:8081/sensor`, sensorData).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );
  }
}
