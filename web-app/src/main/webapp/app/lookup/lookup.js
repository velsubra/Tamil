( function() {
  'use strict';
  
angular.module( 'tamilapp.services' )
  .factory( 'lookup', ['$http','$q', function( $http, $q  ) {
	// Return public API.
	return({
		lookUpWord: lookUpWord,
		lookUpWordAsync: lookUpWordAsync
	});
	
	function lookUpWord(searchWord) {
	var results ={"results":["அன்பது","அன்பால்","அன்பின்","அன்பிற்கு","அன்பில்","அன்பு","அன்புகண்","அன்புடன்","அன்பை","அன்பொடு"]};
    return  results;
	}
	 // Any function returning a promise object can be used to load values asynchronously
	function lookUpWordAsync(searchWord) {
	var deferred = $q.defer();
	setTimeout(function() {
    var results ={"results":["அன்பது","அன்பால்","அன்பின்","அன்பிற்கு","அன்பில்","அன்பு","அன்புகண்","அன்புடன்","அன்பை","அன்பொடு"]};
    deferred.resolve(results.results); 
	
  }, 5000);
  return deferred.promise;
    }
  	
  }] );
  
} )( window.angular );