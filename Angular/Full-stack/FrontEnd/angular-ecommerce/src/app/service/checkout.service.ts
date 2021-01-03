import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Country } from '../common/country';
import { map } from 'rxjs/operators';
import { State } from '../common/state';
import { Purchase } from '../common/purchase';
@Injectable({
  providedIn: 'root'
})
export class CheckoutService {


  private baseurlForCountries='http://localhost:8080/api/countries';
  private baseurlForstates='http://localhost:8080/api/states';
  private baseurlForCheckout='http://localhost:8080/api/checkout/purchase';
  


 // private baseurlForCountries='http://3.128.22.184:8080/api/countries';
 // private baseurlForstates='http://3.128.22.184:8080/api/states';
  constructor(private httpClient: HttpClient) {

   }


   checkout(purchase: Purchase): Observable<any>{
    return this.httpClient.post<Purchase>(this.baseurlForCheckout, purchase);
   }

   getStatesByCountryCode(code: string) {
    const urlForStatesFindByCountryCode=`${this.baseurlForstates}/search/findByCountryCode?code=${code}`;
    return  this.httpClient.get<GetResponseStates>(urlForStatesFindByCountryCode).pipe(
      map(response => response._embedded.states)
    );
  }

   getCountryList():Observable<Country[]> {
    
    return  this.httpClient.get<GetResponseCountries>(this.baseurlForCountries).pipe(
      map(response => response._embedded.countries)
    );
  }

getMonthsForCreditCard(startMonth : number): Observable<number[]> {

  let data: number []=[];

for (let theMonth= startMonth; theMonth<=12; theMonth++) {
  data.push(theMonth);
  
}

  return of(data);

}


getYearForCreditCard(): Observable<number[]> {

  let data: number []=[];

  const startYear: number=new Date().getFullYear();
  const endYear: number= startYear+10;

for (let theYear = startYear; theYear<=endYear; theYear++) {
  data.push(theYear);
  
}

  return of(data);

}


}

interface GetResponseCountries{
  _embedded:{
    countries: Country[];
  }
  
  }

  
interface GetResponseStates{
  _embedded:{
    states: State[];
  }
  
  }