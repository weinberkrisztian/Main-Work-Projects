import { Component, OnInit } from '@angular/core';
import { ProductServiceService } from 'src/app/service/product-service.service';
import { Product } from 'src/app/common/product';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { CartService } from 'src/app/service/cart.service';


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products:Product[]=[];
  curretnCategoryId: number=1;
  previousCategoryId:number=1;
  curretnCategoryName: string;
  searchMode:boolean=false;

  //for pagination
  thePageNumber:number=1;
  thePageSize:number=5;
  theTotalElements: number=0;
  maxSize:number=3;
  boundaryLinks:boolean=true;
  
  // pagination search
  thePreviousKeyword:string=null;


  

  constructor(private productService: ProductServiceService, private cartService:CartService,
    private route:ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(
      () =>{
    this.listProducts(); 
  }
    );

    
  }

  // pageSize dropdown listhez, html oldalrol bejövő adatot állítja be az itteni size változóra
  // visszarakja az elso oldara es ujra lefuttatja a listazast
  updatePageSize(thePageSize:number){
    this.thePageSize=thePageSize;
    this.thePageNumber=1;
    this.listProducts();
  
}


  listProducts(){

    const searchMode=this.route.snapshot.paramMap.has('keyword');
    if(searchMode){
     
      this.listProductsBySearch();
    }
    else{
    this.listProductsByCategory();
  }
  }




  listProductsBySearch(){
    const theKeyword:string=this.route.snapshot.paramMap.get('keyword');

    if(this.thePreviousKeyword != theKeyword){
      this.thePageNumber=1;
    }
    this.thePreviousKeyword=theKeyword;

this.productService.getSearchProduct(this.thePageNumber-1,this.thePageSize,theKeyword).subscribe(
  this.processResultWithPagination()
);
  }




  listProductsByCategory(){
    const hasId: boolean= this.route.snapshot.paramMap.has('id');


    // check if the id is empty or not
    if(hasId){
      // if not empty than we make the id equal with our variable, 
      // using the + symbol to convert the String from gthe get method into a number
    this.curretnCategoryId= +this.route.snapshot.paramMap.get('id');

    this.curretnCategoryName= this.route.snapshot.paramMap.get('name');
  }
  else{
    // if it is empty then we set it to 1 by default
    this.curretnCategoryId=1;

    this.curretnCategoryName='Books';
  }

  // if we have different category id than previous
  // then set thePasgeNumber to 1
  if(this.previousCategoryId != this.curretnCategoryId){
    this.thePageNumber=1;
  }

  this.previousCategoryId=this.curretnCategoryId;

  console.log(`the previous category id: ${this.previousCategoryId} and the current: ${this.curretnCategoryId}`);



    this.productService.getProductListPaginate(this.thePageNumber-1,this.thePageSize,this.curretnCategoryId).subscribe(
      this.processResultWithPagination()
    );
  }
  processResultWithPagination()  {
    return data => {
      this.products=data._embedded.products;
      this.thePageNumber=data.page.number + 1;
      this.thePageSize=data.page.size;
      this.theTotalElements=data.page.totalElements;



    };
  }


  addToCart(tempProduct:Product){

    console.log(`name: ${tempProduct.name} price: ${tempProduct.unitPrice}`);

    const theCartItem=new CartItem(tempProduct);

    this.cartService.addToCart(theCartItem);

  }

}
