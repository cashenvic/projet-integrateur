import { TestBed } from '@angular/core/testing';

import { ConsoleLoggerService } from './console-logger.service';
import { LogLevel } from './log-level.enum';
import { environment } from '../../../environments/environment';

describe('ConsoleLoggerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    expect(service).toBeTruthy();
  });

  it('Log message si en Debug', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.log = jasmine.createSpy('log');
    const message = 'mes';

    service.debug(message);
    if (environment.production) {
      expect(mock).not.toHaveBeenCalled();
    } else {
      expect(mock).toHaveBeenCalledWith(message);
    }
  });

  //////////////////////////////////////////////////////////////////////////////////
  // Log
  it('Log message null', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.log = jasmine.createSpy('log');

    service.log(null);
    expect(mock).not.toHaveBeenCalled();
  });

  it('Log message vide', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.log = jasmine.createSpy('log');

    service.log('');
    expect(mock).not.toHaveBeenCalled();
  });

  it('Log avec message', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.log = jasmine.createSpy('log');
    const message = 'mes';

    service.log(message);
    expect(mock).toHaveBeenCalledWith(message);
  });

  it('Log avec message et paramètre', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.log = jasmine.createSpy('log');
    const message = 'mes';
    const parameter = 'sage';

    service.log(message, parameter);
    expect(mock).toHaveBeenCalledWith(message, parameter);
  });

  it('Log avec une fonction pour message', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.log = jasmine.createSpy('log');
    const message = 'mes';

    service.log(() => message);
    expect(mock).toHaveBeenCalledWith(message);
  });

  //////////////////////////////////////////////////////////////////////////////////
  // Warn
  it('Warn message null', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.warn = jasmine.createSpy('warn');

    service.warn(null);
    expect(mock).not.toHaveBeenCalled();
  });

  it('Warn message vide', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.warn = jasmine.createSpy('warn');

    service.warn('');
    expect(mock).not.toHaveBeenCalled();
  });

  it('Warn avec message', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.warn = jasmine.createSpy('warn');
    const message = 'mes';

    service.warn(message);
    expect(mock).toHaveBeenCalledWith(message);
  });

  it('Warn avec message et paramètre', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.warn = jasmine.createSpy('warn');
    const message = 'mes';
    const parameter = 'sage';

    service.warn(message, parameter);
    expect(mock).toHaveBeenCalledWith(message, parameter);
  });

  it('Warn avec une fonction pour message', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.warn = jasmine.createSpy('warn');
    const message = 'mes';

    service.warn(() => message);
    expect(mock).toHaveBeenCalledWith(message);
  });

  //////////////////////////////////////////////////////////////////////////////////
  // Error
  it('Erreur non spécifiée', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.error = jasmine.createSpy('error');

    service.error(null);
    expect(mock).not.toHaveBeenCalled();
  });

  it('Erreur avec message', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.error = jasmine.createSpy('error');
    const message = 'mes';
    const error = new Error(message);

    service.error(error);
    expect(mock).toHaveBeenCalledWith(error);
  });

  it('Erreur avec une fonction pour message', () => {
    const service: ConsoleLoggerService = TestBed.get(ConsoleLoggerService);
    const mock = service.console.error = jasmine.createSpy('error');
    const message = 'mes';
    const error = new Error(message);

    service.error(() => error);
    expect(mock).toHaveBeenCalledWith(error);
  });
});
