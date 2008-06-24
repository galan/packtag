var charset = {
    
    // This file has to be saved in UTF-8
    test: function() {
    	var testElement = document.getElementById("testElement");
		testElement.innerHTML = "Text with some special characters: áàß€äöüÄÖÜ";
    }

}
