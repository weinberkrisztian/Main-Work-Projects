import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductServiceService } from 'src/app/service/product-service.service';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/service/cart.service';
import { CartItem } from 'src/app/common/cart-item';


@Component({
  selector: 'app-product-detail-list',
  templateUrl: './product-detail-list.component.html',
  styleUrls: ['./product-detail-list.component.css']
})
export class ProductDetailListComponent implements OnInit {

  product:Product=new Product();
  
  constructor(private productService: ProductServiceService,private route: ActivatedRoute, private cartService:CartService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(
      () =>{
    this.getProductById(); 
  }
    );
  }

addToCart(){
  const cartItem=new CartItem(this.product);

  this.cartService.addToCart(cartItem);
}

  getProductById() {
    const theId=+this.route.snapshot.paramMap.get('id');
    this.productService.getProductByIdService(theId).subscribe(
      data=>{
        this.product=data;
      }

    )
  }

}
