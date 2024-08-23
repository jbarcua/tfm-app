import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as Rx from "rxjs/Rx";
import { from, Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Layer } from '../layer';
import { TokenStorageService } from './token-storage.service';
import { environment} from "../../environments/environment";
import * as process from "process";

@Injectable({
  providedIn: 'root'
})
export class PlotsServiceService {

  constructor(private httpClient: HttpClient, private tokenStorage: TokenStorageService) {}

  getPlots(): Observable<Layer[]> {
    //this.tokenStorage.getUser();
    // @ts-ignore
    // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
    // de forma dinamica al lanzar el contenedor Docker.
    return this.httpClient.get<Layer[]>(`http://` + window.env.APP_PLOTS_SERVICE + `:8080/plots/owner/pepe`).
        pipe(
           map((data: Layer[]) => {
             console.log(data);
             return data;
           }), catchError( error => {
             return throwError( 'Something went wrong!' );
           })
        )
    }

    getPlotByCatastralReference(catastralReference: String): Observable<Layer> {
      // @ts-ignore
      // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
      // de forma dinamica al lanzar el contenedor Docker.
      return this.httpClient.get<Layer>(`http://` + window.env.APP_PLOTS_SERVICE + `8080/plots/${catastralReference}`).
          pipe(
             map((data: Layer) => {
               console.log(data);
               return data;
             }), catchError( error => {
               return throwError( 'Something went wrong!' );
             })
          )
      }

    addPlot(owner:String, formData: FormData) {
      // @ts-ignore
      // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
      // de forma dinamica al lanzar el contenedor Docker.
      this.httpClient.post<any>(`http://` + window.env.APP_PLOTS_SERVICE + `8080/plots/file/${owner}`, formData).subscribe(
        (res) => console.log(res),
        (err) => console.log(err)
      );
    }
}
