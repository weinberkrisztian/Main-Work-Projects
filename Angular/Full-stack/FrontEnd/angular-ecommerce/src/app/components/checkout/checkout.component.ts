import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, ValidationErrors } from '@angular/forms';
import { CartService } from 'src/app/service/cart.service';
import { CheckoutService } from 'src/app/service/checkout.service';
import { Country } from 'src/app/common/country';
import { State } from 'src/app/common/state';
import { Purchase } from 'src/app/common/purchase';
import { Customer } from 'src/app/common/customer';
import { Address } from 'src/app/common/address';
import { Order } from 'src/app/common/order';
import { Router } from '@angular/router';
import { OrderItem } from 'src/app/common/order-item';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  totalQuantity: number=0;
  totalPrice: number=0;
  checkoutFormGroup: FormGroup;

  expirationYearList: number[]=[];
  expirationMonthList: number[]=[];
   currentYear: number=new Date().getFullYear();
    startMonth: number=new Date().getMonth()+1;
  countriesBilling: Country[]=[];
  statesBilling: State[]=[];

  statesShipping: State[]=[];
  constructor(private formBuilder: FormBuilder, private cartService:CartService,
     private checkoutService: CheckoutService, private router: Router) { }

  ngOnInit(): void {

    


    this.checkoutFormGroup=this.formBuilder.group({
      customer: this.formBuilder.group({
        firstName: new  FormControl('', [Validators.required, Validators.minLength(2), this.notOnlyWhiteSpaceValidator.bind(this)]),
        lastName: new  FormControl('', [Validators.required, Validators.minLength(2), this.notOnlyWhiteSpaceValidator.bind(this)]),
        email: new FormControl("", [Validators.required,
        Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')
        ])
      }),
      shippingAddress: this.formBuilder.group({
        country: new  FormControl('', [Validators.required]),
        street: new  FormControl('',[Validators.required, this.notOnlyWhiteSpaceValidator.bind(this)]),
        city: new  FormControl('', [Validators.required, Validators.minLength(2), this.notOnlyWhiteSpaceValidator.bind(this)]),
        state: new FormControl('',[Validators.required]),
        zipCode:new  FormControl('', [Validators.required, Validators.minLength(2), this.notOnlyWhiteSpaceValidator.bind(this)]),
      }),
      billingAddress: this.formBuilder.group({
        country: new  FormControl('', [Validators.required]),
        street: new  FormControl('',[Validators.required, this.notOnlyWhiteSpaceValidator.bind(this)]),
        city: new  FormControl('', [Validators.required, Validators.minLength(2), this.notOnlyWhiteSpaceValidator.bind(this)]),
        state: new FormControl('',[Validators.required]),
        zipCode:new  FormControl('', [Validators.required, Validators.minLength(2), this.notOnlyWhiteSpaceValidator.bind(this)]),
      }),
      creditCard: this.formBuilder.group({
        type: new  FormControl('', [Validators.required]),
        name: new  FormControl('', [Validators.required, Validators.minLength(2), this.notOnlyWhiteSpaceValidator.bind(this)]),
        number: new FormControl('', [Validators.required,Validators.pattern('[0-9]{16}')]),
        secCode: new FormControl('', [Validators.required,Validators.pattern('[0-9]{3}')]),
        expirationMonth: new  FormControl('', [Validators.required]),
        expirationYear: new  FormControl('', [Validators.required]),
      })
    });

    this.showPriceAndQuantityStatus();

  }




  get firstName(){
    return this.checkoutFormGroup.get('customer.firstName');
  }

  get lastName(){
    return this.checkoutFormGroup.get('customer.lastName');
  }
  
  get email(){
    return this.checkoutFormGroup.get('customer.email');
  }

  get shipAdCountry(){
    return this.checkoutFormGroup.get('shippingAddress.country');
  }
  get shipAdStreet(){
    return this.checkoutFormGroup.get('shippingAddress.street');
  }
  get shipAdCity(){
    return this.checkoutFormGroup.get('shippingAddress.city');
  }
  get shipAdState(){
    return this.checkoutFormGroup.get('shippingAddress.state');
  }
  get shipAdZipCode(){
    return this.checkoutFormGroup.get('shippingAddress.zipCode');
  }

  get billAdCountry(){
    return this.checkoutFormGroup.get('billingAddress.country');
  }
  get billAdStreet(){
    return this.checkoutFormGroup.get('billingAddress.street');
  }
  get billAdCity(){
    return this.checkoutFormGroup.get('billingAddress.city');
  }
  get billAdState(){
    return this.checkoutFormGroup.get('billingAddress.state');
  }
  get billAdZipCode(){
    return this.checkoutFormGroup.get('billingAddress.zipCode');
  }

  get creditCardType(){
    return this.checkoutFormGroup.get('creditCard.type');
  }
  get creditCardName(){
    return this.checkoutFormGroup.get('creditCard.name');
  }
  get creditCardNumber(){
    return this.checkoutFormGroup.get('creditCard.number');
  }
  get creditCardSecCode(){
    return this.checkoutFormGroup.get('creditCard.secCode');
  }
  get creditCardExpirationMonth(){
    return this.checkoutFormGroup.get('creditCard.expirationMonth');
  }
  get creditCardExpirationYear(){
    return this.checkoutFormGroup.get('creditCard.expirationYear');
  }

  setStatesBilling(code: string){

    console.log("state:"+ code);
    this.checkoutService.getStatesByCountryCode(code).subscribe(
      data=>{
        this.statesBilling=data
      }
    );

  }

  setStatesShipping(code: string){

    console.log("state:"+ code);
    this.checkoutService.getStatesByCountryCode(code).subscribe(
      data=>{
        this.statesShipping=data
      }
    );

  }

