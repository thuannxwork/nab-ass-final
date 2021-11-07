import {Component, OnInit} from '@angular/core';
import {ApiService} from 'src/app/Service/api.service';
import {Product} from 'src/app/Model/product';
import {Category} from '../../Model/category';
import {Brand} from '../../Model/brand';
import {ProductSearch} from '../../Model/productsearch';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  products: Product[] = [];
  categoryList: Category[] = [];
  brandList: Brand[] = [];
  productSearch: ProductSearch = {
    categoryId: '',
    brandId: '',
    color: '',
    fromPrice: '',
    toPrice: ''
  };

  constructor(private api: ApiService) {
  }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    if (this.api.isAuthenticated) {
      this.api.getProducts().subscribe(
        res => {
          this.products = res.oblist;
        }
      );
      this.api.getAllCategory().subscribe(
        res => {
          this.categoryList = res.oblist;
        }
      );
      this.api.getBrand(0).subscribe(
        res => {
          this.brandList = res.oblist;
        }
      );
    }
  }

  onChangeCate(category: any) {
    this.brandList = [];
    if (category.value) {
      this.api.getBrand(category.value).subscribe(
        res => {
          this.brandList = res.oblist;
        }
      );
    } else {
      this.api.getBrand(0).subscribe(
        res => {
          this.brandList = res.oblist;
        }
      );
    }
  }

  addToCart(e) {
    this.api.addToCart(e).subscribe(res => {
      alert('Add to cart success !!!');
    });
  }

  searchProduct() {
    this.products = [];
    this.api.findProducts(this.productSearch.categoryId, this.productSearch.brandId, this.productSearch.color,
      this.productSearch.fromPrice, this.productSearch.toPrice).subscribe(
      res => {
        this.products = res.oblist;
      }
    );
  }

  reset() {
    this.productSearch = {
      categoryId: '',
      brandId: '',
      color: '',
      fromPrice: '',
      toPrice: ''
    };
    this.loadAll();
  }
}
