var mongodb = require('mongodb');
var ObjectID = mongodb.ObjectID;
var crypto = require('crypto');
var express = require('express');
var bodyParser = require('body-parser');
var User = require('./models/User');
var mongoose=require('mongoose');
mongoose.connect('mongodb://localhost:27017/firstmongojs');

var genRandomString = function (length) {
    return crypto.randomBytes(Math.ceil(length / 2))
    .toString('hex')
    .slice(0, length);
};


var sha512 = function (password, salt) {
    var hash = crypto.createHmac('sha512', salt);
    hash.update(password);
    var value = hash.digest('hex');
    return {
        salt: salt,
        passwordHash: value
    };
};

function saltHashPassword(userPassword) {
    var salt = genRandomString(16);
    var passwordData = sha512(userPassword, salt);
    return passwordData;
}

function chechHashPassword(userPassword, salt) {
    var passwordData = sha512(userPassword, salt);
    return passwordData;
}
//
var app = express(); 
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var MongoClient = mongodb.MongoClient;

var url = 'mongodb://localhost:27017'
MongoClient.connect(url, {useNewUrlParser: true }, function (err, client) {
    if (err)
        console.log('Unable to connect to the mongodb server.Error', err);
    else {
        app.post('/register',(request,response,next)=>{
            console.log("Request Generated");
            var post_data=request.body;
        
        var plaint_password=post_data.password;
            console.log("Password   "+ plaint_password);
        var hash_data=saltHashPassword(plaint_password);

        var password=hash_data.passwordHash;
        var salt=hash_data.salt;
        var name=post_data.name;
        var email=post_data.email;
        var username=post_data.username;
        var mobileno=post_data.mobileno;
        var designation=post_data.designation;
            
                
        console.log(name+" " +email);
        /*var insertJson={
            'email':email,
            'password':password,
            'salt':salt,
            'name':name
        };*/
            var insertJson = new User({
    Name: name,
    user_id: 'rks',
    Username: username,
    Email: email,
    Mobile: mobileno,
    Designation: designation,
    Password: password,
    'salt':salt
});
        var db=client.db('firstmongojs');

        db.collection('users')
        .find({'Email':email}).count(function(err,number){
            if(number!=0)
            {
                response.json('Email id already exists');
                console.log('Email id already exists');
            }
            else
            {
                /*db.collection('user')
                .insertOne(insertJson,function(error,res){
                    response.json('Registeration successful');
                    console.log('Registeration successful');
                })*/
                insertJson.save(function(err) {
                    if (err) throw err;
                    response.json('Registeration successful');
                    console.log('Registeration successful');
                })
            }
        })
    });

    app.post('/login',(request,response,next)=>{
        var post_data=request.body;
        
    var email=post_data.email;
    var userPassword=post_data.password;
    console.log(email+ " " + userPassword);

    var db=client.db('firstmongojs');

    db.collection('users')
    .find({'Email':email}).count(function(err,number){
        if(number==0)
        {
            response.json({'message':'Email id not exists'});
            console.log('Email id not exists');
        }
        else
        {
            db.collection('users')
            .findOne({'Email':email},function(err,user){
                var salt=user.salt;
                var hashed_password=chechHashPassword(userPassword,salt).passwordHash;
                var encrypted_password=user.Password;
               if(hashed_password===encrypted_password)
                {
                    response.json({'message':"Login Success"});
                    console.log('Login Success');
                }
                else
                {
                    response.json({'message':'Wrong Password'});
                    console.log('Wrong Password');
                }
            })
        }
    })
});

        app.listen(3000,()=>{
            console.log('Connected to mongodb server, webservice running at port 3000');
    })
    }
});


