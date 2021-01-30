import { TestBed } from '@angular/core/testing';

import { SpinnerService } from './spinner.service';

describe('SpinnerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SpinnerService = TestBed.get(SpinnerService);
    expect(service).toBeTruthy();
  });

  it('show', () => {
    const service: SpinnerService = TestBed.get(SpinnerService);

    service.show();
    expect(service.iterationOfShow).toBe(1);
  });

  it('hide', () => {
    const service: SpinnerService = TestBed.get(SpinnerService);

    service.hide();
    expect(service.iterationOfShow).toBe(0);
  });

  it('show + hide', () => {
    const service: SpinnerService = TestBed.get(SpinnerService);

    service.show();
    service.hide();
    expect(service.iterationOfShow).toBe(0);
  });

  it('show *2', () => {
    const service: SpinnerService = TestBed.get(SpinnerService);

    service.show();
    service.show();
    expect(service.iterationOfShow).toBe(2);
  });

  it('show *2 + hide', () => {
    const service: SpinnerService = TestBed.get(SpinnerService);

    service.show();
    service.show();
    service.hide();
    expect(service.iterationOfShow).toBe(1);
  });
});
