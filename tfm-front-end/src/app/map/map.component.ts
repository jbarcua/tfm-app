import { HttpClient } from '@angular/common/http';
import {Component, EventEmitter, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {latLng, MapOptions, tileLayer, Map, Marker, icon,marker, geoJSON} from 'leaflet';
import { Layer } from '../layer';
import { PlotsServiceService } from '../_services/plots-service.service';
import { TokenStorageService } from '../_services/token-storage.service';

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
export class NgbdModalContent implements OnInit {

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
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  map: Map;
  mapOptions: MapOptions;
  currentUser: any;
  file: FormData;

  constructor(private modalService: NgbModal, private token: TokenStorageService, private httpClient : HttpClient, private router: Router, private plotService : PlotsServiceService) {
  }

  ngOnInit() {
    this.currentUser = this.token.getUser();
    console.log(this.currentUser)
    this.initializeMapOptions();
  }

  onMapReady(map: Map) {
    this.map = map;
    this.addSampleMarker();
  }

  private initializeMapOptions() {
    this.mapOptions = {
      center: latLng(51.505, 0),
      zoom: 12,
      layers: [
        tileLayer(
          'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
          {
            maxZoom: 18,
            attribution: 'Map data © OpenStreetMap contributors'
          })
      ],
    };
  }

  private addSampleMarker() {
    var geojson1;
    const whenClicked = (e, feature) => {
      // e = event
      console.log("asdfsadf");
      console.log(feature);
      this.router.navigate(['/plots/'+feature.properties.id]);
      // You can make your ajax call declaration here
      //$.ajax(... 
    };
    
    function onEachFeature(feature, layer) {
        //bind click
        layer.on("click", function(e) {
            whenClicked(e, feature)
        });
    }

    this.plotService.getPlots().subscribe((res) => {
      res.forEach(plot => {
        var geoData = plot.geoData;
        geoData.features[0].properties.id = plot.catastralReference;
        geoJSON(geoData ,{onEachFeature: onEachFeature}).addTo(this.map);
      });
    });
    
  }

  open() {
    const modalRef = this.modalService.open(NgbdModalContent);
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