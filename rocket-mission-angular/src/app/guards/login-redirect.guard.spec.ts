import { TestBed, async, inject } from '@angular/core/testing';

import { LoginRedirectGuard } from './login-redirect.guard';

describe('LoginRedirectGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoginRedirectGuard]
    });
  });

  it('should ...', inject([LoginRedirectGuard], (guard: LoginRedirectGuard) => {
    expect(guard).toBeTruthy();
  }));
});
