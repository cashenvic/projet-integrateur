import { Component, OnInit } from '@angular/core';
import { SearchService } from 'src/app/services/search/search.service';
import { MatSnackBar } from '@angular/material';

import { ActivatedRoute } from "@angular/router";
import { filter } from 'rxjs/operators';

import {
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter,
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
} from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { Tile } from '../../interfaces/tile';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'fr-FR' },
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
  ]
})


export class HomeComponent implements OnInit {

  msg: string;

  tiles: Tile[] = [
    { text: 'Festivals', cols: 1, rows: 1, color: 'https://image.freepik.com/photos-gratuite/lumieres-bleues-disco_106386-97.jpg', type: 'festivals' },
    { text: 'Hébergements', cols: 1, rows: 1, color: 'https://image.freepik.com/photos-gratuite/conception-appartement-studio-moderne-chambre-espace-vie_1262-12375.jpg', type: 'hebergements' },

  ];
  constructor(private route: ActivatedRoute, private searchService: SearchService, private snackBar: MatSnackBar) {


    this.route.queryParams.subscribe(params => {
        this.msg = params['msg'];
    });

  }

  ngOnInit() {

    if(this.msg == 'empty'){
      this.snackBar.open("Nous n'avons pas pu trouver de Festival, veuillez effectuer une nouvelle recherche", 'OK', {
        duration: 5000,
        panelClass: ['red-snackbar']
      });
    }else if(this.msg == 'emptybed'){
      this.snackBar.open("Nous n'avons pas pu trouver d'hébergement, veuillez effectuer une nouvelle recherche", 'OK', {
        duration: 5000,
        panelClass: ['red-snackbar']
      });
    }
  }

}
