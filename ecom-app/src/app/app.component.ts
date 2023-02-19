import { SecurityService } from './services/securityService';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ecom-app';
  constructor(public ss: SecurityService) {
    console.log(ss.profile?.firstName)
  }
  async Login() {
    await this.ss.kc.login(
      {
        redirectUri: window.location.origin
      }
    )
  }
  onLogout() {
    this.ss.kc.logout(window.location.origin)
  }
}
