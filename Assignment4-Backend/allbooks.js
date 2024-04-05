const { MongoClient } = require('mongodb');

// MongoDB URL
const url = 'mongodb+srv://menuka:menuka@booksassignment4.yfn6mog.mongodb.net';

// Database Name
const dbName = 'books';

// Data to be inserted
const data = [
    {
        "bookID": 4,
        "bookName": "Watchmen",
        "isbn": "978-0930289232",
        "rating": 5,
        "author": "Alan Moore",
        "genre": "Superhero"
    },
    {
        "bookID": 5,
        "bookName": "The Dark Knight Returns",
        "isbn": "978-1563893421",
        "rating": 2,
        "author": "Frank Miller",
        "genre": "Superhero"
    },
    {
        "bookID": 6,
        "bookName": "Saga",
        "isbn": "978-1607066019",
        "rating": 9,
        "author": "Brian K. Vaughan",
        "genre": "Science Fiction"
    },
    {
        "bookID": 7,
        "bookName": "Sandman",
        "isbn": "978-1401238637",
        "rating": 9,
        "author": "Neil Gaiman",
        "genre": "Fantasy"
    },
    {
        "bookID": 8,
        "bookName": "Maus",
        "isbn": "978-0394747231",
        "rating": 6,
        "author": "Art Spiegelman",
        "genre": "Historical"
    },
    {
        "bookID": 9,
        "bookName": "Batman: Year One",
        "isbn": "978-1401207527",
        "rating": 2,
        "author": "Frank Miller",
        "genre": "Superhero"
    },
    {
        "bookID": 10,
        "bookName": "Preacher",
        "isbn": "978-1401240456",
        "rating": 9,
        "author": "Garth Ennis",
        "genre": "Horror"
    },
    {
        "bookName": "Y: The Last Man",
        "isbn": "978-1563899805",
        "rating": 7,
        "author": "Brian K. Vaughan",
        "genre": "Science Fiction"
    },
    {
        "bookName": "The Walking Dead",
        "isbn": "978-1582406725",
        "rating": 5,
        "author": "Robert Kirkman",
        "genre": "Horror"
    },
    {
        "bookName": "Hellboy",
        "isbn": "978-1593070946",
        "rating": 4,
        "author": "Mike Mignola",
        "genre": "Supernatural"
    }
];

// Function to insert data
async function insertData() {
    const client = new MongoClient(url, { useNewUrlParser: true, useUnifiedTopology: true });

    try {
        await client.connect();
        console.log('Connected to the database');

        const db = client.db(dbName);
        const result = await db.collection('books').insertMany(data);
        console.log(`${result.insertedCount} documents inserted`);
    } catch (err) {
        console.error('Error inserting documents:', err);
    } finally {
        await client.close();
        console.log('Connection closed');
    }
}

insertData();
