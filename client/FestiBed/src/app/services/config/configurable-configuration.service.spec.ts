import { TestBed } from '@angular/core/testing';
import { ConfigurableConfigurationService } from './configurable-configuration.service';
import { LoggerService } from '../logger/logger.service';
import { ConsoleLoggerService } from '../logger/console-logger.service';
import { ajax } from 'rxjs/ajax';
import { ConfigData } from './config-data';
import { of } from 'rxjs';

describe('ConfigurableConfigurationService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      {
        provide: LoggerService,
        useClass: ConsoleLoggerService
      }
    ]
  }));

  it('should be created', () => {
    const service: ConfigurableConfigurationService = TestBed.get(ConfigurableConfigurationService);
    expect(service).toBeTruthy();
  });

  it('new', async () => {
    const config = {
      serverBackEndUrl: 'url'
    } as ConfigData;
    spyOn(ajax, 'getJSON').and.returnValue(of(config));

    const service = new ConfigurableConfigurationService(null);
    expect(await service.config).toBe(config);
  });

  it('apiUrl', async () => {
    const config = {
      serverBackEndUrl: 'url'
    } as ConfigData;
    spyOn(ajax, 'getJSON').and.returnValue(of(config));

    const service = new ConfigurableConfigurationService(null);
    expect(await service.apiUrl).toBe(config.serverBackEndUrl);
  });
});
