import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ShoppingListService } from 'src/app/shopping-list/shopping-list.service';
import { Recipe } from '../recipe.model';
import { RecipeService } from '../recipe.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {

  recipeDetail : Recipe;
  id: number;

  constructor(private shoppingListService: ShoppingListService, private recipeService: RecipeService, private activatedRoute: ActivatedRoute,
    private router: Router) { 
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      (params : Params) => {
        this.id = +params['id'];
        this.recipeDetail=this.recipeService.getRecipe(this.id);

      }
    );
  }

  addToShoppingList(){
      this.recipeService.addIngredientsToShoppingList(this.recipeDetail.ingredients);
    
  }

  editRecipe(){
    this.router.navigate(['edit'], {relativeTo: this.activatedRoute});
  }


  deleteRecipe(){
    this.recipeService.deleteRecipe(this.id);
    this.router.navigate(['../'], {relativeTo: this.activatedRoute});
  }
}
