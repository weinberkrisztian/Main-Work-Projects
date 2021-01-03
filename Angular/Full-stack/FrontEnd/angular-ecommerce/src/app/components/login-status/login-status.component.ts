import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {

  isAuthenticated: boolean = false;
  userFullName: string;

  constructor(private oktaAuthServcie: OktaAuthService) { }

  ngOnInit(): void {

    this.oktaAuthServcie.$authenticationState.subscribe(
      (result)=> {
       this.isAuthenticated=result;
       this.getUserDetails();
      }
    );

  }


  getUserDetails() {
    if(this.isAuthenticated){
      this.oktaAuthServcie.getUser().then(
        res => {
          this.userFullName=res.name;
        }
      );
    }

  }

  logout(){
    this.oktaAuthServcie.signOut();
  }

}
