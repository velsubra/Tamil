( function() {

  'use strict';
  angular.module( 'tamilapp.controllers' ).controller( 'tamilWordDescribeController', 
  function( $scope, tamilWordDescribe, wordTypes ) {
	$scope.wordTypes = wordTypes; //Is this a better way?
    $scope.describeResults = []; 
	$scope.wordToDescribe ='';
	
	$scope.isPropertyBoolean = function(propertyToCheck) {
	if( propertyToCheck.value == "true" ||propertyToCheck.value == "false") {
		return true;
	}
	return false;
	}
	
	$scope.getPropertyValue = function(propertyToCheck) {
		if( propertyToCheck.value == "true") {
			return true;
		}
		if( propertyToCheck.value == "false") {
			return false;
		}	
		return propertyToCheck.value;
	}	
	
	$scope.describeWord = function() {
		var deferred = tamilWordDescribe.tamilWordDescribeAsync($scope.wordToDescribe);
		deferred.then(function(data) {	
		$scope.describeResults = data;
	});
	};
  } );

} )( window.angular );