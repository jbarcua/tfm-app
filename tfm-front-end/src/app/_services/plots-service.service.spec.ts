import { TestBed } from '@angular/core/testing';

import { PlotsServiceService } from './plots-service.service';

describe('PlotsServiceService', () => {
  let service: PlotsServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlotsServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
