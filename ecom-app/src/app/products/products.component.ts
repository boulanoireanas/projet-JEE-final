import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  constructor(private http: HttpClient, KeycloakService: KeycloakService) { }

  products: any;

  ngOnInit(): void {

    this.http.get("http://localhost:8888/PRODUCT-SERVICE/products").subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => { }
    }
    )
  }


}
