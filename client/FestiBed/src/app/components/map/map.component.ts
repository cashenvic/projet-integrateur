import {Component, Inject, Injectable, Input, OnInit, ViewChild} from '@angular/core';
import {GoogleMap} from '@angular/google-maps';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {
  @ViewChild(GoogleMap, { static: false }) map: GoogleMap
  @Input() markerFest: {
    position:
      {
        lat: any,
        lng: any
      },
    label:
      {
      text: string,
      color: string
    },
    title: string,
  };


  zoom = 12
  center: google.maps.LatLngLiteral
  options: google.maps.MapOptions = {
    mapTypeId: 'hybrid',
    zoomControl: false,
    scrollwheel: false,
    disableDoubleClickZoom: true,
    maxZoom: 15,
    minZoom: 8,
  }

  marker = {
    position: {
      lat: 45.6,
      lng: 45.8,
    },
    label: {
      color: 'red',
      text: 'Marker label ',
    },
    title: 'Marker title ',
    info: 'Marker info ' ,
    options: {
      animation: google.maps.Animation.BOUNCE,
    },
  };

  constructor() { }

  ngOnInit() {
    //console.log(this.markerFest);
    navigator.geolocation.getCurrentPosition(position => {
      this.center = {
        lat: this.markerFest.position.lat,
        lng: this.markerFest.position.lng
      };
    });
  }

  zoomIn() {
    if (this.zoom < this.options.maxZoom) { this.zoom++; }
  }

  zoomOut() {
    if (this.zoom > this.options.minZoom) { this.zoom--; }
  }

}
