import { Injectable } from '@angular/core';
import{HttpClient} from '@angular/common/http';
import { Observable, from } from 'rxjs';
import{Product} from '../common/product';
import{map} from 'rxjs/operators';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {


  private categorySearchUrl='http://localhost:8080/api/product-category'; 
 // private baseurl='http://localhost:8080/api/products?size=100';
  private baseurl='http://localhost:8080/api/products';

 // private categorySearchUrl='http://3.128.22.184:8080/api/product-category'; 
 //  private baseurl='http://3.128.22.184:8080/api/products';

  constructor(private httpClient: HttpClient) { }


  getProductListPaginate(thePage:number,thePageSize:number ,currentCategoryId: number):Observable<GetResponseProducts>{

    // we have to make that URL access on the backend side
    const searchUrl=`${this.baseurl}/search/findByCategoryId?id=${currentCategoryId}`
    + `&page=${thePage}&size=${thePageSize}`;

    return  this.httpClient.get<GetResponseProducts>(searchUrl);
  }  


  getProductList(currentCategoryId: number):Observable<Product[]>{

    // we have to make that URL access on the backend side
    const searchUrl=`${this.baseurl}/search/findByCategoryId?id=${currentCategoryId}`;

    return  this.httpClient.get<GetResponseProducts>(searchUrl).pipe(
      map(response => response._embedded.products)
    );
  }  


  getProducCategoryList():Observable<ProductCategory[]> {
    return  this.httpClient.get<GetResponseProductCategory>(this.categorySearchUrl).pipe(
      map(response => response._embedded.productCategory)
    );
  }

  getSearchProduct(thePage:number,thePageSize:number,theKeyword:string):Observable<GetResponseProducts> {
    const searchedUrl=`${this.baseurl}/search/findByNameContaining?name=${theKeyword}`
      +`&page=${thePage}&size=${thePageSize}`;

    return  this.httpClient.get<GetResponseProducts>(searchedUrl);
  }

  getProductByIdService(theId: number):Observable<Product> {
    const detailedUrl=`${this.baseurl}/${theId}`;
    return  this.httpClient.get<Product>(detailedUrl);
  }


}

interface GetResponseProducts{
  _embedded:{
    products:Product[];
  },
  page:{
    size:number,
    totalElements:number,
    totalPages:number,
    number:number
  }
}

interface GetResponseProductCategory{
  _embedded:{
    productCategory:ProductCategory[];
  }
}