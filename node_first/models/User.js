var mongoose = require('mongoose');
var Schema = mongoose.Schema;

// create a schema
var userSchema = new Schema({
    Name: {
        type: String,
        required: true
    },
    user_id: {
        type: String,
        required: true
    },
    Username: {
        type: String,
        required: true
    },
    salt:{
        type:String,
        required: true
    },
    Email: {
        type: String,
        required: true
    },
    Mobile: {
        type: String,
        required: true
    },
    Designation: {
        type: String,
        required: true
    },
    Password: {
        type: String,
        required: true,
        
    },
    Photos: {
        url: [{
            type: String
        }],
        //validate: [arrayLimit,'Size reached'],
        required: false
    },
    display_picture: {
        url: [{
            type: String
        }],
        required: false
}

});


function arrayLimit(val) {
    return val.size<= 5;

}
var User = mongoose.model('User', userSchema);

module.exports = User;