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
	$scope.hasResults  =  function() {
	console.log(angular.isDefined($scope.describeResults && $scope.describeResults.word));
	return angular.isDefined(
	$scope.describeResults && $scope.describeResults.words
	&& $scope.describeResults.words.length > 0
	);
	}
	$scope.describeWord = function() {
		$scope.isLoading = true;
		$scope.describeResults=[];
		var deferred = tamilWordDescribe.tamilWordDescribeAsync($scope.wordToDescribe);
		deferred.then(function(data) {	
		$scope.isLoading = false;
		$scope.describeResults = data;
	});
	};
  } );

} )( window.angular );