const mongoose = require('mongoose');
const Scheme = mongoose.Schema;
const Products = new Scheme({
    product_name: {type:String},
    price:{type:Number},
    quantity:{type:Number},
    size:{type:String},
    image:{type:String},
    origin:{type:String},
    id_categories: {type:Scheme.Types.ObjectId,ref:'categories'},
    isFavorite:{type:Boolean},
},{
    timestamps:true
})


module.exports = mongoose.model('products',Products)