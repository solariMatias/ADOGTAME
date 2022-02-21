//Get the button:
mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() { scrollFunction() };

function scrollFunction() {
	if (document.body.scrollTop > 10 || document.documentElement.scrollTop > 10) {
		mybutton.style.display = "block";
	} else {
		mybutton.style.display = "none";
	}
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
	document.body.scrollTop = 0; // For Safari
	document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

	
//Preview image when uploading on input
imgInp.onchange = evt => {
	var imgToChange = document.getElementById("myImg1")
	if(!imgToChange){
		imgToChange = document.getElementById("myImg2")
	}
  	const [file] = imgInp.files
	if (file) {
		 imgToChange.src = URL.createObjectURL(file)
		 }
}

