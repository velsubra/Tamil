( function() {
  'use strict';

 angular.module( 'tamilapp.services' )
.factory('spliter', ['$resource','serverPath', function($resource,serverPath) {
return $resource(serverPath.RESTLocation + 'punarchi/split/:wordToSplit/', null,
    {
        'getSplittedWords': {
					method:'PUT',
					isArray: true,
					headers:{'Content-Type':'text/plain;charset=UTF-8', 'Accept':'*/*'},
					transformRequest: function (data, getHeaders) {
					var headers = getHeaders();
                    headers[ "X-Requested-With" ] = "XMLHttpRequest";					
                    return data.name;
                }					
				   }
    });
}]);


angular.module( 'tamilapp.services' )
  .factory( 'tamilWordSplit', ['$http','$q', 'spliter', function( $http, $q, spliter  ) {
	// Return public API.
	return({		
		tamilWordSplitAsync: tamilWordSplitAsync
	});
		
	// Any function returning a promise object can be used to load values asynchronously
	function tamilWordSplitAsync(wordToSplit, selectedRule) {
	var deferred = $q.defer();	
	spliter.getSplittedWords({wordToSplit : wordToSplit}, selectedRule,function(result){
				deferred.resolve(result);			
	});
	return deferred.promise;	
	
	/* setTimeout(function() {
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
  return deferred.promise; */
    }
  	
  }] );
  
} )( window.angular );