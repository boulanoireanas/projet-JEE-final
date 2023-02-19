import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { Injectable } from "@angular/core";
import { KeycloakEventType } from 'keycloak-angular';



@Injectable({ providedIn: "root" })
export class SecurityService {
    public profile?: KeycloakProfile;
    constructor(public kc: KeycloakService) {
        this.init();
    }
    init() {
        this.kc.keycloakEvents$.subscribe(
            {
                next: (e) => {
                    if (e.type == KeycloakEventType.OnAuthSuccess) {
                        this.kc.loadUserProfile().then(profile => {
                            {
                                this.profile = profile;
                            }
                        })
                    }
                }

            }
        )
    }
    public hasRoleIn(roles: string[]): boolean {
        let userRoles = this.kc.getUserRoles();
        for (let role of roles) {
            if (userRoles.includes(role))
                return true;
        }
        return false;
    }
}
