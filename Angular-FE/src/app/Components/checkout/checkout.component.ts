import {Component, OnInit} from '@angular/core';
import {Cart} from '../../Model/cart';
import {ApiService} from '../../Service/api.service';
import {Router} from '@angular/router';

@Component({
  selector: 'checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  cartList: Cart[];
  totalSum: number = 0;

  constructor(private api: ApiService, private route: Router) {
  }

  ngOnInit() {
    this.api.getCartItems().subscribe(res => {
      this.cartList = res.oblist;
      this.cartList.forEach(value => {
        this.totalSum = this.totalSum + (value.quantity * value.price);
      });
    });
  }

  placeOrder() {
    this.api.placeOrder().subscribe(res => {
      this.route.navigate(['/home/thankyou']);
    });
  }

}
