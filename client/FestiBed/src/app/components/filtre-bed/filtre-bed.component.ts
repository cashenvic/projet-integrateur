import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { SearchService } from '../../services/search/search.service';
import { LocalisationTinyInterface} from '../../interfaces/localisationTiny';
import { TypeHebergementInterface } from '../../interfaces/typeHebergement';
import { Router } from "@angular/router";

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';

@Component({
  selector: 'app-filtre-bed',
  templateUrl: './filtre-bed.component.html',
  styleUrls: ['./filtre-bed.component.scss']
})
export class FiltreBedComponent implements OnInit {
  displayedColumns: string[] = ['Photo', 'Nom', 'type', 'Date_debut', 'Date_fin', 'Note'];

  form: FormGroup;

  nom_departement: string

  departementsFest: LocalisationTinyInterface[] = [];

  typeHebergement: TypeHebergementInterface[] = [];

  typeHeb: string;

  paramsBed: { [key: string]: any } = {};

  inlineRange;

  constructor(private search: SearchService, private router: Router, fb: FormBuilder) {
    const today = new Date(Date.now());

    this.form = fb.group({
      dateRange: [{ begin: new Date(today.getFullYear(), today.getMonth(), today.getDate()), end: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 3) }],
      nom_departement: '',
      typeHeb: ''
    });
  }

  async ngOnInit() {

    await this.search.getDepartementsFest().then((res) => {
      this.departementsFest = res.body;
    });

    await this.search.getTypeHebergement().then((res) => {
      this.typeHebergement = res.body;
    });

  }

  inlineRangeChange($event) {
    this.inlineRange = $event;
  }

  onSubmit(): void {
    if (this.form.value && this.nom_departement && this.typeHeb) {

      this.paramsBed.dateArrivee = moment(this.form.value.dateRange.begin).format('YYYY-MM-DD');
      this.paramsBed.dateDepart = moment(this.form.value.dateRange.end).format('YYYY-MM-DD');;
      this.paramsBed.type = 'CAMPING';

      let nom_departement = this.form.value.nom_departement;
      this.paramsBed.nomDepartement = nom_departement.toUpperCase();;

      this.paramsBed.limit = 100
      this.paramsBed.offset = 0

      console.log(this.paramsBed)

      this.router.navigate(['searchbed'], { queryParams: this.paramsBed });
    } else {
      console.log('KO');
    }
  }


}
