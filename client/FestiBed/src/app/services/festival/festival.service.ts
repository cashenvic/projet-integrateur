import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { LoggerService } from '../logger/logger.service';
import { SpinnerService } from '../spinner/spinner/spinner.service';
import { environment } from 'src/environments/environment';
import { ApplicationError } from '../exception/application-error';

function AlxToObjectString(data?: object): { [key: string]: string } {
  const res = {};
  for (const k of Object.keys(data || {})) {
    const v = data[k];
    res[k] = typeof v === 'string' ? v : JSON.stringify(v);
  }
  return res;
}

@Injectable({
  providedIn: 'root'
})
export class FestivalService {
  private url = environment.serveurAdd;

  constructor(
    private http: HttpClient,
    private spinner: SpinnerService, 
    private logger :LoggerService,) { }

  private async get<T>(url: string, data: object): Promise<HttpResponse<T>> {
    return this.http.get<T>(url, {
      observe: 'response',
      params: {...AlxToObjectString(data)}
    }).toPromise();
  }

  async getAllFestival(limit: any, offset: any, options?): Promise<any> {
    try{
      const getUrl = `${this.url}festival?limit=${limit}&offset=${offset}`;
      const res = await this.get<HttpResponse<string>>(getUrl, options);
      return res.body;
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } 
  }

  async getFestivalById(id : any, limit?: any, offset?: any, options?): Promise<any> {
    try{
      const getUrl = `${this.url}festival?limit=1&offset=1&id=${id}`;
      const res = await this.get<HttpResponse<string>>(getUrl, options);
      return res.body;
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } 
  }


  async getSoireeFestivalById(id: any, options? : any): Promise<any> {
    try{
      const getUrl = `${this.url}festival/soiree?id_festival=${id}`;
      const res = await this.get<HttpResponse<string>>(getUrl, options);
      return res.body;
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } 
  }

  async getCategorieSoireeById(id: any, options? : any): Promise<any> {
    try{
      const getUrl = `${this.url}festival/soiree/categorie?id_soiree=${id}`;
      const res = await this.get<HttpResponse<string>>(getUrl, options);
      return res.body;
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } 
  }


  async postFestivalReserver(params: {[key: string]: string}): Promise<any> {
    try{
      const P = new HttpParams( {fromObject: params} );
      return this.http.post( `${this.url}festival/reserver`, P, {
        observe: 'response',
        responseType: 'text',
        headers: {'content-type': 'application/x-www-form-urlencoded'}
      }).toPromise();
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } 
  }




}
