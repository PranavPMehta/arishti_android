
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var gMessageSchema = new Schema({
    id: String,
    timestamp: String,
    user_id: String,
    status: String,
    location: String,
    reference_id: String,
    data: [String | Buffer],
    viewed_user_id: [{type:String}]
});

// create a schema
var groupChatSchema = new Schema({
    group_id : String,
    gmessages: [gMessageSchema]

});
var GroupChat = mongoose.model('GroupChat', groupChatSchema);
module.exports = GroupChat;