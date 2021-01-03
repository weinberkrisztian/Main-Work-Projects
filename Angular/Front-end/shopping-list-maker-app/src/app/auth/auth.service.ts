import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, Subject, throwError } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { User } from "./user.model";
import { environment} from '../../environments/environment';

export interface AuthResopnseData{
    kind: string;
    idToken: string;
    email: string;
    refreshToken: string;
    expiresIn: string;
    localId: string;
    registered?: boolean;
}

@Injectable({
    providedIn: 'root'
})
export class AuthService{

    userSubject = new BehaviorSubject<User>(null);
    private tokenExpirationTimer : any;

    constructor(private http: HttpClient,  private router: Router){}

    logout(){
        this.userSubject.next(null);
        this.router.navigate(['/auth']);
        localStorage.removeItem('userData');
        if(this.tokenExpirationTimer){
            clearTimeout(this.tokenExpirationTimer);
        }
        this.tokenExpirationTimer=null;
    }

    autoLogout(expirationDuration: number){
      this.tokenExpirationTimer=  setTimeout(() =>{
            this.logout();
        }, expirationDuration);
    }

    signUp(email: string, password: string){
        return this.http.post<AuthResopnseData>('https://identitytoolkit.googleapis.com/v1/accounts:signUp?key='+environment.firebaseAPIKey,
         {
            email: email,
            password: password,
            returnSecureToken: true
        }).pipe(
            catchError(this.handleErrors),
            tap(resData => {
                this.handleAuthentication(resData.email, resData.localId, resData.idToken, +resData.expiresIn);
            })
         );
    }


    login(email: string, password: string){
        return this.http.post<AuthResopnseData>('https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key='+environment.firebaseAPIKey,
         {
            email: email,
            password: password,
            returnSecureToken: true
        }).pipe(
            catchError(this.handleErrors),
            tap(resData => {
                this.handleAuthentication(resData.email, resData.localId, resData.idToken, +resData.expiresIn);
            })
    );
    }

    autoLogin(){
        const userData: {
            email: string;
            id: string;
            _token: string;
            _tokenExpirationDate : string;
        }= JSON.parse(localStorage.getItem('userData'));
        if(!userData){
            return;
        }
        
        const loadedUser= new User(userData.email, userData.id, userData._token, new Date(userData._tokenExpirationDate));

        if(loadedUser.token){
            this.userSubject.next(loadedUser);
            const expirationDuration= new Date(userData._tokenExpirationDate).getTime() - new Date().getTime();
            this.autoLogout(expirationDuration);
        }
    }

    private handleAuthentication(email: string, userId: string, token: string, expiresIn: number){
        const expirationDate= new Date(new Date().getTime() + +expiresIn * 1000);
        const user = new User(email, userId, token, expirationDate);
        this.userSubject.next(user);
        this.autoLogout(expiresIn * 1000);
        localStorage.setItem('userData', JSON.stringify(user));
    }

    private handleErrors(errorResponse: HttpErrorResponse){
            let errorMessage='An error occured!';
            console.log(errorResponse);

            if(!errorResponse.error || !errorResponse.error.error ){
                return throwError(errorMessage);
            }
            switch(errorResponse.error.error.message){
                case 'EMAIL_EXISTS' : 
                errorMessage= 'Email already exists!';
                break;
                case 'EMAIL_NOT_FOUND':
                errorMessage= 'Email does not exists!';
                break;
                case 'INVALID_PASSWORD':
                errorMessage= 'Password is invalid!';
                break;        
        }
        return throwError(errorMessage);
    
    }
}