copyShippingToBilling(event){
  if(event.target.checked){
    this.statesBilling=this.statesShipping;
    this.checkoutFormGroup.controls.billingAddress
    .setValue(this.checkoutFormGroup.controls.shippingAddress.value);
  }else{
    this.checkoutFormGroup.controls.billingAddress.reset();
  }
  }

  choosingExpirationYear(){
    const creditCardFormGroup= this.checkoutFormGroup.get('creditCard');
    const selectedYear: number= Number(creditCardFormGroup.value.expirationYear);

    let startMonth;
    if(selectedYear===this.currentYear){
     startMonth=new Date().getMonth()+1;
    }else{
     startMonth=1;
          
        }
 
        this.checkoutService.getMonthsForCreditCard(startMonth).subscribe(
          data=>{
            this.expirationMonthList=data
          }
        );


      }

      notOnlyWhiteSpaceValidator(control : FormControl): ValidationErrors{
        
        if((control.value != null) && control.value.trim().length === 0){
          return {'notOnlyWhiteSpaceValidator' : true}
        }else {
          return null;
        }
      }
  

onSubmit(){
  
  if(this.checkoutFormGroup.invalid){
    this.checkoutFormGroup.markAllAsTouched();
    return;
  }else{

    let order= new Order();
    order.totalPrice=this.totalPrice;
    order.totalQuantity=this.totalQuantity;

    const cartItems= this.cartService.CartItems;

    let orderItems: OrderItem[]=cartItems.map(
      item => new OrderItem(item)
    );

    let purchase= new Purchase();

    purchase.customer= this.checkoutFormGroup.controls['customer'].value;


    purchase.shippingAddress=this.checkoutFormGroup.controls['shippingAddress'].value;
    console.log(this.checkoutFormGroup.controls['shippingAddress'].value);
    const shippingState: string = JSON.parse(JSON.stringify(purchase.shippingAddress.state));
    const shippingCountry: string = JSON.parse(JSON.stringify(purchase.shippingAddress.country));
    purchase.shippingAddress.state=shippingState;
    purchase.shippingAddress.country=shippingCountry;

    purchase.billingAddress=this.checkoutFormGroup.controls['billingAddress'].value;
    const billingState: string = JSON.parse(JSON.stringify(purchase.billingAddress.state));
    const billingCountry: string = JSON.parse(JSON.stringify(purchase.billingAddress.country));
    purchase.billingAddress.state=billingState;
    purchase.billingAddress.country=billingCountry;


    purchase.order=order;
    purchase.orderItems=orderItems;

    console.log(purchase);
    this.checkoutService.checkout(purchase).subscribe(
      {
        next: response => {
          alert(`Your order has been recieved. \nOrder Tracking number: ${response.orderTrackingNumber}`);
          this.resetCart();
        },
        error: errorResponse => {
          alert(`There was an error: ${errorResponse.message}`);
        }
      }
    );
    
    
  }

  
}

resetCart(){
  this.cartService.CartItems= [];
  this.cartService.totalPrice.next(0);
  this.cartService.totalQuantity.next(0);

  this.checkoutFormGroup.reset();

  this.router.navigate(['/category']);

}

showPriceAndQuantityStatus() {


  this.cartService.totalPrice.subscribe(
    data=>{
      this.totalPrice=data
    }
  );
  this.cartService.totalQuantity.subscribe(
    data=>{
      this.totalQuantity=data
    }
  );
  this.cartService.computeCartTotalts();


  this.checkoutService.getYearForCreditCard().subscribe(
    data=>{
      this.expirationYearList=data
    }
  );

  this.checkoutService.getMonthsForCreditCard(this.startMonth).subscribe(
    data=>{
      this.expirationMonthList=data
    }
  );

this.checkoutService.getCountryList().subscribe(
  data=>{
    this.countriesBilling=data
  }
);





}



}
