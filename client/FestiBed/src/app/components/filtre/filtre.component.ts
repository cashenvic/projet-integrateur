import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { SearchService } from '../../services/search/search.service';
import { LocalisationTinyInterface} from '../../interfaces/localisationTiny';
import { DomaineInterface } from '../../interfaces/domaine';
import { Router } from "@angular/router";

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';

@Component({
  selector: 'app-filtre',
  templateUrl: './filtre.component.html',
  styleUrls: ['./filtre.component.scss']
})
export class FiltreComponent implements OnInit {
  displayedColumns: string[] = ['Photo', 'Nom', 'nom_departement', 'Date_debut', 'Date_fin', 'Note'];

  form: FormGroup;

  nom_departement: string

  departementsFest: LocalisationTinyInterface[] = [];

  categories: DomaineInterface[] = [];

  categorie: number;

  paramsFest: { [key: string]: any } = {};

  inlineRange;

  constructor(private search: SearchService, private router: Router, fb: FormBuilder) {
    const today = new Date(Date.now());

    this.form = fb.group({
      dateRange: [{ begin: new Date(today.getFullYear(), today.getMonth(), today.getDate()), end: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 3) }],
      nom_departement: '',
      categorie: ''
    });
  }

  async ngOnInit() {

    await this.search.getDepartementsFest().then((res) => {
      this.departementsFest = res.body;
    });

    await this.search.getCategories().then((res) => {
      this.categories = res.body;
    });

  }

  inlineRangeChange($event) {
    this.inlineRange = $event;
  }

  onSubmit(): void {
    if (this.form.value && this.nom_departement && this.categorie) {

      this.paramsFest.date_debut = moment(this.form.value.dateRange.begin).format('DD/MM/YYYY');
      this.paramsFest.date_fin = moment(this.form.value.dateRange.end).format('DD/MM/YYYY');;
      this.paramsFest.id_domaine = this.form.value.categorie;
      this.paramsFest.departement = this.form.value.nom_departement;

      this.paramsFest.limit = 100
      this.paramsFest.offset = 0

      console.log(this.paramsFest)

      this.router.navigate(['search'], { queryParams: this.paramsFest });
    } else {
      console.log('KO');
    }
  }


}
