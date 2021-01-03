import { Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/service/cart.service';
import { CartItem } from 'src/app/common/cart-item';

@Component({
  selector: 'app-cart-details',
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.css']
})
export class CartDetailsComponent implements OnInit {

  cartItems: CartItem[]=[] ;
  totalPrice: number=0;
  totalQuantity: number=0;

  constructor(private cartService:CartService) { }

  ngOnInit(): void {
    this.listCartDetails();

  }


  listCartDetails() {
    this.cartItems=this.cartService.CartItems;

    this.cartService.totalPrice.subscribe(
      data=>
        this.totalPrice=data
    );

    this.cartService.totalQuantity.subscribe(
      data=>
      this.totalQuantity=data
    );

    this.cartService.computeCartTotalts();

  }

  incrementQuantity(cartItem : CartItem) {
    this.cartService.addToCart(cartItem);
  }


  decrementQuantity(cartItem : CartItem){
    this.cartService.decrementQuantity(cartItem);

  }
  remove(cartItem: CartItem) {
    this.cartService.remove(cartItem);

  }


}
