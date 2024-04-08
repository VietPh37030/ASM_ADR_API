var express = require('express');
var router = express.Router();
//Them model
//API them distributor
const Categories = require('../models/categories');
const Products = require('../models/products');
 const Upload = require('../config/common/upload');
    ///GỬI EMAIL ĐĂNG KÝ TÀI KHOẢN THÀNH CÔNG
const Users = require('../models/user'); 
const Transporter = require('../config/common/mail') 
router.post('/register-send-email',Upload.single('avatar'),async(req,res) =>{ 
    try { 
        const data = req.body; 
        const {file} = req 
        const newUser = Users({ 
          username: data.username,  
          password : data.password, 
          email: data.email,  
          name: data.name, 
          avatar:`${req.protocol}://${req.get("host")}/uploads/${file.filename}`,  
          //url avatar http://localhost:3000/uploads/filename 
        }) 
        const result = await newUser.save() 
        if(result) 
        {   //Gửi mail 
            const mailOptions = { 
                from: "anhpvph37030@fpt.edu.vn", //email gửi đi 
                to: result.email, // email nhận 
                subject: "Đăng ký thành công", //subject 
                text: "Cảm ơn bạn đã đăng ký", // nội dung mail 
              }; 
            // Nếu thêm thành công result !null trả về dữ liệu 
            await Transporter.sendMail(mailOptions); // gửi mail 
            res.json({ 
                "status" : 200, 
                "messenger" : "Thêm thành công", 
                "data" : result 
            }) 
        }else 
        {// Nếu thêm không thành công result null, thông báo không thành công 
            res.json({ 
                "status" : 400 , 
                "messenger" : "Lỗi, thêm không thành công", 
                "data" : [] 
            }) 
        } 
    } catch (error) { 
        console.log(error); 
    }
})
// BÀI 4: JWT (Đăng nhập trả về token, refreshToken) 
const JWT = require('jsonwebtoken'); 
const SECRETKEY = "FPTPOLYTECHNIC" 
router.post('/login',async (req,res)=>{ 
    try { 
        const {username,password} = req.body; 
        const user = await Users.findOne({username,password}) 
        if(user) 
        {    
            //Token người dùng sẽ sử dụng gửi lên trên header mỗi lần muốn gọi api 
            const token = JWT.sign({id: user._id},SECRETKEY,{expiresIn: '1h'}); 
            //Khi token hết hạn, người dùng sẽ call 1 api khác để lấy token mới 
            //Lúc này người dùng sẽ truyền refreshToken lên để nhận về 1 cặp token,refreshToken mới 
            //Nếu cả 2 token đều hết hạn người dùng sẽ phải thoát app và đăng nhập lại 
            const refreshToken = JWT.sign({id: user._id},SECRETKEY,{expiresIn: '1d'}) 
            //expiresIn thời gian token 
            res.json({ 
                "status" : 200, 
                "messenger" : "Đăng nhâp thành công", 
                "data" : user, 
                "token" : token, 
                "refreshToken" : refreshToken 
            }) 
        }else 
        { 
            // Nếu thêm không thành công result null, thông báo không thành công 
            res.json({ 
                "status" : 400 , 
                "messenger" : "Lỗi, đăng nhập không thành công", 
                "data" : [] 
            }) 
        } 
    } catch (error) { 
        console.log(error); 
    } 
}) 
// user
router.get("/get-list-user", async (req, res) => {
  try {
    //lấy danh sách theo thứ tự nhà phân phối mới nhất
    const data = await Users.find().sort({ createdAt: -1 });
   
    if (data) {
      //nếu lây ds thanh công thì trả về danh sách dữ liệu
      res.json({
        status: 200,
        messenger: "Lấy danh sách thành công",
        data: data,
      });
    } else {
      res.json({
        status: 400,
        messenger: "lấy danh sách thất bại",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
  }
});
router.put("/update-user-by-id/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const data = req.body;
    const result = await Users.findByIdAndUpdate(id, {
     username:data.username,
     password:data.password,
     name:data.name,
     avatar:data.avatar,
    });
    if (result) {
      res.json({
        status: 200,
        messenger: "tìm thấy id và update thành công",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        messenger: "update thất bại",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
  }
});
router.get("/get-user-info", async (req, res) => {
  try {
    const userId = req.query.id; // Lấy id của người dùng từ query params
    const user = await Users.findById(userId); // Tìm người dùng trong cơ sở dữ liệu dựa trên id
    
    if (user) {
      res.json({
        status: 200,
        messenger: "Lấy thông tin người dùng thành công",
        data: user,
      });
    } else {
      res.json({
        status: 404,
        messenger: "Không tìm thấy người dùng",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({
      status: 500,
      messenger: "Lỗi server",
      data: null,
    });
  }
});


// close 
///// catergories
router.get("/get-list-categories", async (req, res) => {
    try {
      //lấy danh sách theo thứ tự nhà phân phối mới nhất
      const data = await Categories.find().sort({ createdAt: -1 });
     
      if (data) {
        //nếu lây ds thanh công thì trả về danh sách dữ liệu
        res.json({
          status: 200,
          messenger: "Lấy danh sách thành công",
          data: data,
        });
      } else {
        res.json({
          status: 400,
          messenger: "lấy danh sách thất bại",
          data: [],
        });
      }
    } catch (error) {
      console.log(error);
    }
  });

  //viết api để xóa nhà phân phối theo id
  router.delete("/delete-categories-by-id/:id", async (req, res) => {
    try {
      const { id } = req.params;
      const result = await Categories.findByIdAndDelete(id);
      if (result) {
        res.json({
          status: 200,
          messenger: "tìm và xóa theo id thành công",
          data: result,
        });
      } else {
        res.json({
          status: 400,
          messenger: "tìm và xóa thất bại",
          data: [],
        });
      }
    } catch (error) {
      console.log(error);
    }
  });
  //api update nha phân phối
  router.put("/update-categories-by-id/:id", async (req, res) => {
    try {
      const { id } = req.params;
      const data = req.body;
      const result = await Categories.findByIdAndUpdate(id, {
        loai:data.loai,
        anh_loai:data.anh_loai,
      });
      if (result) {
        res.json({
          status: 200,
          messenger: "tìm thấy id và update thành công",
          data: result,
        });
      } else {
        res.json({
          status: 400,
          messenger: "update thất bại",
          data: null,
        });
      }
    } catch (error) {
      console.log(error);
    }
  });
  router.post('/add-categories', async (req, res) => {
    try {
        const data = req.body;//lay du lieu tu body
        const newCategories = new Categories({
                loai:data.loai,
                anh_loai:data.anh_loai,
        });//tao 1 doi tuong moi
        const result = await newCategories.save();//them vao database
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Them thanh cong",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Them that bai",
                "data": []
            })

        }
    } catch (error) {
        console.log(error);
    }
})
///cloes categores
// products
router.get("/get-list-product", async (req, res) => {
  try {
    //lấy danh sách theo thứ tự nhà phân phối mới nhất
    const data = await Products.find().populate('id_categories');
   
    if (data) {
      //nếu lây ds thanh công thì trả về danh sách dữ liệu
      res.json({
        status: 200,
        messenger: "Lấy danh sách thành công",
        data: data,
      });
    } else {
      res.json({
        status: 400,
        messenger: "lấy danh sách thất bại",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
  }
});
router.post('/add-product', async (req, res) => {
  try {
      const data = req.body;//lay du lieu tu body
      const newProduct = new Products({
        product_name:data.product_name,
        price:data.price,
        quantity:data.quantity,
        size:data.size,
        image:data.image,
        origin:data.origin,
        id_categories:data.id_categories,
        isFavorite:data.isFavorite,
      });//tao 1 doi tuong moi
      const result = await newProduct.save();//them vao database
      if (result) {
          res.json({
              "status": 200,
              "messenger": "Them thanh cong",
              "data": result
          })
      } else {
          res.json({
              "status": 400,
              "messenger": "Them that bai",
              "data": []
          })

      }
  } catch (error) {
      console.log(error);
  }
})
router.get('/get-favorite-products', async (req, res) => {
  try {
    const productId = req.query.id; // Lấy id của sản phẩm từ query params
    const favoriteProducts = await Products.find({ _id: productId });
   
    if (favoriteProducts) {
      // Trả về danh sách sản phẩm yêu thích có trạng thái isFavorite là true
      const filteredProducts = favoriteProducts.filter(product => product.isFavorite === true);
      res.json({
        status: 200,
        messenger: "Lấy danh sách sản phẩm yêu thích thành công",
        data: filteredProducts,
      });
    } else {
      res.json({
        status: 404,
        messenger: "Không tìm thấy sản phẩm yêu thích",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({
      status: 500,
      messenger: "Lỗi server",
      data: null,
    });
  }
});
router.put('/update-product-by-id/:id',async(req,res)=>{
  try {
      const {id} = req.params
      const data = req.body;
      const updateproduct = await Products.findById(id)
      let result = null;
      if (updateproduct) {
          updateproduct.product_name = data.product_name ?? updateproduct.product_name;
          updateproduct.price = data.price ?? updateproduct.price;
          updateproduct.quantity = data.quantity ?? updateproduct.quantity;
          updateproduct.size = data.size ?? updateproduct.size;
          updateproduct.image = data.image ?? updateproduct.image;
          updateproduct.origin = data.origin ?? updateproduct.origin;
          updateproduct.id_categories = data.id_categories ?? updateproduct.id_categories;
          updateproduct.isFavorite = data.isFavorite ?? updateproduct.isFavorite;
          result = await updateproduct.save();
      }
      if (result) {
          res.json({
              "status":200,
              "messenger":"THnaah cong",
              "data":result
          })
      }else{
          res.json({
              "status":400,
              "messenger":"That bai",
              "data":[]
          })   
      }

  } catch (error) {
      console.log(error);
  }
})

// close project


  module.exports = router;