import { ElementRef, EventEmitter, OnDestroy, Output , ViewChild} from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Ingredient } from 'src/app/shared/ingredient.model';
import { ShoppingListService } from '../shopping-list.service';

@Component({
  selector: 'app-shopping-edit',
  templateUrl: './shopping-edit.component.html',
  styleUrls: ['./shopping-edit.component.css']
})
export class ShoppingEditComponent implements OnInit, OnDestroy {

  startEditingSubscription: Subscription;
  shoppingListForm: FormGroup;
  editMode= false;
  editedItemIndex: number;
  editedItem: Ingredient;
 
  constructor(private shoppingListService: ShoppingListService) { }


  ngOnInit(): void {
     
    this.startEditingSubscription=this.shoppingListService.startEditing.subscribe(
      (index: number)=> {
        this.editMode=true;
        this.editedItemIndex=index;
        this.editedItem=this.shoppingListService.getIngredient(this.editedItemIndex);
        this.shoppingListForm.get('name').setValue(this.editedItem.name);
        this.shoppingListForm.get('amount').setValue(this.editedItem.amount);
      }
    );

    this.shoppingListForm= new FormGroup({
      'name': new FormControl(null, [Validators.required]),
      'amount': new FormControl(null,[Validators.required,Validators.pattern('[1-9]+[0-9]*$')])
    });
  }

  onSubmit(){
    const currentIngredient= new Ingredient(this.shoppingListForm.get('name').value,this.shoppingListForm.get('amount').value);
    if(this.editMode){
    this.shoppingListService.updateIngredient(currentIngredient, this.editedItemIndex);
    }else{
    this.shoppingListService.addIngredient(currentIngredient);
    }
    this.editMode=false;

  }

  onClear(){
    this.shoppingListForm.reset();
    this.editMode=false;
  }

  onDelete(){
    if(this.editMode){
      this.shoppingListService.deleteIngredient(this.editedItemIndex);
      this.onClear();
    }
  }

  ngOnDestroy(): void {
    this.startEditingSubscription.unsubscribe();
  }
}
