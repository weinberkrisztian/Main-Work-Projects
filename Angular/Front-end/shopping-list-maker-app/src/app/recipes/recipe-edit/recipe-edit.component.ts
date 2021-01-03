import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Recipe } from '../recipe.model';
import { RecipeService } from '../recipe.service';

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit {

  id: number;
  editMode = false;
  recipeForm: FormGroup;
  imagePathReference: string= '';
  constructor(private activatedRoute: ActivatedRoute, private recipeService: RecipeService, private router: Router) { }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(
      (params: Params) => {
        this.id= params['id'];
        this.editMode= params['id'] != null;
        this.initForm();
      }
    );
  }


  private initForm(){
    let recipeName= '';
    let recipeDescription= '';
    let recipeImagePath= '';
    let ingredientFormArray= new FormArray([]);
    
    if(this.editMode){
      const recipe=this.recipeService.getRecipe(this.id);
      recipeName=recipe.name;
      recipeDescription=recipe.description;
      recipeImagePath=recipe.imagePath;
      if(recipe['ingredients']){
        for(let ingredient of recipe.ingredients){
          ingredientFormArray.push(
            new FormGroup(
              {
                'name' : new FormControl(ingredient.name, [Validators.required]),
                'amount': new FormControl(ingredient.amount, [Validators.required, Validators.pattern('[1-9]+[0-9]*$')])
              }
            )
          );
        }
      }
      
    }

    this.recipeForm= new FormGroup({
      'name': new FormControl(recipeName, [Validators.required]),
      'description': new FormControl(recipeDescription, [Validators.required]),
      'imagePath': new FormControl(recipeImagePath, [Validators.required]),
      'ingredients' : ingredientFormArray
    });
  }

  onSubmit(){
    const newRecipe= new Recipe(
      this.recipeForm.get('name').value,
      this.recipeForm.get('description').value,
      this.recipeForm.get('imagePath').value,
      this.recipeForm.get('ingredients').value,
      
    );
    if(this.editMode){
      this.recipeService.updateRecipe(this.id,newRecipe);
      this.router.navigate(['../'], {relativeTo: this.activatedRoute});

    }else{
      this.recipeService.addRecipe(newRecipe);
      this.router.navigate(['../', this.recipeService.getRecipes().length-1], {relativeTo: this.activatedRoute});
    }


  }

  getFormArrayControls(){
    return (<FormArray>this.recipeForm.get('ingredients')).controls;
  }

  onAddIngredient(){
    (<FormArray>this.recipeForm.get('ingredients')).push(
      new FormGroup(
        {
          'name': new FormControl(null, [Validators.required]),
          'amount': new FormControl(null,[Validators.required, Validators.pattern('[1-9]+[0-9]*$')])
        }
      )
    );
  }

  onCancel(){
    this.recipeForm.reset();
    this.router.navigate(['../'], {relativeTo: this.activatedRoute});
  }

  onDeleteIngredient(index: number){
    (<FormArray>this.recipeForm.get('ingredients')).removeAt(index);
  }

  getFormGroupName(index: number, formControlName: string){
    return 'ingredients.'+index+'.'+formControlName;
  }

}
