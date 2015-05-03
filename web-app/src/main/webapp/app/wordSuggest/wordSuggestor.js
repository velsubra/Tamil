( function() {
  'use strict';
  
  
 angular.module( 'tamilapp.services' )
.factory('lookup', ['$resource','serverPath', function($resource, serverPath) {
return $resource(serverPath.RESTLocation + 'lookup/words/', {maxcount:10},
    {
        'getSuggestion': {
					method:'PUT',
					headers:{'Content-Type':'text/plain;charset=UTF-8', 'Accept':'*/*'}	
				   }
    });
}]);
angular.module( 'tamilapp.services' )
  .factory( 'wordSuggestor', ['$http','$q','lookup', function( $http, $q,lookup  ) {
	// Return public API.
	return({
		getWordSuggestion: lookUpWordAsync
	});
	
	 // Any function returning a promise object can be used to load values asynchronously
	function lookUpWordAsync(searchWord) {	
	var deferred = $q.defer();
		lookup.getSuggestion(searchWord,function(result){
				deferred.resolve(result.results);			
	});
	return deferred.promise;
	
	/* setTimeout(function() {
    var results ={"results":["அன்பது","அன்பால்","அன்பின்","அன்பிற்கு","அன்பில்","அன்பு","அன்புகண்","அன்புடன்","அன்பை","அன்பொடு"]};
    deferred.resolve(results.results); 
	
  }, 5000); */
  
    }
  	
  }] );
  
} )( window.angular );