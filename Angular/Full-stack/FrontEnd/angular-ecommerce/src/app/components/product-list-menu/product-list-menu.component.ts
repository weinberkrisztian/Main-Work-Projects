import { Component, OnInit } from '@angular/core';
import { ProductServiceService } from 'src/app/service/product-service.service';
import { ProductCategory } from 'src/app/common/product-category';

@Component({
  selector: 'app-product-list-menu',
  templateUrl: './product-list-menu.component.html',
  styleUrls: ['./product-list-menu.component.css']
})
export class ProductListMenuComponent implements OnInit {

   productCategory:ProductCategory[];

  constructor(private productService: ProductServiceService) { }

  ngOnInit(): void {
    this.listOfProductListCategories();
  }


  listOfProductListCategories() {
    this.productService.getProducCategoryList().subscribe(
      data => {
        console.log("The incomming data"+ JSON.stringify(data));
        this.productCategory=data;

      }

    );
  }

}
