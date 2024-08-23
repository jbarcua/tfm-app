import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MapComponent, NgbdModalContent } from './map/map.component';
import {LeafletModule} from '@asymmetrik/ngx-leaflet';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { StatisticsComponent } from './statistics/statistics.component';
import { ChartModule } from 'angular-highcharts';
import { DetailComponent, NgbdModalContentSensor, NgbdModalSensorCsvContent } from './detail/detail.component';
import { IconsModule } from './icons/icons.module';
import { SensorComponent } from './sensor/sensor.component';
import { PlotsComponent } from './plots/plots.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    LoginComponent,
    RegisterComponent,
    StatisticsComponent,
    DetailComponent,
    NgbdModalContent,
    NgbdModalContentSensor,
    NgbdModalSensorCsvContent,
    SensorComponent,
    PlotsComponent
  ],
  imports: [
    BrowserModule,
    LeafletModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ChartModule,
    IconsModule,
    ReactiveFormsModule,
    RouterModule
    
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
