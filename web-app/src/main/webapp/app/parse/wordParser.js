( function() {
  'use strict';  
  
 angular.module( 'tamilapp.services' )
.factory('wordParse', ['$resource','serverPath', function($resource, serverPath) {
return $resource(serverPath.RESTLocation + 'punarchi/parse/', {maxpathscount:10},
    {
        'getParsedWords': {
					method:'PUT',
					isArray: true,
					headers:{'Content-Type':'text/plain;charset=UTF-8', 'Accept':'*/*'}	
				   }
    });
}]);
angular.module( 'tamilapp.services' )
  .factory( 'wordParser', ['$http','$q','wordParse', function( $http, $q,wordParse  ) {
	// Return public API.
	return({
		getParsedWords: getParsedWordsAsync
	});
	
	 // Any function returning a promise object can be used to load values asynchronously
	function getParsedWordsAsync(searchWord) {	
	var deferred = $q.defer();
		wordParse.getParsedWords(searchWord,function(result){
		var splitResults = [];		
		angular.forEach(result, function(value) {
			angular.forEach(value.splitLists, function(splitList) {
				splitResults.push(splitList.splitList)	
			});
		});		
		deferred.resolve(splitResults);			
	});
	return deferred.promise;  
    }
  	
  }] );
  
} )( window.angular );