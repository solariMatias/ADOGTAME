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
	document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}


//Preview image when uploading on input
imgInp.onchange = evt => {
	var imgToChange = document.getElementById("dogImg")
	if (!imgToChange) {
		imgToChange = document.getElementById("defaultImg")
	}
	const [file] = imgInp.files
	if (file) {
		imgToChange.src = URL.createObjectURL(file)
	}
}


// dissappear navbar on scroll
$(document).ready(function() {
	$(".menu-icon").on("click", function() {
		$("nav ul").toggleClass("showing");
	});
});

$(window).on("scroll", function() {
	if ($(window).scrollTop()>150) {
		$('nav').addClass('black');
	}

	else {
		$('nav').removeClass('black');
	}
})
//----------------