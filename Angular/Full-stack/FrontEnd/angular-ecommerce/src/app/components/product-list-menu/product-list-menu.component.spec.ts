import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductListMenuComponent } from './product-list-menu.component';

describe('ProductListMenuComponent', () => {
  let component: ProductListMenuComponent;
  let fixture: ComponentFixture<ProductListMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductListMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductListMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
