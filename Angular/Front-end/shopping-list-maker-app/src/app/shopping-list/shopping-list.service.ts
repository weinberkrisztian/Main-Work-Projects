import { EventEmitter, Injectable } from "@angular/core";
import { Subject } from "rxjs";
import { Ingredient } from "../shared/ingredient.model";

@Injectable({
    providedIn: 'root'
})
export class ShoppingListService{

    ingredientsChanged= new Subject<Ingredient []>();
    startEditing= new Subject<number>();

     private ingredients : Ingredient [] = [
        new Ingredient("Apples", 5),
        new Ingredient("Tomatos", 10)
      ];


      getIngredients(){
          return this.ingredients.slice();
          
      }

      addIngredient(ingredient: Ingredient){
          this.ingredients.push(ingredient);
          this.ingredientsChanged.next(this.ingredients.slice());
      }

      addIngredients(ingredients: Ingredient[]){

        this.ingredients.push(...ingredients);
        this.ingredientsChanged.next(this.ingredients.slice());
    }

    updateIngredient(ingredient: Ingredient, editedItemIndex: number){
        this.ingredients[editedItemIndex]=ingredient;
        this.ingredientsChanged.next(this.ingredients.slice());
    }

    deleteIngredient(editedItemIndex: number){
        this.ingredients.splice(editedItemIndex,1);
        this.ingredientsChanged.next(this.ingredients.slice());
    }

    getIngredient(index: number){
        return this.ingredients[index];
    }

}