import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VmList } from './vm-list';

describe('VmList', () => {
  let component: VmList;
  let fixture: ComponentFixture<VmList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VmList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VmList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
