import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from '../recipe.model';
import { RecipeService } from '../recipe.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit, OnDestroy {
  recipes: Recipe[] ;
  recipesSubscription: Subscription;


  constructor(private recipeService: RecipeService, private router: Router) { }


  ngOnInit(): void {
    this.recipesSubscription=this.recipeService.recipeChanged.subscribe(
      (recipeList: Recipe[]) => {
        this.recipes=recipeList;
      }
      );
      this.recipes=this.recipeService.getRecipes();
  }

  createNewRecipe(){
    this.router.navigate(['/recipes', 'new']);
  }

  ngOnDestroy(): void {
    this.recipesSubscription.unsubscribe();
    }
}
