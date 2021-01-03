import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-search-list',
  templateUrl: './search-list.component.html',
  styleUrls: ['./search-list.component.css']
})
export class SearchListComponent implements OnInit {

  

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  searchForProduct(value:string){
  this.router.navigateByUrl(`/search/${value}`);

  }

}
