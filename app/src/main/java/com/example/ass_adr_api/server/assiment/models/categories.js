const mongoose = require('mongoose');
const Scheme = mongoose.Schema;
const Categories = new Scheme({
    loai: { type: String },
    anh_loai:{type:String},
}, {
    timestamps: true
})

module.exports = mongoose.model('categories',Categories)