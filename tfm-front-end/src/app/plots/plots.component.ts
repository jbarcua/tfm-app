import { Component, OnInit } from '@angular/core';
import { Layer } from '../layer';
import { PlotsServiceService } from '../_services/plots-service.service';

@Component({
  selector: 'app-plots',
  templateUrl: './plots.component.html',
  styleUrls: ['./plots.component.css']
})
export class PlotsComponent implements OnInit {

  plots: Layer[]; 

  constructor(private plotService: PlotsServiceService) { }

  ngOnInit(): void {
    this.plotService.getPlots().subscribe((res) => {
      this.plots = res;
    });
  }

}
