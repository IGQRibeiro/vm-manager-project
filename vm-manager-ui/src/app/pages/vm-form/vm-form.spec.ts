import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VmForm } from './vm-form';

describe('VmForm', () => {
  let component: VmForm;
  let fixture: ComponentFixture<VmForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VmForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VmForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
