// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  baseUrl:"http://localhost:8888", // gateway URL
  signupUrl : "/api/sso/home/signup",
  loginUrl : "/api/sso/home/auth",
  addToCartUrl : "/api/payment/user/addToCart",
  viewCartUrl : "/api/payment/user/viewCart",
  updateCartUrl : "/api/payment/user/updateCart",
  deleteCartUrl: "/api/payment/user/delCart",
  addAddressUrl : "/api/payment/user/addAddress",
  viewAddressUrl : "/api/payment/user/getAddress",
  productsUrl : "/api/inventory/user/getProducts",
  addProductUrl : "/api/inventory/admin/addProduct",
  deleteProductUrl : "/api/inventory/admin/delProduct",
  updateProductUrl : "/api/inventory/admin/updateProducts",
  viewOrderUrl : "/api/payment/seller/viewOrders",
  updateOrderUrl : "/api/payment/seller/updateOrder",
  placeOrderUrl : "/api/payment/user/placeOrder",
  logoutUrl : "/api/sso/home/logout",
  categoryUrl : "/api/inventory/user/getAllCategory",
  brandUrl : "/api/inventory/user/getBrandByCategoryId",
  findProductsUrl : "/api/inventory/user/findProducts",
};

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error',  // Included with Angular CLI.
