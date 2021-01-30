import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog-add',
  templateUrl: './dialog-add.component.html',
  styleUrls: ['./dialog-add.component.scss']
})
export class DialogAddComponent implements OnInit {
  places_reserver = 1;
  max_place = 1;
  constructor(
    public dialogRef: MatDialogRef<DialogAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { 
      this.max_place = data.nb_place_dispo;
    }

  ngOnInit() {
  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }
}
