import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {TokenService} from "../services/token.service";

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const axiosService : TokenService = inject(TokenService);
  if(axiosService.authenticated){
    return true;
  }else{
    router.navigate(["/auth/login"]);
    return false;
  }
};
