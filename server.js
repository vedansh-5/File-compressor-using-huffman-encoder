const express = require('express');
const multer = require('multer');
const fs = require('fs');
const path = require('path');
const { exec } = require('child_process'); // Import exec to run system commands

const app = express();
const upload = multer({ dest: 'Uploads/' });

// Serve static files (index.html, script.js, etc.)
app.use(express.static('public'));

// Endpoint to handle file upload and compression
app.post('/upload', upload.single('file'), (req, res) => {
    if (!req.file) {
        return res.json({ success: false });
    }

    const filePath = path.join(__dirname, req.file.path);

    // Read the file and pass the contents to Java HuffmanEncoder
    fs.readFile(filePath, 'utf8', (err, data) => {
        if (err) {
            return res.json({ success: false });
        }

        // Save the text content into a temp file for Huffman encoding
        fs.writeFileSync('Uploads/input.txt', data);

        // Run the Java HuffmanCoder program
        exec('javac HuffmanCoder.java && java HuffmanCoder input.txt', (err, stdout, stderr) => {
            if (err || stderr) {
                console.error(`Error: ${stderr || err}`);
                return res.json({ success: false });
            }

            console.log(stdout); // You can log the output of the Huffman Java program

            // Assuming the Java program writes the encoded file as 'encoded.txt'
            res.json({ success: true });
        });
    });
});

// Endpoint to serve the encoded file
app.get('/download/encoded.txt', (req, res) => {
    const filePath = path.join(__dirname, 'Uploads', 'encoded.txt');
    res.download(filePath);
});

// Endpoint to serve the decoded file
app.get('/download/decoded.txt', (req, res) => {
    const filePath = path.join(__dirname, 'Uploads', 'decoded.txt');
    res.download(filePath);
});

// Start the server
app.listen(3000, () => {
    console.log('Server running on http://localhost:3000');
});
