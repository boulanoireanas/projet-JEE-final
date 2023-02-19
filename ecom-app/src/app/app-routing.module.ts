import { AuthGuard } from './guards/security.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from "./products/products.component";
import { CustomersComponent } from "./customers/customers.component";
import { OrdersComponent } from "./orders/orders.component";
import { OrderDetailsComponent } from "./order-details/order-details.component";

const routes: Routes = [
  {
    path: "products", component: ProductsComponent, canActivate: [AuthGuard], data: { roles: ['USER', 'ADMIN'] }
  },
  {
    path: "customers", component: CustomersComponent, canActivate: [AuthGuard], data: { roles: ['USER', 'ADMIN'] }
  },
  {
    path: "orders/:customerId", component: OrdersComponent, canActivate: [AuthGuard], data: { roles: ['USER'] }
  },
  {
    path: "order-details/:orderId", component: OrderDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
