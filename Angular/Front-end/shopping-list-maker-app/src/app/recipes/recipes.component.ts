import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Recipe } from './recipe.model';
import { RecipeService } from './recipe.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']

})
export class RecipesComponent implements OnInit , OnDestroy{



  constructor() { 

  }
  
  ngOnInit(): void {
  }
  
  ngOnDestroy(): void {

  }


}
