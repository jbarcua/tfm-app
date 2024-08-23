import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {latLng, MapOptions, tileLayer, Map, Marker, icon,marker, geoJSON, latLngBounds, polygon, LeafletMouseEvent, LatLng} from 'leaflet';
import { PlotsServiceService } from '../_services/plots-service.service';
import { TokenStorageService } from '../_services/token-storage.service';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SensorService } from '../_services/sensor.service';
import { Sensor } from '../sensor';
import { SensorRequest } from '../sensor-request';

@Component({
  selector: 'ngbd-modal-content',
  template: `
    <div class="modal-header">
      <h4 class="modal-title">Import plots</h4>

      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
    <form [formGroup] = "uploadForm" (ngSubmit)="onSubmit()">   
      <p>Select a file to import</p>
      <input type="file" class="form-control-file" name="profile" (change)="onFileSelect($event)" />
      </form>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-primary" (click)="onSubmit()">Import</button>
      <button type="button" class="btn btn-outline-dark" (click)="activeModal.close('Close click')">Close</button>
    </div>
    
  `
})
export class NgbdModalSensorCsvContent implements OnInit {

  uploadForm: FormGroup;  

  saved: EventEmitter<any> = new EventEmitter();

  constructor(private formBuilder: FormBuilder, public activeModal: NgbActiveModal) {}

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }

  onSubmit () {
    const formData = new FormData();
    var file = this.uploadForm.get('profile')!.value;
    formData.append('file', file);
    this.activeModal.close(formData);
  }


  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.uploadForm.get('profile')!.setValue(file);
    }
  }
}

@Component({
  selector: 'ngbd-modal-content',
  template: `
    <div class="modal-header">
      <h4 class="modal-title">Add a new sensor</h4>

      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <form [formGroup] = "sensorForm" (ngSubmit)="onSubmit()">   
        <input type="text" class="form-control-file" name="lat" value="{{lat}}" disabled />
        <input type="text" class="form-control-file" name="long" value="{{long}}" disabled />
        <select class="form-select" name="type" formControlName ="type" aria-label="Sensor type">
          <option selected>Open this select menu</option>
          <option value="temperature">Temperature</option>
          <option value="pression">Pression</option>
          <option value="precipitation">Precipitation</option>
        </select>
      </form>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-primary" (click)="onSubmit()">Add</button>
      <button type="button" class="btn btn-outline-dark" (click)="activeModal.close('Close click')">Close</button>
    </div>
  `
})
export class NgbdModalContentSensor implements OnInit {
  @Input() long;
  @Input() lat;
  @Input() catastralReference;
  @Input() owner;
  sensorForm: FormGroup;  
  saved: EventEmitter<any> = new EventEmitter();

  constructor(private formBuilder: FormBuilder, public activeModal: NgbActiveModal) {}


  ngOnInit() {
    this.sensorForm = this.formBuilder.group( {
      type: ['']
    });
  }

  onSubmit () {
    var type = this.sensorForm.get('type')!.value;
    let sensor = new SensorRequest(this.owner, this.catastralReference, this.lat, this.long, type);
    this.activeModal.close(sensor);
  }
}


@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
  catastralReference: String;
  map: Map;
  mapOptions: MapOptions;
  currentUser: any;
  marker: Marker;
  file: FormData;
  sensorIcon = icon({
    iconUrl: 'assets/images/sensor.png',
    iconSize:     [30, 30], // size of the icon
    shadowSize:   [0, 0], // size of the shadow
    iconAnchor:   [0, 0], // point of the icon which will correspond to marker's location
    shadowAnchor: [0, 0],  // the same for the shadow
    popupAnchor:  [0, 0] // point from which the popup should open relative to the iconAnchor
});

  constructor(private modalService: NgbModal, private token: TokenStorageService, private httpClient : HttpClient, private router: Router, private sensorService : SensorService, private plotService : PlotsServiceService, private $route: ActivatedRoute) {
  }

  ngOnInit() {
    this.catastralReference = this.$route.snapshot.paramMap.get('id')!; 
    this.initializeMapOptions();
  }

  open() {
    const modalRef = this.modalService.open(NgbdModalContentSensor);
    modalRef.componentInstance.lat = this.marker.getLatLng().lat;
    modalRef.componentInstance.long = this.marker.getLatLng().lng;
    modalRef.componentInstance.catastralReference = this.catastralReference;
    modalRef.componentInstance.owner = 'pepe';
    modalRef.result.then((result) => {
      if (result) {
        console.log("aseeeeeeedf")
        console.log(result);
        this.addSensor(result);
      }
      });
  }

  addSensor (receivedEntry: SensorRequest) {
    this.sensorService.addSensor(receivedEntry);
  }

  getPlotData (catastralReference: String) {
    this.plotService.getPlotByCatastralReference(catastralReference);
  }

  
  onMapReady(map: Map) {
    this.map = map;
    this.fillMap();

    /*
    var polygonPoints = [
      latLng(37.786617, -122.404654),
      latLng(37.797843, -122.407057),
      latLng(37.798962, -122.398260),
      latLng(37.794299, -122.395234)];
    var poly = polygon(polygonPoints);
    this.map.fitBounds(poly.getBounds());
    poly.addTo(this.map);
    */
  }

  private initializeMapOptions() {
    this.mapOptions = {
      center: latLng(0, 0),
      zoom: 12,
      minZoom: 12,
      layers: [
        tileLayer(
          'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
          {
            maxZoom: 18,
            attribution: 'Map data © OpenStreetMap contributors'
          })
      ],
    };
    
    /*
    var polygonPoints = [
      latLng(37.786617, -122.404654),
      latLng(37.797843, -122.407057),
      latLng(37.798962, -122.398260),
      latLng(37.794299, -122.395234)];
    var poly = polygon(polygonPoints);
    var bounds = latLngBounds(latLng(5.499550, -167.276413), latLng(83.162102, -52.233040));
    */
  }

  private fillMap() {
    this.plotService.getPlotByCatastralReference(this.catastralReference).subscribe((plot) => {
      var geoData = plot.geoData;
      let plotGeoJson = geoJSON(geoData).addTo(this.map);
      this.map.fitBounds(plotGeoJson.getBounds());
      plot.sensorList.forEach(sensor => {
        var lat = sensor.latitude;
        var long = sensor.longitude;
        new Marker(new LatLng(lat, long), {icon: this.sensorIcon}).addTo(this.map);
      });
    });
    const whenClicked = (e) => {
      if (this.marker != null) {
        this.map.removeLayer(this.marker);
      }
      var coord = (e as LeafletMouseEvent).latlng;
      var lat = coord.lat;
      var lng = coord.lng;
      console.log("You clicked the map at latitude: " + lat + " and longitude: " + lng);
      this.marker = new Marker(coord, {icon: this.sensorIcon}).addTo(this.map);
    }
    this.map.on('click', function(e){
      whenClicked(e);
      });
    }

  openCsvModal() {
    const modalRef = this.modalService.open(NgbdModalSensorCsvContent);
    modalRef.componentInstance.file = this.file;
    modalRef.result.then((result) => {
      if (result) {
        console.log("asdf")
      this.importPlots(result);
      }
      });
  }

  importPlots (receivedEntry: FormData) {
    this.plotService.addPlot("pepe", receivedEntry);
  }

}
