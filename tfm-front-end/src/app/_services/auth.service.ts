import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as process from "process";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    // @ts-ignore
    // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
    // de forma dinamica al lanzar el contenedor Docker.
    return this.http.post(`http://` + window.env.FRONT_ENDPOINT + ':8999/auth/authenticate', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, password: string): Observable<any> {
    // @ts-ignore
    // Variable "env" no existe en este momento, se definira dentro del "src/index.html"
    // de forma dinamica al lanzar el contenedor Docker.
    return this.http.post(`http://` + window.env.FRONT_ENDPOINT + ':8999/auth/register', {
      username,
      password
    }, httpOptions);
  }
}
