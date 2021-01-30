import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import {DomaineInterface} from '../../interfaces/domaine';
import {HebergementInterface} from '../../interfaces/hebergement';
import {LocalisationInterface} from '../../interfaces/localisation';
import {LocalisationTinyInterface} from '../../interfaces/localisationTiny';
import {TypeHebergementInterface } from '../../interfaces/typeHebergement';
import {FestivalInterface} from "../../interfaces/festival";



@Injectable({
  providedIn: 'root'
})
/*
function AlxToObjectString(data?: object): { [key: string]: string } {
  const res = {};
  for (const k of Object.keys(data || {})) {
    const v = data[k];
    res[k] = typeof v === 'string' ? v : JSON.stringify(v);
  }
  return res;
}*/

export class SearchService {
  bed = false;
  festival = false;
  URL_DepFest = 'https://api.myjson.com/bins/wz65q';
  URL_Categ = 'https://api.myjson.com/bins/13oxv2';

  URL_HebType = 'https://api.myjson.com/bins/1h6otm';
  URL_DepDed = 'https://api.myjson.com/bins/6khjm';

  URL_FestivalSearch = 'http://localhost:8090/api/festival/search'; /*TEST : https://api.myjson.com/bins/18795e*/
  URL_HebergementSearch = 'http://localhost:8090/api/hebergement/search';

  URL_Festival = 'http://localhost:8090/api/festival?limit=50&offset=0'; /*TEST : https://api.myjson.com/bins/18795e*/
  URL_Herbergement = 'http://localhost:8090/api/hebergement?limit=10&offset=0'

  public async getFestival<T>(): Promise<HttpResponse<FestivalInterface[]>>{
      return this.http.get<FestivalInterface[]>(this.URL_Festival, {
        observe: 'response',
        headers: new HttpHeaders(),
        responseType: 'json'
      }).toPromise();
  }

  public async getHebergement<T>(): Promise<HttpResponse<HebergementInterface[]>>{
      return this.http.get<HebergementInterface[]>(this.URL_Herbergement, {
        observe: 'response',
        headers: new HttpHeaders(),
        responseType: 'json'
      }).toPromise();
  }


  get getSelectBed() {
    return this.bed;
  }

  get getSelectFestival() {
    return this.festival;
  }

  selectBed(){
    this.bed = true;
    this.festival = false;
  }

  selectFestival(){
    this.festival = true;
    this.bed = false;
  }

  public async getSearch<T>(params: { [key: string]: any}): Promise<HttpResponse<FestivalInterface[]>>{
    const param = new HttpParams({fromObject: params});
    return this.http.get<FestivalInterface[]>(this.URL_FestivalSearch, {
      observe: 'response',
      headers: new HttpHeaders(),
      responseType: 'json',
      params: param,
    }).toPromise();
  }

  public async getSearchBed<T>(params: { [key: string]: any}): Promise<HttpResponse<HebergementInterface[]>>{
    const param = new HttpParams({fromObject: params});
    return this.http.get<HebergementInterface[]>(this.URL_HebergementSearch, {
      observe: 'response',
      headers: new HttpHeaders(),
      responseType: 'json',
      params: param,
    }).toPromise();
  }

  public async getDepartementsFest<T>(): Promise<HttpResponse<LocalisationTinyInterface[]>>{
    return this.http.get<LocalisationTinyInterface[]>(this.URL_DepFest, {
      observe: 'response',
      headers: new HttpHeaders(),
      responseType: 'json'
    }).toPromise();
  }

  public async getDepartementsBed<T>(): Promise<HttpResponse<LocalisationTinyInterface[]>>{
    return this.http.get<LocalisationTinyInterface[]>(this.URL_DepDed, {
      observe: 'response',
      headers: new HttpHeaders(),
      responseType: 'json'
    }).toPromise();
  }

  public async getCategories<T>(): Promise<HttpResponse<DomaineInterface[]>>{
    return this.http.get<DomaineInterface[]>(this.URL_Categ, {
      observe: 'response',
      headers: new HttpHeaders(),
      responseType: 'json'
    }).toPromise();
  }

  public async getTypeHebergement<T>(): Promise<HttpResponse<TypeHebergementInterface[]>>{
    return this.http.get<TypeHebergementInterface[]>(this.URL_HebType, {
      observe: 'response',
      headers: new HttpHeaders(),
      responseType: 'json'
    }).toPromise();
  }

  constructor(private http: HttpClient) { }

  ngOnInit() {

  }
}
