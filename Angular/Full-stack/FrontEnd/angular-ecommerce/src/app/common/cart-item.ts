import { Product } from './product';

export class CartItem {

    id: string;
    name:string;
    unitPrice:number;
    imageUrl:string;
    
    quantity:number;

    constructor(product:Product){
        this.id=product.id;
        this.name=product.name;
        this.unitPrice=product.unitPrice;
        this.imageUrl=product.imageUrl;

        this.quantity=1;
    }
}
