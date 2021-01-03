import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { RecipeService } from '../recipes/recipe.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit , OnDestroy{

  isAuthenticated= false;
  private userSubscription: Subscription;

  constructor(private recipeService: RecipeService, private authService: AuthService,) { }


  ngOnInit(): void {
    this.userSubscription=this.authService.userSubject.subscribe(
      (user) => {
        this.isAuthenticated= !user ? false : true;
      }
    );
  }

  logout(){
    this.authService.logout();
    
  }

  storeRecipes(){
    this.recipeService.storeRecipes();
  }

  fetchRecipes(){
    this.recipeService.fetchRecipes().subscribe();
  }


  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }
}
