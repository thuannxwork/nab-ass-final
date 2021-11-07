import {Component, OnInit, Input, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Product} from 'src/app/Model/product';
import {EventEmitter} from '@angular/core';
import {ApiService} from '../../../Service/api.service';
import {NavigationExtras, Router} from '@angular/router';


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  @Input() public product;

  @Output() productAddToCart: EventEmitter<Product> = new EventEmitter<Product>();

  constructor(private http: HttpClient, private route: Router) {
  }

  ngOnInit() {

  }

  addToCart() {
    this.productAddToCart.emit(this.product);
  }

  viewDetail(productId: number) {
    const navigationExtras: NavigationExtras = {
      queryParams: {
        'productId': productId
      }
    };
    this.route.navigate(['/home/productdetail'], navigationExtras);
  }

}
