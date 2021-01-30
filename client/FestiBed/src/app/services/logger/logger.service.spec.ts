import { TestBed } from '@angular/core/testing';

import { LoggerService } from './logger.service';
import { environment } from 'src/environments/environment';

describe('LoggerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LoggerService = TestBed.get(LoggerService);
    expect(service).toBeTruthy();
  });

  it('Debug mode', () => {
    const service: LoggerService = TestBed.get(LoggerService);
    expect(service.isDebugMode).toBe(!environment.production);
  });
});
