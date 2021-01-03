import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { AuthComponent } from "./auth.component";

const appRoutes: Routes= [
    {path: 'auth', component: AuthComponent},
];

@NgModule({
    declarations: [
        AuthComponent
    ],
    imports: [
        RouterModule.forChild(appRoutes),
        SharedModule,
    ]
})
export class AuthModule{}