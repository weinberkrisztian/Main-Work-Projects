import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import {  AuthResopnseData, AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {



  authFormGroup: FormGroup;
  isLoginMode : boolean = true;
  isLoading : boolean= false;
  error: string = null;


  constructor(private authService: AuthService, private router : Router) { }

  ngOnInit(): void {

    this.authFormGroup= new FormGroup(
      {
        'email': new FormControl(null, [Validators.required, Validators.email]),
        'password': new FormControl(null, [Validators.required, Validators.minLength(6)])
      }

    );
  }

  onSwitchMode(){
    this.isLoginMode=!this.isLoginMode;
  }


  onSubmit(){
    if(!this.authFormGroup.valid){
      return;
    }

    const email= this.authFormGroup.get('email').value;
    const password= this.authFormGroup.get('password').value;

    let authObs: Observable<AuthResopnseData>;

    this.isLoading=true;

    if(this.isLoginMode){
      authObs=this.authService.login(email, password);
    }else{
      authObs= this.authService.signUp(email, password)
  }

    authObs.subscribe(
      responseData => {
        console.log(responseData);
        this.isLoading=false;
        this.router.navigate(['/recipes']);
      }
    , error =>{
      console.log(error);
      
        this.error= error;
      this.isLoading=false;
    });
    this.authFormGroup.reset();

  }

  handleAlert(){
    this.error = null;
  }

}
