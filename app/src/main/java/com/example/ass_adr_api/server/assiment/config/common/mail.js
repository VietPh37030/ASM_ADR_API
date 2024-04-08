var nodemailer = require("nodemailer");

const transporter = nodemailer.createTransport({
    service:"gmail",
    auth :{
        user:"anhpvph37030@fpt.edu.vn",
            pass:"tkfw fxld hizp gmiv"
    },
})
module.exports = transporter;