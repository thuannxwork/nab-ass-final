import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Product} from 'src/app/Model/product';
import {ApiService} from 'src/app/Service/api.service';
import {Category} from '../../../Model/category';
import {Brand} from '../../../Model/brand';

@Component({
  selector: 'app-edit-item',
  templateUrl: './edit-item.component.html',
  styleUrls: ['./edit-item.component.css']
})
export class EditItemComponent implements OnInit {

  product: Product = {
    id: 0,
    description: '',
    price: 0,
    name: '',
    quantity: 0,
    categoryId: 0,
    brandId: 0,
    color: '',
    image: null,
    category: null,
    brand: null,
  };
  products: Product[] = [];
  fileToUpload: File = null;
  auth: string;
  prodid: string;
  imageUrl: string = '/assets/img/noimage.png';
  categoryList: Category[] = [];
  brandList: Brand[] = [];

  constructor(private route: ActivatedRoute, private api: ApiService, private router: Router) {
    if (this.api.isAuthenticated) {
      this.auth = this.api.getToken();
      this.api.getProducts().subscribe(
        res => {
          res.oblist.forEach(pro => {
            if (pro.id == this.prodid) {
              this.product = pro;
              this.fileToUpload = pro.image;
              this.api.getBrand(pro.categoryId).subscribe(
                res1 => {
                  this.brandList = res1.oblist;
                }
              );
            }
          });
        }
      );
      this.api.getAllCategory().subscribe(
        res => {
          this.categoryList = res.oblist;
        }
      );
    }
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.prodid = params['user'];
    });
  }

  handleFileInput(file: FileList) {
    this.fileToUpload = file.item(0);
    var reader = new FileReader();
    reader.onload = (event: any) => {
      this.imageUrl = event.target.result;
    };
    reader.readAsDataURL(this.fileToUpload);
  }

  updateProd(desc: any, quan: any, price: any, prodname: any, image: any) {
    this.api.updateProduct(desc.value, quan.value, price.value, prodname.value, this.fileToUpload, this.product.id).subscribe(res => {
      alert('Update product success !!!');
    });
  }

  goToHome() {
    this.router.navigate(['/admin']);
  }

}
