import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { Ingredient } from '../shared/ingredient.model';
import { ShoppingListService } from './shopping-list.service';

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html',
  styleUrls: ['./shopping-list.component.css']
})
export class ShoppingListComponent implements OnInit, OnDestroy {

  ingredients : Ingredient [];
  private ingredientChangedSubscription: Subscription;
  
  constructor(private shoppingListService: ShoppingListService) { }


  
  ngOnInit(): void {
    this.ingredients=this.shoppingListService.getIngredients();
    this.ingredientChangedSubscription= this.shoppingListService.ingredientsChanged.subscribe(
      (ingredients: Ingredient[]) => ( this.ingredients= ingredients))
    }
    
    
    ngOnDestroy(): void {
      this.ingredientChangedSubscription.unsubscribe();
    }

    onEditItem(index: number){
      this.shoppingListService.startEditing.next(index);
    }

}
