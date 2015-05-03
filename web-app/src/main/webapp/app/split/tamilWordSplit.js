( function() {
  'use strict';

 angular.module( 'tamilapp.services' )
.factory('spliter', ['$resource','serverPath', function($resource,serverPath) {
return $resource(serverPath.RESTLocation + 'lookup/words/describe/', null,
    {
        'update': {
					method:'PUT',
					headers:{'Content-Type':'text/plain;charset=UTF-8', 'Accept':'*/*'} 		
				   }
    });
}]);


angular.module( 'tamilapp.services' )
  .factory( 'tamilWordSplit', ['$http','$q', function( $http, $q  ) {
	// Return public API.
	return({		
		tamilWordSplitAsync: tamilWordSplitAsync
	});
		
	// Any function returning a promise object can be used to load values asynchronously
	function tamilWordSplitAsync(splitIntoWords) {
	var deferred = $q.defer();
	setTimeout(function() {
    var results =[
		{  "handlerName":"உயிர்வரின் உக்குறள் மெய்விட்டோடும்  ","handlerDescription":"உயிர்வரின் உக்குறள் 	  மெய்விட்டோடும்  ",
		   "splitLists":[
				{"splitList":["இளந்து","என்றல்"],"mapContext":null},
				{"splitList":["இளந்தென்று","அல்"],"mapContext":null}
			 ]
		},
		{
			"handlerName":"நன்னூல்விதி-219(மவ்வீறு)","handlerDescription":"நன்னூல்விதி-219(மவ்வீறு)",
			"splitLists":[
				{"splitList":["இளம்","தென்றல்"],"mapContext":null}
			 ]
		}];
    deferred.resolve(results); 	
  }, 3000);
  return deferred.promise;
    }
  	
  }] );
  
} )( window.angular );