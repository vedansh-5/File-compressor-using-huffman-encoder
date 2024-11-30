document.addEventListener("DOMContentLoaded", function() {
  // This ensures that the DOM is fully loaded before attaching event listeners

  // Upload file button event listener
  const uploadButton = document.querySelector("button");
  if (uploadButton) {
      uploadButton.addEventListener("click", uploadFile);
  }
});

// Define the uploadFile function
function uploadFile() {
  const fileInput = document.getElementById("upload-file");
  const file = fileInput.files[0];

  if (file) {
      const formData = new FormData();
      formData.append("file", file);

      // Add loading bar for file upload
      showLoadingBar('upload');

      fetch('/upload', {
          method: 'POST',
          body: formData,
      })
      .then(response => response.text())
      .then(data => {
          // Handle server response
          console.log(data);
          showLoadingBar('done'); // Hide loading bar when done
      })
      .catch(error => {
          console.error("Error uploading file:", error);
          showLoadingBar('done');
      });
  }
}

function showLoadingBar(action) {
  const progressBar = document.getElementById("loading-bar");
  if (action === 'upload') {
      progressBar.style.width = "50%"; // Simulating loading
  } else if (action === 'done') {
      progressBar.style.width = "100%";
  }
}
