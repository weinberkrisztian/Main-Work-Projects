import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { Ingredient } from "../shared/ingredient.model";
import { ShoppingListService } from "../shopping-list/shopping-list.service";
import { Recipe } from "./recipe.model";
import { exhaustMap, map, take, tap }  from 'rxjs/operators';
import { AuthService } from "../auth/auth.service";
@Injectable({
  providedIn: 'root'
})
export class RecipeService{

    recipeChanged= new Subject<Recipe[]>();
    private recipes: Recipe[] = [
/*         new Recipe("Tasty Schnitzel", "Very Tasty Schnitzel", "https://cdn.pixabay.com/photo/2016/06/15/19/09/food-1459693_960_720.jpg",[
            new Ingredient('Meat',1), new Ingredient('French Fries',20)
        ]),
        new Recipe("Burger", "Big tasty burger", "https://cdn.pixabay.com/photo/2016/03/05/19/02/hamburger-1238246__340.jpg",[
            new Ingredient('Bread Slice',2),new Ingredient('Meat',1),new Ingredient('Salad',1)
        ]) */
      ];

      constructor(private shoppingListService: ShoppingListService, private http: HttpClient,private router :Router, private authService: AuthService){
          
      }

      storeRecipes(){
        const recipes = this.recipes.slice();
        // put method overrides all the data on the backend
        this.http.put('https://shopping-list-maker-app-default-rtdb.firebaseio.com/recipes.json', 
        recipes)
        .subscribe(
          response => {
            console.log(response);
          }
        );
      }

      fetchRecipes(){
            return  this.http.get<Recipe[]>('https://shopping-list-maker-app-default-rtdb.firebaseio.com/recipes.json').pipe(
        map(
          recipes => {
            return recipes.map(recipe => {
              return {...recipe, ingredients: recipe.ingredients ? recipe.ingredients : []};
            });
          }
        ),tap(
          recipes => {
            this.recipes=recipes;
            this.recipeChanged.next(this.recipes);
          }
        ));
      }


      getRecipe(id :number){
        return this.recipes[id];
      }

      getRecipes(){
          return this.recipes.slice();
      }

      addIngredientsToShoppingList(ingredients: Ingredient []){
        this.shoppingListService.addIngredients(ingredients);
      }

      addRecipe(newRecipe: Recipe){
        this.recipes.push(newRecipe);
        this.recipeChanged.next(this.recipes.slice());
      }

      updateRecipe(index: number, updatedRecipe: Recipe){
        this.recipes[index]=updatedRecipe;
        this.recipeChanged.next(this.recipes.slice());
      }

      deleteRecipe(index: number){
        this.recipes.splice(index,1);
        this.recipeChanged.next(this.recipes.slice());

      }

}