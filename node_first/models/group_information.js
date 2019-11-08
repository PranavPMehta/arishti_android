
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var gUsrSchema = new Schema({
    user_id: String,
    username: String
});
var groupInformationSchema = new Schema({
    group_id : String,
    display_picture:{
        url : String
        },
    description: String,
    members: [gUsrSchema],
    admin: String,
    creation_timestamp:String,
    creator : String
});
var GroupInformation = mongoose.model('GroupInformation', groupInformationSchema);
module.exports = GroupInformation;