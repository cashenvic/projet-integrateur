import { Component, OnInit } from '@angular/core';
import * as jwt from 'jsonwebtoken';
import { AuthService } from './services/auth/auth.service';
import { SpinnerService } from './services/spinner/spinner/spinner.service';
import { ApplicationError } from './services/exception/application-error';
import { LoggerService } from './services/logger/logger.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'FestiBed';


  constructor(private authService : AuthService,
    private spinner : SpinnerService,
    private logger : LoggerService) {}
 
  ngOnInit() {
    try{
      this.spinner.show();
      if(localStorage.getItem('tokenFestibed') === null) {
        const token = jwt.sign({ foo: 'bar' }, 'shhhhh');
        localStorage.setItem('tokenFestibed', token.toString());
      }
      if(!this.authService.isAuth){
        this.authService.infoUser();
      }
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();
    }
  }

}
