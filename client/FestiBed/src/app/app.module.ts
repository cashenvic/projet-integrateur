import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';

import { NgxSpinnerModule } from 'ngx-spinner';
import { LoggerService } from './services/logger/logger.service';
import { ConsoleLoggerService } from './services/logger/console-logger.service';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SatDatepickerModule, SatNativeDateModule } from 'saturn-datepicker';
import { DragScrollModule } from 'ngx-drag-scroll';

import { UserIdleModule } from 'angular-user-idle';

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatStepperModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatBadgeModule,
  MatTableModule,
  MatSortModule
} from '@angular/material';

import { NgxMatDrpModule } from 'ngx-mat-daterange-picker';
import { GoogleMapsModule } from '@angular/google-maps';
import { AngularFireModule } from '@angular/fire';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import {CardModule} from 'ngx-card/ngx-card';

import {DataViewModule} from 'primeng/dataview';

import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
registerLocaleData(localeFr, 'fr');
import { LOCALE_ID } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { PanierComponent } from './components/panier/panier/panier.component';
import { SearchComponent } from './components/search/search.component';
import { ListeFestivalsComponent } from './components/liste-festivals/liste-festivals.component';
import { ListeBedComponent } from './components/liste-bed/liste-bed.component';
import { BedComponent } from './components/bed/bed.component';
import { FestivalComponent } from './components/festival/festival.component';
import { ProfilComponent } from './components/profil/profil.component';
import { GestionFestivalComponent } from './components/gestion-festival/gestion-festival.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './components/home/home.component';
import { FiltreComponent } from './components/filtre/filtre.component';
import { MapComponent } from './components/map/map.component';
import { LoginComponent } from './components/login/login.component';
import { environment } from 'src/environments/environment';
import { SignupComponent } from './components/signup/signup.component';
import { PaiementComponent } from './components/panier/paiement/paiement.component';
import { ReservationsComponent } from './components/panier/reservations/reservations.component';
import { GestionLogementComponent } from './components/gestion-logement/gestion-logement.component';
import { FactureComponent } from './components/facture/facture.component';
import { DialogAddComponent } from './components/dialog-add/dialog-add.component';
import { FiltreBedComponent } from './components/filtre-bed/filtre-bed.component';
import { SearchBedComponent } from './components/search-bed/search-bed.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NotFoundComponent,
    PanierComponent,
    SearchComponent,
    ListeFestivalsComponent,
    ListeBedComponent,
    BedComponent,
    FestivalComponent,
    ProfilComponent,
    GestionFestivalComponent,
    HomeComponent,
    FiltreComponent,
    MapComponent,
    LoginComponent,
    SignupComponent,
    PaiementComponent,
    ReservationsComponent,
    GestionLogementComponent,
    FactureComponent,
    DialogAddComponent,
    FiltreBedComponent,
    SearchBedComponent,
  ],
  imports: [
    BrowserModule,
    SatDatepickerModule, SatNativeDateModule,
    AppRoutingModule,
    NgxSpinnerModule,
    BrowserAnimationsModule,
    HttpClientModule,
    HttpClientJsonpModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatDialogModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSnackBarModule,
    MatStepperModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatBadgeModule,
    MatButtonModule,
    MatTableModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    NgxMatDrpModule,
    GoogleMapsModule,
    AngularFireAuthModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFirestoreModule,
    CardModule,
    DragScrollModule,
    DataViewModule,
    // Optionally you can set time for `idle`, `timeout` and `ping` in seconds.
    // Default values: `idle` is 600 (10 minutes), `timeout` is 600 (10 minutes) 
    // and `ping` is 120 (2 minutes).
    UserIdleModule.forRoot({idle: 60, timeout: 30, ping: 120})
  ],
  providers: [
    {
      provide: LoggerService,
      useClass: ConsoleLoggerService
    },
    {
      provide: LOCALE_ID,
      useValue: "fr-FR"
    },
  ],
  bootstrap: [AppComponent],
  entryComponents: [DialogAddComponent]
})
export class AppModule { }
