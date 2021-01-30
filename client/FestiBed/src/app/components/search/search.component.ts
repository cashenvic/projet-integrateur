import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { MatTableDataSource } from '@angular/material';
import { FestivalInterface } from '../../interfaces/festival';
import { HttpClient } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import { Router } from "@angular/router";
import { SearchService } from "../../services/search/search.service";

import { ApplicationError } from 'src/app/services/exception/application-error';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  displayedColumns: string[] = ['Photo', 'Nom', 'Lieu', 'Date_debut', 'Date_fin', 'Note'];

  urlPhoto = "https://via.placeholder.com/600x600.png/6E78A6/FFFFFF/";
  dataSource: MatTableDataSource<FestivalInterface>;

  params: any;
  searchedFestival: FestivalInterface[];

  constructor(private search: SearchService, private route: ActivatedRoute,
    private routerHomme: Router,
    private spinner: SpinnerService,
    private logger: LoggerService) {
    this.route.queryParams.subscribe(params => {
      this.params = params;
    });

  }

  async ngOnInit() {
    try {
      this.spinner.show();
      await this.search.getSearch(this.params).then(res => {
        if(res.body.length === 0){
          this.routerHomme.navigate(['/home'], { queryParams: { msg: 'empty' } });
        }

        this.searchedFestival = res.body;
        this.dataSource = new MatTableDataSource(res.body);
      });

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
