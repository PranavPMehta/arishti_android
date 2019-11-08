var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/encrypt_db');


var db=mongoose.connection;
db.on('error', console.log.bind(console, "connection error"));
db.once('open', function(callback){
    console.log("connection succeeded");
})


var User = require('./models/User');
var PersonalChat = require('./models/personal_chat');
var GroupChat = require('./models/group_messages');
var GroupInformation = require('./models/group_information');
var MeetingGroup = require('./models/meeting_group');

var rohit = new User({
    Name: 'Rohit',
    user_id: 'rks',
    Username: 'rksshaw',
    Email: 'a@a.com',
    Mobile: '9205831383',
    Designation: 'Student',
    Password: 'password'
});

var pchat = new PersonalChat({
    user_1: 'rks',
    user_2: 'skr',
    chat: [{id: '1',
        timestamp: '123',
        user_id: 'rks1',
        status: 'success',
        location: 'Pune',
        reference_id: 'ttt',
        data: 'Hello'},
        {id: '1',
        timestamp: '123',
        user_id: 'rks1',
        status: 'success',
        location: 'Pune',
        reference_id: 'ttt',
        data: 'Hello1'}]
});

var gchat = new GroupChat({
    group_id: 'g1',
    gmessages: [
        {   id: '1',
            timestamp: '123',
            user_id: 'rks1',
            status: 'success',
            location: 'Pune',
            reference_id: 'ttt',
            data: 'Hello',
            viewed_user_id: ['Rohit']
        }
    ]
});

var ginfo = new GroupInformation({
    group_id: '1',
    display_picture :{url:'dp1'},
    description : 'Group1',
    members: [
        {user_id:'rks',username:'rrr'},
        {user_id:'skr',username: 'sss'}],
    admin: 'rohit',
    creation_timestamp: 'today',
    creator: 'rohit'
});

var meet1 = new MeetingGroup({
    presentation_id: 'arishti',
    display_picture :{url:'dp1'},
    description : 'Meetup',
    agenda : 'Integration',
    members: [
        {user_id:'rks',username:'rrr'},
        {user_id:'skr',username: 'sss'}],
    organizer : 'kanak',
    presentor: 'shushmita',
    creation_timestamp: 'today',
    chat: [{id: '1',
        timestamp: '123',
        user_id: 'rks1',
        status: 'success',
        location: 'Pune',
        reference_id: 'ttt',
        data: 'Hello'}]
})

rohit.save(function(err) {
    if (err) throw err;
    console.log('Meeting Info saved successfully!');
})