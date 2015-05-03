( function() {
  'use strict';
  
angular.module( 'tamilapp.services' )
.factory('describer', ['$resource','serverPath', function($resource,serverPath) {
return $resource(serverPath.RESTLocation + 'lookup/words/describe/', null,
    {
        'update': {
					method:'PUT',
					headers:{'Content-Type':'text/plain;charset=UTF-8', 'Accept':'*/*'} 		
				   }
    });
}]);

  
angular.module( 'tamilapp.services' )
  .factory( 'tamilWordDescribe', ['$http','$q','$resource','describer', function( $http, $q,$resource,describer ) {
	// Return public API.
	return({		
		tamilWordDescribeAsync: tamilWordDescribeAsync
	});
		
	// Any function returning a promise object can be used to load values asynchronously
	function tamilWordDescribeAsync(wordToDescribe) {
	var deferred = $q.defer();
		describer.update(wordToDescribe,function(result){
				deferred.resolve(result.value);			
	});
	return deferred.promise;	
	// setTimeout(function() {
    // var results ={"words":[{"value":"சொல்","type":"பெயர்ச்சொல்","properties":{"property":[{"name":"peyarchchol","value":"true"},{"name":"uyarthinhai","value":"false"}],"name":null}},{"value":"சொல்","type":"தொழிற்பெயர்","properties":{"property":[{"name":"root","value":"சொல்"}],"name":null}},{"value":"சொல்","type":"வினையடி","properties":{"property":[{"name":"kaddalhai","value":"true"},{"name":"transitive","value":"false"}],"name":null}},{"value":"சொல்","type":"வினையடி","properties":{"property":[{"name":"kaddalhai","value":"true"},{"name":"transitive","value":"true"}],"name":null}}]};
    // deferred.resolve(results); 	
  // }, 3000);
  
    }
  	
  }] );
  
} )( window.angular );

