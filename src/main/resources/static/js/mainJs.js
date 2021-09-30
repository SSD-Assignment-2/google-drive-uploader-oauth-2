var  totalValOfUploadedFiles, noOfSelectedFiles, uploadedFiles, fileCount ; // initialize the variables

// function for called when user select the files
function fileIsSelect(e) {
	files = e.target.files; // object of FileList
	var output = [];
	fileCount = files.length;
	noOfSelectedFiles = 0;
	for (var i = 0; i < fileCount; i++) {
		var file = files[i];
		output.push(file.name, ' (', file.size, ' bytes, ', file.lastModifiedDate
				.toLocaleDateString(), ')');
		output.push('<br/>');
		noOfSelectedFiles += file.size;
	}
	document.getElementById('chosenFiles').innerHTML = output.join('');

}

//function for update the progress bar
function updateProgressBar(e) {
	if (e.lengthComputable) {
		var completedPercentage = parseInt((e.loaded + totalValOfUploadedFiles) * 100 / noOfSelectedFiles);
		var progressBar = document.getElementById('prBar');
		progressBar.style.width = completedPercentage + '%';
		progressBar.innerHTML = completedPercentage + ' % Completed';
	} else {
		console.err("Incapable to figure length");
	}
}

//Function for when file upload is completed
function uploadIsCompleted(e) {
	totalValOfUploadedFiles += document.getElementById('file').files[uploadedFiles].size;
	uploadedFiles++;
	if (uploadedFiles < fileCount) {
		uploadNextFile();
	} else {
		var progressBar = document.getElementById('prBar');
		progressBar.style.width = '100%';
		progressBar.innerHTML = '100% complete';
		swal("Success!", "file(s) uploading is successfully finished", "success");
	}
}

// function for get the next file and send to the server
function uploadNextFile() {
	var xmlHttpReq = new XMLHttpRequest();
	var formData = new FormData();
	var file = document.getElementById('file').files[uploadedFiles];
	formData.append("multipartFile", file);
	xmlHttpReq.upload.addEventListener("progress", updateProgressBar, false);
	xmlHttpReq.addEventListener("load", uploadIsCompleted, false);
	xmlHttpReq.addEventListener("error", uploadIsFailed, false);
	xmlHttpReq.open("POST", "/upload");
	xmlHttpReq.send(formData);
}

//function for detect errors
function uploadIsFailed(e) {
	swal("Error!", "Error occurs in uploading file(s)", "danger");
}

// function for start the uploading process
function startUpload() {
	if (document.getElementById('file').files.length <= 0) {
		swal("Cannot Upload the File(s)!", "Please select file(s) to upload", "warning");
	} else {
		totalValOfUploadedFiles = uploadedFiles = 0;
		uploadNextFile();
	}
}

// reset the page
function resetScreen() {
	document.getElementById('prBar').style.width = '0%';
	document.getElementById('prBar').innerText = '';
	document.getElementById("chosenFiles").innerHTML = '';
	document.getElementById("imgForm").reset();
}

// button clicks event listners
window.onload = function() {
	document.getElementById('file').addEventListener('change', fileIsSelect, false);
	document.getElementById('uploadBtn').addEventListener('click', startUpload, false);
	document.getElementById('resetBtn').addEventListener('click', resetScreen, false);
}
