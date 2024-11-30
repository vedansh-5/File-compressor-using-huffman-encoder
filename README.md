# Huffman File Compression Web Application

## Overview
This is a web application that implements Huffman file compression. Users can upload a text file, compress it using Huffman encoding, and download the compressed file. They can also decompress the file and download the decoded content.

## Technologies Used
- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Node.js, Express.js, Java (Huffman Encoding)
- **File Handling**: Java File API

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/huffman-encoder.git
    ```
2. Install the necessary dependencies:
    ```bash
    cd huffman-encoder
    npm install
    ```
3. Compile the Java Huffman Encoder:
    ```bash
    javac HuffmanCoder.java
    ```
4. Start the server:
    ```bash
    node server.js
    ```
5. Open the app in your browser: [http://localhost:3000](http://localhost:3000)

## Usage
- Upload a `.txt` file for compression.
- Click "Upload and Compress" to compress the file.
- Download the compressed file by clicking the "Download Encoded File" button.
- Upload the encoded file to decompress it and download the decoded version.

## Folder Structure
