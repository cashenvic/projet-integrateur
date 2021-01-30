import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';
import { CommandeService } from 'src/app/services/commande/commande.service';
import { MatDialog } from '@angular/material';
import { DialogAddComponent } from '../dialog-add/dialog-add.component';
import { Router } from '@angular/router';

import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-facture',
  templateUrl: './facture.component.html',
  styleUrls: ['./facture.component.scss']
})
export class FactureComponent implements OnInit {

  constructor(private commande: CommandeService,
    private router : Router,
    private logger: LoggerService,
    private spinner: SpinnerService,
    private dialog: MatDialog) {
  }

  ngOnInit() {
  }

  public captureScreen() {

    let data = document.getElementById('contentToConvert');

    html2canvas(data).then(canvas => {


      let pdf = new jspdf('p', 'mm', 'a4'); // A4 size page of PDF

      pdf.setFontSize(20)
      pdf.text(10, 25, 'Ma réservation')

      // pdf.setFontSize(12)
      // pdf.text(10, 35, 'Détailts des billets')

      const contentDataURL = canvas.toDataURL('image/png')
      let imgWidth = 180;
      let imgHeight = 205;
      pdf.addImage(contentDataURL, 'PNG', 10, 25, imgWidth, imgHeight)

      // pdf.addPage();
      //
      // pdf.setFontSize(12)
      // pdf.text(10, 25, 'Détailts Informations hébergement')

      pdf.save('ma_reservation.pdf'); // Generated PDF

      this.commande.resetPanier();

      this.router.navigate(["/home"]);

    });
  }

}
