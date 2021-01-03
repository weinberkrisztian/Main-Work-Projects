import { Injectable } from '@angular/core';
import { CartItem } from '../common/cart-item';
import { ReplaySubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  

  CartItems: CartItem[]=[];

  totalPrice: Subject<number>=new ReplaySubject<number>();
  totalQuantity: Subject<number>=new ReplaySubject<number>();

  constructor() {


   }

   addToCart(theCartItem:CartItem){

   let alreadyExist: boolean=false;
   let existingCartItem: CartItem=undefined;

    if(this.CartItems.length>0){
/*
      for (let tempItem of this.CartItems) {
        if(tempItem.id==theCartItem.id){
          existingCartItem=tempItem;
          break;
        }
      }
*/
// goes over the array from the first element, if it matches with the specific id then it stops
existingCartItem=this.CartItems.find(tempCartItem => tempCartItem.id==theCartItem.id);

      alreadyExist=(existingCartItem != undefined);
    }
      if(alreadyExist){
        existingCartItem.quantity++;
      }
      else{
        this.CartItems.push(theCartItem);
      }

        this.computeCartTotalts();
    


   }
  computeCartTotalts() {
    let totalPriceValue: number=0;
    let totalQuantityValue:number=0;
    for (const currentItem of this.CartItems) {
      totalPriceValue += currentItem.unitPrice* currentItem.quantity;
      totalQuantityValue += currentItem.quantity;
      
    }

    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);

    this.logCartData(totalPriceValue,totalQuantityValue);
  }


  logCartData(totalPriceValue: number, totalQuantityValue: number) {
    console.log("Cart Content");
    for (const tempItem of this.CartItems) {
      const subTotal=tempItem.quantity*tempItem.unitPrice;
      console.log(`Name: ${tempItem.name} Price: ${tempItem.unitPrice} Quantity: ${tempItem.quantity} subtotal: ${tempItem.unitPrice * tempItem.quantity}`
      +` subtotal${subTotal}`);
    }
    console.log(`totalPrice: ${totalPriceValue.toFixed(2)}, totalQuantity: ${totalQuantityValue}`);
    console.log('----');

  }

  remove(cartItem: CartItem) {
    const theCartIndex= this.CartItems.findIndex(
      tempCartItem => tempCartItem.id==cartItem.id
    );

    if(theCartIndex > -1){
      this.CartItems.splice(theCartIndex,1);

      this.computeCartTotalts();
    }
  }

  decrementQuantity(cartItem: CartItem) {
    cartItem.quantity--;

    if(cartItem.quantity==0){
      this.remove(cartItem);
    }else{
      this.computeCartTotalts();
    }
  }

}
