import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Product} from '../../Model/product';
import {ApiService} from '../../Service/api.service';

@Component({
  selector: 'productdetail',
  templateUrl: './productdetail.component.html',
  styleUrls: ['./productdetail.component.css']
})
export class ProductdetailComponent implements OnInit {

  productId: string;
  product: Product = {
    id: null,
    description: null,
    price: null,
    name: null,
    quantity: null,
    categoryId: null,
    brandId: null,
    color: null,
    image: null,
    category: {},
    brand: {}
  };

  constructor(private route: ActivatedRoute, private api: ApiService) {
    this.api.getProducts().subscribe(
      res => {
        res.oblist.forEach(pro => {
          if (pro.id == this.productId) {
            this.product = pro;
          }
        });
      }
    );
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.productId = params['productId'];
    });
  }

}
