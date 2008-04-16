var dPackage = {
    
    /** nice eh */
    myMethod: function(aValue, bValue) {
        var x = aValue * 2; // some stuff
        return x + bValue;
    },
    
    // or not?
    anotherMethod: function() {
		var message = xPackage.myMethod(12, 11.3);
		alert(message);
    },
    
    dudelidu: function(message) {
    	var mes = message + " space text ";
    }
}
