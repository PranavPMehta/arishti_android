
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var pMessageSchema = new Schema({
    id: String,
    timestamp: String,
    user_id: String,
    status: String,
    location: String,
    reference_id: String,
    data: [String | Buffer]
});

// create a schema
var personalChatSchema = new Schema({
    user_1 : String,
    user_2 : String,
    chat: [pMessageSchema]

});
var PersonalChat = mongoose.model('PersonalChat', personalChatSchema);
module.exports = PersonalChat;