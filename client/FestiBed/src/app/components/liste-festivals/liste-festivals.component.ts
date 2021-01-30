import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { FestivalInterface } from '../../interfaces/festival';
import { SearchService } from "../../services/search/search.service";
import { ApplicationError } from 'src/app/services/exception/application-error';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';

@Component({
  selector: 'app-liste-festivals',
  templateUrl: './liste-festivals.component.html',
  styleUrls: ['./liste-festivals.component.scss']
})
export class ListeFestivalsComponent implements OnInit {
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  displayedColumns: string[] = ['Photo', 'Nom', 'Lieu', 'Date_debut', 'Date_fin', 'Note'];

  urlPhoto = "https://via.placeholder.com/600x600.png/6E78A6/FFFFFF/";
  dataSource: MatTableDataSource<FestivalInterface>;

  festivals: FestivalInterface[];
  constructor(private search: SearchService,
    private spinner: SpinnerService,
    private logger: LoggerService) {
  }

  async ngOnInit() {
    try {
      this.spinner.show();
      await this.search.getFestival().then(res => {
        this.festivals = res.body;
      });
      this.dataSource = new MatTableDataSource(this.festivals);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();
    }
  }

}
