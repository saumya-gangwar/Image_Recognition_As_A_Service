const express = require('express'); //"^4.13.4"
const aws = require('aws-sdk'); //"^2.2.41"
const bodyParser = require('body-parser');
const multer = require('multer'); // "^1.3.0"
const multerS3 = require('multer-s3'); //"^2.7.0"
const BUCKET_NAME = 'cse546ccinputbucket';;
const queueURL = "https://sqs.us-east-1.amazonaws.com/918740485184/OutputResponseQueue";
const { Consumer } = require('sqs-consumer');

aws.config.update({
    secretAccessKey: '',
    accessKeyId: '',
    region: 'us-east-1'
});

const app = express();
const s3 = new aws.S3();
app.use(bodyParser.json());
const upload = multer({
    storage: multerS3({
        s3: s3,
        ACL: 'public-read',
        bucket: BUCKET_NAME,
        key: function (req, file, cb) {
            console.log(file);
            cb(null, file.originalname); //use Date.now() for unique file keys
        }
    })
});

//open http://localhost:3000/ in browser to see upload form
app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});
var result = []
//used by upload form
app.post('/upload', upload.array('upl',500), (req, res, next) => {
    res.sendFile(__dirname + '/upload.html');
    const app = Consumer.create({
        queueUrl: queueURL,
        handleMessage: async (message) => {
            console.log(message);
            var str  = message.Body.split('/');
            result.push({id : str[0], value:str[1]});
        },
        sqs: new aws.SQS()
      });
      
      app.on('error', (err) => {
        console.error(err.message);
      });
      
      app.on('processing_error', (err) => {
        console.error(err.message);
      });
      app.start();
});
app.post('/res', upload.array('upl',500), (req, res, next) => {
    // res.write("Total number of response received - "+ result.length);
    res.send(result)
    console.log("Total number of response received - "+ result.length);
    result = [];
});
//used by uploaded page to get back to localhost to upload more images.
app.post('/', upload.array('upl',500), (req, res, next) => {
    res.redirect(301,"/");
});

app.listen(3000, () => {
    console.log('Example app listening on port 3000!');
});
